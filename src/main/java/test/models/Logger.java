package test.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Logger {

    /**
     * Logger name
     */
    private String name;

    /**
     * Logger level (usually takes values from a list:
     *                  TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF)
     */
    private String level;

    /**
     * Additivity is set to true by default
     */
    private Boolean additivity = true;

    /**
     * set of appenders attached to the logger
     */
    private Set<Appender> appenderSet;

    public Logger(String name) {
        this.name = name;
        appenderSet = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getAdditivity() {
        return additivity;
    }

    public void setAdditivity(Boolean additivity) {
        this.additivity = additivity;
    }

    public Set<Appender> getAppenderSet() {
        return appenderSet;
    }

    public void setAppenderSet(Set<Appender> appenderSet) {
        this.appenderSet = appenderSet;
    }

    /**
     * add appender to {@link Logger#appenderSet} of this {@code Logger}
     * (if appender is not a null)
     * @param appender attached appender
     */
    public void addAppender(Appender appender) {
        if(appender != null)
            appenderSet.add(appender);
    }
}
