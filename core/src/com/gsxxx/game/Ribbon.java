package com.gsxxx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Iterator;
import java.util.LinkedList;


public class Ribbon {
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private LinkedList<Point> pointsList;
    private int lineThickness;
    private int maxLengthOfRibbon; //max amount of points stored in list

    Ribbon() {
        pointsList = new LinkedList<Point>();
        lineThickness = 10;
        maxLengthOfRibbon = 200;
    }

    void render(){
        Gdx.gl.glLineWidth(lineThickness);
//        shapeRenderer.setProjectionMatrix();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);


        if(pointsList.size() > 1){
            while(calculateLengthOfRibbon() > maxLengthOfRibbon){
                pointsList.remove(0);
            }
            Iterator<Point> iterator = pointsList.iterator();
            Point previous = iterator.next();
            while(iterator.hasNext()){
                Point tmp = iterator.next();
                shapeRenderer.line(tmp.getX(), tmp.getY(), previous.getX(), previous.getY());
                previous = tmp;
            }
        }
        shapeRenderer.end();
    }

    void addCoordinates(int x, int y){
        pointsList.add(new Point(x, y));
    }

    void clearCoordinates(){
        pointsList.clear();
    }

    class Point{
        private int x;
        private int y;

        Point(int x, int y){
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

    private int calculateLengthOfRibbon(){
        int length = 0;
        Iterator<Point> iterator = pointsList.iterator();
        Point previous = iterator.next();
        while(iterator.hasNext()){
            Point tmp = iterator.next();
            length += Math.sqrt(Math.pow((double) tmp.getX() - (double)previous.getX(), 2) + Math.pow((double) tmp.getY() - (double) previous.getY(), 2));
            previous = tmp;
        }
        return length;
    }
}
