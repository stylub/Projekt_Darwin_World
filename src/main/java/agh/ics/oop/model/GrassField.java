package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private Map<Vector2d, Grass> grass = new HashMap<>();
    public GrassField(int n) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator((int)sqrt(n*10), (int)sqrt(n*10), n);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grass.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (super.isOccupied(position) || grass.containsKey(position));
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(animals.containsKey(position)){
            return super.objectAt(position);
        }
        return grass.get(position);
    }

    @Override
    public Collection<WorldElement> getElements(){
        ArrayList<WorldElement> elements = new ArrayList<>(grass.values());
        elements.addAll(super.getElements());
        return elements;
    }
     public Boundary getCurrentBounds(){
        Vector2d topRight = new Vector2d(0,0);
        Vector2d bottomLeft = new Vector2d(Integer.MAX_VALUE,Integer.MAX_VALUE);
        for(WorldElement element : this.getElements()){
            if (!element.getPosition().precedes(topRight)){
                topRight = element.getPosition().upperRight(topRight);
            }
            if (!element.getPosition().follows(bottomLeft)) {
                bottomLeft = element.getPosition().lowerLeft(bottomLeft);
            }
        }
        return new Boundary(bottomLeft,topRight);
    }
}
