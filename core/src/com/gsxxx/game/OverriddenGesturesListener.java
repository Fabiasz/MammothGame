package com.gsxxx.game;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class OverriddenGesturesListener implements GestureDetector.GestureListener {

    Ribbon ribbon;

    OverriddenGesturesListener(Ribbon ribbon) {
        this.ribbon = ribbon;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        ribbon.addCoordinates((int) x, (int) y);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        ribbon.clearCoordinates();
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
