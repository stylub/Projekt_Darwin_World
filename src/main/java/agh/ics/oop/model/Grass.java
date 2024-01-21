package agh.ics.oop.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Grass implements WorldElement{
    private final Vector2d position;
    public Grass(Vector2d position){
        this.position = position;
    }
    public Vector2d getPosition(){
        return position;
    }
    public String toString(){
        return "*";
    }
    public Circle toCircle(){
        return new Circle(0.5, Color.GREEN);
    }
}
