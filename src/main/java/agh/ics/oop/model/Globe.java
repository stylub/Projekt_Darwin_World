package agh.ics.oop.model;

import agh.ics.oop.model.util.GrassGenerator;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class Globe implements WorldMap{
    private final Boundary boundary;
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Map<Vector2d,Grass> grass = new HashMap<>();
    private final UUID id = UUID.randomUUID();

    private final int newGrass;

    GrassGenerator grassGenerator;
    MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    public void registerObserver(MapChangeListener observer) {
        observers.add(observer);
    }
    public void unregisterObserver(MapChangeListener observer) {
        observers.remove(observer);
    }
    public void notifyObservers(String description) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this,description);
        }
    }

    public Globe(int width,int height,int startingGrass, int newGrass){
        this.newGrass = newGrass;
        boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
        this.grassGenerator = new GrassGenerator(width + 1,height + 1,startingGrass,0.8);
        for(var pos : grassGenerator){
            grass.put(pos,new Grass(pos));
        }
    }
    @Override
    public void place(Animal animal){
        animals.put(animal.getPosition(), animal);
    }

    /**
     * rotates animal by multiple of 45 degrees angle.
     * @param directionChange specify how much animal will be rotated
     */
    public void rotate(Animal animal,int directionChange) {
        animal.rotate(directionChange);
    }

    public void rotate(Animal animal){
        animal.rotate();
    }

    /**
     * Moves animal forward in current direction of given animal
     * @param animal The animal to move forward
     */

    public void forward(Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        Vector2d newPosition = animal.forward();
        if(!insideMap(newPosition)){
            newPosition = wrap(newPosition);
//            if(newPosition.equals(oldPosition))
//                rotate(animal,4);
//            else
                animal.moveTo(newPosition);

        }
        animals.remove(oldPosition);
        animals.put(newPosition,animal);
    }

    public Vector2d wrap(Vector2d position){
        int x = position.getX();
        int y = position.getY();
        if(x < 0){
            x = boundary.rightTop().getX();
        }
        if(x > boundary.rightTop().getX()){
            x = 0;
        }
        if(y < 0){
            y +=1;
        }
        if(y > boundary.rightTop().getY()){
            y -=1;
        }
        return new Vector2d(x,y);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grass.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position){
        if(animals.containsKey(position)) {
            return animals.get(position);
        }
        else return grass.getOrDefault(position, null);
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    @Override
    public void addListener(MapChangeListener listener) {
        this.observers.add(listener);
    }

    @Override
    public void removeListener(MapChangeListener listener) {
        this.observers.remove(listener);
    }

    @Override
    public UUID getId() {
        return id;
    }

    private boolean insideMap(Vector2d position){
        return boundary.rightTop().follows(position) && boundary.leftBottom().precedes(position);
    }

    public void growGrass(){
        grassGenerator.generateNewPositions(this.newGrass,grass);
        for(var pos : grassGenerator){
            grass.put(pos,new Grass(pos));
        }
    }

    public void update(){
        List<Animal> animalsToUpdate = new ArrayList<>(animals.values());
        for(Animal animal : animalsToUpdate){
            rotate(animal);
            forward(animal);
        }
        growGrass();
        notifyObservers("Update");
    }
}
