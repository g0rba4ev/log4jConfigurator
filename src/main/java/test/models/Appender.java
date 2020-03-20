package test.models;

import java.util.HashMap;
import java.util.Map;

public class Appender {

    private String alias;
    private String appenderType; // e.g. org.apache.log4j.DailyRollingFileAppender
    private Map<String, String> propsMap = new HashMap<>();

    public Appender(String alias, String appenderType) {
        this.alias = alias;
        this.appenderType = appenderType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
