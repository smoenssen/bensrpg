package com.smoftware.bensrpg.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.screens.Map2Screen;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;
import com.smoftware.bensrpg.sprites.tileObjects.ArmoryDoor;
import com.smoftware.bensrpg.sprites.tileObjects.ArmoryDoorExit;
import com.smoftware.bensrpg.sprites.tileObjects.BoundsObject;
import com.smoftware.bensrpg.sprites.tileObjects.GenericObject;
import com.smoftware.bensrpg.sprites.tileObjects.Obstacle;
import com.smoftware.bensrpg.sprites.tileObjects.ToMap1;
import com.smoftware.bensrpg.sprites.tileObjects.ToMap2;
import com.smoftware.bensrpg.sprites.tileObjects.Water;
import com.smoftware.bensrpg.sprites.tileObjects.ZeroOpacity;


/**
 * Created by brentaureli on 8/28/15.
 */
public class B2WorldCreator {
    //private Array<Goomba> goombas;
    //private Array<Turtle> turtles;
    private Array<Water> waterArrary;
    private Array<ZeroOpacity> zeroOpacityArray;

    public B2WorldCreator(BensRPG game, PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //create bodies/fixtures
        for(MapObject object : map.getLayers().get("Generic Objects").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new GenericObject(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("Bounds").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new BoundsObject(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("Armory Door").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new ArmoryDoor(game, screen, object);
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

    public B2WorldCreator(BensRPG game, Map1Screen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        waterArrary = new Array<Water>();

         //create bodies/fixtures
        for(MapObject object : map.getLayers().get("Obstacles").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new Obstacle(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("Water Obstacle").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                waterArrary.add(new Water(game, screen, object));
        }

        for(MapObject object : map.getLayers().get("Bridge Obstacle").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new Obstacle(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("To Next Map").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new ToMap2(game, screen, object);
        }
    }

    public B2WorldCreator(BensRPG game, Map2Screen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        waterArrary = new Array<Water>();
        zeroOpacityArray = new Array<ZeroOpacity>();

        for(MapObject object : map.getLayers().get("Obstacles").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new Obstacle(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("Water Obstacle").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                waterArrary.add(new Water(game, screen, object));
        }

        for(MapObject object : map.getLayers().get("Bridge Obstacles").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new Obstacle(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("To Previous Map").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                new ToMap1(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("0 Opacity").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                zeroOpacityArray.add(new ZeroOpacity(game, screen, object));
        }
    }

    public B2WorldCreator(BensRPG game, ArmoryScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //create bodies/fixtures
        for(MapObject object : map.getLayers().get("Counter").getObjects().getByType(RectangleMapObject.class)){
            new GenericObject(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("Bounds").getObjects().getByType(RectangleMapObject.class)){
            new BoundsObject(game, screen, object);
        }

        for(MapObject object : map.getLayers().get("Exit Door").getObjects().getByType(RectangleMapObject.class)){
            new ArmoryDoorExit(game, screen, object);
        }
    }

    public Array<Water> getWaterArrary() {
        return waterArrary;
    }

    public Array<ZeroOpacity> getZeroOpacityArray() {
        return zeroOpacityArray;
    }
}
