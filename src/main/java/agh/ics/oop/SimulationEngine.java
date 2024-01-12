package agh.ics.oop;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine{
    private List<Simulation> simulationList;
    public SimulationEngine(List<Simulation> simulationList){
        this.simulationList = simulationList;
    }
    public void runSync(){
        for(Simulation simulation : simulationList){
            simulation.run();
        }
    }
    public void runAsync() throws InterruptedException{
        ArrayList<Thread> threads = new ArrayList<>();
        for(Simulation simulation : simulationList){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }
    public void awaitSimulationsEnd(List<Thread> threads) throws InterruptedException{
        for(Thread thread : threads) {
            thread.join();
        }
    }
    public void awaitSimulationsEnd(ExecutorService executorService) throws InterruptedException{
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
    public void runAsyncInThreadpool() throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for(Simulation simulation : simulationList){
            executorService.submit(simulation);
        }
        executorService.shutdown();
    }
}
