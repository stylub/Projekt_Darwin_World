package agh.ics.oop.model.util;

import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Vector2d;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RandomPositionGenerator  implements Iterable<Vector2d>, Iterator<Vector2d> {
    protected final int maxWidth;
    protected final int maxHeight;
    protected List<Vector2d> positions = new ArrayList<>();
    protected int currentIndex = 0;

    public RandomPositionGenerator(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }
    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        generatePositions(grassCount);
    }

    public RandomPositionGenerator(Boundary boundary, int grassCount) {
        this.maxWidth = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
        this.maxHeight = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        generatePositions(grassCount);
    }

    private void generatePositions(int n) {
        currentIndex = 0;
        List<Vector2d> possiblePositions = new ArrayList<>();
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                possiblePositions.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(possiblePositions);
        positions = new ArrayList<>(possiblePositions.subList(0, n));
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
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        else {
            return positions.get(currentIndex++);
        }
    }
}