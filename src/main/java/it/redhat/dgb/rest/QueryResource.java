package it.redhat.dgb.rest;

import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;
import it.redhat.dgb.model.SftRec;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("query")
public class QueryResource {

   @Inject
   @Remote("sftrec")
   RemoteCache<String, SftRec> booksCache;

   @GET
   @Produces("application/json")
   public List<SftRec> query(@PathParam("term") String term) {
      Query<SftRec> query = Search.getQueryFactory(booksCache)
            .create("from it.redhat.dgb.SftRec s where s.id_progressivo < :id_progressivo");
      query.maxResults(100);
      query.setParameter("id_progressivo", 10);

      List<SftRec> books = query.execute().list();
      Log.info(books);
      return books;
   }
}
