package agh.ics.oop.model;

public record Boundary(Vector2d leftBottom, Vector2d rightTop) {
    public boolean contains(Vector2d position) {
        return position.follows(leftBottom) && position.precedes(rightTop);
    }
    public int getWidth(){
        return rightTop.getX() - leftBottom.getX();
    }
    public int getHeight(){
        return rightTop.getY() - leftBottom.getY();
    }
}
