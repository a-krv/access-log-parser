import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {

    private int totalRequests = 0;
    private long totalTraffic = 0;

    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    private int googleBotCount = 0;
    private int yandexBotCount = 0;

    private Set<String> pages = new HashSet<>();
    private Set<String> notExistingPages = new HashSet<>();

    private Map<String, Integer> osCounts = new HashMap<>();
    private Map<String, Integer> browserCounts = new HashMap<>();


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

        if (entry.getResponseCode() == 200) {
            pages.add((entry.getRequestPath()));
        } else if (entry.getResponseCode() == 404) {
            notExistingPages.add(entry.getRequestPath());
        }

        String osType = entry.getUserAgent().getOsType();
        osCounts.put(osType, osCounts.getOrDefault(osType, 0) + 1);


        String browserType = entry.getUserAgent().getBrowserType();
        browserCounts.put(browserType, browserCounts.getOrDefault(browserType, 0) + 1);


        if (browserType.equalsIgnoreCase("Googlebot")) {
            googleBotCount++;
        } else if (browserType.equalsIgnoreCase("YandexBot")) {
            yandexBotCount++;
        }
    }


    public Set<String> getAllPages() {
        return new HashSet<>(pages);
    }

    public Set<String> getAllNotExistingPages() {
        return new HashSet<>(notExistingPages);
    }

    public Map<String, Double> getOsStatistics() {
        Map<String, Double> osMap = new HashMap<>();
        int totalOsCount = osCounts.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : osCounts.entrySet()) {
            double osPercent = (double) entry.getValue() / totalOsCount;
            osMap.put(entry.getKey(), osPercent);
        }
        return osMap;
    }

    public Map<String, Double> getBrowserStatistics() {
        Map<String, Double> browserMap = new HashMap<>();
        int totalBrowserCount = browserCounts.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : browserCounts.entrySet()) {
            double browserPercent = (double) entry.getValue() / totalBrowserCount;
            browserMap.put(entry.getKey(), browserPercent);
        }
        return browserMap;
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

    public double getGoogleBotRatio() {
        return totalRequests > 0 ? (double) googleBotCount / totalRequests : 0;
    }

    public double getYandexBotRatio() {
        return totalRequests > 0 ? (double) yandexBotCount / totalRequests : 0;
    }
}