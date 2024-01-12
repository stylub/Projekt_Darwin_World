package agh.ics.oop.model;
import java.util.Objects;


public class Animal implements WorldElement{
    private MapDirection mapDirection;
    private Vector2d position;


    public Animal(Vector2d position){
        this.position = position;
        this.mapDirection = MapDirection.NORTH;
    }
    public Animal(){
        this(new Vector2d(2,2));
    }
    @Override
    public String toString(){
        return this.mapDirection.toString();
    }
    boolean isAt(Vector2d position){
        return Objects.equals(this.position, position);
    }
    public void move(MoveValidator validator, MoveDirection move){
        switch (move){
            case LEFT -> this.mapDirection = this.mapDirection.previous();
            case RIGHT -> this.mapDirection = this.mapDirection.next();
            case FORWARD,BACKWARD -> this.position = newPosition(validator, move);
        }
    }
    private Vector2d newPosition(MoveValidator validator,MoveDirection move){
        Vector2d newPosition = null;
        if(move.equals(MoveDirection.FORWARD)) {
            newPosition = this.position.add(this.mapDirection.toUnitVector());
        }
        else{
            newPosition = this.position.subtract(this.mapDirection.toUnitVector());
        }
        if (validator.canMoveTo(newPosition)){
            return newPosition;
        }
        return this.position;
    }
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getMapDirection() {
        return mapDirection;
    }
}
