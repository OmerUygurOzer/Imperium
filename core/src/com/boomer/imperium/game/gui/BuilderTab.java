package com.boomer.imperium.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boomer.imperium.game.entities.buildings.Buildable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public final class BuilderTab extends Table {

    private final List<ImageButton> existingButtons;
    private final LinkedHashMap<ImageButton, Buildable> mappedButtons;

    private final Listener listener;

    public BuilderTab(Skin skin, final Listener listener) {
        super(skin);
        this.listener = listener;
        this.existingButtons = new ArrayList<ImageButton>();
        this.mappedButtons = new LinkedHashMap<ImageButton, Buildable>();
        for(int i = 0 ; i < 12 ; i++){
            final ImageButton imageButton = new ImageButton((Drawable)null);
            imageButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if(listener!=null)
                        listener.selectedBuildable(mappedButtons.get(imageButton));
                }
            });
            existingButtons.add(imageButton);
        }
    }


    public void setBuildables(List<Buildable> buildables) {
        clearBuildables();
        for(int i = 0; i < buildables.size() && i < existingButtons.size(); i ++){
            if(i%2==0)
                row();
            existingButtons.get(i).getStyle().imageUp = buildables.get(i).getUIIcon();
            mappedButtons.put(existingButtons.get(i),buildables.get(i));
            add(existingButtons.get(i)).size(100f,70f)
                    .pad(5f)
                    .expand()
                    .center();
        }
        for(int i = buildables.size() ; i < existingButtons.size() ; i++){
            removeActor(existingButtons.get(i));
        }
        this.pack();
    }

    public void clearBuildables(){
        mappedButtons.clear();
        clear();
        pack();
    }

    public interface Listener{
        void selectedBuildable(Buildable buildable);
    }


}
