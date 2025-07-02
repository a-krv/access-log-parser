import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {

    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String requestPath;
    private final int responseCode;
    private final long responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public enum HttpMethod {
        GET,
        POST,
        PATCH
    }

    public LogEntry(String line) {
        String logRegex = "(\\S+) \\S+ \\S+ \\[(.*?)\\] \"(\\S+) (.*?) .*?\" (\\d{3}) (\\d+) \"(.*?)\" \"(.*?)\"";
        Pattern pattern = Pattern.compile(logRegex);
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Строка лога не соответствует ожидаемому формату");
        }

        this.ipAddress = matcher.group(1);

        String dateTimeStr = matcher.group(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", java.util.Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeStr, formatter);
        this.dateTime = zonedDateTime.toLocalDateTime();

        String methodStr = matcher.group(3);
        this.method = parseMethod(methodStr);
        this.requestPath = matcher.group(4);
        this.responseCode = Integer.parseInt(matcher.group(5));
        this.responseSize = Long.parseLong(matcher.group(6));
        this.referer = matcher.group(7);
        String userAgentStr = matcher.group(8);

        this.userAgent = new UserAgent(userAgentStr);
    }

    private HttpMethod parseMethod(String str) {
        return HttpMethod.valueOf(str);

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", dateTime=" + dateTime +
                ", method=" + method +
                ", requestPath='" + requestPath + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgent=" + userAgent +
                '}';
    }
}