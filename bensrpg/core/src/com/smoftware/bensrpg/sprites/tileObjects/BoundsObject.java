package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by moenssr on 8/23/2017.
 */

public class BoundsObject extends InteractiveTileObject {
    BensRPG game;
    Hero player;

    public BoundsObject(BensRPG game, PlayScreen screen, Hero player, MapObject object) {
        super(screen, object);
        this.game = game;
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.BOUNDS_OBJECT_BIT);
    }

    public BoundsObject(BensRPG game, ArmoryScreen screen, Hero player, MapObject object) {
        super(screen, object);
        this.game = game;
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.BOUNDS_OBJECT_BIT);
    }

    @Override
    public void onCollision(Hero hero) {
        Gdx.app.log("tag", "onCollision Bounds!");
    }
}
