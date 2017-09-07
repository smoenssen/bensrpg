package com.smoftware.bensrpg.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by moenssr on 8/30/2017.
 */

public class ActionButtons {
    Viewport viewport;
    Stage stage = null;
    boolean leftPressed = false, rightPressed = false, leftJustPressed = false, rightJustPressed = false;
    OrthographicCamera cam;
    public Table buttonTable;

    public ActionButtons(BensRPG game){
        final BensRPG thisGame = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(BensRPG.V_WIDTH/BensRPG.PPM, BensRPG.V_HEIGHT/BensRPG.PPM, cam);

        buttonTable = new Table();

        //Gdx.input.setInputProcessor(stage);

        Image rightImg = new Image(new Texture("controllers/touchpadKnob.png"));
        rightImg.setSize(25, 25);
        rightImg.addListener(new InputListener() {

            // Note: these functions are only called once
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "A pressed");
                rightPressed = true;
                BensRPG.player.handleAButtonPressed();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "A released");
                rightPressed = false;
                BensRPG.player.handleAButtonReleased();
            }
        });

        Image leftImg = new Image(new Texture("controllers/touchpadKnob.png"));
        leftImg.setSize(25, 25);
        leftImg.addListener(new InputListener() {

            // Note: these functions are only called once
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "B pressed");
                leftPressed = true;
                BensRPG.player.handleBButtonPressed();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "B released");
                leftPressed = false;
                BensRPG.player.handleBButtonReleased();
            }
        });

        //int xPad = 8;
        // top, left, bottom, right
        buttonTable.row().pad(0, 8, 8, 2);
        buttonTable.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        buttonTable.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());

        buttonTable.left().bottom();

        buttonTable.pack();
        Gdx.app.log("tag", String.format("table width = %3.2f", buttonTable.getWidth()));
        buttonTable.setX(BensRPG.V_WIDTH - buttonTable.getWidth() - 10);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        /*
        //todo: can this be removed???
        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }
        });
        */
    }
    //public void draw(){
    //    stage.draw();
    //}

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
