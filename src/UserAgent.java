public class UserAgent {
    private final String osType;
    private final String browserType;

    public UserAgent(String userAgentStr) {
        this.osType = detectOs(userAgentStr);
        this.browserType = detectBrowser(userAgentStr);
    }

    private String detectOs(String userAgent) {
        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("windows")) {
            return "Windows";
        } else if (userAgent.contains("mac os") || userAgent.contains("macintosh")) {
            return "macOS";
        } else if (userAgent.contains("linux")) {
            return "Linux";
        } else {
            return "Other";
        }
    }

    private String detectBrowser(String userAgent) {
        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("edge") || userAgent.contains("edg/")) {
            return "Edge";
        } else if (userAgent.contains("googlebot")) {
            return "Googlebot";
        } else if (userAgent.contains("yandexbot")) {
            return "YandexBot";
        } else if (userAgent.contains("opr") || userAgent.contains("opera")) {
            return "Opera";
        } else if (userAgent.contains("chrome")) {
            return "Chrome";
        } else if (userAgent.contains("firefox")) {
            return "Firefox";
        } else {
            return "Other";
        }
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowserType() {
        return browserType;
    }

    @Override
    public String toString() {
        return "UserAgent{" + "osType='" + osType + '\'' + ", browserType='" + browserType + '\'' + '}';
    }

}
