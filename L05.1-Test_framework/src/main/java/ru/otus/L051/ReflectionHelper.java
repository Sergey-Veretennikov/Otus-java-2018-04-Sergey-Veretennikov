package ru.otus.L051;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionHelper {
    public static ArrayList<Class> getClassesFromPackage(final String packageName)
            throws IOException, URISyntaxException, ClassNotFoundException {
        ArrayList<String> classNames = getClassNamesFromPackage(packageName);
        ArrayList<Class> result = new ArrayList<Class>();

        for (String className : classNames) {
            String fullClassName = packageName + "." + className;
            Class clazz = Class.forName(fullClassName);
            result.add(clazz);
        }
        return result;
    }

    private static ArrayList<String> getClassNamesFromPackage(final String packageName)
            throws IOException, URISyntaxException {
        ArrayList<String> names = new ArrayList<String>();

        String packageUrl = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(packageUrl);

        if (url.getProtocol().equals("jar")) {
            String entryName;

            String jarFileName = URLDecoder.decode(url.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));

            JarFile jf = new JarFile(jarFileName);
            Enumeration<JarEntry> entries = jf.entries();
            while (entries.hasMoreElements()) {
                entryName = entries.nextElement().getName();

                if (entryName.startsWith(packageUrl) && entryName.length() > packageUrl.length() + 5) {
                    entryName = entryName.substring(packageUrl.length() + 1, entryName.lastIndexOf('.'));
                    names.add(entryName);
                }
            }
        } else {
            URI uri = new URI(url.toString());
            File folder = new File(uri.getPath());
            File[] files = folder.listFiles();
            String entryName;
            for (File actual : files) {
                entryName = actual.getName();
                entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                names.add(entryName);
            }
        }
        return names;
    }
}
