package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class Panel extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture hpImage;
    private Texture blank;
    private Sprite sprite,sprite2,sprite3,sprite4;
    private float health = 0.2f;//1 full health, 0 dead
    private ShapeRenderer shapeRenderer;

    Panel() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font_01.fnt"));
        font.setColor(Color.DARK_GRAY);
        sprite = new Sprite(new Texture("hp.png"));
        sprite2 = new Sprite(new Texture("hp2.png"));
        sprite3 = new Sprite(new Texture("hp3.png"));
        sprite4 = new Sprite(new Texture("hp4.png"));
        blank = new Texture("blank.png");
        shapeRenderer=new ShapeRenderer();

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        //hpImage.dispose();
    }

    public void render() {
        batch.begin();
        font.draw(batch, "score", 50, 150);
        font.draw(batch, "hp", 1580, 150);

        //healthbar
        if (health > 0.75f) {
            sprite.setCenter(1500, 120);
            sprite.draw(batch);
            batch.setColor(Color.valueOf("009999"));
        } else if (health > 0.5f) {
            sprite2.setCenter(1500, 120);
            sprite2.draw(batch);
            batch.setColor(Color.valueOf("00CCCC"));
        } else if(health > 0.25f){
            sprite3.setCenter(1500, 120);
            sprite3.draw(batch);
            batch.setColor(Color.valueOf("99FFFF"));
        }else{
            sprite4.setCenter(1500, 120);
            sprite4.draw(batch);
            batch.setColor(Color.valueOf("99FFFF"));
        }

        batch.draw(blank, 1680, 100, 200 * health, 30);
        batch.end();

        shapeRenderer.setColor(Color.valueOf("009999"));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(1680, 100, 200, 30);
        shapeRenderer.end();


    }
}
