package test.classes;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Test {

    public static void main(String[] args) throws IOException {


        ObjectMapper mapper = new ObjectMapper();

        ObjectNode loggerNode = mapper.createObjectNode();
        loggerNode.put("loggerName", "my.logger");
        loggerNode.put("loggerLevel", "DEBUG");

        ArrayNode appenders = mapper.createArrayNode();
        appenders.add("app1");
        appenders.add("app2");
        loggerNode.put("appender", appenders);

        String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(loggerNode);
        System.out.println(s);
    }

}
