package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 9/4/17.
 */

public class ZeroOpacity extends AbstractCollisionTileObject {
    BensRPG game;

    public ZeroOpacity(BensRPG game, Map2Screen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.ZERO_OPACITY);
    }

    public void setCollisionFilter(short filter) {
        setCategoryFilter(filter);
    }

    @Override
    public void onCollision(Hero hero) {

    }
}
