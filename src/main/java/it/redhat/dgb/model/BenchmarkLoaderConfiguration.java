package it.redhat.dgb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BenchmarkLoaderConfiguration {
    
    private static List<String> names = new ArrayList<>(Arrays.asList("nIpCnCHngs", "SjRCEUMVUA", "yCnRsmYJkU", "tnTwBfhlKS", "yUkSWlpgar",
            "nUlUqHIAhZ", "hcrtLuwqqg", "fIemKwqlKP", "FKVEdEVEoM", "ziSLidpllW", "obFEEdGcvE", "VpqRKcZmLK", "kmOCgJsRXe", "xCBehuaihp", "kCNSrZbFqF"));
    
    private long startDay;
    private int days;
    private int dailyEntries;
    private String sessionName;

    public BenchmarkLoaderConfiguration(){
        Random random = new Random();
        this.sessionName = names.get(random.nextInt(names.size())) + "_" + names.get(random.nextInt(names.size()));
    }

    public long getStartDay() {
        return startDay;
    }

    public BenchmarkLoaderConfiguration setStartDay(long startDay) {
        this.startDay = startDay;
        return this;
    }

    public int getDays() {
        return days;
    }

    public BenchmarkLoaderConfiguration setDays(int days) {
        this.days = days;
        return this;
    }

    public int getDailyEntries() {
        return dailyEntries;
    }

    public BenchmarkLoaderConfiguration setDailyEntries(int dailyEntries) {
        this.dailyEntries = dailyEntries;
        return this;
    }

    public String getSessionName() {
        return sessionName;
    }
    
}
