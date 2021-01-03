package fomin;

import java.util.Random;
//Данный ИИ просто рандомно ставит символ в свободную ячейку, избегая периметра поля.
public class ArtificialIntelligenceLevel0 {
    static Random random = new Random();

    protected static void turnAI0(Boxes[][] boxes) {
        while (true) {
            int numberOfBoxX = random.nextInt(boxes.length);
            int numberOfBoxY = random.nextInt(boxes.length);
            if (isFullCenter(boxes) && boxes[numberOfBoxX][numberOfBoxY].isEmpty()) {
                boxes[numberOfBoxX][numberOfBoxY].setCircle();
                break;
            }
            if (boxes[numberOfBoxX][numberOfBoxY].isEmpty() && numberOfBoxX > 0 && numberOfBoxX < boxes.length - 1 && numberOfBoxY > 0 && numberOfBoxY < boxes.length - 1) {
                boxes[numberOfBoxX][numberOfBoxY].setCircle();
                break;
            }
        }
    }

    private static boolean isFullCenter(Boxes[][] boxes) {
        for (int i = 1; i < boxes.length - 1; i++) {
            for (int j = 1; j < boxes.length - 1; j++) {
                if (boxes[i][j].isSymbol(Boxes.EMPTY)) return false;
            }
        }
        return true;
    }
}
