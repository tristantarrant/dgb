package it.redhat.dgb.model;

public class Report {
    public String description;
    public long duration;
    public int query_size;
    public int cache_size;

    @Override
    public String toString() {
        return "Report{" +
                "description='" + description + '\'' +
                ", duration=" + duration +
                ", query_size=" + query_size +
                ", cache_size=" + cache_size +
                '}';
    }
}
