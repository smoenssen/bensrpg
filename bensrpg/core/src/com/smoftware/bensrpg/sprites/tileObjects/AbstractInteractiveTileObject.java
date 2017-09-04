package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by steve on 9/3/17.
 */

public abstract class AbstractInteractiveTileObject {

    public AbstractInteractiveTileObject() {

    }

    public abstract void Interact(Rectangle playerPosition);
}
