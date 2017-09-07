package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by moenssr on 8/22/2017.
 */

public class ArmoryDoorExit  extends AbstractCollisionTileObject {
    BensRPG game;

    public ArmoryDoorExit(BensRPG game, ArmoryScreen screen, MapObject object){
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.ARMORY_EXIT_DOOR_BIT);
    }

    @Override
    public void onCollision(Hero hero) {
        // go back to previous screen
        game.setPreviousScreen();
    }

}
