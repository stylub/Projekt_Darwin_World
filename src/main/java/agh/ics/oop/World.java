package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        WorldGUI.main(args);
//        System.out.println("System wystartował.");
//
//        ArrayList<Simulation> simulations = new ArrayList<>();
//        ConsoleMapDisplay listener = new ConsoleMapDisplay();
//
//        for(int i = 0; i < 1000; i++){
//            GrassField map = new GrassField(10);
//            map.addListener(listener);
//            String[] moves = new String[]{"f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f"};
//            List<Vector2d> positions = List.of(new Vector2d(2,2),new Vector2d(2,2));
//            simulations.add(new Simulation(OptionsParser.movesStringToEnum(moves),positions,map));
//        }
//
//        SimulationEngine simulationEngine = new SimulationEngine(simulations);
//        try {
//            simulationEngine.runAsyncInThreadpool();
//        }
//        catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        System.out.println("System zakończył działanie.");
    }



    public static void run(MoveDirection[] moves)  {
        for(MoveDirection move: moves) {
            switch (move){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu.");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
            }
        }
    }
}
