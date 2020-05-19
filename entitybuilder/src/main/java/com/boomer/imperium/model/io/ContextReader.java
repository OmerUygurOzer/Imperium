package com.boomer.imperium.model.io;

import com.google.gson.Gson;
import com.boomer.imperium.Context;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutionException;

public class ContextReader extends SwingWorker<Context,Void> {

    private final String path;
    private final ContextIOListener contextIOListener;

    public ContextReader(String path, ContextIOListener contextIOListener) {
        this.path = path;
        this.contextIOListener = contextIOListener;
    }

    @Override
    protected Context doInBackground() throws Exception {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(new File(path));
        JSONObject contextObject = (JSONObject) jsonParser.parse(reader);
        return new Gson().fromJson(contextObject.toJSONString(),Context.class);
    }

    @Override
    protected void done() {
        super.done();
        try {
            contextIOListener.contextRead(get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
