package agh.ics.oop.model;

import agh.ics.oop.model.Vector2d;

import java.util.*;

import static java.lang.Math.sqrt;

public class RandomPositionGenerator implements Iterable<Vector2d>, Iterator<Vector2d> {
    private final List<Vector2d> positions;
    private int currentIndex = 0;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        List<Vector2d> allPositions = new LinkedList<>();
        for (int i = 0; i < maxWidth; i++) {
            for(int j = 0; j < maxHeight; j++){
                allPositions.add(new Vector2d(i,j));
            }
        }
        long seed = System.nanoTime();
        Collections.shuffle(allPositions, new Random(seed));
        positions = allPositions.subList(0,grassCount);
    }
    @Override
    public Iterator<Vector2d> iterator() {
        return this;
    }
    @Override
    public boolean hasNext() {
        return currentIndex < positions.size();
    }
    @Override
    public Vector2d next() {
        return positions.get(currentIndex++);
    }
}
