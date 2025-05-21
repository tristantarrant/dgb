package it.redhat.dgb.rest;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.infinispan.client.hotrod.RemoteCache;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;
import it.redhat.dgb.model.BenchmarkLoaderConfiguration;
import it.redhat.dgb.model.ObjectSizeFetcher;
import it.redhat.dgb.model.SftRec;
import it.redhat.dgb.model.SftRecBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/benchmark")
public class BenchmarkResource {
    private final static int BULK_SIZE = 100;
    private final static int daily_entries = 400000;

    @Inject
    @Remote("sftrec")
    RemoteCache<String, SftRec> sftrec_cache;

    private void log(String name, String msg){
        Log.info( "[" + name + "] " + msg);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String info() {
        return "CDLC - REST EndPoint to manage benchmark data in the cache";
    }

    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public BenchmarkLoaderConfiguration sample() {
        return new BenchmarkLoaderConfiguration();
    }

    @POST
    @Path("load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String autoLoad(BenchmarkLoaderConfiguration data) {
        CompletableFuture.runAsync(() -> loadEntries(data));
        return "loading data entries in the SFTREC Cache. Check logs for results";
    }

    @POST
    @Path("day/{day}")
    @Produces(MediaType.TEXT_PLAIN)
    public String loadOneDay(@PathParam("day") int day) {
        BenchmarkLoaderConfiguration configuration = new BenchmarkLoaderConfiguration()
                .setDays(1)
                .setDailyEntries(daily_entries)
                .setStartDay(1672527600000L - (1000 * 60 * 60 * 24) + (1000L * 60 * 60 * 24 * day));
        CompletableFuture.runAsync(() -> loadEntries(configuration));
        return "loading data entries in the SFTREC Cache. Check logs for results";
    }

    private void loadEntries(BenchmarkLoaderConfiguration data){
        String timeInHHMMSS;
        long dailyStartTime;
        long dailyEndTime;
        long partialStartTime;
        long partialEndTime;
        int globalCounter = 0;
        int bulkCounter = 0;
        int counter = 0;
        long globalStartTime = System.currentTimeMillis();
        partialStartTime = globalStartTime;
        HashMap<String, SftRec> bulk = new HashMap<>();
        for(int i=0; i<data.getDays(); i++){
            dailyStartTime = System.currentTimeMillis();
            for(int j=0; j<data.getDailyEntries(); j++){
                String id = UUID.randomUUID().toString();
                SftRec recEntry = SftRecBuilder.build(data, i);
                bulk.put(id, recEntry);
                counter++;
                bulkCounter++;
                globalCounter++;
                if(bulkCounter == BULK_SIZE){
                    sftrec_cache.putAll(bulk);
                    bulkCounter = 0;
                    bulk.clear();
                }
                if(counter == 10000){
                    partialEndTime = System.currentTimeMillis();
                    Duration duration = Duration.ofMillis(partialEndTime - partialStartTime);
                    partialStartTime = partialEndTime;
                    long HH = duration.toHours();
                    long MM = duration.toMinutesPart();
                    long SS = duration.toSecondsPart();
                    timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
                    counter = 0;
                    this.log(data.getSessionName(), "inserted " + globalCounter + " entries, partial time frame (10k): " + timeInHHMMSS);
                }
            }
            dailyEndTime = System.currentTimeMillis();
            Duration duration = Duration.ofMillis(dailyEndTime - dailyStartTime);
            long HH = duration.toHours();
            long MM = duration.toMinutesPart();
            long SS = duration.toSecondsPart();
            timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
            this.log(data.getSessionName(), "==============================================================");
            this.log(data.getSessionName(), "completed day " + i + " time frame for this day: " + timeInHHMMSS);
            this.log(data.getSessionName(), "==============================================================");
        }
        long globalEndTime = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(globalEndTime - globalStartTime);
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        this.log(data.getSessionName(), "==============================================================");
        this.log(data.getSessionName(), "inserted " + globalCounter + " entries, timeframe: " + timeInHHMMSS);
        this.log(data.getSessionName(), "==============================================================");
    }

    @POST
    @Path("size")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getCacheSize(BenchmarkLoaderConfiguration data) {
        CompletableFuture.runAsync(() -> calculateSize(data));
        return "calculating entries SIZE. Check logs for results.";
    }

    private void calculateSize(BenchmarkLoaderConfiguration data){
        String id;
        long idSize = 0;
        long cacheSize = 0;
        long entrySize;
        SftRec entry;
        int counter = 0;
        for(int i=0; i<data.getDays(); i++){
            for(int j=0; j<data.getDailyEntries(); j++){
                counter++;
                id = UUID.randomUUID().toString();
                idSize += ObjectSizeFetcher.getObjectSize(id);
                entry = SftRecBuilder.build(data, i);
                entrySize = ObjectSizeFetcher.getObjectSize(entry);
                cacheSize += entrySize;
            }
        }
        this.log(data.getSessionName(), "\n\n==============================================================\n\n");
        this.log(data.getSessionName(), "Number of days stored:         " + data.getDays() + "  -  Number of entry for each day:    " + data.getDailyEntries());
        this.log(data.getSessionName(), "Total CACHE size:              " + (idSize + cacheSize));
        this.log(data.getSessionName(), "Average KEY size:              " + (idSize / counter));
        this.log(data.getSessionName(), "Average OBJECT size:           " + (cacheSize / counter));
    }

}
