package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int notificationsSum = 0;
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        notificationsSum++;
        System.out.println(message);
        System.out.println("Map ID: " + worldMap.getId().toString());
        System.out.println("Notifications sum: " + notificationsSum);
        System.out.println(worldMap.toString());

    }
}
