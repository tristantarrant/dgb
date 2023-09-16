package it.redhat.dgb.rest;

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

    @GET
    public String info() {
        return "CDLC - REST EndPoint to manage benchmark data in the cache"; 
    }

    @POST
    @Path("load")
    public String autoLoad(BenchmarkLoaderConfiguration data) {

        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> loadEntries(data));
        return "loading data entries in the SFTREC Cache. Check logs for results";
    }

    private void loadEntries(BenchmarkLoaderConfiguration data){
        for(int i=0; i<data.days; i++){
            for(int j=0; j<data.dailyEntries; j++){
                String id = UUID.randomUUID().toString();
                SftRec recEntry = SftRecBuilder.build(data, i);
                sftrec_cache.put(id, recEntry);
                LOGGER.info("inserting new entry with ID: " + id);
            }
        }
    }

    @GET
    @Path("size")
    public String getCacheSize(BenchmarkLoaderConfiguration data) {
        LOGGER.info("requested cache size calculation");
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> calculateSize(data));
        return "calculating entries SIZE. Check logs for results.";
    }

    private void calculateSize(BenchmarkLoaderConfiguration data){
        String id = new String();
        long idSize = 0;
        long cacheSize = 0;
        long entrySize = 0;
        SftRec entry = null;
        int counter = 0;
        for(int i=0; i<data.days; i++){
            for(int j=0; j<data.dailyEntries; j++){
                counter++;
                id = UUID.randomUUID().toString();
                idSize += ObjectSizeFetcher.getObjectSize(id);
                entry = SftRecBuilder.build(data, i);
                entrySize = ObjectSizeFetcher.getObjectSize(entry);
                cacheSize += entrySize;
            }
        }
        LOGGER.info("\n\n==============================================================\n\n");
        LOGGER.info("Number of days stored:         " + data.days + "  -  Number of entry for each day:    " + data.dailyEntries);
        LOGGER.info("Total CACHE size:              " + (idSize + cacheSize));
        LOGGER.info("Average KEY size:              " + (idSize / counter));
        LOGGER.info("Average OBJECT size:           " + (cacheSize / counter));

    }


    
}
