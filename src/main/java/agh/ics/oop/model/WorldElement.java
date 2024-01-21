package agh.ics.oop.model;

import javafx.scene.shape.Circle;

public interface WorldElement {
    Vector2d getPosition();
    String toString();
    Circle toCircle();
}
