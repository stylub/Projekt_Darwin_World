package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> movesStringToEnum(String[] moves){

        int enumMovesIndex = 0;
        List<MoveDirection> enumMoves = new ArrayList<>();

        for (String move: moves) {

            switch (move) {

                case "f" -> {
                    enumMoves.add(MoveDirection.FORWARD);
                }
                case "b" -> {
                    enumMoves.add(MoveDirection.BACKWARD);
                }
                case "r" -> {
                    enumMoves.add(MoveDirection.RIGHT);
                }
                case "l" -> {
                    enumMoves.add(MoveDirection.LEFT);
                }
                default -> {
                    throw new IllegalArgumentException(move + " is not legal move specification");
                }
            }
        }

        return enumMoves;
    }
}
