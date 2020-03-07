package test.models;

import java.util.HashMap;
import java.util.Map;

public class Appender {
    private String name;
    private String appenderType; // e.g. org.apache.log4j.DailyRollingFileAppender
    private Map<String, String> propsMap = new HashMap<>();

    public Appender(String name, String appenderType) {
        this.name = name;
        this.appenderType = appenderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppenderType() {
        return appenderType;
    }

    public void setAppenderType(String appenderType) {
        this.appenderType = appenderType;
    }

    public Map<String, String> getPropsMap() {
        return propsMap;
    }

    public void setPropsMap(Map<String, String> propsMap) {
        this.propsMap = propsMap;
    }

    public void addProperty(String key, String value){
        propsMap.put(key, value);
    }
}
