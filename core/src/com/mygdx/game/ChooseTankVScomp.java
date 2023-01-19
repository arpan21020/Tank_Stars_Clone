package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

public class ChooseTankVScomp implements Screen {
    Texture img;
    TankStars game;
    int tanknum=1;
    int tank1,tank2;
    Label tankname;
    OrthographicCamera camera;
    Sprite background;
    Viewport viewport;
    Stage stage;
    Skin skin;
    final float GAME_WIDTH= 1920;
    final float GAME_HEIGHT= 1080;
    public ChooseTankVScomp(final TankStars game){
        this.game=game;
        skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        background=new Sprite(new Texture(Gdx.files.internal("Space.jpg")));

        background.setPosition(0,0);
        background.setSize(GAME_WIDTH,GAME_HEIGHT);


//        float aspectRatio=(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
//                float aspectRatio=(float)1080/(float)1920;

        camera = new OrthographicCamera();
        viewport=new StretchViewport(GAME_WIDTH,GAME_HEIGHT,camera);
        viewport.apply();
//        camera.setToOrtho(false,800,400);
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);
//        Image back = new Image(new Texture(Gdx.files.internal("back.png")));
        ImageButton back=new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("back.png")))));

        back.setPosition(32f,525f);
        back.setSize(60f,60f);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                game.setScreen(new MainScreen(game));
            }
        });

        Group group1= new Group();
        Image placeholder2 = new Image(new Texture(Gdx.files.internal("placeholder2.jpg")));
        placeholder2.setPosition(700f,310f);
        placeholder2.setSize(350f,60f);
        tankname = new Label("ABRAMS",skin);
        tankname.setPosition(800f,330f);
        tankname.setFontScale(2f);
        group1.addActor(placeholder2);
        group1.addActor(tankname);
        group1.setPosition(0f,0f);

        final Image sidetank1 = new Image(new Texture(Gdx.files.internal("Tanks/Abrams.png"))) ;
        final Image sidetank2 = new Image(new Texture(Gdx.files.internal("Tanks/Frost.png"))) ;
        final Image sidetank3 = new Image(new Texture(Gdx.files.internal("Tanks/Coalition.png"))) ;
        sidetank1.setPosition(30f,-10f);
        sidetank2.setPosition(1030f,-10f);
        sidetank3.setPosition(1030f,-10f);
//        sidetank1.setAlign(0);

        Image placeholder = new Image(new Texture(Gdx.files.internal("placeholder.jpg"))) ;
        placeholder.setPosition(650f,0f);


        Button play=new TextButton("Play",skin,"small");
        play.setSize(200f,75f);
        play.setPosition(750f,150f);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                tank1=tanknum;
                Random ran=new Random();
                tank2= ran.nextInt(1,4);
                game.setScreen(new BattleArena(game,tank1,tank2));
            }
        });



        ImageButton left=new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("back.png")))));

        left.setPosition(670f,300f);
        left.setSize(75f,75f);
        ImageButton right=new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("right.png")))));

        right.setPosition(1000f,300f);
        right.setSize(75f,75f);

        left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y) {
                leftListner(tanknum,sidetank1,sidetank2,sidetank3);

            }
        });
        right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
               rightListner(tanknum,sidetank1,sidetank2,sidetank3);
            }
        });

        stage.addActor(sidetank1);
        stage.addActor(sidetank2);
        stage.addActor(sidetank3);

        stage.addActor(placeholder);
        stage.addActor(group1);
        stage.addActor(back);
        stage.addActor(play);
//        stage.addActor(upgrade);
        stage.addActor(left);
        stage.addActor(right);



    }
    public void leftListner(int tanknumber,Image sidetank1,Image sidetank2,Image sidetank3){
        if (tanknumber == 1) {
            this.tanknum = 3;
            this.tankname.setText("COALITION");

            sidetank3.setPosition(30f, -10f);
            sidetank2.setPosition(1030,-10f);
            sidetank1.setPosition(1030,-10f);

        } else if (tanknumber == 2) {
            this.tanknum = 1;
            this.tankname.setText("ABRAMS");

            sidetank1.setPosition(30f, -10f);
            sidetank2.setPosition(1030,-10f);
            sidetank3.setPosition(1030,-10f);


        } else if (tanknumber == 3) {
            this.tanknum = 2;
            this.tankname.setText("FROST");
            sidetank2.setPosition(30f, -10f);
            sidetank3.setPosition(1030f, -10f);
            sidetank1.setPosition(1030f, -10f);
        }
    }

    public void rightListner(int tanknumber,Image sidetank1,Image sidetank2,Image sidetank3){
        if (tanknumber == 1) {
            this.tanknum = 2;
            this.tankname.setText("FROST");
            sidetank2.setPosition(30f, -10f);
            sidetank3.setPosition(1030,-10f);
            sidetank1.setPosition(1030,-10f);

        } else if (tanknumber == 2) {
            this.tanknum = 3;
            this.tankname.setText("COALITION");

            sidetank3.setPosition(30f, -10f);
            sidetank2.setPosition(1030,-10f);
            sidetank1.setPosition(1030,-10f);


        } else if (tanknumber == 3) {
            this.tanknum = 1;
            this.tankname.setText("ABRAMS");

            sidetank1.setPosition(30f, -10f);
            sidetank3.setPosition(1030f, -10f);
            sidetank2.setPosition(1030f, -10f);
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch2.begin();
        background.draw(game.batch2);
        game.batch2.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);
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
        background.getTexture().dispose();
    }
}
