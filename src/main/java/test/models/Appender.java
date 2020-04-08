package test.models;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Appender {

    private String alias;
    private String appenderType; // e.g. "org.apache.log4j.DailyRollingFileAppender"
    private Map<String, String> propsMap = new HashMap<>();

    public Appender(String alias, String appenderType) {
        this.alias = alias;
        this.appenderType = appenderType;
    }

    public Appender(Appender appender) {
        this.alias = appender.alias;
        this.appenderType = appender.appenderType;
        this.propsMap.putAll( appender.propsMap );
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

    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode appenderNode = mapper.createObjectNode();
        ObjectNode appenderPropsNode = mapper.createObjectNode();

        appenderNode.put("Alias", this.getAlias());
        appenderNode.put("Appender", this.getAppenderType());
        appenderNode.put("appenderProps", appenderPropsNode);

        Map<String, String> appenderPropsMap = this.getPropsMap();
        for(String key : this.getPropsMap().keySet()){
            appenderPropsNode.put(key, appenderPropsMap.get(key));
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString( appenderNode );
        } catch (IOException ioe) {
            // TODO handle this exception
            return "";
        }
    }

}
