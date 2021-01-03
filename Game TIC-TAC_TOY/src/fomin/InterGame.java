package fomin;

//Тут происходит основная логика игры: совершается ход машины, проверка на победу, хранение статических ключевых параметров. По сути это набор интсрументов статических для базовой структуры игры.
/*Содержит в себе статические значения количества символов для победы и уровень сложности. Раскрашивает выигрушную комбинацию для наглядности*/
public class InterGame {
    protected static int pointsToWin = 5;
    protected static int levelAI = 1;

    protected static void machineTurn(Boxes[][] boxes) {
        switch (levelAI) {
            case 0:
                ArtificialIntelligenceLevel0.turnAI0(boxes);
                break;
            case 1:
                ArtificialIntelligenceLevel1.turnAI1(boxes, pointsToWin);
                break;
            case 2:
                ArtificialIntelligenceLevel2.turnAI2(boxes, pointsToWin, 2);
                break;
            case 3:
                ArtificialIntelligenceLevel3.turnAI3(boxes, pointsToWin);
                break;
        }
    }

    static boolean isFullMap(Boxes[][] boxes) {
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                if (boxes[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;

    }

    private static int pointsHorizontal, pointsVertical, pointsMainDiagonal1, pointsMainDiagonal2, pointsSecondaryDiagonal1, pointsSecondaryDiagonal2;

    static boolean isVictory(Boxes[][] boxes, char symbol) {
        for (int i = 0; i < boxes.length; i++) {
            if (isFullLinesForVictory(boxes, symbol, i)) return true;
            if (isFullDiagonalsForVictory(boxes, symbol, i)) return true;
        }
        return false;
    }

    private static boolean isFullLinesForVictory(Boxes[][] boxes, char symbol, int i) {
        pointsHorizontal = 0;
        pointsVertical = 0;
        for (int j = 0; j < boxes.length; j++) {
            if (boxes[i][j].isSymbol(symbol)) {
                pointsHorizontal++;
                if (pointsHorizontal == pointsToWin) {
                    setHorizontalColor(boxes, i, j, symbol);
                    return true;
                }
            } else {
                pointsHorizontal = 0;
            }
            if (boxes[j][i].isSymbol(symbol)) {
                pointsVertical++;
                if (pointsVertical == pointsToWin) {
                    setVerticalColor(boxes, i, j, symbol);
                    return true;
                }
            } else {
                pointsVertical = 0;
            }
        }
        return false;
    }

    private static boolean isFullDiagonalsForVictory(Boxes[][] boxes, char symbol, int i) {
        pointsMainDiagonal1 = 0;
        pointsMainDiagonal2 = 0;
        pointsSecondaryDiagonal1 = 0;
        pointsSecondaryDiagonal2 = 0;
        for (int j = 0; j < boxes.length; j++) {
            if ((i + j) < boxes.length && boxes[i + j][j].isSymbol(symbol)) {
                pointsMainDiagonal1++;
                if (pointsMainDiagonal1 == pointsToWin) {
                    setMainDiagonalColor(boxes, (i + j), j, symbol);
                    return true;
                }
            } else {
                pointsMainDiagonal1 = 0;
            }
            if ((i + j) < boxes.length && boxes[j][j + i].isSymbol(symbol)) {
                pointsMainDiagonal2++;
                if (pointsMainDiagonal2 == pointsToWin) {
                    setMainDiagonalColor(boxes, j, (j + i), symbol);
                    return true;
                }
            } else {
                pointsMainDiagonal2 = 0;
            }
            if ((i + j) < boxes.length && boxes[i + j][boxes.length - j - 1].isSymbol(symbol)) {
                pointsSecondaryDiagonal1++;
                if (pointsSecondaryDiagonal1 == pointsToWin) {
                    setSecondaryDiagonalColor(boxes, (i + j), (boxes.length - j - 1), symbol);
                    return true;
                }
            } else {
                pointsSecondaryDiagonal1 = 0;
            }
            if ((i + j) < boxes.length && boxes[j][boxes.length - j - i - 1].isSymbol(symbol)) {
                pointsSecondaryDiagonal2++;
                if (pointsSecondaryDiagonal2 == pointsToWin) {
                    setSecondaryDiagonalColor(boxes, j, (boxes.length - j - i - 1), symbol);
                    return true;
                }
            } else {
                pointsSecondaryDiagonal2 = 0;
            }
        }
        return false;
    }
    //раскрашиваем выигрушную комбинацию
    private static void setHorizontalColor(Boxes[][] boxes, int i, int j, char symbol) {
        for (int k = 0; k < pointsToWin; k++) {
            boxes[i][j - k].setColor();
        }
        int k = 1;
        while (true) {
            if ((j + k) < boxes.length && boxes[i][j + k].isSymbol(symbol)) {
                boxes[i][j + k].setColor();
                k++;
            } else {
                break;
            }
        }
    }

    private static void setVerticalColor(Boxes[][] boxes, int i, int j, char symbol) {
        for (int k = 0; k < pointsToWin; k++) {
            boxes[j - k][i].setColor();
        }
        int k = 1;
        while (true) {
            if ((j + k) < boxes.length && boxes[j + k][i].isSymbol(symbol)) {
                boxes[j + k][i].setColor();
                k++;
            } else {
                break;
            }
        }
    }

    private static void setMainDiagonalColor(Boxes[][] boxes, int i, int j, char symbol) {
        for (int k = 0; k < pointsToWin; k++) {
            boxes[i - k][j - k].setColor();
        }
        int k = 1;
        while (true) {
            if ((i + k) < boxes.length && (j + k) < boxes.length && boxes[i + k][j + k].isSymbol(symbol)) {
                boxes[i + k][j + k].setColor();
                k++;
            } else {
                break;
            }
        }
    }

    private static void setSecondaryDiagonalColor(Boxes[][] boxes, int i, int j, char symbol) {
        for (int k = 0; k < pointsToWin; k++) {
            boxes[i - k][j + k].setColor();
        }
        int k = 1;
        while (true) {
            if ((i + k) < boxes.length && (j - k) >= 0 && boxes[i + k][j - k].isSymbol(symbol)) {
                boxes[i + k][j - k].setColor();
                k++;
            } else {
                break;
            }
        }
    }
}

