import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;;import java.util.ArrayList;
import java.util.List;

public class SimulationEngineTest{
    @Test
    public void RunSyncTest(){
        ArrayList<Simulation> simulations = new ArrayList<>();

        List<MoveDirection> directions = OptionsParser.movesStringToEnum(new String[]{"f", "f","f","r","b","l","f","f","r","f","b","f","f","f"});
        List<Vector2d> positions = List.of(new Vector2d(2,2),new Vector2d(2,2),new Vector2d(3,3));
        WorldMap map1 = new RectangularMap(7,7);
        ConsoleMapDisplay listener = new ConsoleMapDisplay();
        map1.addListener(listener);
        Simulation simulation1 = new Simulation(directions, positions, map1);

        simulations.add(simulation1);

        WorldMap map2 = new GrassField(10);
        map2.addListener(listener);
        Simulation simulation2 = new Simulation(directions, positions, map2);

        simulations.add(simulation2);

        SimulationEngine simulationEngine = new SimulationEngine(simulations);
        simulationEngine.runSync();
    }

}
