package com.javarush.island.aleinik.utils;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassScanner {

    public static  List<Class<?>> getClasses(String packageName) {
        //TODO: remove IOException from the method signature
        //TODO work with exceptions !!! Create your own OR die!

        ClassLoader classLoader  = Thread.currentThread().getContextClassLoader();
        List<File> directories  = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();
        if(classLoader != null){

            String path = packageName.replace('.', '/');

            try {
                Enumeration<URL> resources  = classLoader.getResources(path);
                while(resources.hasMoreElements()){
                    URL resource = resources.nextElement();
                    if(resource.getProtocol().equals("file")){
                        File file = new File(resource.toURI());
                        directories.add(file);
                    }


                }

                for (File directory : directories) {
                    classes.addAll(findClasses(directory, packageName));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }


        }
        return classes;
    }

    public static List<Class<?>> findClasses(File directory, String packageName){
        List<Class<?>> classes = new ArrayList<>();
        if(!directory.exists()){
            return classes;
        }
        File[] files = directory.listFiles();

        if(files != null){
            for (File file : files) {
                if(file.isDirectory()){
                    String newPackageName = packageName + "." + file.getName();
                    classes.addAll(findClasses(file, newPackageName));
                } else if (file.isFile() && file.getName().endsWith(".class")) {
                    String fileName = file.getName().substring(0, file.getName().length() - 6);
                    String fullClassName = packageName + "." + fileName;
                    try {
                        Class<?> aClass = Class.forName(fullClassName);
                        classes.add(aClass);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Cannot load class: " + fullClassName, e);
                    }

                }


            }

        }

        return classes;
    }

}
