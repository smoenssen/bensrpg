package com.smoftware.bensrpg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smoftware.bensrpg.screens.PlayScreen;
import com.smoftware.bensrpg.sprites.Hero;

import java.util.Stack;

public class BensRPG extends Game {

	public static final boolean bDebug = true;

	//public static final int V_WIDTH = 480;
	//public static final int V_HEIGHT = 320;
	public static final int V_WIDTH = 240;
	public static final int V_HEIGHT = 160;
	public static final float ASPECT_RATIO = (float)V_WIDTH/(float)V_HEIGHT;
	public static final float PPM = 100;	// pixels per meter

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short HERO_BIT = 1;
	public static final short ARMORY_DOOR_BIT = 2;
	public static final short ARMORY_EXIT_DOOR_BIT = 4;
	public static final short GENERIC_OBJECT_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short BOUNDS_OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;
	public static final short FIREBALL_BIT = 1024;

	public Hero player;
	public SpriteBatch batch;
	private Stack screenStack;

	private OrthographicCamera camera;
	private Stage stage;
	private Touchpad touchpad;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private float blockSpeed;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	//public static AssetManager manager;

	@Override
	public void create () {
		player = new Hero();
		batch = new SpriteBatch();
		screenStack = new Stack();

		//Create camera
		float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10f*aspectRatio, 10f);

		//Create a touchpad skin
		touchpadSkin = new Skin();
		//Set background image
		touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
		//Set knob image
		touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
		//Create TouchPad Style
		touchpadStyle = new Touchpad.TouchpadStyle();
		//Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		touchKnob.setMinWidth(10);
		touchKnob.setMinHeight(10);

		//Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		//Create new TouchPad with the created style
        //srm - first param has to do with sensitivity
		touchpad = new Touchpad(3, touchpadStyle);
		//setBounds(x,y,width,height)
		touchpad.setBounds(0, 0, 50, 50);

		//Create a Stage and add TouchPad
		Viewport viewport = new FitViewport(BensRPG.V_WIDTH, BensRPG.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, batch);
		stage.addActor(touchpad);
		Gdx.input.setInputProcessor(stage);

		blockSpeed = 5;

		// Make the hero (instance) the class that handles the inputs
		//Gdx.input.setInputProcessor(player);
/*
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("audio/sounds/powerup.wav", Sound.class);
		manager.load("audio/sounds/powerdown.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);

		manager.finishLoading();
*/
		PlayScreen s = new PlayScreen(this);
		setCurrentScreen(s);

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}

	public void setPreviousScreen() {
		// NOTE: Current screen is always on top of stack
		//Gdx.app.log("tag", String.format("setPreviousScreen begin stack size = %d", screenStack.size()));
		if (screenStack.size() > 1) {
			//clean up current screen
			Screen screen = (Screen)screenStack.pop();
			//screen.dispose();

			//peek previous screen, which now will be the current screen
			screen = (Screen)screenStack.peek();
			setScreen(screen);
		}
		//Gdx.app.log("tag", String.format("setPreviousScreen end stack size = %d", screenStack.size()));
	}

	public void setCurrentScreen(Screen screen) {
		//Gdx.app.log("tag", String.format("setCurrentScreen begin stack size = %d", screenStack.size()));
		screenStack.push(screen);
		setScreen(screen);
		//Gdx.app.log("tag", String.format("setCurrentScreen end stack size = %d", screenStack.size()));
	}

	@Override
	public void render () {

		super.render();

		//Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		//Move player with TouchPad
		player.b2body.setLinearVelocity(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());

		//player.setX(player.getX() + touchpad.getKnobPercentX()*blockSpeed);
		//player.setY(player.getY() + touchpad.getKnobPercentY()*blockSpeed);

		//Draw
		batch.begin();
		player.draw(batch);
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		//manager.dispose();
		batch.dispose();
	}
}
