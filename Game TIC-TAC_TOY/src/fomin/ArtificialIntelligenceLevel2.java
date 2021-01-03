package fomin;
//Тут появляется воля к победе у машины. Она в приоритете пытается собрать собственную выигрышную комбинацию, но не забывает своевременно блокировать комбинации пользователя за 2 хода до предполагаемой победы
public class ArtificialIntelligenceLevel2 extends ArtificialIntelligenceLevel1 {

    protected static void turnAI2(Boxes[][] boxes, int pointsToWin, int maxVerifyPoints) {
        for (int i = 1; i < pointsToWin; i++) {
            if (i > maxVerifyPoints) {
                if (isTurnAI2Realization(boxes, i, pointsToWin)) {
                    return;
                }
            } else {
                if (isTurnAI2Realization(boxes, i, pointsToWin)) {
                    return;
                } else {
                    if (isTurnAI1Realization(boxes, i, pointsToWin)) {
                        return;
                    }
                }
            }
        }
        turnAI0(boxes);
    }

    protected static boolean isTurnAI2Realization(Boxes[][] boxes, int maxPreVictory, int pointsToWin) {
        swapSymbols();
        int newPointsToWin;
        int preVictory = maxPreVictory;
            newPointsToWin = pointsToWin - preVictory;
            if (isMadeLineTurnImportant(boxes, pointsToWin - preVictory + 1)) {
                swapSymbols();
                return true;
            }
            if (isMadeDiagonalTurnImportant(boxes, pointsToWin - preVictory + 1)) {
                swapSymbols();
                return true;
            }
            for (int i = 0; i < boxes.length; i++) {
                if (isMadeLineTurn(boxes, i, newPointsToWin, preVictory)) {
                    swapSymbols();
                    return true;
                }
                if (isMadeDiagonalTurn(boxes, i, newPointsToWin, preVictory)) {
                    swapSymbols();
                    return true;
                }
            }
        swapSymbols();
        return false;
    }

    protected static void swapSymbols() {
        if (Boxes.flagTurnAI2) {
            Boxes.flagTurnAI2 = false;
        } else {
            Boxes.flagTurnAI2 = true;
        }
        char bufferSymbol = Boxes.USER;
        Boxes.USER = Boxes.MACHINE;
        Boxes.MACHINE = bufferSymbol;
    }
}
