package test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassScanner {

    /**
     * method finds all Java classes contained inside a directory and returns a list of them
     * @param dir directory path to search for classes in it
     * @return list of all Java classes contained inside a directory
     */
    public static List<String> getClassNames(String dir) {

        List<String> classNames = new ArrayList<>();

        class LocalFileVisitor extends SimpleFileVisitor<Path> {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if(fileName.endsWith(".jar")){
                    classNames.addAll(ClassScanner.getClassNamesFromJar(file.toString()));
                } else if (fileName.endsWith(".class")) {
                    String className = file.toString()
                            .substring(dir.length()) // Since here is the full path to the file, delete the path to the directory in which we are looking for files from it
                            .replace(File.separatorChar, '.'); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
                return FileVisitResult.CONTINUE;
            }
        }


        try{
            Files.walkFileTree(Paths.get(dir), new LocalFileVisitor());
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