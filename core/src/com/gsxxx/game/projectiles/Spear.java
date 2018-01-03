package com.gsxxx.game.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gsxxx.game.MammothGame;

public class Spear extends ProjectilesPrototype{

    private SpriteBatch batch;
    private Sprite projectileSprite;

    private Body spearBody;


    public Spear(int projectileStartingPositionX, int projectileStartingPositionY){
        //set starting position
        float projectileImageScale = 0.5f;

        //spear look
        batch = new SpriteBatch();
        projectileSprite = new Sprite(new Texture("spear.png"));
        projectileSprite.setScale(projectileImageScale);

        //spear's body
        BodyDef spearBodyDef = new BodyDef();
        spearBodyDef.type = BodyDef.BodyType.DynamicBody;
        spearBodyDef.position.set(projectileStartingPositionX, projectileStartingPositionY);
        spearBody = MammothGame.world.createBody(spearBodyDef);
        PolygonShape spearHitbox = new PolygonShape();
        spearHitbox.setAsBox(projectileSprite.getWidth(), projectileSprite.getHeight());
        // create a fixture definition to apply our shape to
        FixtureDef spearFixtureDef = new FixtureDef();
        spearFixtureDef.shape = spearHitbox;
        spearFixtureDef.density = 500f;
        spearFixtureDef.friction = 0.4f;
        spearFixtureDef.restitution = 0.1f; // make it not bounce at all
        spearBody.createFixture(spearFixtureDef);

        //apply force to spear
//        spearBody.applyForceToCenter(0.0f, -100.0f, true);
//        spearBody.applyLinearImpulse(100.0f, 1000f, 800, 400, true);

        spearHitbox.dispose();
    }
    public void render(){
        batch.begin();
        projectileSprite.setRotation(MathUtils.radiansToDegrees * spearBody.getAngle());
        projectileSprite.setPosition(spearBody.getPosition().x, spearBody.getPosition().y);
        projectileSprite.draw(batch);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }
}
