package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 9/4/17.
 */

public class ToMap2 extends AbstractCollisionTileObject {
    BensRPG game;

    public ToMap2(BensRPG game, Map1Screen screen, MapObject object){
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.NEXT_MAP_BIT);
    }

    @Override
    public void onCollision(Hero hero) {
        game.setCurrentScreen(new Map2Screen(game));
    }
}