package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by moenssr on 9/8/2017.
 */

public class Bridge  extends AbstractCollisionTileObject {
    BensRPG game;

    public Bridge(BensRPG game, Map1Screen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.BRIDGE_BIT);
    }

    public Bridge(BensRPG game, Map2Screen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.BRIDGE_BIT);
    }

    public void setCollisionFilter(short filter) {
        setCategoryFilter(filter);
    }

    @Override
    public void onCollision(Hero hero) {

    }
}
