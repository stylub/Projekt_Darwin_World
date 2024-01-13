package agh.ics.oop.model;
import java.util.Objects;


public class Animal implements  WorldElement{
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;

    public Animal(){
        this.position = new Vector2d(2,2);
    }

    public Animal(Vector2d position){
        this.position = position;
    }

    @Override
    public String toString() {
        return switch (direction) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }

    public boolean isAt(Vector2d position) {
        return  this.position.equals(position);
    }

    public void rotate(int directionChange){
        for(int i =0;i<directionChange;i++)
            direction = direction.next();
    }

    public Vector2d forward(){
        Vector2d newPosition = position.add(direction.toUnitVector());
        this.position = newPosition;
        return newPosition;
    }

    public void moveTo(Vector2d position){
        this.position = position;
    }
    public MapDirection getDirection(){
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }
}
