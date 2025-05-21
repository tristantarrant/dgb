package it.redhat.dgb.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.logging.Log;

public class CsvReport {
    private final List<String> csv;
    private final int size;

    public CsvReport(int size){
        this.csv = new ArrayList<>();
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
