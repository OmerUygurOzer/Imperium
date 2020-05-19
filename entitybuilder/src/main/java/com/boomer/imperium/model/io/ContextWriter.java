package com.boomer.imperium.model.io;

import com.google.gson.Gson;
import com.boomer.imperium.Context;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;

public class ContextWriter extends SwingWorker<Context,Void> {

    private final Context context;
    private final ContextIOListener contextIOListener;

    public ContextWriter(Context context,ContextIOListener contextIOListener){
        this.context = context;
        this.contextIOListener = contextIOListener;
    }

    @Override
    protected Context doInBackground() throws Exception {
        File contextFile = new File(context.getFilePath() + File.separator + context.getName().trim().toLowerCase() + ".json");
        //Write JSON file
        FileWriter fileWriter = new FileWriter(contextFile);
        fileWriter.write(new Gson().toJson(context));
        fileWriter.flush();
        return context;
    }

    @Override
    protected void done() {
        super.done();
        contextIOListener.contextWritten(context);
    }
}
