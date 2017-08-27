package com.smoftware.bensrpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	//Tiled layers
	//PlayScreen
	public static final short GRAPHICS_LAYER = 0;
	public static final short GENERIC_OBJECT_LAYER = 1;
	public static final short ARMORY_DOOR_LAYER = 2;
	public static final short BOUNDS_PLAYSCREEN_LAYER = 3;

	//ArmoryScreen
	public static final short ARMORY_EXIT_DOOR_LAYER = 2;
	public static final short ARMORY_COUNTER_LAYER = 3;
	public static final short BOUNDS_ARMORYSCREEN_LAYER = 4;

	public Hero player;
	public SpriteBatch batch;
	private Stack screenStack;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	//public static AssetManager manager;

	@Override
	public void create () {
		player = new Hero();
		batch = new SpriteBatch();
		screenStack = new Stack();

		// Make the hero (instance) the class that handles the inputs
		Gdx.input.setInputProcessor(player);
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
	}

	public void setPreviousScreen() {
		// NOTE: Current screen is always on top of stack
		Gdx.app.log("tag", String.format("setPreviousScreen begin stack size = %d", screenStack.size()));
		if (screenStack.size() > 1) {
			//clean up current screen
			Screen screen = (Screen)screenStack.pop();
			//screen.dispose();

			//peek previous screen, which now will be the current screen
			screen = (Screen)screenStack.peek();
			setScreen(screen);
		}
		Gdx.app.log("tag", String.format("setPreviousScreen end stack size = %d", screenStack.size()));
	}

	public void setCurrentScreen(Screen screen) {
		Gdx.app.log("tag", String.format("setCurrentScreen begin stack size = %d", screenStack.size()));
		screenStack.push(screen);
		setScreen(screen);
		Gdx.app.log("tag", String.format("setPreviousScreen end stack size = %d", screenStack.size()));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		//manager.dispose();
		batch.dispose();
	}
}
