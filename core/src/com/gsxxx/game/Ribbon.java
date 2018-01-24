package com.gsxxx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


class Ribbon {
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean canBeDrawn = true;
    private boolean isTimerSet = false;
    private LinkedList<Point> pointsList;

    //ribbon properties
    private int lineThickness;
    private int maxRibbonLength;
    private int fadeTime; //in milliseconds
    private Body ribbonBody;

    private boolean shouldBeDestroyed = false;
    Ribbon() {
        pointsList = new LinkedList<Point>();
        lineThickness = 10;
        maxRibbonLength = 200;
        fadeTime = 1000;
    }

    void render() {
        Gdx.gl.glLineWidth(lineThickness);

        if (pointsList.size() > 1) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
//            Iterator<Point> iterator = pointsList.iterator();
//            Point previous = iterator.next();
//            while (iterator.hasNext()) {
//                Point tmp = iterator.next();
//                shapeRenderer.line(tmp.getX(), tmp.getY(), previous.getX(), previous.getY());
//                previous = tmp;
//            }
            shapeRenderer.line(pointsList.getFirst().getX(), pointsList.getFirst().getY(), pointsList.getLast().getX(), pointsList.getLast().getY());
            shapeRenderer.end();
        }
    }

    void addCoordinates(int x, int y) {
        if (canBeDrawn) {
            if (calculateLengthOfRibbon() > maxRibbonLength) {
                turnOffDrawing();
                while (calculateLengthOfRibbon() > maxRibbonLength){
                    pointsList.removeLast();
                }
            } else {
                pointsList.add(new Point(x, y));
            }
        }
    }

    private void clearCoordinates() {
        pointsList.clear();
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

    void turnOffDrawing() {
        canBeDrawn = false;
        if (!isTimerSet) {
            isTimerSet = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    clearCoordinates();
                    isTimerSet = false;
                    shouldBeDestroyed = true;
                }
            }, fadeTime);

            BodyDef ribbonBodyDef = new BodyDef();
            ribbonBodyDef.type = BodyDef.BodyType.StaticBody;
            Vector2 ribbonHitboxCoordinates[] = new Vector2[2];
            ribbonHitboxCoordinates[0] = new Vector2((float) pointsList.getFirst().getX() / 200, (float) pointsList.getFirst().getY() / 200);
            ribbonHitboxCoordinates[1] = new Vector2((float) pointsList.getLast().getX() / 200,  (float)pointsList.getLast().getY()/ 200);
            ChainShape ribbonHitbox = new ChainShape();
            ribbonHitbox.createChain(ribbonHitboxCoordinates);

            ribbonBody = MammothGame.world.createBody(ribbonBodyDef);
            ribbonBody.createFixture(ribbonHitbox, 0);
            ribbonHitbox.dispose();
            ribbonBody.setUserData("ribbon");
        }
        System.out.println(calculateLengthOfRibbon());
    }

    void dispose() {
        MammothGame.world.destroyBody(ribbonBody);
        shouldBeDestroyed = false;
        canBeDrawn = true;
    }

    boolean checkIfShouldBeDestroyed(){
        return shouldBeDestroyed;
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
}
