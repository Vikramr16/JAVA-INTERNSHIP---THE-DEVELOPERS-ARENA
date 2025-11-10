package com.example.multienv;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String message;
    private String activeMessage;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getActiveMessage() {
        return activeMessage;
    }
    public void setActiveMessage(String activeMessage) {
        this.activeMessage = activeMessage;
    }
}
