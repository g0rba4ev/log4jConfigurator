package test.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassScanner {

    public static List<String> getClassNames(String path) {

        List<String> classNames = new ArrayList<>();

        class LocalFileVisitor extends SimpleFileVisitor<Path> {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                classNames.addAll(ClassScanner.getClassNamesFromJar(file.toString()));
                return FileVisitResult.CONTINUE;
            }
        }


        try{
            Files.walkFileTree(Paths.get(path), new LocalFileVisitor());
        } catch (IOException ioe) {
            // TODO add logger
        }

        return classNames;
    }

    /**
     * method finds all Java classes contained inside a jar-file at {@param pathToJar} and returns a list of them
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