package com.smoftware.bensrpg.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
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
    public abstract void Interact(Rectangle playerPosition);

    protected void setMapPixelDimensions(TiledMap map) {
        MapProperties prop = map.getProperties();
        int mapWidthInTiles = prop.get("width", Integer.class);
        int mapHeightInTiles = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidthInTiles * tilePixelWidth;
        mapPixelHeight = mapHeightInTiles * tilePixelHeight;
    }
}
