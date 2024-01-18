import agh.ics.oop.model.Animal;
import agh.ics.oop.model.AnimalBuilder;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

public class AnimalsTest {

    @Test
    void testComparatorEnergy(){
        Animal animal1 = new AnimalBuilder()
                .setEnergy(10)
                .build();
        Animal animal2 = new AnimalBuilder()
                .setEnergy(20)
                .build();

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


}
