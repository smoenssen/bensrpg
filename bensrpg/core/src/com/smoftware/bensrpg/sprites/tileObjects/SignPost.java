package com.smoftware.bensrpg.sprites.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.screens.Map1Screen;
import com.smoftware.bensrpg.sprites.Hero;

/**
 * Created by steve on 9/3/17.
 */

public class SignPost extends AbstractInteractiveTileObject{

    private Stage stage;
    private Skin skin;
    private BensRPG game;
    private Hero player;
    private Rectangle bounds;

    public SignPost(String text, BensRPG game, Map1Screen screen, Hero player, MapObject object) {
        this.game = game;
        this.player = player;

        // convert rectangle to correct scaled size
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.bounds.x /= BensRPG.PPM;
        this.bounds.y /= BensRPG.PPM;
        this.bounds.width /= BensRPG.PPM;
        this.bounds.height /= BensRPG.PPM;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/9_px.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.DARK_GRAY;
        parameter.shadowColor = Color.LIGHT_GRAY;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        BitmapFont fontSign = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        stage = new Stage();
/*
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font12;
        button = new TextButton("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean tempor hendrerit hendrerit.", textButtonStyle);
        button.setColor(Color.RED);
*/
        //button.setX(240);
        //button.setY(200);


        skin = new Skin();
        skin.add("myFont", fontSign, BitmapFont.class);
        skin.addRegions(new TextureAtlas(Gdx.files.internal("skin/bensrpg_data/bensrpg.atlas")));
        skin.load(Gdx.files.internal("skin/bensrpg_data/bensrpg.json"));

        TextArea textArea = new TextArea(text, skin);

        float padding = 32;
        float signWidth = Gdx.graphics.getWidth()- (padding * 2);
        float signHeight = 95;
        textArea.setX(padding);
        //srm top of screen: textArea.setY(Gdx.graphics.getHeight() - padding - signHeight);
        textArea.setY(padding);
        textArea.setWidth(signWidth);
        textArea.setHeight(signHeight);
        textArea.setAlignment(Align.center);
        textArea.layout();

        ////////////////////////////////////////
        final Label label = new Label(text, skin);
        label.setAlignment(Align.center);
        label.setWrap(true);
/*
        final Table scrollTable = new Table();
        scrollTable.add(text);
        scrollTable.row();
        scrollTable.add(label);
        scrollTable.row();

        final ScrollPane scroller = new ScrollPane(scrollTable);
*/
        //final Table table = new Table();
        //table.setFillParent(true);
        //table.add(scroller).fill().expand();

        final ScrollPane scroller = new ScrollPane(label);
        scroller.setX(padding);
        scroller.setY(Gdx.graphics.getHeight() - padding - signHeight);
        scroller.setWidth(signWidth);
        scroller.setHeight(signHeight);
        scroller.layout();
        this.stage.addActor(textArea);
        /////////////////////////////////////
/*
        TextField textField = new TextField(text, skin);
        textField.setX(10);
        textField.setY(220);
        textField.setWidth(200);
        textField.setHeight(30);
        stage.addActor(textField);
        */
        //stage.addActor(textArea);


        /*
        Table table = new Table();
        button.setSkin(skin);
        button.getLabelCell().width(BensRPG.V_WIDTH);
        button.getLabelCell().height(50);
        button.getLabel().setWrap(true);
        button.invalidate();

        table.add(button).width(BensRPG.V_WIDTH);
        table.setX(240);
        table.setY(200);
        table.pack();
        stage.addActor(table);
        */
    }

    public void draw() {
        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }


    public Rectangle getBounds() { return bounds; }

    @Override
    public void Interact(Rectangle playerPosition) {
        Gdx.app.log("tag", "Interacting!!!");

        if (playerPosition.overlaps(getBounds())) {
            Gdx.app.log("tag", "player overlapped signpost!!!");
        }
    }
}
