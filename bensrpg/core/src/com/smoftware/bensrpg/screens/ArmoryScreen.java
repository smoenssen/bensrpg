package com.smoftware.bensrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.scenes.Hud;
import com.smoftware.bensrpg.sprites.Hero;
import com.smoftware.bensrpg.tools.B2WorldCreator;
import com.smoftware.bensrpg.tools.WorldContactListener;

/**
 * Created by brentaureli on 8/14/15.
 */
public class ArmoryScreen extends AbstractScreen {
    //Reference to our Game, used to set Screens
    private BensRPG game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Hero player;

    private Music music;


    public ArmoryScreen(BensRPG game){
        //srm - might want to change this to use libgdx asset manager
        //atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        this.player = game.player;

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("maps/Armory.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1  / BensRPG.PPM);

        //Get map dimensions
        MapProperties prop = map.getProperties();
        int mapWidthInTiles = prop.get("width", Integer.class);
        int mapHeightInTiles = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidthInTiles * tilePixelWidth;
        mapPixelHeight = mapHeightInTiles * tilePixelHeight;

        //create cam used to follow hero through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(BensRPG.V_WIDTH / BensRPG.PPM, BensRPG.V_HEIGHT / BensRPG.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch);

        //initially set our gamcam to be centered correctly at the bottom center of map
        float viewportCenter = gamePort.getWorldWidth() / 2;
        float rhEdgeOfMap =  mapPixelWidth/BensRPG.PPM;
        float mapCenter = rhEdgeOfMap / 2;
        gamecam.position.set((viewportCenter - mapCenter) - (viewportCenter - rhEdgeOfMap), gamePort.getWorldHeight() / 2f, 0);

        //create our Box2D world, setting no gravity in X or Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);

        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        //create hero in our game world
        creator = new B2WorldCreator(game, this);
        game.player.setScreen(this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

        //Get player control back to this screen
        game.player.setScreen(this);
    }

    public void update(float dt){
        //takes 1 step in the physics simulation(60 times per second), velocity, position
        world.step(1 / 60f, 6, 2);

        player.update(dt);

        //attach our gamecam to our player's coordinates
        //since map width is narrower than virtual screen, don't adjust camera to follow player in x position
        if(player.currentState != Hero.State.DEAD) {
            gamecam.position.y = player.b2body.getPosition().y;
        }

        //keep camera within map
        //http://wiki.v5ent.com/doku.php?id=libgdx:libgdx:wiki:orthographic-camera
        gamecam.zoom = MathUtils.clamp(gamecam.zoom, 0.1f, 100/gamecam.viewportWidth);
        float effectiveViewportHeight = gamecam.viewportHeight * gamecam.zoom;
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        mapRenderer.render();

        //renderer our Box2DDebugLines
        if (BensRPG.bDebug)
            b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        /*
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Item item : items)
            item.draw(game.batch);
        */
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //hud.stage.draw();
/*
        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
*/
    }
    /*
        public boolean gameOver(){
            if(player.currentState == Hero.State.DEAD && player.getStateTimer() > 3){
                return true;
            }
            return false;
        }
    */
    @Override            //if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
    //player.fire();
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.app.log("tag", "Armory scree hide!");
        //dispose();
    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        Gdx.app.log("tag", "Armory scree dispose!");
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud(){ return hud; }
}