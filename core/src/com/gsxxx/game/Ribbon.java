package com.gsxxx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


class Ribbon {
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean canBeDrawn = true;
    private LinkedList<Point> pointsList;

    //ribbon properties
    private int lineThickness;
    private int maxLengthOfRibbon;
    private int fadeTime; //in milliseconds

    Ribbon() {
        pointsList = new LinkedList<Point>();
        lineThickness = 10;
        maxLengthOfRibbon = 200;
        fadeTime = 1000;
    }

    void render() {
        Gdx.gl.glLineWidth(lineThickness);

        if (pointsList.size() > 1) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            Iterator<Point> iterator = pointsList.iterator();
            Point previous = iterator.next();
            while (iterator.hasNext()) {
                Point tmp = iterator.next();
                shapeRenderer.line(tmp.getX(), tmp.getY(), previous.getX(), previous.getY());
                previous = tmp;
            }
            shapeRenderer.end();
        }
    }

    void addCoordinates(int x, int y) {
        if (canBeDrawn && calculateLengthOfRibbon() <= maxLengthOfRibbon) {
            if (pointsList.size() == 0) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        clearCoordinates();
                    }
                }, fadeTime);
            }
            pointsList.add(new Point(x, y));
        } else {
            canBeDrawn = false;
        }
    }

    private void clearCoordinates() {
        pointsList.clear();
        canBeDrawn = true;
    }

    private class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = 1080 - y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }
    }

    private int calculateLengthOfRibbon() {
        if (pointsList.size() == 0) return 0;
        int length = 0;
        Iterator<Point> iterator = pointsList.iterator();
        Point previous = iterator.next();
        while (iterator.hasNext()) {
            Point tmp = iterator.next();
            length += Math.sqrt(Math.pow((double) tmp.getX() - (double) previous.getX(), 2) + Math.pow((double) tmp.getY() - (double) previous.getY(), 2));
            previous = tmp;
        }
        return length;
    }

    void turnOffDrawing() {
        canBeDrawn = false;
    }
}
