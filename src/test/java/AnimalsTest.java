import agh.ics.oop.model.Animal;
import agh.ics.oop.model.AnimalBuilder;
import agh.ics.oop.model.Incubator;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

public class AnimalsTest {

    Incubator incubator = new Incubator(0,new AnimalBuilder());
    AnimalBuilder animalBuilder = new AnimalBuilder()
            .setEnergy(100000)
            .setProcreationEnergy(100)
            .setGenomeLength(32)
            .setFullEnergy(10);

    @Test
    void testComparatorEnergy(){
        Animal animal1 =  animalBuilder.build();
        Animal animal2 =  animalBuilder.build();

        assert(animal1.compareTo(animal2) < 0);
        assert(animal2.compareTo(animal1) > 0);
    }
    @Test
    void testCompareAge(){
        Animal animal1 = new AnimalBuilder()
                .setEnergy(20)
                .setPosition(new Vector2d(0,0))
                .build();
        Animal animal2 = new AnimalBuilder()
                .setEnergy(10)
                .build();

        for (int i = 0; i < 10; i++) {
            animal1.forward();
        }
        assert(animal1.compareTo(animal2) > 0);
        assert(animal2.compareTo(animal1) < 0);
    }

    @Test
    void testCompareNumberOfChildren(){
        Animal animal1 = new AnimalBuilder()
                .setEnergy(10)
                .setProcreationEnergy(0)
                .build();
        Animal animal2 = new AnimalBuilder()
                .setEnergy(10)
                .build();

        animal1.procreate();
        assert(animal1.compareTo(animal2) > 0);
        assert(animal2.compareTo(animal1) < 0);
    }

    @Test
    void testAddingDescendants(){
        Incubator incubator = new Incubator(0,animalBuilder);
        Animal animal1 = animalBuilder.build();
        Animal animal2 = animalBuilder.build();

        Animal animal3 = incubator.BornNewAnimal(animal1,animal2,0);
        Animal animal4 = incubator.BornNewAnimal(animal1,animal2,0);
        Animal animal5 = incubator.BornNewAnimal(animal1,animal2,0);

        Animal animal6 = incubator.BornNewAnimal(animal3,animal4,0);
        Animal animal7 = incubator.BornNewAnimal(animal4,animal5,0);
        Animal animal8 = incubator.BornNewAnimal(animal6,animal7,0);

        assert(animal1.getNumberOfChildren() == 3);
        assert(animal2.getNumberOfChildren() == 3);
        assert(animal1.getNumberOfDescendants() == 6);
        assert(animal2.getNumberOfDescendants() == 6);
    }

    @Test
    void testIncestDescendantsCounting(){
        Incubator incubator = new Incubator(0, animalBuilder);
        Animal animal1 = animalBuilder.build();
        Animal animal2 = animalBuilder.build();

        Animal animal3 = incubator.BornNewAnimal(animal1,animal2,0);
        Animal animal4 = incubator.BornNewAnimal(animal1,animal3,0);
        Animal animal5 = incubator.BornNewAnimal(animal1,animal4,0);

        Animal animal6 = incubator.BornNewAnimal(animal1,animal2,0);
        Animal animal7 = incubator.BornNewAnimal(animal5,animal6,0);


        assert(animal1.getNumberOfChildren() == 4);
        assert(animal2.getNumberOfChildren() == 2);
        assert(animal1.getNumberOfDescendants() == 5);
        assert(animal2.getNumberOfDescendants() == 5);
    }


}
