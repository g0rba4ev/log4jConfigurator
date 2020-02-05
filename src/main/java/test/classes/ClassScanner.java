package test.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassScanner {

        public static List<String> getClassNames(String path) {
        List<String> classNames = new ArrayList<>();
        final File dir = new File(path);

        File[] dirFiles = dir.listFiles();

        for (final File entry : dirFiles) {
            //if entry is directory -> recursion
            if (entry.isDirectory()) {

                classNames.addAll(getClassNames(entry.getPath()));

            } else if (entry.getPath().endsWith(".jar")) {
                //if entry is a jar-file
                classNames.addAll(getClassNamesFromJar(entry.getPath()));

            }
        }


        return classNames;
    }

    /**
     * method fill classNames with the list of all Java classes contained inside a jar file at {@param pathToJar}
     * @param pathToJar path to the jar-file int the format "/yourPathToJar/yourJar.jar"
     * @return list of all Java classes contained inside a jar file
     */
    public static List<String> getClassNamesFromJar(String pathToJar){

        List<String> classNames = new ArrayList<>();
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(pathToJar))){

            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    // This ZipEntry represents a class. Convert path to this class to full class name:
                    String className = entry.getName().replace('/', '.'); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
            return classNames;

        } catch (IOException ioe) {
            // TODO add logger
            return classNames;
        }


    }
}