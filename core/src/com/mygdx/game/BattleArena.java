package com.mygdx.game;
import java.lang.*;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BattleArena implements Screen, InputProcessor {

    TankStars game;
    int count;
    Music mus;
    boolean turn=true;
    private Tank player1;
    private Tank player2;
    private TiledMap map;
    private OrthogonalTiledMapRenderer rendered;
    OrthographicCamera camera;
    Sprite sprite;
    Stage stage;
    Image p1tank = new Image(new Texture("Tanks/Abrams.png"));
    Image p2tank=new Image(new Texture("Tanks/Abrams2.png"));
    int ismenu=0;
    int isheal=1;
    float xfire=0;
    int isterrain=1;
    ShapeRenderer bg1;

    ShapeRenderer bg2;
    private World world;

    public World getWorld() {
        return world;
    }

    private Box2DDebugRenderer b2dr;

//    float health1=300f,health2=330f;


    Viewport viewport;
    final float GAME_WIDTH= 1920;
    final float GAME_HEIGHT= 1000;
    private Skin skin;
    ShapeRenderer h1,h2;
    ShapeRenderer nh1,nh2;
    ShapeRenderer fuel;
    ArrayList<ShapeRenderer> aims;
    Bullet fire1;
    Bullet fire2;
    Label fuel1;
    Label fuel2;

    public BattleArena(final TankStars game, int mode, int tank1, int tank2) {
        //Vsfriend
        this.game = game;




        bg1=new ShapeRenderer();
        bg2=new ShapeRenderer();
        h1=new ShapeRenderer();
        h2=new ShapeRenderer();
        nh1=new ShapeRenderer();
        nh2=new ShapeRenderer();
        fuel=new ShapeRenderer();
        aims=new ArrayList<ShapeRenderer>();
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());

        sprite=new Sprite(new Texture(Gdx.files.internal("BlueNight.png")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WIDTH,GAME_HEIGHT);

//        Image terrain =new Image(new Texture(Gdx.files.internal("terrain.png")));
//        terrain.setPosition(-10f,0f);
//        terrain.setSize(1200,600f);
        stage=new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

//        float aspectRatio=(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        camera = new OrthographicCamera(GAME_WIDTH,GAME_HEIGHT);
//        viewport=new StretchViewport(GAME_WIDTH,GAME_HEIGHT,camera);
//        viewport=new ScreenViewport();
//        viewport.apply();

//        camera.setToOrtho(false,800,400);
        world=new World(new Vector2(0,-10),true);
        world.setContactListener(new MyContactListener());
        b2dr=new Box2DDebugRenderer();
        vsfriendTanks(tank1,tank2);

        player1=new Tank(this,1);
        player2=new Tank(this,2);
        fire1=new Bullet(player1);
        fire2=new Bullet(player2);

        BodyDef bdef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fdef =new FixtureDef();
        Body body;
        map=new TmxMapLoader().load("Terraintile.tmx");
        rendered=new OrthogonalTiledMapRenderer(map);
        for (MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body=world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);
        skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Image menu =new Image(new Texture(Gdx.files.internal("menu.png")));
        menu.setSize(60,60);
        menu.setPosition(32,525);
        menu.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               ismenu=1;
               isheal=0;
               isterrain=0;
               final Image placeholder=new Image(new Texture(Gdx.files.internal("placeholder.jpg")));
               placeholder.setPosition((Gdx.graphics.getWidth()-500)/2,0);
               stage.addActor(placeholder);

               final Group group=new Group();

               Label label=new Label("Settings",skin);
               label.setFontScale(1.5f);
               label.setPosition(500f,570f);

               Button resume=new TextButton(" Resume ",skin,"small");
               resume.setPosition(420,490);
               resume.setSize(250,60);
               resume.addListener(new ClickListener(){
                   @Override
                   public void clicked(InputEvent event,float x,float y){
                       ismenu=0;
                       isheal=1;
                       isterrain=1;
                       group.setPosition(1000f,0);
                       placeholder.setPosition(100000f,0f);

                   }
               });

               Button save=new TextButton(" Save Game ",skin,"small");
               save.setPosition(420,400);
               save.setSize(250,60);
               Button sound=new TextButton(" Sound : ON ",skin,"small");
               sound.setPosition(420,320);
               sound.setSize(250,60);
               Button music=new TextButton(" Music : ON ",skin,"small");
               music.setPosition(420,240);
               music.setSize(250,60);
               Button returnmenu=new TextButton(" Main menu ",skin,"small");
               returnmenu.setPosition(420,160);
               returnmenu.setSize(250,60);
               returnmenu.addListener(new ClickListener(){
                   @Override
                   public void clicked(InputEvent event,float x,float y){
                       game.setScreen(new MainScreen(game));

                   }
               });


               group.addActor(label);
               group.addActor(resume);
               group.addActor(save);
               group.addActor(sound);
               group.addActor(music);
               group.addActor(returnmenu);


               stage.addActor(group);
           }
        });


        Label fname1=new Label("FUEL",skin);
        Label fname2=new Label("FUEL",skin);
        fname1.setPosition(50,70);
        fname2.setPosition(1000,70);
        fuel1=new Label(Float.toString(player1.getFuel()),skin);
        fuel2=new Label(Float.toString(player2.getFuel()),skin);
        fuel1.setPosition(50,50);
        fuel2.setPosition(1000,50);
        stage.addActor(fname1);
        stage.addActor(fname2);
        stage.addActor(fuel1);
        stage.addActor(fuel2);
//        stage.addActor(terrain);
//        stage.addActor(fueltag);
//        stage.addActor(this.p1tank);
//        stage.addActor(this.p2tank);
        stage.addActor(menu);

    }

    public BattleArena(final TankStars game,int tank1, int tank2) {
        this.game = game;
        bg1=new ShapeRenderer();
        bg2=new ShapeRenderer();
        h1=new ShapeRenderer();
        h2=new ShapeRenderer();
        nh1=new ShapeRenderer();
        nh2=new ShapeRenderer();
        fuel=new ShapeRenderer();
        aims=new ArrayList<ShapeRenderer>();
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        aims.add(new ShapeRenderer());
        sprite=new Sprite(new Texture(Gdx.files.internal("BlueNight.png")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WIDTH,GAME_HEIGHT);
//        Image terrain =new Image(new Texture(Gdx.files.internal("terrain.png")));
//        terrain.setPosition(-10f,0f);
//        terrain.setSize(1200,600f);
        stage=new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

//        float aspectRatio=(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        camera = new OrthographicCamera(GAME_WIDTH,GAME_HEIGHT);
//        viewport=new StretchViewport(GAME_WIDTH,GAME_HEIGHT,camera);
//        viewport=new ScreenViewport();
//        viewport.apply();

//        camera.setToOrtho(false,800,400);
        world=new World(new Vector2(0,-10),true);
        world.setContactListener(new MyContactListener());
        b2dr=new Box2DDebugRenderer();
        vscompTanks(tank1,tank2);

        player1=new Tank(this,1);
        player2=new Tank(this,2);
        fire1=new Bullet(player1);
        fire2=new Bullet(player2);
        BodyDef bdef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fdef =new FixtureDef();
        Body body;
        map=new TmxMapLoader().load("Terraintile.tmx");
        rendered=new OrthogonalTiledMapRenderer(map);
        for (MapObject object: map.getLayers().get(1).getObjects().getByType((RectangleMapObject.class))){

            Rectangle rect=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body=world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);
        skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        Label fueltag=new Label("Fuel",skin);
        fueltag.setFontScale(0.6f);
        fueltag.setPosition(80f,95f);
        Image menu =new Image(new Texture(Gdx.files.internal("menu.png")));
        menu.setSize(60,60);
        menu.setPosition(32,525);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ismenu=1;
                isheal=0;
                isterrain=0;
                final Image placeholder=new Image(new Texture(Gdx.files.internal("placeholder.jpg")));
                placeholder.setPosition((Gdx.graphics.getWidth()-500)/2,0);
                stage.addActor(placeholder);

                final Group group=new Group();

                Label label=new Label("Settings",skin);
                label.setFontScale(1.5f);
                label.setPosition(500f,570f);

                Button resume=new TextButton(" Resume ",skin,"small");
                resume.setPosition(420,490);
                resume.setSize(250,60);
                resume.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        ismenu=0;
                        isheal=1;
                        isterrain=1;
                        group.setPosition(1000f,0);
                        placeholder.setPosition(100000f,0f);

                    }
                });

                Button save=new TextButton(" Save Game ",skin,"small");
                save.setPosition(420,400);
                save.setSize(250,60);
                Button sound=new TextButton(" Sound : ON ",skin,"small");
                sound.setPosition(420,320);
                sound.setSize(250,60);
                Button music=new TextButton(" Music : ON ",skin,"small");
                music.setPosition(420,240);
                music.setSize(250,60);
                Button returnmenu=new TextButton(" Main menu ",skin,"small");
                returnmenu.setPosition(420,160);
                returnmenu.setSize(250,60);
                returnmenu.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        game.setScreen(new MainScreen(game));
                    }
                });


                group.addActor(label);
                group.addActor(resume);
                group.addActor(save);
                group.addActor(sound);
                group.addActor(music);
                group.addActor(returnmenu);


                stage.addActor(group);
            }
        });



        Label fname1=new Label("FUEL",skin);
        Label fname2=new Label("FUEL",skin);
        fname1.setPosition(50,70);
        fname2.setPosition(1000,70);
        fuel1=new Label("100",skin);
        fuel2=new Label("100",skin);
        fuel1.setPosition(50,50);
        fuel2.setPosition(1000,50);
        stage.addActor(fname1);
        stage.addActor(fname2);
        stage.addActor(fuel1);
        stage.addActor(fuel2);

//        stage.addActor(terrain);
        stage.addActor(fueltag);
//        stage.addActor(this.p1tank);
//        stage.addActor(this.p2tank);
        stage.addActor(menu);
//        map=new TmxMapLoader().load("Terraintile.tmx");
//        rendered=new OrthogonalTiledMapRenderer(map);

    }
    @Override
    public void show() {
        mus=Gdx.audio.newMusic(Gdx.files.internal("Music/Gameplay.mp3"));
        mus.setLooping(true);
        mus.play();

    }
    public void inputHandler(){
        if (turn){
            if (Gdx.input.isKeyPressed(Input.Keys.UP)){
//                player1.b2body.applyLinearImpulse(new Vector2(0,4f),player1.b2body.getWorldCenter(),true);
                player1.setAngle(player1.getAngle()+0.1f);;


            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                player1.setAngle(player1.getAngle()-0.1f);;

//                player1.b2body.applyLinearImpulse(new Vector2(0,-4f),player1.b2body.getWorldCenter(),true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player1.b2body.getPosition().x<1900 && player1.getFuel()>0){
                player1.b2body.applyLinearImpulse(new Vector2(10f,0),player1.b2body.getWorldCenter(),true);
                player1.setFuel(player1.getFuel()-1f);
                fuel1.setText(Float.toString(player1.getFuel()));



            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player1.b2body.getPosition().x>2 && player1.getFuel()>0){
                player1.b2body.applyLinearImpulse(new Vector2(-10f,0),player1.b2body.getWorldCenter(),true);
                player1.setFuel(player1.getFuel()-1f);

                fuel1.setText(Float.toString(player1.getFuel()));


            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)){
                fire1.makebullet();
                float range=horizontalRange(player1);
                Vector2 force=new Vector2((float)Math.cos(Math.toRadians(player1.getAngle()))*player1.getPower(),(float)Math.sin(Math.toRadians(player1.getAngle()))*player1.getPower());
                fire1.b2body.applyLinearImpulse(force,fire1.b2body.getWorldCenter(),true);
                if (range+player1.b2body.getPosition().x>=player2.b2body.getPosition().x-50 && range+player1.b2body.getPosition().x<=player2.b2body.getPosition().x+50 ){
                    player2.setHealth(player2.getHealth()-5f);
                }
                System.out.println("Range from player 1:"+range);
                System.out.println("range+player1.b2body.getPosition().x:"+range+player1.b2body.getPosition().x);
                player1.setFuel(50f);
//                fire.update();

               turn=!turn;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player1.getPower()<=100){
                player1.setPower(player1.getPower()+0.2f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player1.getPower()>=10){
                player1.setPower(player1.getPower()-0.2f);
            }




        }
        if (!turn){
            if (Gdx.input.isKeyPressed(Input.Keys.UP)){
//                player2.b2body.applyLinearImpulse(new Vector2(0,4f),player2.b2body.getWorldCenter(),true);
                player2.setAngle(player2.getAngle()-0.1f);;

            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//                player2.b2body.applyLinearImpulse(new Vector2(0,-4f),player2.b2body.getWorldCenter(),true);
                player2.setAngle(player2.getAngle()+0.1f);;

            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)  && player2.b2body.getPosition().x<1900 &&  player2.getFuel()>0){
                player2.b2body.applyLinearImpulse(new Vector2(10f,0),player2.b2body.getWorldCenter(),true);
                player2.setFuel(player2.getFuel()-1f);
//                fuel2.setText(Float.toString(player2.getFuel()));


            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  && player2.b2body.getPosition().x>2 &&  player2.getFuel()>0){
                player2.b2body.applyLinearImpulse(new Vector2(-10f,0),player2.b2body.getWorldCenter(),true);
                player2.setFuel(player2.getFuel()-1f);
//                fuel2.setText(Float.toString(player2.getFuel()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)){
                fire2.makebullet();
                float range=horizontalRange(player2);
                Vector2 force=new Vector2((float)Math.cos(Math.toRadians(player2.getAngle()))*player2.getPower()*(-1),(float)Math.sin(Math.toRadians(player2.getAngle()))*player2.getPower());
                fire2.b2body.applyLinearImpulse(force,fire2.b2body.getWorldCenter(),true);
                if (player2.b2body.getPosition().x+range>=player1.b2body.getPosition().x-50 &&  player2.b2body.getPosition().x+range<=player1.b2body.getPosition().x+50){
                    player1.setHealth(player1.getHealth()-5f);
                }
                System.out.println("Range from player 2:"+range);
                System.out.println("player2.b2body.getPosition().x+range:"+player2.b2body.getPosition().x+range);
                player2.setFuel(50);

                turn=!turn;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player2.getPower()<=100){
                player2.setPower(player2.getPower()-0.2f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player2.getPower()>=10){
                player2.setPower(player2.getPower()+0.2f);

            }


        }

    }
    public float trajectory(float x,float angle,float power,Tank play){
//        x= (x+play.b2body.getPosition().x/1.98f);
        double xtan=x*Math.tan(Math.toRadians(angle));
        double gx2=(20*x*x)/((2*power*power)*Math.cos(Math.toRadians(angle)));
        double y=xtan-(gx2);

        return (float)y;


    }
    public float horizontalRange(Tank play){
        double r=(play.getPower()* play.getPower())*Math.sin(2*(Math.toRadians(play.getAngle()))/20);
        if (play.getPl()==2){
            r = -r;
        }
        return (float) r;
    }
    public void update(){
        world.step(1/60f,6,2);
        inputHandler();
        player1.update();
        player2.update();
        try{
            fire1.update();
            fire2.update();

        }
        catch (Exception e) {System.out.println(e.getMessage());}


    }
    public void vsfriendTanks(int tank1,int tank2){
        if (tank1==1){
            this.p1tank=new Image(new Texture("Tanks/Abrams.png"));
        } else if (tank1==2) {
            this.p1tank=new Image(new Texture("Tanks/Frost.png"));

        } else if (tank1==3) {
            this.p1tank=new Image(new Texture("Tanks/Coalition.png"));
        }
//        this.p1tank.setSize(50f,50f);
//        this.p1tank.setPosition(115f,115f);

        if (tank2==1){
            this.p2tank=new Image(new Texture("Tanks/Abrams2.png"));
        } else if (tank2==2) {
            this.p2tank=new Image(new Texture("Tanks/Frost2.png"));

        } else if (tank2==3) {
            this.p2tank=new Image(new Texture("Tanks/Coalition2.png"));
        }
//        this.p2tank.setSize(50f,50f);
//        this.p2tank.setPosition(1000f,200f);
    }
    public void vscompTanks(int tank1,int tank2){
        if (tank1==1){
            this.p1tank=new Image(new Texture("Tanks/Abrams.png"));
        } else if (tank1==2) {
            this.p1tank=new Image(new Texture("Tanks/Frost.png"));

        } else if (tank1==3) {
            this.p1tank=new Image(new Texture("Tanks/Coalition.png"));
        }
//        this.p1tank.setSize(50f,50f);
//        this.p1tank.setPosition(115f,115f);

        if (tank2==1){
            this.p2tank=new Image(new Texture("Tanks/Abrams2.png"));
        } else if (tank2==2) {
            this.p2tank=new Image(new Texture("Tanks/Frost2.png"));

        } else if (tank2==3) {
            this.p2tank=new Image(new Texture("Tanks/Coalition2.png"));
        }
//        this.p2tank.setSize(50f,50f);
//        this.p2tank.setPosition(1000f,200f);



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        update();

        b2dr.render(world,camera.combined);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();
        if (isterrain==1){
            rendered.setView(camera);
            rendered.render();
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        fuel1.setText(Float.toString(player1.getFuel()));
        fuel2.setText(Float.toString(player2.getFuel()));

        if (isheal==1){
            h1.begin(ShapeRenderer.ShapeType.Filled);
            h1.rect(200f,550f,350f,15f);
            h1.setColor(Color.RED);
            h1.end();
            h2.begin(ShapeRenderer.ShapeType.Filled);
            h2.rect(650f,550f,350f,15f);
            h2.setColor(Color.RED);
            h2.end();
            nh1.begin(ShapeRenderer.ShapeType.Filled);
            nh1.rect(200f,550f,player1.getHealth(),15f);
            nh1.setColor(Color.GREEN);
            nh1.end();
            nh2.begin(ShapeRenderer.ShapeType.Filled);
            nh2.rect(650f,550f, player2.getHealth(), 15f);
            nh2.setColor(Color.GREEN);
            nh2.end();
            if (turn){
                int i=0;
                for (ShapeRenderer obj:aims){
                    obj.begin(ShapeRenderer.ShapeType.Filled);
//                obj.circle(i+player1.b2body.getPosition().x,400f,2f);
                    obj.circle(50+i+player1.b2body.getPosition().x/1.98f,50+trajectory(i, player1.getAngle(), player1.getPower(), player1)+player1.b2body.getPosition().y/1.848f,2f);
                    obj.end();
                    i+= player1.getPower();
                }
            }
            else {
                int i=0;
                for (ShapeRenderer obj:aims){
                    obj.begin(ShapeRenderer.ShapeType.Filled);
//                obj.circle(i+player1.b2body.getPosition().x,400f,2f);
                    obj.circle(-50-i+player2.b2body.getPosition().x/1.98f,50+trajectory(-i, player2.getAngle(), player2.getPower(), player2)+player2.b2body.getPosition().y/1.848f,2f);
                    obj.end();
                    i+= player2.getPower();
                }
            }

        }



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

//        rendered.setView(camera);
//        rendered.render();

    }

    @Override
    public void resize(int width, int height) {
//        viewport.update(width,height);
        camera.position.set(GAME_WIDTH/2,GAME_HEIGHT/2,0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public void update(float delt){

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        map.dispose();
        rendered.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
