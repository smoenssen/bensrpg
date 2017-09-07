package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 9/4/17.
 */

public class ToMap1 extends AbstractCollisionTileObject {
    BensRPG game;

    public ToMap1(BensRPG game, Map2Screen screen, MapObject object){
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.PREV_MAP_BIT);
    }

    @Override
    public void onCollision(Hero hero) {
        // go back to previous screen
        game.setPreviousScreen();
    }
}