package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

public class Tank extends Sprite {
    public World world;
    public Body b2body;
    BattleArena arena;
    public SpriteBatch batch;
    Sprite sprite;
    Image tank1;
    Stage stage;
    private int pl;
    private float health;
    private float angle;
    private float power;
    private  float fuel;

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public int getPl() {
        return pl;
    }

    public void setPl(int pl) {
        this.pl = pl;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public Tank(BattleArena arena, int player){
        this.arena=arena;
        this.pl=player;
        this.world=arena.getWorld();
        this.health=350f;
        this.angle=45;
        this.power=50;
        this.fuel=50;
        if (player==1){

            tank1=arena.p1tank;
        } else if (player==2) {
            System.out.println("player:2"+pl);
            tank1=arena.p2tank;
        }
        tank1.setSize(70,70);

        stage=new Stage(new ScreenViewport());
        arena.stage.addActor(tank1);

        tankGenerate();
    }
    public void tankGenerate(){
        BodyDef bdef=new BodyDef();
        sprite=new Sprite(new Texture(Gdx.files.internal("Tanks/Abrams.png")));

        sprite.setSize(50f,50f);
        bdef.type=BodyDef.BodyType.DynamicBody;
        if (this.pl==1){

            bdef.position.set(200,350);
        } else if (this.pl==2) {


            bdef.position.set(1900,400);
        }
        b2body=world.createBody(bdef);

        FixtureDef fdef =new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(20);
        fdef.shape=shape;
        fdef.friction=5.1f;
//        fdef.density=1.2f;
//        fdef.
        b2body.createFixture(fdef).setUserData(this);
    }
    public void update(){
//        arena.game.batch3.begin();
//        sprite.draw(arena.game.batch3);
//        arena.game.batch3.end();

        tank1.setPosition(b2body.getPosition().x/1.98f,b2body.getPosition().y/1.848f);
//        tank1.setPosition(b2body.getPosition().x,b2body.getPosition().y);
//        System.out.println("x:"+b2body.getPosition().x);
//        System.out.println("y:"+b2body.getPosition().y);

        stage.draw();
    }

}
