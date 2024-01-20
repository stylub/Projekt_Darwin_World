package agh.ics.oop.model.util;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Globe;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;

public class FollowAnimal {
    private final Globe globe;
    private Animal animal;
    private boolean isFollowingAnimal = false;

    public FollowAnimal(Globe globe){
        this.globe = globe;
    }
    public boolean startFollowing(Vector2d position){
        Animal animal = globe.getAnimal(position);
        if(animal != null){
            this.animal = animal;
            isFollowingAnimal = true;
            return true;
        }
        return false;
    }
    public void stopFollowing(){
        isFollowingAnimal = false;
        Animal animal = null;
    }

    public HashMap<String,String> getAnimalInfo(){
        HashMap<String,String> animalInfo = new HashMap<>();
        if(isFollowingAnimal){
            String genome = animal.getGenome().toString();
            int activeGene = animal.getActiveGene();
            genome = genome.substring(0,activeGene) + "[" + genome.charAt(activeGene) + "]" + genome.substring(activeGene+1);
            animalInfo.put("genome",genome);
            animalInfo.put("grassEaten",String.valueOf(animal.getNumberOfGrassEaten()));
            animalInfo.put("energy",String.valueOf(animal.getEnergy()));
            animalInfo.put("children",String.valueOf(animal.getNumberOfChildren()));
            animalInfo.put("descendants",String.valueOf(animal.getNumberOfDescendants()));
            animalInfo.put("age",String.valueOf(animal.getAge()));
            animalInfo.put("dayOfDeath",String.valueOf(animal.getBornDay() + animal.getAge()));
        }
        return animalInfo;
    }
}
