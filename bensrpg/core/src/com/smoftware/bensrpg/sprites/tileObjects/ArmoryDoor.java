package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by moenssr on 8/21/2017.
 */

public class ArmoryDoor extends AbstractCollisionTileObject {
    BensRPG game;

    public ArmoryDoor(BensRPG game, PlayScreen screen, MapObject object){
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.ARMORY_DOOR_BIT);
    }

    @Override
    public void onCollision(Hero hero) {
        // open Armory map
        game.setCurrentScreen(new ArmoryScreen(game));
        //MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}
