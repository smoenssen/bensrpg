package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 9/3/17.
 */

public class Obstacle extends AbstractCollisionTileObject {
    BensRPG game;

    public Obstacle(BensRPG game, Map1Screen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.OBSTACLE_BIT);
    }

    public Obstacle(BensRPG game, Map2Screen screen, MapObject object) {
        super(screen, object);
        this.game = game;

        fixture.setUserData(this);
        setCategoryFilter(BensRPG.OBSTACLE_BIT);
    }

    @Override
    public void onCollision(Hero hero) {

    }
}