package com.boomer.imperium.model.io;

import com.boomer.imperium.Context;
import com.boomer.imperium.NewContextData;
import com.boomer.imperium.scripts.ScriptMirror;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutionException;

public class NewContextGenerator extends SwingWorker<Context,Void> {

    private static final String CLASS_EXTENSION = "java";

    private final NewContextData newContextData;
    private final ContextIOListener contextIOListener;

    public NewContextGenerator(NewContextData newContextData,ContextIOListener contextIOListener){
        this.newContextData = newContextData;
        this.contextIOListener = contextIOListener;
    }

    @Override
    protected Context doInBackground() throws Exception {
        File projectRoot = new File(getProjectRoot());
        File scriptsFolder = new File(newContextData.getScriptPath());
        System.out.println(projectRoot.getAbsolutePath());
        URL url = projectRoot.toURI().toURL();
        URL[] urls = new URL[]{url};
        ClassLoader classLoader = new URLClassLoader(urls);
        Iterable<File> classfiles = Iterables.filter(ImmutableList.copyOf(scriptsFolder.listFiles()),
                input -> Files.getFileExtension(input.getName()).equals(CLASS_EXTENSION));
        for(File classFile : classfiles){
            createScriptMirrorFromClass(classLoader,classFile);
        }
        return null;
    }

    @Override
    protected void done() {
        super.done();
        try {
            contextIOListener.contextGenerated(get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private ScriptMirror createScriptMirrorFromClass(ClassLoader classLoader, File classFile) throws ClassNotFoundException {
        String name = getClassName(classFile);
        Class clazz = classLoader.loadClass(getClassName(classFile));
        System.out.println(clazz.getName());


        return null;
    }

    private String getProjectRoot(){
        String javaRootPath = getJavaPackagePath(newContextData.getJavaPackage());
        int rootIndex = newContextData.getScriptPath().lastIndexOf(javaRootPath);
        return newContextData.getScriptPath().substring(0,rootIndex);
    }

    private String getClassName(File classFile){
        String javaRootPath = getJavaPackagePath(newContextData.getJavaPackage());
        int rootIndex = classFile.getAbsolutePath().lastIndexOf(javaRootPath);
        String clazzPath = classFile.getAbsolutePath().substring(rootIndex,classFile.getAbsolutePath().length());
        int extensionIndex = clazzPath.lastIndexOf("."+CLASS_EXTENSION);
        return clazzPath.substring(0,extensionIndex).replaceAll("\\\\",".");
    }

    private String getJavaPackagePath(String javaPackage){
        return javaPackage.replaceAll("\\.","\\\\");
    }
}
