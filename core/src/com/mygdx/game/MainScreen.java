package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.awt.*;

public class MainScreen implements Screen {
    TankStars game;
//    Sound soundgame;
    Music music;
    OrthographicCamera camera;
    Sprite sprite;
    Stage stage;
    int ismenu=0;

    Viewport viewport;
    final float GAME_WIDTH= 192;
    final float GAME_HEIGHT= 100;
    private Skin skin;
    ShapeRenderer bg1,bg2;
    public MainScreen(final TankStars game){
        this.game=game;
        music=Gdx.audio.newMusic(Gdx.files.internal("Music/Menu.mp3"));
//        sound.loop(1.0f,1.0f,1.0f);
//        music.setLooping(true);
        music.play();
        bg1=new ShapeRenderer();
        bg2=new ShapeRenderer();
        sprite=new Sprite(new Texture(Gdx.files.internal("MainScreen - Copy.jpg")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WIDTH,GAME_HEIGHT);
        stage=new Stage(new ScreenViewport());

//        float aspectRatio=(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        camera = new OrthographicCamera();
        viewport=new StretchViewport(GAME_WIDTH,GAME_HEIGHT,camera);
//        viewport=new ScreenViewport();
        viewport.apply();
//        camera.setToOrtho(false,800,400);
        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);
        skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        Button vsfriend=new TextButton("Vs friend",skin,"small");
        vsfriend.setSize(150,50);
        vsfriend.setPosition(760,400);
        vsfriend.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                game.setScreen(new ChooseTankVSfriend(game));
            }
        });

        Button vscomp=new TextButton("VS COMPUTER",skin,"small");
        vscomp.setSize(150,50);
        vscomp.setPosition(760,300);
        vscomp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                game.setScreen(new ChooseTankVScomp(game));
            }
        });

        Button savedgames=new TextButton("SAVED\nGAMES",skin,"small");
        savedgames.setSize(150,50);
        savedgames.setPosition(760,200);
        savedgames.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                System.out.println("Saved Games");
                game.setScreen(new Savedgames(game));
            }
        });

        ImageButton setting=new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("setting.png")))));
        setting.setSize(60,60);
        setting.setPosition(32,525);
        setting.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ismenu=1;
                final com.badlogic.gdx.scenes.scene2d.ui.Image placeholder=new com.badlogic.gdx.scenes.scene2d.ui.Image(new Texture(Gdx.files.internal("placeholder.jpg")));
                placeholder.setPosition((Gdx.graphics.getWidth()-500)/2,0);
                stage.addActor(placeholder);

                final Group group=new Group();
                ImageButton cross=new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("cross.png")))));

                cross.setPosition(700f,560f);
                cross.setSize(40f,40f);
                cross.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        ismenu=0;
                        group.setPosition(1000f,0);
                        placeholder.setPosition(100000f,0f);

                    }
                });
                com.badlogic.gdx.scenes.scene2d.ui.Label label=new Label("Settings",skin);
                label.setFontScale(1.5f);
                label.setPosition(500f,570f);

                Button store=new TextButton(" Store ",skin,"small");
                store.setPosition(415,430);
                store.setSize(260,80);
                store.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        ismenu=0;
                        group.setPosition(1000f,0);
                        placeholder.setPosition(100000f,0f);

                    }
                });

                Button sound=new TextButton(" Sound : ON ",skin,"small");
                sound.setPosition(415,300);
                sound.setSize(260,80);
                Button music=new TextButton(" Music : ON ",skin,"small");
                music.setPosition(415,170);
                music.setSize(260,80);
//                Button returnmenu=new TextButton(" Main menu ",skin,"small");
//                returnmenu.setPosition(420,130);
//                returnmenu.setSize(250,60);
//                returnmenu.addListener(new ClickListener(){
//                    @Override
//                    public void clicked(InputEvent event,float x,float y){
//                        game.setScreen(new MainScreen(game));
//                    }
//                });


                group.addActor(cross);
                group.addActor(label);
                group.addActor(store);
                group.addActor(sound);
                group.addActor(music);


                stage.addActor(group);
            }
        });
        stage.addActor(vsfriend);
        stage.addActor(vscomp);
        stage.addActor(setting);
        stage.addActor(savedgames);
        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void show() {
//        soundmusic.setVolume(1f);
//        soundmusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (ismenu==1){
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            bg1.begin(ShapeRenderer.ShapeType.Filled);
            bg1.rect(0f,0f,290,Gdx.graphics.getHeight());

            bg1.setColor(new Color(0.13f, 0.3f, 0.5f, 0.5f));


            bg1.end();
            bg2.begin(ShapeRenderer.ShapeType.Filled);
            bg2.rect(790f,0f,290,Gdx.graphics.getHeight());

            bg2.setColor(new Color(0.13f, 0.3f, 0.5f, 0.5f));


            bg2.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

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
        sprite.getTexture().dispose();
        music.dispose();

    }
}
