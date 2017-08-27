package com.smoftware.bensrpg.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;
import com.smoftware.bensrpg.sprites.tileObjects.ArmoryDoor;
import com.smoftware.bensrpg.sprites.tileObjects.ArmoryDoorExit;
import com.smoftware.bensrpg.sprites.tileObjects.BoundsObject;
import com.smoftware.bensrpg.sprites.tileObjects.GenericObject;


/**
 * Created by brentaureli on 8/28/15.
 */
public class B2WorldCreator {
    //private Array<Goomba> goombas;
    //private Array<Turtle> turtles;
    Hero player;

    public B2WorldCreator(BensRPG game, PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        this.player = game.player;

        //create bodies/fixtures
        for(MapObject object : map.getLayers().get(BensRPG.GENERIC_OBJECT_LAYER).getObjects().getByType(RectangleMapObject.class)){
            new GenericObject(game, screen, player, object);
        }

        for(MapObject object : map.getLayers().get(BensRPG.GENERIC_OBJECT_LAYER).getObjects().getByType(PolygonMapObject.class)){
            new GenericObject(game, screen, player, object);
        }

        for(MapObject object : map.getLayers().get(BensRPG.BOUNDS_PLAYSCREEN_LAYER).getObjects().getByType(RectangleMapObject.class)){
            new BoundsObject(game, screen, player, object);
        }

        for(MapObject object : map.getLayers().get(BensRPG.ARMORY_DOOR_LAYER).getObjects().getByType(RectangleMapObject.class)){
            new ArmoryDoor(game, screen, player, object);
        }
/*
        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            new Coin(screen, object);
        }

        //create all goombas
        goombas = new Array<Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX() / BensRPG.PPM, rect.getY() / BensRPG.PPM));
        }
        turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / BensRPG.PPM, rect.getY() / BensRPG.PPM));
        }
*/
    }

    public B2WorldCreator(BensRPG game, ArmoryScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        this.player = game.player;

        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create bodies/fixtures
        for(MapObject object : map.getLayers().get(BensRPG.ARMORY_COUNTER_LAYER).getObjects().getByType(RectangleMapObject.class)){
            new GenericObject(game, screen, player, object);
        }

        for(MapObject object : map.getLayers().get(BensRPG.BOUNDS_ARMORYSCREEN_LAYER).getObjects().getByType(RectangleMapObject.class)){
            new BoundsObject(game, screen, player, object);
        }

        for(MapObject object : map.getLayers().get(BensRPG.ARMORY_EXIT_DOOR_LAYER).getObjects().getByType(RectangleMapObject.class)){
            new ArmoryDoorExit(game, screen, player, object);
        }
    }
}
