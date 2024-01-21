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
    public boolean startFollowing(int x, int y){
        Animal animal = globe.getAnimal(new Vector2d(x,y));
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
            animalInfo.put("genome",showGenome());
            animalInfo.put("grassEaten",String.valueOf(animal.getNumberOfGrassEaten()));
            animalInfo.put("energy",String.valueOf(animal.getEnergy()));
            animalInfo.put("children",String.valueOf(animal.getNumberOfChildren()));
            animalInfo.put("descendants",String.valueOf(animal.getNumberOfDescendants()));
            animalInfo.put("age",String.valueOf(animal.getAge()));
            if (animal.isDead()) {
                animalInfo.put("dayOfDeath", String.valueOf(animal.getBornDay() + animal.getAge()));
            }
            else{
                animalInfo.put("dayOfDeath",null);
            }
        }
        return animalInfo;
    }
    private String showGenome(){
        StringBuilder genome = new StringBuilder();
        for(int gene : animal.getGenome()){
            genome.append(gene).append(" ");
        }
        String gen = genome.toString();
        int activeGene = animal.getActiveGene() * 2;
        return gen.substring(0,activeGene) + "[" + gen.charAt(activeGene) + "]" + gen.substring(activeGene+1);
    }
    public boolean isFollowing(){
        return this.isFollowingAnimal;
    }
}
