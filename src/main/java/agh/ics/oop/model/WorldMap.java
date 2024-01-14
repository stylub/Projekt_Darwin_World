package agh.ics.oop.model;

import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap{

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void place(Animal animal);


    /**
     * update all worldElements on the map
     */
    void update();


    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);
    /**
     * Return boundaries of the map.
     *
     * @return Boundary containing map corners.
     */
    Boundary getCurrentBounds();
    void addListener(MapChangeListener listener);
    void removeListener(MapChangeListener listener);
    UUID getId();

}