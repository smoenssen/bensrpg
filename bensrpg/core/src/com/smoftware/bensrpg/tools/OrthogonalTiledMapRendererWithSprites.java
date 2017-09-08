package com.smoftware.bensrpg.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by moenssr on 9/8/2017.
 */

//http://www.gamefromscratch.com/post/2014/05/01/LibGDX-Tutorial-11-Tiled-Maps-Part-2-Adding-a-character-sprite.aspx

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    SpriteBatch sb;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, float unitScale, SpriteBatch sb) {
        super(map, unitScale, sb);
        this.sb = sb;
    }

    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            sb.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
        }
    }
}
