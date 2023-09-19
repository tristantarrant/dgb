package it.redhat.dgb.rest;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import org.infinispan.client.hotrod.RemoteCache;

import io.quarkus.infinispan.client.Remote;
import it.redhat.dgb.model.SftRec;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sftrec")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SftRecResource {
   
    @Inject
    @Remote("consob_sftrec") 
    RemoteCache<String, SftRec> sftrec_cache;

    @GET
    public String info() {
        return "CDLC - EndPoint REST for sftrec cache"; 
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(SftRec rec) {
        // genereting id of the object
        String id = UUID.randomUUID().toString();
        sftrec_cache.put(id, rec);
        return id;
    }

    @GET
    @Path("/{id}")
    public CompletionStage<SftRec> getEntry(String id) {
        return sftrec_cache.getAsync(id); 
    }

}