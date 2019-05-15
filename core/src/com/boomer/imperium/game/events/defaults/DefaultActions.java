package com.boomer.imperium.game.events.defaults;

import com.boomer.imperium.game.GameFlags;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.events.Action;
import com.boomer.imperium.game.events.Parameters;

import java.util.List;

import static com.boomer.imperium.game.LogicUtils.unbox;
import static com.boomer.imperium.game.events.Parameters.Key.*;

public final class DefaultActions {

    public static final Action SELECT_ENTITIES_IN_GUI = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameGui().entitiesDeSelected();
            gameContext.getGameGui().selectedEntities(parameters.getEntities(SELECTED_ENTITIES));
            return false;
        }
    };

    public static final Action DESELECTED_ENTITIES_IN_GUI = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameGui().entitiesDeSelected();
            return false;
        }
    };

    public static final Action SELECTED_ENTITIES_TARGET_TILE = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().setTargetTileForSelected(gameContext.getGameWorld().map.findTile(parameters.getVector(MOUSE_LOCATION)));
            return false;
        }
    };

    public static final Action MOUSE_HOVER_IN_GAME_WORLD = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().mouseHover(parameters.getVector(MOUSE_LOCATION));
            return false;
        }
    };

    public static final Action MOUSE_DRAG_IN_GAME_WORLD = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().mouseDrag(parameters.getRectangle(MOUSE_DRAG_RECTANGLE));
            return false;
        }
    };

    public static final Action MOUSE_RIGHT_CLICK_IN_GAME_WORLD = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters, float deltaTime) {
            gameContext.getGameWorld().mouseRightClick(parameters.getVector(MOUSE_LOCATION));
            return false;
        }
    };

    public static final Action MOUSE_LEFT_CLICK_IN_GAME_WORLD = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters, float deltaTime) {
            gameContext.getGameWorld().mouseLeftClick(parameters.getVector(MOUSE_LOCATION));
            return false;
        }
    };

    public static final Action ADJUST_CURSOR_IN_GUI = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters, float deltaTime) {
            gameContext.gameCursor().entityHoveredOver(parameters.getEntity(ENTITY));
            return false;
        }
    };

    public static final Action PICK_BUILDABLE_IN_GAME_WORLD = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().setBuildingToBuild(parameters.getBuildable(BUILDABLE_TO_BUILD));
            return false;
        }
    };

    public static final Action ADJUST_CURSOR_FOR_BUILDING = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.gameCursor().building();
            return false;
        }
    };

    public static final Action RESET_CURSOR_TO_STANDARD = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.gameCursor().clearCursor();
            return false;
        }
    };

    public static final Action DAYS_PASSED = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().dayPassed(unbox(parameters.getInt(Parameters.Key.DAYS),0));
            return false;
        }
    };

    public static final Action WEEKS_PASSED = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().weekPassed(unbox(parameters.getInt(Parameters.Key.WEEKS),0));
            return false;
        }
    };

    public static final Action MONTH_PASSED = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().monthPassed(unbox(parameters.getInt(Parameters.Key.MONTHS),0));
            return false;
        }
    };

    public static final Action YEARS_PASSED = new Action() {
        @Override
        public boolean perform(GameContextInterface gameContext, Parameters parameters,float deltaTime) {
            gameContext.getGameWorld().yearPassed(unbox(parameters.getInt(Parameters.Key.YEARS),0));
            return false;
        }
    };



    private DefaultActions(){}

}
