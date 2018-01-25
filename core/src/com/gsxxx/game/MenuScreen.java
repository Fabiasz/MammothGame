package com.gsxxx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public final class MenuScreen implements Screen {
    //reference to the game
    MammothGame game;
    //menu textures
    private Texture background;
    private Texture mainPlayButton;
    private Texture mainPlayActiveButton;
    private Texture mainExitButton;
    private Texture mainExitActiveButton;

    //menu images properties variables
    private float buttonPositionX;
    private float buttonInfoPositionX;
    private float buttonPositionY;
    private float buttonInfoPositionY;
    private float buttonWidth;
    private float buttonHeight;

    MenuScreen(MammothGame game) {
        this.game = game;
        //setting textures
        background = new Texture("menubackground.png");
        mainPlayButton = new Texture("play.png");
        mainPlayActiveButton = new Texture("playactive.png");
        mainExitButton = new Texture("quit.png");
        mainExitActiveButton = new Texture("quitactive.png");


        //setting variables
        buttonPositionX = 500;
        buttonInfoPositionX = 1100;
        buttonPositionY = 420;
        buttonInfoPositionY = 420;
        buttonWidth = 300;
        buttonHeight = 300;

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0, 0, 0, 5760, 1080);

        if (Gdx.input.getX() > 500 && Gdx.input.getX() < 800 && Gdx.input.getY() > 300 && Gdx.input.getY() < 600) {
            game.batch.draw(mainPlayActiveButton, buttonPositionX, buttonPositionY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new PlayScreen(game));

            }
        } else {
            game.batch.draw(mainPlayButton, buttonPositionX, buttonPositionY, buttonWidth, buttonHeight);
        }
        if (Gdx.input.getX() > 1100 && Gdx.input.getX() < 1400 && Gdx.input.getY() > 300 && Gdx.input.getY() < 600) {
            game.batch.draw(mainExitActiveButton, buttonInfoPositionX, buttonInfoPositionY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(mainExitButton, buttonInfoPositionX, buttonInfoPositionY, buttonWidth, buttonHeight);
        }

        game.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
