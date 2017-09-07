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

public class GenericObject extends AbstractCollisionTileObject {
    BensRPG game;

    public GenericObject(BensRPG game, PlayScreen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.GENERIC_OBJECT_BIT);
    }

    public GenericObject(BensRPG game, ArmoryScreen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.GENERIC_OBJECT_BIT);
    }

    @Override
    public void onCollision(Hero hero) {
        Gdx.app.log("tag", "onCollision!");
    }
}
