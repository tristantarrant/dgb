package it.redhat.dgb.rest;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.infinispan.client.hotrod.RemoteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.infinispan.client.Remote;
import it.redhat.dgb.model.BenchmarkLoaderConfiguration;
import it.redhat.dgb.model.ObjectSizeFetcher;
import it.redhat.dgb.model.SftRec;
import it.redhat.dgb.model.SftRecBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/benchmark")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BenchmarkResource {
    private static final Logger LOGGER = LoggerFactory.getLogger("cdlc");
   
    @Inject
    @Remote("sftrec") 
    RemoteCache<String, SftRec> sftrec_cache;

    private void log(String name, String msg){
        LOGGER.info( "[" + name + "] " + msg);
    }
    
    @GET
    public String info() {
        return "CDLC - REST EndPoint to manage benchmark data in the cache"; 
    }

    @POST
    @Path("load")
    public String autoLoad(BenchmarkLoaderConfiguration data) {
                System.out.println("========================= 0");

        CompletableFuture.runAsync(() -> loadEntries(data));
        return "loading data entries in the SFTREC Cache. Check logs for results";
    }

    private void loadEntries(BenchmarkLoaderConfiguration data){
        System.out.println("========================= 1");
        String timeInHHMMSS = null;
        long globalEndTime = 0;
        long dailyStartTime = 0;
        long dailyEndTime = 0;
        long partialStartTime = 0;
        long partialEndTime = 0;
        int globalCounter = 0;
        int counter = 0;
        long globalStartTime = System.currentTimeMillis();
        partialStartTime = globalStartTime;
        for(int i=0; i<data.getDays(); i++){
        System.out.println("========================= 2");
            dailyStartTime = System.currentTimeMillis();
            for(int j=0; j<data.getDailyEntries(); j++){
                        System.out.println("========================= 3");

                String id = UUID.randomUUID().toString();
                System.out.println("========================= !!!! 1");
                SftRec recEntry = SftRecBuilder.build(data, i);
                System.out.println("========================= !!!! 2");
                sftrec_cache.put(id, recEntry);
                        System.out.println("========================= 4");
                counter++;
                globalCounter++;
                if(counter > 100000){
                    partialEndTime = System.currentTimeMillis();
                    Duration duration = Duration.ofMillis(partialEndTime - partialStartTime);
                    partialStartTime = partialEndTime;
                    long HH = duration.toHours();
                    long MM = duration.toMinutesPart();
                    long SS = duration.toSecondsPart();
                    timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
                    counter = 0;
                    this.log(data.getSessionName(), "inserted " + globalCounter + " entries, partial time frame (100k): " + timeInHHMMSS);
                }
            }
            dailyEndTime = System.currentTimeMillis();
            Duration duration = Duration.ofMillis(dailyEndTime - dailyStartTime);
            dailyStartTime = dailyEndTime;
            long HH = duration.toHours();
            long MM = duration.toMinutesPart();
            long SS = duration.toSecondsPart();
            timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
            this.log(data.getSessionName(), "\n\n==============================================================\n\n");
            this.log(data.getSessionName(), "completed day " + i + " time frame for this day: " + timeInHHMMSS);
            this.log(data.getSessionName(), "==============================================================");
        }
        globalEndTime = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(globalEndTime - globalStartTime);
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        this.log(data.getSessionName(), "\n\n==============================================================\n\n");
        this.log(data.getSessionName(), "inserted " + globalCounter + " entries, timeframe: " + timeInHHMMSS);
        this.log(data.getSessionName(), "==============================================================");
    }

    @GET
    @Path("size")
    public String getCacheSize(BenchmarkLoaderConfiguration data) {
        CompletableFuture.runAsync(() -> calculateSize(data));
        return "calculating entries SIZE. Check logs for results.";
    }

    private void calculateSize(BenchmarkLoaderConfiguration data){
        String id = new String();
        long idSize = 0;
        long cacheSize = 0;
        long entrySize = 0;
        SftRec entry = null;
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
