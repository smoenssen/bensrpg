package com.smoftware.bensrpg.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by moenssr on 8/21/2017.
 */

// srm for ideas... http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/

public abstract class AbstractScreen implements Screen {
    public int mapPixelWidth;
    public int mapPixelHeight;

    public abstract TiledMap getMap();
    public abstract World getWorld();
}
