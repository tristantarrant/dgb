package it.redhat.dgb.model;

import java.time.Duration;

public class TimeFormatter {
    
    public static String print(long timeInMilliseconds){
        Duration duration = Duration.ofMillis(timeInMilliseconds);
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }
}
