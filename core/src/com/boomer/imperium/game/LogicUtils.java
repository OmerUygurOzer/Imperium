package com.boomer.imperium.game;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContext;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.Entity;
import com.boomer.imperium.game.map.Tile;
import com.boomer.imperium.game.map.TileVector;

import java.util.List;

public final class LogicUtils {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double distance(Vector2 from, Vector2 to) {
        return distance(from.x, from.y, to.x, to.y);
    }

    public static double distance(Tile t1, Tile t2) {
        return distance(t1.tileX, t1.tileY, t2.tileX, t2.tileY);
    }

    public static boolean areTilesAdjacent(Tile t1, Tile t2) {
        return Math.abs(t1.tileX - t2.tileX) <= 1 && Math.abs(t1.tileY - t2.tileY) <= 1;
    }

    public static boolean areEntitiesAdjacent(Entity e1, Entity e2) {
        return Math.abs(e1.tileX() - e2.tileX()) <= 1 && Math.abs(e1.tileY() - e2.tileY()) <= 1;
    }

    public static <T> T randomSelect(List<T> selectable) {
        return selectable.get(MathUtils.random(0, selectable.size() - 1));
    }



    public static int unbox(Integer integer, int defaultVal) {
        return integer == null ? defaultVal : integer;
    }

    private LogicUtils() {
    }
}
