import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {

    private int totalRequests = 0;
    private long totalTraffic = 0;

    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    private int googleBotCount = 0;
    private int yandexBotCount = 0;

    public void addEntry(LogEntry entry) {
        totalRequests++;
        totalTraffic += entry.getResponseSize();

        LocalDateTime time = entry.getDateTime();
        if (minTime == null || time.isBefore(minTime)) {
            minTime = time;
        }
        if (maxTime == null || time.isAfter(maxTime)) {
            maxTime = time;
        }

        String agentName = entry.getUserAgent().getBrowserType();

        if (agentName.equalsIgnoreCase("Googlebot")) {
            googleBotCount++;
        } else if (agentName.equalsIgnoreCase("YandexBot")) {
            yandexBotCount++;
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) {
            return 0.0;
        }
        long seconds = ChronoUnit.SECONDS.between(minTime, maxTime);

        if (seconds == 0) {
            return totalTraffic;
        }

        double hours = seconds / 3600.0;

        return (double) totalTraffic / hours;
    }

    public int getGoogleBotCount() {
        return googleBotCount;
    }

    public int getYandexBotCount() {
        return yandexBotCount;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public double getGooglebotRatio() {
        return totalRequests > 0 ? (double) googleBotCount / totalRequests : 0;
    }

    public double getYandexbotRatio() {
        return totalRequests > 0 ? (double) yandexBotCount / totalRequests : 0;
    }
}
