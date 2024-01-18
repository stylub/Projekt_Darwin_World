package agh.ics.oop.model;

public enum MapDirection {
    NORTH("N", new Vector2d(0,1)),
    NORTHEAST("NE", new Vector2d(1,1)),
    EAST("E", new Vector2d(1, 0)),
    SOUTHEAST("SE", new Vector2d(1,-1)),
    SOUTH("S", new Vector2d(0, -1)),
    SOUTHWEST("SW", new Vector2d(-1,-1)),
    WEST("W", new Vector2d(-1, 0)),
    NORTHWEST("NW", new Vector2d(-1,1));
    private final String name;
    private final Vector2d unitVector;
    private MapDirection(String name, Vector2d unitVector) {
        this.name = name;
        this.unitVector = unitVector;
    }
    @Override
    public String toString() {
        return this.getName();
    }
    public MapDirection next() {
        return values()[(ordinal() + 1) % values().length];
    }
    public MapDirection previous() {
        return values()[(ordinal() + 7) % values().length];
    }
    public MapDirection rotate(int rotation){
        return values()[(ordinal() + rotation) % values().length];
    }
    public Vector2d toUnitVector(){
        return this.unitVector;
    }
    public String getName(){
        return this.name;
    }
}
