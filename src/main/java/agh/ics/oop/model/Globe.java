package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class Globe implements WorldMap{
    private Boundary boundary;
    private Map<Vector2d, Animal> animals = new HashMap<>();

    private Map<Vector2d,Grass> grasses = new HashMap<>();
    private final UUID id = UUID.randomUUID();
    MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    public void registerObserver(MapChangeListener observer) {
        observers.add(observer);
    }
    public void unregisterObserver(MapChangeListener observer) {
        observers.remove(observer);
    }
    protected void notifyObservers(String description) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this,description);
        }
    }

    @Override
    public void place(Animal animal){
        animals.put(animal.getPosition(), animal);
        notifyObservers("Animal placed at " + animal.getPosition());
    }

    public Vector2d wrap(Vector2d position){
        int x = position.x;
        int y = position.y;
        if(x<0){
            x = boundary.upperRight().x;
        }
        if(x>boundary.upperRight().x){
            x = 0;
        }
        if(y<0){
            y = boundary.upperRight().y;
        }
        if(y>boundary.upperRight().y){
            y = 0;
        }
        return new Vector2d(x,y);
    }
    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        if (animal.move(direction, this)) {
            animals.remove(oldPosition);
            animals.put(animal.getPosition(), animal);
            if (!oldPosition.equals(animal.getPosition())) {
                notifyObservers("Animal moved from " + oldPosition + " to " + animal.getPosition());
            }
            else{
                notifyObservers("Animal rotated to the " + animal.getDirection());
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public Boundary getCurrentBounds() {
        return null;
    }

    @Override
    public void addListener(MapChangeListener listener) {

    }

    @Override
    public void removeListener(MapChangeListener listener) {

    }

    @Override
    public UUID getId() {
        return id;
    }
}
