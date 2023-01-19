package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SplashScreen implements Screen {
    Texture img;
    TankStars game;
    ShapeRenderer status;
    ShapeRenderer statusupgrade;
    int upgrade=50;

    OrthographicCamera camera;
    Sprite sprite;
    Viewport viewport;
    final float GAME_WIDTH= 1920;
    final float GAME_HEIGHT= 1080;


    public SplashScreen(TankStars game){

        this.game=game;
        status=new ShapeRenderer();
        statusupgrade=new ShapeRenderer();
        sprite=new Sprite(new Texture(Gdx.files.internal("HomeImage.png")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WIDTH,GAME_HEIGHT);

//        float aspectRatio=(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
//                float aspectRatio=(float)1080/(float)1920;

        camera = new OrthographicCamera();
        viewport=new StretchViewport(GAME_WIDTH,GAME_HEIGHT,camera);
        viewport.apply();
//        camera.setToOrtho(false,800,400);
        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();
        status.begin(ShapeRenderer.ShapeType.Filled);
        status.rect(90,15,900,15);
        status.end();
        statusupgrade.begin(ShapeRenderer.ShapeType.Filled);
        statusupgrade.rect(90,15,upgrade,15);
        statusupgrade.setColor(Color.GREEN);

        statusupgrade.end();
        boolean shouldRender = (upgrade<=900);

        if (shouldRender) {
            upgrade+=50;
            Gdx.graphics.requestRendering();}


        if (upgrade>900){
            game.setScreen((new MainScreen(game)));
//            dispose();
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
    }
}
