package test.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {
    private static int iter = 0;

    public static void main(String[] args) throws IOException {
        long time = System.nanoTime();

        String path = "src/main/resources/log4Configurator.properties";
        File file = new File(path);

        Properties prop = new Properties();
        logProperties(prop);
        prop.load(new FileReader(file));
        logProperties(prop);
        List<String> classNames = new ArrayList<>();


        for(Object propetry : prop.values()){
            List<String> temp = ClassScanner.getClassNames((String)propetry);
            classNames.addAll(temp);
        }


        int i = 0;
        for (String className : classNames) {
            i++;
            System.out.println("№" + i + ": " + className);
        }

        time = System.nanoTime() - time;
        System.out.printf("Elapsed %,9.3f ms\n", time/1_000_000.0);

    }

    private static void logProperties(Properties prop){
        System.out.println("----------------New Log №" + iter + "------------");
        iter++;
        for (String key : prop.stringPropertyNames())
        {
            System.out.println(key + ": " + prop.get(key));

        }
        System.out.println("----------------End Log---------------\n");
    }

}
