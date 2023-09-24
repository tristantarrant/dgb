package it.redhat.dgb.model;

import io.quarkus.logging.Log;

import java.util.ArrayList;
import java.util.List;

public class CsvReport {
    private List<String> csv;
    private int size;

    public CsvReport(int size){
        this.csv = new ArrayList<String>();
        this.size = size;
    }

    public void add(String row){
        this.csv.add(row);
        if (csv.size() == size){
            StringBuilder buffer = new StringBuilder("\n");
            for (String record: csv) {
                buffer.append(record).append("\n");
            }
            Log.info(buffer.toString());
        }
    }
}
