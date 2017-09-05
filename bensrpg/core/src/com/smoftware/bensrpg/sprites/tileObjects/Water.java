package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 9/1/17.
 */

public class Water extends AbstractCollisionTileObject {
    BensRPG game;
    Hero player;

    public Water(BensRPG game, Map1Screen screen, Hero player, MapObject object) {
        super(screen, object);
        this.game = game;
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.WATER_BIT);
    }

    public Water(BensRPG game, Map2Screen screen, Hero player, MapObject object) {
        super(screen, object);
        this.game = game;
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.WATER_BIT);
    }

    public void setCollisionFilter(short filter) {
        setCategoryFilter(filter);
    }

    @Override
    public void onCollision(Hero hero) {

    }
}
