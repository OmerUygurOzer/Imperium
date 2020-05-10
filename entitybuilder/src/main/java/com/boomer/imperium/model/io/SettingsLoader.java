package com.boomer.imperium.model.io;

import com.boomer.imperium.model.Settings;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.Reader;

public class SettingsLoader extends SwingWorker<Settings,Void> {

    private final String settingsPath;
    private final SettingsLoadedListener settingsLoadedListener;

    private SettingsLoader(String settingsPath,SettingsLoadedListener settingsLoadedListener){
        this.settingsPath = settingsPath;
        this.settingsLoadedListener = settingsLoadedListener;
    }

    @Override
    protected Settings doInBackground() throws Exception {
        Reader reader = new FileReader(settingsPath);
        JSONParser parser = new JSONParser();
        JSONArray settingsJson = (JSONArray) parser.parse(reader);
        // Todo(Omer): Finish later for persistance of settings.
        return null;
    }
}
