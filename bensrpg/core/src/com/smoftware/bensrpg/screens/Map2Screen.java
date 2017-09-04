package com.smoftware.bensrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.sprites.Hero;
import com.smoftware.bensrpg.sprites.tileObjects.SignPost;
import com.smoftware.bensrpg.tools.B2WorldCreator;
import com.smoftware.bensrpg.tools.WorldContactListener;

/**
 * Created by brentaureli on 8/14/15.
 */
public class Map2Screen extends AbstractScreen {
    //Reference to our Game, used to set Screens
    private BensRPG game;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Music music;

    //Interactive tiles
    private SignPost signPost1;
    private SignPost signPost2;
    private boolean showSignPost1 = false;
    private boolean showSignPost2 = false;

    public Map2Screen(BensRPG game){
        this.game = game;

        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(BensRPG.V_WIDTH / BensRPG.PPM, BensRPG.V_HEIGHT / BensRPG.PPM, gamecam);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("RPGGame/maps/Map_2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1  / BensRPG.PPM);

        //set map dimensions
        setMapPixelDimensions(map);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        /*
        //Interactive tiles
        for(MapObject object : map.getLayers().get("Sign 1").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                signPost1 = new SignPost("Lorem ipsum dolor sit amet", game, this, game.player, object);
        }

        for(MapObject object : map.getLayers().get("Sign 2").getObjects().getByType(RectangleMapObject.class)){
            if (object != null)
                signPost2 = new SignPost("Lorem ipsum sign 2", game, this, game.player, object);
        }
*/
        //create our Box2D world, setting no gravity in X or Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);

        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        //create hero in our game world
        creator = new B2WorldCreator(game, this);
        game.player.setScreen(this);

        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {
        //Get player control back to this screen
        game.player.setScreen(this);
    }

    public void update(float dt){
        //takes 1 step in the physics simulation(60 times per second), velociy, position
        world.step(1 / 60f, 6, 2);

        game.player.update(dt);

        //attach our gamecam to our players.x and y coordinates
        if(game.player.currentState != Hero.State.DEAD) {
            gamecam.position.x = game.player.b2body.getPosition().x;
            gamecam.position.y = game.player.b2body.getPosition().y;
        }

        //keep camera within map
        gamecam.zoom = MathUtils.clamp(gamecam.zoom, 0.1f, 100/gamecam.viewportWidth);
        float effectiveViewportWidth = gamecam.viewportWidth * gamecam.zoom;
        float effectiveViewportHeight = gamecam.viewportHeight * gamecam.zoom;
        gamecam.position.x = MathUtils.clamp(gamecam.position.x, effectiveViewportWidth / 2f, ((float)mapPixelWidth / BensRPG.PPM) - (effectiveViewportWidth / 2f));
        gamecam.position.y = MathUtils.clamp(gamecam.position.y, effectiveViewportHeight / 2f, ((float)mapPixelHeight / BensRPG.PPM) - (effectiveViewportHeight / 2f));

        //update our gamecam with correct coordinates after changes
        gamecam.update();

        //tell our renderer to draw only what our camera can see in our game world.
        mapRenderer.setView(gamecam);
    }


    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        mapRenderer.render();

        //renderer our Box2DDebugLines
        if (BensRPG.bDebug)
            b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.player.draw(game.batch);

        if (showSignPost1)
            signPost1.draw();

        if (showSignPost2)
            signPost2.draw();

        game.batch.end();
    }

    //srm - maybe can use this
    //if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
    //player.fire();

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void Interact(Rectangle playerPosition) {
        if (playerPosition.overlaps(signPost1.getBounds()) && showSignPost1 == false) {
            showSignPost1 = true;
            signPost1.Interact(playerPosition);
        }
        else if (playerPosition.overlaps(signPost2.getBounds()) && showSignPost2 == false) {
            showSignPost2 = true;
            signPost2.Interact(playerPosition);
        }
        else {
            showSignPost1 = false;
            showSignPost2 = false;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

}