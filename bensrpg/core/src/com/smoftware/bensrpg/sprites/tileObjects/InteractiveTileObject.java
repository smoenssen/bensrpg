package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 8/20/17.
 */

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screenPlay;
    protected ArmoryScreen screenArmory;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, MapObject object){
        this.object = object;
        this.screenPlay = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / BensRPG.PPM, (bounds.getY() + bounds.getHeight() / 2) / BensRPG.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / BensRPG.PPM, bounds.getHeight() / 2 / BensRPG.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public InteractiveTileObject(ArmoryScreen screen, MapObject object){
        this.object = object;
        this.screenArmory = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / BensRPG.PPM, (bounds.getY() + bounds.getHeight() / 2) / BensRPG.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / BensRPG.PPM, bounds.getHeight() / 2 / BensRPG.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void onCollision(Hero mario);
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(BensRPG.GRAPHICS_LAYER);

        //srm - need to scale up and then divide by tile size
        return layer.getCell((int)(body.getPosition().x * BensRPG.PPM / 16),
                (int)(body.getPosition().y * BensRPG.PPM / 16));
    }
}