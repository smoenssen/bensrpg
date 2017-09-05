package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.smoftware.bensrpg.BensRPG;

/**
 * Created by steve on 9/4/17.
 */

public class Switch extends AbstractInteractiveTileObject{

    private Rectangle bounds;

    public Switch(MapObject object) {

        // convert rectangle to correct scaled size
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.bounds.x /= BensRPG.PPM;
        this.bounds.y /= BensRPG.PPM;
        this.bounds.width /= BensRPG.PPM;
        this.bounds.height /= BensRPG.PPM;
    }

    public Rectangle getBounds() { return bounds; }

    @Override
    public void Interact(Rectangle playerPosition) {
        Gdx.app.log("tag", "Interacting!!!");

        if (playerPosition.overlaps(getBounds())) {
            Gdx.app.log("tag", "player overlapped switch!!!");
        }
    }
}
