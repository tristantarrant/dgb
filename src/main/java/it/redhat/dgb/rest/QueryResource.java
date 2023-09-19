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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("query")
public class QueryResource {

   @Inject
   @Remote("sftrec")
   RemoteCache<String, SftRec> sftrecCache;

   @GET
   @Produces("application/json")
   public List<SftRec> query() {
      Query<SftRec> query = Search.getQueryFactory(sftrecCache)
            .create("from it.redhat.dgb.SftRec s WHERE s.reporting_timestamp = 1681250400000");
      query.maxResults(100);

      List<SftRec> entries = query.execute().list();
      Log.info(entries);
      return entries;
   }

   @GET
   @Path("base")
   @Produces("application/json")
   public List<SftRec> base(@QueryParam("fromDate") long fromDate, @QueryParam("endDate") long endDate) {
      System.out.println("======= " + fromDate + " ======= " + endDate);
      Query<SftRec> query = Search.getQueryFactory(sftrecCache)
            .create("from it.redhat.dgb.SftRec s WHERE s.received_Report_Date >= :fromDate AND s.received_Report_Date <= :endDate");
      query.maxResults(100);
      query.setParameter("fromDate", fromDate);
      query.setParameter("endDate", endDate);

      List<SftRec> entries = query.execute().list();
      Log.info(entries);
      return entries;
   }

   @GET
   @Path("aggr")
   @Produces("application/json")
   public List<Object[]> aggregated(@QueryParam("fromDate") long fromDate, @QueryParam("endDate") long endDate) {
      Query<Object[]> query = Search.getQueryFactory(sftrecCache)
            .create("select count(*) from it.redhat.dgb.SftRec s WHERE s.received_Report_Date >= :fromDate AND s.received_Report_Date <= :endDate");
      query.maxResults(100);
      query.setParameter("fromDate", fromDate);
      query.setParameter("endDate", endDate);

      //List<SftRec> entries = query.execute().list();
      List<Object[]> reslt = query.execute().list();
      Log.info(reslt);
      return reslt;
   }

}
