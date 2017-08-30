package com.smoftware.bensrpg.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.ArmoryScreen;
import com.smoftware.bensrpg.screens.PlayScreen;

/**
 * Created by brentaureli on 8/27/15.
 */
public class Hero extends Sprite implements InputProcessor{

    public enum State { WALKING_LEFT, WALKING_RIGHT, WALKING_UP, WALKING_DOWN, FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD };
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private Animation heroWalkingUp;
    private Animation heroWalkingDown;
    private Animation heroWalkingRight;
    private Animation heroWalkingLeft;
    private TextureRegion heroUpStanding;
    private TextureRegion heroDownStanding;
    private TextureRegion heroRightStanding;
    private TextureRegion heroLeftStanding;

    private float stateTimer;
    private PlayScreen screenPlay;
    private ArmoryScreen screenArmory;

    private float lastPositionX;
    private float lastPositionY;
    private boolean isDefined;

    private short numButtonsDown;
    private boolean leftKeyDown;
    private boolean rightKeyDown;
    private boolean upKeyDown;
    private boolean downKeyDown;

    float velocityX;
    float velocityY;
    float velocityXFactor;
    float velocityYFactor;

    public Hero(){
        //initialize default values
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        lastPositionX = 32;
        lastPositionY = 72;
        isDefined = false;

        numButtonsDown = 0;
        leftKeyDown = false;
        rightKeyDown = false;
        upKeyDown = false;
        downKeyDown = false;

        velocityX = 1.0f;
        velocityY = 1.0f;
        velocityXFactor = 0.8f;
        velocityYFactor = 0.8f;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        Texture texture = new Texture(Gdx.files.internal("sprites/Character_1.png"));

        heroDownStanding = new TextureRegion(texture, 0, 0, 16, 16);
        frames.add(new TextureRegion(texture, 1 * 16, 0, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 0, 16, 16));
        frames.add(new TextureRegion(texture, 2 * 16, 0, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 0, 16, 16));
        heroWalkingDown = new Animation(0.1f, frames);
        frames.clear();

        heroLeftStanding = new TextureRegion(texture, 0, 16, 16, 16);
        frames.add(new TextureRegion(texture, 1 * 16, 16, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 16, 16, 16));
        frames.add(new TextureRegion(texture, 2 * 16, 16, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 16, 16, 16));
        heroWalkingLeft = new Animation(0.1f, frames);
        frames.clear();

        heroRightStanding = new TextureRegion(texture, 0, 32, 16, 16);
        frames.add(new TextureRegion(texture, 1 * 16, 32, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 32, 16, 16));
        frames.add(new TextureRegion(texture, 2 * 16, 32, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 32, 16, 16));
        heroWalkingRight = new Animation(0.1f, frames);
        frames.clear();

        heroUpStanding = new TextureRegion(texture, 0, 48, 16, 16);
        frames.add(new TextureRegion(texture, 1 * 16, 48, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 48, 16, 16));
        frames.add(new TextureRegion(texture, 2 * 16, 48, 16, 16));
        frames.add(new TextureRegion(texture, 0 * 16, 48, 16, 16));
        heroWalkingUp = new Animation(0.1f, frames);
        frames.clear();

        //set initial values for hero's location, width and height. And initial frame as standing.
        setBounds(0, 0, 16 / BensRPG.PPM, 16 / BensRPG.PPM);
        setRegion(heroDownStanding);

    }

    public void setScreen(PlayScreen screen) {
        screenPlay = screen;
        world = screen.getWorld();

        Gdx.app.log("tag", String.format("lastPositionX = %3.2f, lastPositionY = %3.2f", lastPositionX, lastPositionY));

        //define hero in Box2d, at last known position
        defineHero(lastPositionX / BensRPG.PPM, lastPositionY / BensRPG.PPM);
    }

    public void setScreen(ArmoryScreen screen) {
        screenArmory = screen;
        world = screen.getWorld();

        //define hero in Box2d, centered at bottom of screen
        //float x = (Gdx.graphics.getWidth()/2 - getWidth()/2)/BensRPG.PPM;
        //lastPositionX = getX() * BensRPG.PPM;
        lastPositionY = getY() * BensRPG.PPM;

        lastPositionX = ((getX() + getWidth()/2)) * BensRPG.PPM;
        //lastPositionY = ((getY() + getHeight()/2)) * BensRPG.PPM;

        float x = 72 / BensRPG.PPM;
        float y = 24 / BensRPG.PPM;
        defineHero(x, y);
    }

    @Override
    public boolean keyDown(int keycode) {
        //Gdx.app.log("tag", String.format("keycode = %d", keycode));

        //if two buttons are down, cut down current and max velocities since
        //velocity is being applied in two directions

        if (keycode == Input.Keys.UP) {
            numButtonsDown++;
            upKeyDown = true;

            if (leftKeyDown)
                b2body.setLinearVelocity(new Vector2(-velocityX * velocityXFactor, velocityY * velocityYFactor));
            else if (rightKeyDown)
                b2body.setLinearVelocity(new Vector2(velocityX * velocityXFactor, velocityY * velocityYFactor));
            else if (downKeyDown) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                setRegion(heroDownStanding);
            }
            else
                b2body.setLinearVelocity(new Vector2(0, velocityY));
        }
        if (keycode == Input.Keys.DOWN) {
            numButtonsDown++;
            downKeyDown = true;

            if (leftKeyDown)
                b2body.setLinearVelocity(new Vector2(-velocityX * velocityXFactor, -velocityY * velocityYFactor));
            else if (rightKeyDown)
                b2body.setLinearVelocity(new Vector2(velocityX * velocityXFactor, -velocityY * velocityYFactor));
            else if (upKeyDown) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                setRegion(heroUpStanding);
            }
            else
                b2body.setLinearVelocity(new Vector2(0, -velocityY));
        }
        if (keycode == Input.Keys.RIGHT) {
            numButtonsDown++;
            rightKeyDown = true;

            if (downKeyDown)
                b2body.setLinearVelocity(new Vector2(velocityX * velocityXFactor, -velocityY * velocityYFactor));
            else if (upKeyDown)
                b2body.setLinearVelocity(new Vector2(velocityX * velocityXFactor, velocityY * velocityYFactor));
            else if (leftKeyDown) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                setRegion(heroLeftStanding);
            }
            else
                b2body.setLinearVelocity(new Vector2(velocityX, 0));
        }
        if (keycode == Input.Keys.LEFT) {
            numButtonsDown++;
            leftKeyDown = true;

            if (downKeyDown)
                b2body.setLinearVelocity(new Vector2(-velocityX * velocityXFactor, -velocityY * velocityYFactor));
            else if (upKeyDown)
                b2body.setLinearVelocity(new Vector2(-velocityX * velocityXFactor, velocityY * velocityYFactor));
            else if (rightKeyDown) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                setRegion(heroRightStanding);
            }
            else
                b2body.setLinearVelocity(new Vector2(-velocityX, 0));
        }

        return true;
    }

    void setLinearVelocityForOneButtonDown() {
        if (numButtonsDown == 1) {
            if (leftKeyDown)
                b2body.setLinearVelocity(new Vector2(-velocityX, 0));
            else if (rightKeyDown)
                b2body.setLinearVelocity(new Vector2(velocityX, 0));
            else if (upKeyDown)
                b2body.setLinearVelocity(new Vector2(0, velocityX));
            else if (downKeyDown)
                b2body.setLinearVelocity(new Vector2(0, -velocityY));
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            numButtonsDown--;
            leftKeyDown = false;

            if (numButtonsDown == 0) {
                setRegion(heroLeftStanding);
                b2body.setLinearVelocity(new Vector2(0, 0));
            }
            else if (numButtonsDown == 1)
                setLinearVelocityForOneButtonDown();
        }
        if (keycode == Input.Keys.RIGHT) {
            numButtonsDown--;
            rightKeyDown = false;

            if (numButtonsDown == 0) {
                setRegion(heroRightStanding);
                b2body.setLinearVelocity(new Vector2(0, 0));
            }
            else if (numButtonsDown == 1)
                setLinearVelocityForOneButtonDown();
        }
        if (keycode == Input.Keys.UP) {
            numButtonsDown--;
            upKeyDown = false;

            if (numButtonsDown == 0) {
                setRegion(heroUpStanding);
                b2body.setLinearVelocity(new Vector2(0, 0));
            }
            else if (numButtonsDown == 1)
                setLinearVelocityForOneButtonDown();
        }
        if (keycode == Input.Keys.DOWN) {
            numButtonsDown--;
            downKeyDown = false;

            if (numButtonsDown == 0) {
                setRegion(heroDownStanding);
                b2body.setLinearVelocity(new Vector2(0, 0));
            }
            else if (numButtonsDown == 1)
                setLinearVelocityForOneButtonDown();
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void update(float dt){

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //update sprite with the correct frame depending on hero's current action
        //if standing, then don't update
        if (getState() != State.STANDING)
            setRegion(getFrame(dt));

    }

    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        Gdx.app.log("tag", String.format("currentState = %s", currentState.toString()));

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case WALKING_LEFT:
                region = (TextureRegion)heroWalkingLeft.getKeyFrame(stateTimer, true);
                break;
            case WALKING_RIGHT:
                region = (TextureRegion)heroWalkingRight.getKeyFrame(stateTimer, true);
                break;
            case WALKING_UP:
                region = (TextureRegion)heroWalkingUp.getKeyFrame(stateTimer, true);
                break;
            case WALKING_DOWN:
                region = (TextureRegion)heroWalkingDown.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = heroDownStanding;
                break;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        Gdx.app.log("tag", String.format("x = %3.2f, y = %3.2f", b2body.getLinearVelocity().x, b2body.getLinearVelocity().y));

        if (b2body.getLinearVelocity().y > 0.6f)
            return State.WALKING_UP;
        else if (b2body.getLinearVelocity().y < -0.6f)
            return State.WALKING_DOWN;
        else if (b2body.getLinearVelocity().x > 0)
            return State.WALKING_RIGHT;
        else if (b2body.getLinearVelocity().x < 0)
            return State.WALKING_LEFT;
        else
            return State.STANDING;
    }

    public float getStateTimer(){
        return stateTimer;
    }

/*
    public void hit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.STANDING_SHELL)
            ((Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? Turtle.KICK_RIGHT : Turtle.KICK_LEFT);
        else {
            if (marioIsBig) {
                marioIsBig = false;
                timeToRedefineMario = true;
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                BensRPG.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
            } else {
                die();
            }
        }
    }
    */


    public void defineHero(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / BensRPG.PPM);
        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(6f / BensRPG.PPM, 6f / BensRPG.PPM);
        fdef.filter.categoryBits = BensRPG.HERO_BIT;
        fdef.filter.maskBits = BensRPG.GENERIC_OBJECT_BIT |
                               BensRPG.BOUNDS_OBJECT_BIT |
                               BensRPG.ARMORY_DOOR_BIT |
                               BensRPG.ARMORY_EXIT_DOOR_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.0f;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / BensRPG.PPM, 6 / BensRPG.PPM), new Vector2(2 / BensRPG.PPM, 6 / BensRPG.PPM));
        fdef.filter.categoryBits = BensRPG.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
        head.dispose();

        isDefined = true;
    }
/*
    public void fire(){
        fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }
*/
    public void draw(Batch batch){
        super.draw(batch);
        //for(FireBall ball : fireballs)
        //    ball.draw(batch);
    }
}
