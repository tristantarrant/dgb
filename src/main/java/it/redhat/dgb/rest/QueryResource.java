package it.redhat.dgb.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;
import it.redhat.dgb.model.SftRec;
import it.redhat.dgb.model.TimeFormatter;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("query")
public class QueryResource {

   @Inject
   @Remote("sftrec")
   RemoteCache<String, SftRec> sftrecCache;

   @GET
   @Produces("application/json")
   public List<SftRec> simple() {
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
            .create("select count(uTI) from it.redhat.dgb.SftRec s WHERE s.received_Report_Date >= :fromDate AND s.received_Report_Date <= :endDate");
      query.maxResults(100);
      query.setParameter("fromDate", fromDate);
      query.setParameter("endDate", endDate);

      //List<SftRec> entries = query.execute().list();
      List<Object[]> reslt = query.execute().list();
      Log.info(reslt);
      return reslt;
   }

   @GET
   @Path("{type}")
   @Produces("application/json")
   public List<SftRec> query(@PathParam("type") String type) {
      // Remote Query, using protobuf
      QueryFactory qf = Search.getQueryFactory(sftrecCache);
      Query<SftRec> query = qf.create(queryString(type));
      long startQuery = System.currentTimeMillis();
      List<SftRec> recs = query.execute().list();
      long endQuery = System.currentTimeMillis();

      Log.info("Aggregated Query type 1 time of execution: " + TimeFormatter.print(endQuery - startQuery));
      return recs;
   }

   private String queryString(String type){
      HashMap<String, String> queries = new HashMap<String, String>();
      queries.put("RPS", "SELECT report_status, COUNT(uTI) FROM it.redhat.dgb.SftRec GROUP BY report_status ORDER BY report_status");
      queries.put("MTS", "SELECT matching_status, COUNT(uTI) FROM it.redhat.dgb.SftRec GROUP BY matching_status ORDER BY matching_status");
      queries.put("LRS", "SELECT loan_reconciliatio_n_status, COUNT(uTI) FROM it.redhat.dgb.SftRec GROUP BY loan_reconciliatio_n_status ORDER BY loan_reconciliatio_n_status");
      queries.put("CRS", "SELECT collateral_reconciliation_status, COUNT(uTI) FROM it.redhat.dgb.SftRec GROUP BY collateral_reconciliation_status ORDER BY collateral_reconciliation_status");
      return queries.get(type);
   }

}
