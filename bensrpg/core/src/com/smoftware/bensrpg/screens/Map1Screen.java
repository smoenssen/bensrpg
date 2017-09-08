package com.smoftware.bensrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
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
import com.smoftware.bensrpg.GameState;
import com.smoftware.bensrpg.sprites.Hero;
import com.smoftware.bensrpg.sprites.tileObjects.SignPost;
import com.smoftware.bensrpg.tools.B2WorldCreator;
import com.smoftware.bensrpg.tools.OrthogonalTiledMapRendererWithSprites;
import com.smoftware.bensrpg.tools.WorldContactListener;

/**
 * Created by brentaureli on 8/14/15.
 */
public class Map1Screen extends AbstractScreen {
    //Reference to our Game, used to set Screens
    private BensRPG game;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRendererWithSprites mapRenderer;
    private MapLayer spriteLayer;
    TextureRegion textureRegion;

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


    public Map1Screen(BensRPG game){
        this.game = game;

        //create cam used to follow hero through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(BensRPG.V_WIDTH / BensRPG.PPM, BensRPG.V_HEIGHT / BensRPG.PPM, gamecam);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("RPGGame/maps/Map_1.tmx");
        mapRenderer = new OrthogonalTiledMapRendererWithSprites(map, 1  / BensRPG.PPM, game.batch);

        Texture texture = new Texture(Gdx.files.internal("sprites/Character_1.png"));
        textureRegion = new TextureRegion(texture, 0, 0, 16, 16);

        spriteLayer = map.getLayers().get("Player");

        TextureMapObject tmo = new TextureMapObject(textureRegion);
        tmo.setX(100);
        tmo.setY(100);
        spriteLayer.getObjects().add(tmo);

        //set map dimensions
        setMapPixelDimensions(map);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //Interactive tiles
        short index = 0;
        for(MapObject object : map.getLayers().get("Interaction").getObjects().getByType(RectangleMapObject.class)){
            if (object != null) {
                if (index++ == 0)
                    signPost1 = new SignPost("Lorem ipsum dolor sit amet sign 1", game, this, object);
                else
                    signPost2 = new SignPost("Lorem ipsum dolor sit amet sign 2", game, this, object);
            }
        }

        //create our Box2D world, setting no gravity in X or Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);

        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        //create hero in our game world
        creator = new B2WorldCreator(game, this);
        BensRPG.player.setScreen(this);

        //game states
        creator.disableWaterCollision(BensRPG.state.map1WaterDisabled);
        creator.disableBridgeCollision(BensRPG.state.map1WaterDisabled);

        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {
        //Get player control back to this screen
        //todo: uncomment
        BensRPG.player.setScreen(this);
    }

    public void update(float dt){
        //takes 1 step in the physics simulation(60 times per second), velociy, position
        world.step(1 / 60f, 6, 2);

        BensRPG.player.update(dt);

        //attach our gamecam to our players.x and y coordinates
        if(BensRPG.player.currentState != Hero.State.DEAD) {
            gamecam.position.x = BensRPG.player.b2body.getPosition().x;
            gamecam.position.y = BensRPG.player.b2body.getPosition().y;
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
        //separate our update logic from renderb2body.di
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /////test code
       // gamecam.update();
       // mapRenderer.setView(gamecam);
       // mapRenderer.render();
        /////

        //render our game map
        mapRenderer.render();

        //renderer our Box2DDebugLines
        if (BensRPG.bDebug)
            b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        BensRPG.player.draw(game.batch);

        if (showSignPost1) {
            signPost1.draw();
        }

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