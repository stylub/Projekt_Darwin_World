import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class IntegrationTest {
    @Test
    public void inputArraysTest(){
        List<Vector2d> positions = List.of(new Vector2d(3,3));
        List<Vector2d> positionsEmpty = new ArrayList<>();
        List<MoveDirection> moves = OptionsParser.movesStringToEnum(new String[]{"f"});
        List<MoveDirection> movesEmpty = OptionsParser.movesStringToEnum(new String[]{});
        WorldMap map = new RectangularMap(5,5);
        Simulation simulationMovesEmpty = new Simulation(movesEmpty, positions, map);
        Simulation simulationPositionsEmpty = new Simulation(moves, positionsEmpty, map);
        Simulation simulationBothEmpty = new Simulation(movesEmpty, positionsEmpty, map);
        simulationMovesEmpty.run();
        simulationPositionsEmpty.run();
        simulationPositionsEmpty.run();

        assertTrue(simulationMovesEmpty.getMovesList().isEmpty());
        assertEquals(simulationMovesEmpty.getAnimalList().get(0).getPosition(), new Vector2d(3,3));
        assertEquals(simulationMovesEmpty.getAnimalList().get(0).getMapDirection(), MapDirection.NORTH);
        assertTrue(simulationPositionsEmpty.getAnimalList().isEmpty());
        assertTrue(simulationBothEmpty.getMovesList().isEmpty());
        assertTrue(simulationBothEmpty.getAnimalList().isEmpty());
    }
    @Test
    public void orientationTest(){
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,3), new Vector2d(2,2)); // empty list of
        List<MoveDirection> movesList = OptionsParser.movesStringToEnum(new String[]{"r", "l"});
        WorldMap map = new RectangularMap(5,5);
        Simulation simulation = new Simulation(movesList, positions,map);
        simulation.run();

        assertEquals(simulation.getAnimalList().get(0).getMapDirection(), MapDirection.EAST);
        assertEquals(simulation.getAnimalList().get(1).getMapDirection(), MapDirection.WEST);
        assertEquals(simulation.getAnimalList().get(2).getMapDirection(), MapDirection.NORTH);

        simulation.run();

        assertEquals(simulation.getAnimalList().get(0).getMapDirection(), MapDirection.SOUTH);
        assertEquals(simulation.getAnimalList().get(1).getMapDirection(), MapDirection.SOUTH);
    }
    @Test
    public void movingTest(){
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(1,1), new Vector2d(3,3)); // empty list of
        List<MoveDirection> movesList = OptionsParser.movesStringToEnum(new String[]{"r", "l", "f", "f", "f"});
        WorldMap map = new RectangularMap(5,5);
        Simulation simulation = new Simulation(movesList, positions,map);
        simulation.run();

        assertEquals(simulation.getAnimalList().get(0).getPosition(), new Vector2d(3,2));
        assertEquals(simulation.getAnimalList().get(1).getPosition(), new Vector2d(0,1));
        assertEquals(simulation.getAnimalList().get(2).getPosition(), new Vector2d(3,4));

        movesList = OptionsParser.movesStringToEnum(new String[]{"r", "l", "b", "b", "b"});
        simulation = new Simulation(movesList, positions, new RectangularMap(5,5));
        simulation.run();

        assertEquals(simulation.getAnimalList().get(0).getPosition(), new Vector2d(1,2));
        assertEquals(simulation.getAnimalList().get(1).getPosition(), new Vector2d(2,1));
        assertEquals(simulation.getAnimalList().get(2).getPosition(), new Vector2d(3,2));
    }
    @Test
    public void mapBordersTest(){
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(2,1), new Vector2d(1,1), new Vector2d(1,2));
        List<MoveDirection> moves = OptionsParser.movesStringToEnum(new String[]{"r", "r", "l", "f", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});
        WorldMap map = new RectangularMap(5,5);
        Simulation simulation = new Simulation(moves, positions,map);
        simulation.run();

        assertTrue(simulation.getAnimalList().get(0).getPosition().precedes(new Vector2d(5,5)));
        assertTrue(simulation.getAnimalList().get(1).getPosition().precedes(new Vector2d(5,5)));
        assertTrue(simulation.getAnimalList().get(2).getPosition().precedes(new Vector2d(5,5)));
        assertTrue(simulation.getAnimalList().get(3).getPosition().precedes(new Vector2d(5,5)));
        assertTrue(simulation.getAnimalList().get(0).getPosition().follows(new Vector2d(-1,-1)));
        assertTrue(simulation.getAnimalList().get(1).getPosition().follows(new Vector2d(-1,-1)));
        assertTrue(simulation.getAnimalList().get(2).getPosition().follows(new Vector2d(-1,-1)));
        assertTrue(simulation.getAnimalList().get(3).getPosition().follows(new Vector2d(-1,-1)));
    }
}
