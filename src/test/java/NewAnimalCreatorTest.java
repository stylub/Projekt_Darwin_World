import agh.ics.oop.model.Animal;
import agh.ics.oop.model.AnimalBuilder;
import agh.ics.oop.model.NewAnimalCreator;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

public class NewAnimalCreatorTest {

    @Test
    void allRandomVariantTest(){
        AnimalBuilder animalBuilder = new AnimalBuilder();
        animalBuilder.setEnergy(200);
        animalBuilder.setFullEnergy(100);
        animalBuilder.setProcreationEnergy(50);
        animalBuilder.setNumberOfMutations(5);
        animalBuilder.setGenomeLength(32);
        animalBuilder.setPosition(new Vector2d(2,2));

        NewAnimalCreator procreate = new NewAnimalCreator(0,animalBuilder);
        Animal animal1 = animalBuilder.build();
        Animal animal2 = animalBuilder.build();
        Animal newAnimal = procreate.BornNewAnimal(animal1,animal2);

        assert newAnimal.getEnergy() == 100;
    }
    @Test
    void swapMutationTest(){
        AnimalBuilder animalBuilder = new AnimalBuilder();
        animalBuilder.setEnergy(200);
        animalBuilder.setFullEnergy(100);
        animalBuilder.setProcreationEnergy(50);
        animalBuilder.setNumberOfMutations(5);
        animalBuilder.setGenomeLength(32);
        animalBuilder.setPosition(new Vector2d(2,2));

        NewAnimalCreator procreate = new NewAnimalCreator(1,animalBuilder);
        Animal animal1 = animalBuilder.build();
        Animal animal2 = animalBuilder.build();
        Animal newAnimal = procreate.BornNewAnimal(animal1,animal2);

        assert newAnimal.getEnergy() == 100;
    }
}
