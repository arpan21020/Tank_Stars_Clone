package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Savedgames implements Screen {

    TankStars game;
    OrthographicCamera camera;
    ShapeRenderer placeholder;
    Sprite sprite;
    Stage stage;

    Viewport viewport;
    final float GAME_WIDTH= 192;
    final float GAME_HEIGHT= 100;
    private Skin skin;
    public Savedgames(final TankStars game){
        placeholder=new ShapeRenderer();
        this.game=game;
        sprite=new Sprite(new Texture(Gdx.files.internal("PurpleEvening.png")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WIDTH,GAME_HEIGHT);
        skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


//        float aspectRatio=(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        camera = new OrthographicCamera();
        viewport=new StretchViewport(GAME_WIDTH,GAME_HEIGHT,camera);
//        viewport=new ScreenViewport();
        viewport.apply();
//        camera.setToOrtho(false,800,400);
        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);

        ImageButton back=new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("back.png")))));

        back.setPosition(32f,525f);
        back.setSize(60f,60f);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainScreen(game));
            }
        });

        Label label=new Label("Saved Games",skin);
        label.setFontScale(2f);
        label.setPosition(400f,550f);
        stage.addActor(back);
        stage.addActor(label);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        placeholder.begin(ShapeRenderer.ShapeType.Filled);
        placeholder.rect((Gdx.graphics.getWidth()-800)/2,10f,800f,500f);

        placeholder.setColor(new Color(11, 64, 0, 0.5f));


        placeholder.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
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
    }
}
