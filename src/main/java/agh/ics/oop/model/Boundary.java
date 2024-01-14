package agh.ics.oop.model;

public record Boundary(Vector2d leftBottom, Vector2d rightTop) {
    public boolean contains(Vector2d position) {
        return position.follows(leftBottom) && position.precedes(rightTop);
    }
}
