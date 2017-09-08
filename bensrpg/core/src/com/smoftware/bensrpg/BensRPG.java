package com.smoftware.bensrpg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smoftware.bensrpg.controllers.ActionButtons;
import com.smoftware.bensrpg.controllers.FixedThumbpadController;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.sprites.Hero;

import java.util.Stack;

public class BensRPG extends Game {

	public static final boolean bDebug = true;

	// NOTE!!! SETTINGS FOR DESKTOP ARE IN THE DesktopLauncher
	public static int V_WIDTH = 256;
	public static int V_HEIGHT = 160;
	public static float ASPECT_RATIO = (float)V_WIDTH/(float)V_HEIGHT;
	public static final float PPM = 100;	// pixels per meter

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short HERO_BIT = 1;
	public static final short ARMORY_DOOR_BIT = 2;
	public static final short ARMORY_EXIT_DOOR_BIT = 4;
	public static final short GENERIC_OBJECT_BIT = 8;
	public static final short OBSTACLE_BIT = 16;
	public static final short BOUNDS_OBJECT_BIT = 32;
	public static final short WATER_BIT = 64;
	public static final short NEXT_MAP_BIT = 128;
	public static final short PREV_MAP_BIT = 256;
	public static final short ZERO_OPACITY = 512;
	public static final short FIREBALL_BIT = 1024;

	/*
	nothing
	obstacle
	to next map
	to prev map
	interaction (sign, people, gems, doors, etc.)
	water
	bridge
	under bridge
	waterfall
	zero opacity
	player
	 */

	public static Hero player;
	public static GameState state;
	public SpriteBatch batch;
	private Stack screenStack;
	private Screen currentScreen;

	private OrthographicCamera camera;
	private Viewport viewport;
	private Stage stage;
	private FixedThumbpadController touchpad;
	private ActionButtons actionButtons;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	//public static AssetManager manager;

	@Override
	public void create () {
		player = new Hero();
		batch = new SpriteBatch();
		screenStack = new Stack();
		state = new GameState(); //todo: load state from file

		// set width and height to fill screen
		ASPECT_RATIO = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		V_HEIGHT = (int)((float)V_WIDTH / ASPECT_RATIO);

		Gdx.app.log("tag", String.format("screen width = %d, height = %d", V_WIDTH, V_HEIGHT));

		//Create camera and view port
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10f*ASPECT_RATIO, 10f);
		viewport = new FitViewport(BensRPG.V_WIDTH, BensRPG.V_HEIGHT, new OrthographicCamera());

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            //Create a Stage and controllers
            stage = new Stage(viewport, batch);
            actionButtons = new ActionButtons(this);
			// todo: FloatingThumbpadController has an issue with being reset to 0 position if the A or B button are pressed
            touchpad = new FixedThumbpadController();//FloatingThumbpadController();
            stage.addActor(touchpad.getTouchpad());
            stage.addActor(actionButtons.buttonTable);
            actionButtons.setStage(stage);
            Gdx.input.setInputProcessor(stage);
        }
		else
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
		Map1Screen s = new Map1Screen(this);
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
			currentScreen = (Screen)screenStack.peek();
			setScreen(currentScreen);
		}
		//Gdx.app.log("tag", String.format("setPreviousScreen end stack size = %d", screenStack.size()));
	}

	public void setCurrentScreen(Screen screen) {
		//Gdx.app.log("tag", String.format("setCurrentScreen begin stack size = %d", screenStack.size()));
		currentScreen = screen;
		screenStack.push(currentScreen);
		setScreen(currentScreen);
		//Gdx.app.log("tag", String.format("setCurrentScreen end stack size = %d", screenStack.size()));
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);

		if (Gdx.app.getType() == Application.ApplicationType.Android)
			actionButtons.resize(width, height);
	}

	public void update(float dt){
		handleInput();
	}

	public void handleInput(){
		/*
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			// For Android, this type of handling should only be used to see if button is being held down.
			// For one shot button presses it is handled in the ActionButton listeners
			if (actionButtons.isRightPressed()) {
                Gdx.app.log("tag", "A pressed");
            }
            else {
				Gdx.app.log("tag", "A released");
			}

			if (actionButtons.isLeftPressed()) {
                Gdx.app.log("tag", "B pressed");
                player.handleBButtonPressed();
			}
			else {
				Gdx.app.log("tag", "B released");
				player.handleBButtonReleased();
			}
		}
		*/
	}

	@Override
	public void render () {
		super.render();
		update(Gdx.graphics.getDeltaTime());
		camera.update();

		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			//Move player with Touchpad - velocity is directly proportional to the knob position
			//player.b2body.setLinearVelocity(touchpad.getDirection());
			//Gdx.app.log("tag", String.format("touchpad = %3.2f", touchpad.getDirection().x));
			player.b2body.setLinearVelocity(touchpad.getDirection().x * Hero.velocityXFactor, touchpad.getDirection().y * Hero.velocityYFactor);
		}

		batch.begin();
		player.draw(batch);
		batch.end();

		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
	}

	@Override
	public void dispose () {
		super.dispose();
		//manager.dispose();
		batch.dispose();
	}
}
