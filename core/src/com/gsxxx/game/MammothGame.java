package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

public class MammothGame extends ApplicationAdapter {

    private EndlessScrollingBackground background;
    private Mammoth mammoth;
    Ribbon ribbon;

    @Override
    public void create() {
        ribbon = new Ribbon();
        //gestures listener initialization
        Gdx.input.setInputProcessor(new GestureDetector(new OverriddenGesturesListener(ribbon)));

        background = new EndlessScrollingBackground();
        mammoth = new Mammoth();
    }

    @Override
    public void render() {
        background.render();
        mammoth.render();
        ribbon.render();
    }

    @Override
    public void dispose() {
        background.dispose();
        mammoth.dispose();
    }
}
