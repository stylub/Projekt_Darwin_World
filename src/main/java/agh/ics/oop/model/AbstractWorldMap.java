package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    private final UUID id = UUID.randomUUID();
    List<MapChangeListener> listeners = new ArrayList<>();
    Map<Vector2d, Animal> animals = new HashMap<>();
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    public void move(Animal animal, MoveDirection direction) {
        if (animals.containsValue(animal)) {
            Vector2d oldPosition = animal.getPosition();
            MapDirection oldMapDirection = animal.getMapDirection();
            animal.move(this,direction);
            animals.remove(oldPosition);
            animals.put(animal.getPosition(),animal);
            if(!oldPosition.equals(animal.getPosition()) || !oldMapDirection.equals(animal.getMapDirection())){
                mapChanged("Animal " + animal.toString() + animal.getPosition().toString() + " changed position");
            }
            else{
                mapChanged("Animal " + animal.toString() + animal.getPosition().toString() + " tried to change position but failed");
            }
        }
    }
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            mapChanged("Animal " + animal.toString() + " spawned at map");
        }
        else{
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
    }
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }
    public boolean canMoveTo(Vector2d position) {
        return (!animals.containsKey(position) && position.follows(new Vector2d(0,0)));
    }
    public Collection<WorldElement> getElements(){
        ArrayList<WorldElement> elements = new ArrayList<>(animals.values());
        return Collections.unmodifiableCollection(elements);
    }
    public String toString(){
        Boundary bounds = getCurrentBounds();
        return mapVisualizer.draw(bounds.leftBottom(), bounds.rightTop());
    }
    public void addListener(MapChangeListener listener){
        listeners.add(listener);
    }
    public void removeListener(MapChangeListener listener){
        listeners.remove(listener);
    }
    private void mapChanged(String message){
        for(MapChangeListener listener : listeners){
            listener.mapChanged(this, message);
        }
    }
    public UUID getId(){
        return this.id;
    }
}
