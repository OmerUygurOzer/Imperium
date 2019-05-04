package com.boomer.imperium.game.entities.buildings.defaults;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.boomer.imperium.game.Nation;
import com.boomer.imperium.game.configs.GameConfigs;
import com.boomer.imperium.game.configs.GameContextInterface;
import com.boomer.imperium.game.entities.buildings.Buildable;
import com.boomer.imperium.game.entities.buildings.Building;

import java.util.Arrays;
import java.util.List;

public class DefaultBuildings {

    private final Nation nation;
    private final GameContextInterface gameContext;

    public DefaultBuildings(GameContextInterface gameContextInterface, Nation nation){
        this.gameContext = gameContextInterface;
        this.nation = nation;
    }

    public final Buildable FORT = new Buildable() {
        @Override
        public String getName() {
            return "Fort";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable UNIVERSITY = new Buildable() {
        @Override
        public String getName() {
            return "University";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable FARMLAND = new Buildable() {
        @Override
        public String getName() {
            return "Farmland";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable GUILD = new Buildable() {
        @Override
        public String getName() {
            return "Guild";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable FACTORY = new Buildable() {
        @Override
        public String getName() {
            return "Factory";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable MINE = new Buildable() {
        @Override
        public String getName() {
            return "Mine";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable MARKET = new Buildable() {
        @Override
        public String getName() {
            return "Market";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable INN = new Buildable() {
        @Override
        public String getName() {
            return "INN";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable TEMPLE = new Buildable() {
        @Override
        public String getName() {
            return "Temple";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final Buildable HARBOR = new Buildable() {
        @Override
        public String getName() {
            return "Harbor";
        }

        @Override
        public Sprite getCursorFillerSprite() {
            return null;
        }

        @Override
        public Rectangle getCursorFillerRectangle() {
            return null;
        }

        @Override
        public Sprite getUIIcon() {
            return null;
        }

        @Override
        public Building build() {
            return null;
        }
    };

    public final List<Buildable> ALL = Arrays.asList(FORT,MINE,FACTORY,FARMLAND,GUILD,MARKET,UNIVERSITY,INN,HARBOR,TEMPLE);


}
