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
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 8/20/17.
 */

public abstract class AbstractCollisionTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screenPlay;
    protected Map1Screen map1;
    protected ArmoryScreen screenArmory;
    protected MapObject object;

    protected Fixture fixture;

    public AbstractCollisionTileObject(PlayScreen screen, MapObject object){
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
        fdef.restitution = 0.0f;
        fixture = body.createFixture(fdef);

    }

    public AbstractCollisionTileObject(ArmoryScreen screen, MapObject object){
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

    public TiledMapTileLayer.Cell getCell(String layerName){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);

        //srm - need to scale up and then divide by tile size
        return layer.getCell((int)(body.getPosition().x * BensRPG.PPM / 16),
                (int)(body.getPosition().y * BensRPG.PPM / 16));
    }

    public Rectangle getBounds() { return bounds; };
}
