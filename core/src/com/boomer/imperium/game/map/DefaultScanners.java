package com.boomer.imperium.game.map;

import java.util.List;

public final class DefaultScanners {

    public static MapScanner IS_LAND_AVAILABLE = new MapScanner() {
        @Override
        public boolean scan(Map map, int tileX, int tileY ,List<TileVector> tileVectorList) {
            for(TileVector tileVector : tileVectorList){
                Tile candidate = map.getTileAt(tileX + tileVector.x, tileY + tileVector.y);
                if(candidate==null || !candidate.canBeMovedTo())
                    return false;
            }
            return true;
        }
    };


    private DefaultScanners(){}
}
