package com.boomer.imperium.model.io;

import com.boomer.imperium.Context;
import com.boomer.imperium.NewContextData;
import com.boomer.imperium.scripts.ClassTypes;
import com.boomer.imperium.scripts.ScriptMirror;
import com.boomer.imperium.scripts.mirrors.Attribute;
import com.boomer.imperium.scripts.mirrors.AttributeList;
import com.boomer.imperium.scripts.mirrors.ScriptList;
import org.checkerframework.checker.units.qual.C;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        JarFile jarFile = new JarFile(newContextData.getScriptPath());
        Enumeration<JarEntry> e = jarFile.entries();
        URL[] urls = { new URL("jar:file:" + newContextData.getScriptPath()+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        Context context = Context.fromData(newContextData);
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            System.out.println(className);
            if(!className.startsWith(newContextData.getJavaPackage())){
                continue;
            }
            try {
                Class scriptCandidate = cl.loadClass(className);
                if (ClassTypes.isComponent(scriptCandidate)) {
                    ScriptMirror scriptMirror = extractScriptMirrorForComponent(scriptCandidate);
                    context.getScriptList().getValue().put(scriptMirror.getName(), scriptMirror);
                }
            }catch (NoClassDefFoundError classDefFoundError){
                continue;
            }
        }
        return context;
    }

    private ScriptMirror extractScriptMirrorForComponent(Class scriptClass){
        AttributeList attributeList = new AttributeList();
        for(Field field : scriptClass.getFields()){
            if(Modifier.isPublic(field.getModifiers())
                && !Modifier.isFinal(field.getModifiers()) &&
                    (ClassTypes.SUPPORTED_PRIMITIVES.contains(field.getType()) ||
                    ClassTypes.LIBGDX_PRIMITIVES.contains(field.getType().getCanonicalName()))){
                Attribute attribute = ClassTypes.initializeAttributeForField(field);
                attributeList.getValue().put(attribute.getName(),attribute);
            }
        }
        return new ScriptMirror(scriptClass.getName(),null,attributeList,
                ScriptMirror.Type.PREMADE,scriptClass.getCanonicalName());
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
}
