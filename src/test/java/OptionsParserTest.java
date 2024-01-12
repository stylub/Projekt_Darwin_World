import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


import java.util.ArrayList;
import java.util.List;

public class OptionsParserTest {
    @Test
    public void movesStringToEnumMethodTest(){
        String[] moves1 = {"f", "r", "b", "l",};
//        String[] moves2 = {"c", "z", "e", "s", "c", "f", "a", "r", "b", "a", "t", "o", "l", "ż", "e", "g", "n", "a", "j"};
//        String[] moves3 = {"a", "c", "a", "c", "a", "c"};
        String[] moves4 = {};

        List<MoveDirection> outputOneTwo = new ArrayList<>(List.of(MoveDirection.values()));
        List<MoveDirection> outputThreeFour = new ArrayList<>();

        Assert.assertEquals(OptionsParser.movesStringToEnum(moves1), outputOneTwo);
//        Assert.assertEquals(OptionsParser.movesStringToEnum(moves2), outputOneTwo);
//        Assert.assertEquals(OptionsParser.movesStringToEnum(moves3), outputThreeFour);
        Assert.assertEquals(OptionsParser.movesStringToEnum(moves4), outputThreeFour);
    }
}
