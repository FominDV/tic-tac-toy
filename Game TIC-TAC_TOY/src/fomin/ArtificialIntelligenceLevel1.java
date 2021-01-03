package fomin;
//Данный ИИ блокирует выигрышные комбинации пользователя за 2 хода до предпологаемой победы. Проверяет всё строго в необходимой хронологии.
public class ArtificialIntelligenceLevel1 extends ArtificialIntelligenceLevel0 {
    private static int pointsHorizontal, pointsVertical, pointsMainDiagonal1, pointsMainDiagonal2, pointsMainDiagonal11, pointsMainDiagonal22;

    protected static void turnAI1(Boxes[][] boxes, int pointsToWin) {
        if (!isTurnAI1Realization(boxes, 2, pointsToWin)) {
            turnAI0(boxes);
        }
    }

    protected static boolean isTurnAI1Realization(Boxes[][] boxes, int maxPreVictory, int pointsToWin) {
        int newPointsToWin;
        for (int preVictory = 1; preVictory <= maxPreVictory; preVictory++) {
            newPointsToWin = pointsToWin - preVictory;
            if (isMadeLineTurnImportant(boxes, pointsToWin - preVictory + 1)) return true;
            if (isMadeDiagonalTurnImportant(boxes, pointsToWin - preVictory + 1)) return true;
            for (int i = 0; i < boxes.length; i++) {
                if (isMadeLineTurn(boxes, i, newPointsToWin, preVictory)) return true;
                if (isMadeDiagonalTurn(boxes, i, newPointsToWin, preVictory)) return true;
            }
        }
        return false;
    }

    protected static boolean isMadeLineTurn(Boxes[][] boxes, int i, int pointsToWin, int needingPoints) {
        pointsHorizontal = 0;
        pointsVertical = 0;
        for (int j = 0; j < boxes.length; j++) {
            if (boxes[i][j].isSymbol(Boxes.USER)) {
                pointsHorizontal++;
                if (pointsHorizontal == pointsToWin) {
                    if (isEmptyNextBoxForLine(boxes, i, j + 1, 'e', needingPoints) && isMachineMadeTurn(boxes[i][j + 1])) {
                        return true;
                    } else if (j - pointsToWin >= 0 && isEmptyNextBoxForLine(boxes, i, j - pointsToWin, 'w', needingPoints) && isMachineMadeTurn(boxes[i][j - pointsToWin])) {
                        return true;
                    }
                }
            } else {
                pointsHorizontal = 0;
            }
            if (boxes[j][i].isSymbol(Boxes.USER)) {
                pointsVertical++;
                if (pointsVertical == pointsToWin) {
                    if (isEmptyNextBoxForLine(boxes, j + 1, i, 's', needingPoints) && isMachineMadeTurn(boxes[j + 1][i])) {
                        return true;
                    } else if (j - pointsToWin >= 0 && isEmptyNextBoxForLine(boxes, j - pointsToWin, i, 'n', needingPoints) && isMachineMadeTurn(boxes[j - pointsToWin][i])) {
                        return true;
                    }
                }
            } else {
                pointsVertical = 0;
            }
        }
        return false;
    }

    protected static boolean isMachineMadeTurn(Boxes box) {
        if (box.isEmpty()) {
            box.setCircle();
            return true;
        } else {
            return false;
        }
    }

    protected static boolean isEmptyNextBoxForLine(Boxes[][] boxes, int i, int j, char side, int needingPoints) {
        int points;
        switch (side) {
            case 'n':
                points = i - needingPoints + 1;
                if (points >= 0 && (boxes[points][j].isEmpty() || boxes[points][j].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            case 'e':
                points = j + needingPoints - 1;
                if (points < boxes.length && (boxes[i][points].isEmpty() || boxes[i][points].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            case 's':
                points = i + needingPoints - 1;
                if (points < boxes.length && (boxes[points][j].isEmpty() || boxes[points][j].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            case 'w':
                points = j - needingPoints + 1;
                if (points >= 0 && (boxes[i][points].isEmpty() || boxes[i][points].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    protected static boolean isMadeLineTurnImportant(Boxes[][] boxes, int pointsToWin) {
        for (int i = 0; i < boxes.length; i++) {
            pointsHorizontal = 0;
            pointsVertical = 0;
            int[] bufferBoxHorizontal = {-1, -1};
            int[] bufferBoxVertical = {-1, -1};
            int bufferHorizontal = 0, bufferVertical = 0, flagUserSymbolHorizontal = 0, flagUserSymbolVertical = 0;
            for (int j = 0; j < boxes.length; j++) {
                //Horizontal
                if (boxes[i][j].isSymbol(Boxes.USER)) {
                    flagUserSymbolHorizontal = 1;
                    pointsHorizontal++;
                    if (bufferBoxHorizontal[0] >= 0 && pointsHorizontal + bufferHorizontal == pointsToWin) {
                        isMachineMadeTurn(boxes[bufferBoxHorizontal[0]][bufferBoxHorizontal[1]]);
                        return true;
                    }
                } else {
                    if (boxes[i][j].isEmpty() && flagUserSymbolHorizontal == 1 && boxes[i][j - 1].isSymbol(Boxes.USER)) {
                        bufferHorizontal = pointsHorizontal + 1;
                        bufferBoxHorizontal[0] = i;
                        bufferBoxHorizontal[1] = j;
                        pointsHorizontal = 0;
                    } else {
                        pointsHorizontal = 0;
                        bufferBoxHorizontal[0] = -1;
                        bufferBoxHorizontal[1] = -1;
                        bufferHorizontal = 0;
                    }
                }
                //Vertical
                if (boxes[j][i].isSymbol(Boxes.USER)) {
                    flagUserSymbolVertical = 1;
                    pointsVertical++;
                    if (bufferBoxVertical[0] >= 0 && pointsVertical + bufferVertical == pointsToWin) {
                        isMachineMadeTurn(boxes[bufferBoxVertical[0]][bufferBoxVertical[1]]);
                        return true;
                    }
                } else {
                    if (boxes[j][i].isEmpty() && flagUserSymbolVertical == 1 && boxes[j - 1][i].isSymbol(Boxes.USER)) {
                        bufferVertical = pointsVertical + 1;
                        bufferBoxVertical[0] = j;
                        bufferBoxVertical[1] = i;
                        pointsVertical = 0;
                    } else {
                        pointsVertical = 0;
                        bufferBoxVertical[0] = -1;
                        bufferBoxVertical[1] = -1;
                        bufferVertical = 0;
                    }
                }
            }
        }
        return false;
    }

    protected static boolean isMadeDiagonalTurnImportant(Boxes[][] boxes, int pointsToWin) {
        for (int i = 0; i < boxes.length; i++) {
            pointsMainDiagonal1 = 0;
            pointsMainDiagonal2 = 0;
            pointsMainDiagonal11 = 0;
            pointsMainDiagonal22 = 0;
            int[] bufferBoxDiagonal1 = {-1, -1};
            int[] bufferBoxDiagonal2 = {-1, -1};
            int[] bufferBoxDiagonal11 = {-1, -1};
            int[] bufferBoxDiagonal22 = {-1, -1};
            int bufferDiagonal1 = 0, bufferDiagonal2 = 0, flagUserSymbolDiagonal1 = 0, flagUserSymbolDiagonal2 = 0;
            int bufferDiagonal11 = 0, bufferDiagonal22 = 0, flagUserSymbolDiagonal11 = 0, flagUserSymbolDiagonal22 = 0;
            for (int j = 0; j < boxes.length - i; j++) {
                //Diagonal1
                if (boxes[i + j][j].isSymbol(Boxes.USER)) {
                    pointsMainDiagonal1++;
                    flagUserSymbolDiagonal1 = 1;
                    if (bufferBoxDiagonal1[0] >= 0 && pointsMainDiagonal1 + bufferDiagonal1 == pointsToWin) {
                        isMachineMadeTurn(boxes[bufferBoxDiagonal1[0]][bufferBoxDiagonal1[1]]);
                        return true;
                    }
                } else {
                    if (boxes[i + j][j].isEmpty() && flagUserSymbolDiagonal1 == 1 && boxes[i + j - 1][j - 1].isSymbol(Boxes.USER)) {
                        bufferDiagonal1 = pointsMainDiagonal1 + 1;
                        bufferBoxDiagonal1[0] = i + j;
                        bufferBoxDiagonal1[1] = j;
                        pointsMainDiagonal1 = 0;
                    } else {
                        pointsMainDiagonal1 = 0;
                        bufferBoxDiagonal1[0] = -1;
                        bufferBoxDiagonal1[1] = -1;
                        bufferDiagonal1 = 0;
                    }
                }
                //Diagonal1-2
                if (boxes[j][j + i].isSymbol(Boxes.USER)) {
                    pointsMainDiagonal11++;
                    flagUserSymbolDiagonal11 = 1;
                    if (bufferBoxDiagonal11[0] >= 0 && pointsMainDiagonal11 + bufferDiagonal11 == pointsToWin) {
                        isMachineMadeTurn(boxes[bufferBoxDiagonal11[0]][bufferBoxDiagonal11[1]]);
                        return true;
                    }
                } else {
                    if (boxes[j][j + i].isEmpty() && flagUserSymbolDiagonal11 == 1 && boxes[j - 1][j + i - 1].isSymbol(Boxes.USER)) {
                        bufferDiagonal11 = pointsMainDiagonal11 + 1;
                        bufferBoxDiagonal11[0] = j;
                        bufferBoxDiagonal11[1] = j + i;
                        pointsMainDiagonal11 = 0;
                    } else {
                        pointsMainDiagonal11 = 0;
                        bufferBoxDiagonal11[0] = -1;
                        bufferBoxDiagonal11[1] = -1;
                        bufferDiagonal11 = 0;
                    }
                }
                //Diagonal2
                if (boxes[i + j][boxes.length - j - 1].isSymbol(Boxes.USER)) {
                    flagUserSymbolDiagonal2 = 1;
                    pointsMainDiagonal2++;
                    if (bufferBoxDiagonal2[0] >= 0 && pointsMainDiagonal2 + bufferDiagonal2 == pointsToWin) {
                        isMachineMadeTurn(boxes[bufferBoxDiagonal2[0]][bufferBoxDiagonal2[1]]);
                        return true;
                    }
                } else {
                    if (boxes[j + i][boxes.length - j - 1].isEmpty() && flagUserSymbolDiagonal2 == 1 && boxes[i + j - 1][boxes.length - j].isSymbol(Boxes.USER)) {
                        bufferDiagonal2 = pointsMainDiagonal2 + 1;
                        bufferBoxDiagonal2[0] = j + i;
                        bufferBoxDiagonal2[1] = boxes.length - j - 1;
                        pointsMainDiagonal2 = 0;
                    } else {
                        pointsMainDiagonal2 = 0;
                        bufferBoxDiagonal2[0] = -1;
                        bufferBoxDiagonal2[1] = -1;
                        bufferDiagonal2 = 0;
                    }
                }
                //Diagonal2-2
                if (boxes[j][boxes.length - i - j - 1].isSymbol(Boxes.USER)) {
                    flagUserSymbolDiagonal22 = 1;
                    pointsMainDiagonal22++;
                    if (bufferBoxDiagonal22[0] >= 0 && pointsMainDiagonal22 + bufferDiagonal22 == pointsToWin) {
                        isMachineMadeTurn(boxes[bufferBoxDiagonal22[0]][bufferBoxDiagonal22[1]]);
                        return true;
                    }
                } else {
                    if (boxes[j][boxes.length - i - j - 1].isEmpty() && flagUserSymbolDiagonal22 == 1 && boxes[j - 1][boxes.length - i - j].isSymbol(Boxes.USER)) {
                        bufferDiagonal22 = pointsMainDiagonal22 + 1;
                        bufferBoxDiagonal22[0] = j;
                        bufferBoxDiagonal22[1] = boxes.length - j - i - 1;
                        pointsMainDiagonal22 = 0;
                    } else {
                        pointsMainDiagonal22 = 0;
                        bufferBoxDiagonal22[0] = -1;
                        bufferBoxDiagonal22[1] = -1;
                        bufferDiagonal22 = 0;
                    }
                }
            }
        }
        return false;
    }

    protected static boolean isMadeDiagonalTurn(Boxes[][] boxes, int i, int pointsToWin, int needingPoints) {
        pointsMainDiagonal1 = 0;
        pointsMainDiagonal2 = 0;
        pointsMainDiagonal11 = 0;
        pointsMainDiagonal22 = 0;
        //Diagonal1
        for (int j = 0; j < boxes.length - i; j++) {
            if (boxes[i + j][j].isSymbol(Boxes.USER)) {
                pointsMainDiagonal1++;
                if (pointsMainDiagonal1 == pointsToWin) {
                    if (isEmptyNextBoxForDiagonal(boxes, i + j + 1, j + 1, '1', needingPoints) && isMachineMadeTurn(boxes[i + j + 1][j + 1])) {
                        return true;
                    } else if (j + i - pointsToWin >= 0 && isEmptyNextBoxForDiagonal(boxes, i + j - pointsToWin, j - pointsToWin, 'q', needingPoints) && isMachineMadeTurn(boxes[i + j - pointsToWin][j - pointsToWin])) {
                        return true;
                    }
                }
            } else {
                pointsMainDiagonal1 = 0;
            }
            //Diagonal11
            if (boxes[j][i + j].isSymbol(Boxes.USER)) {
                pointsMainDiagonal11++;
                if (pointsMainDiagonal11 == pointsToWin) {
                    if (isEmptyNextBoxForDiagonal(boxes, j + 1, i + j + 1, '1', needingPoints) && isMachineMadeTurn(boxes[j + 1][i + j + 1])) {
                        return true;
                    } else if (j - pointsToWin >= 0 && isEmptyNextBoxForDiagonal(boxes, j - pointsToWin, i + j - pointsToWin, 'q', needingPoints) && isMachineMadeTurn(boxes[j - pointsToWin][i + j - pointsToWin])) {
                        return true;
                    }
                }
            } else {
                pointsMainDiagonal11 = 0;
            }
            //Diagonal2
            if (boxes[j + i][boxes.length - j - 1].isSymbol(Boxes.USER)) {
                pointsMainDiagonal2++;
                if (pointsMainDiagonal2 == pointsToWin) {
                    if (boxes.length - j - 2 >= 0 && isEmptyNextBoxForDiagonal(boxes, j + i + 1, boxes.length - j - 2, '2', needingPoints) && isMachineMadeTurn(boxes[j + i + 1][boxes.length - j - 2])) {
                        return true;
                    } else if (j + i - pointsToWin >= 0 && boxes.length - j - 1 + pointsToWin < boxes.length && isEmptyNextBoxForDiagonal(boxes, j + i - pointsToWin, boxes.length - j - 1 + pointsToWin, 'w', needingPoints) && isMachineMadeTurn(boxes[j + i - pointsToWin][boxes.length - j - 1 + pointsToWin])) {
                        return true;
                    }
                }
            } else {
                pointsMainDiagonal2 = 0;
            }
            //Diagonal22
            if (boxes[j][boxes.length - j - i - 1].isSymbol(Boxes.USER)) {
                pointsMainDiagonal22++;
                if (pointsMainDiagonal22 == pointsToWin) {
                    if (boxes.length - j - i - 2 >= 0 && isEmptyNextBoxForDiagonal(boxes, j + 1, boxes.length - j - i - 2, '2', needingPoints) && isMachineMadeTurn(boxes[j + 1][boxes.length - j - i - 2])) {
                        return true;
                    } else if (j - pointsToWin >= 0 && boxes.length - j - i - 1 + pointsToWin < boxes.length && isEmptyNextBoxForDiagonal(boxes, j - pointsToWin, boxes.length - j - i - 1 + pointsToWin, 'w', needingPoints) && isMachineMadeTurn(boxes[j - pointsToWin][boxes.length - j - i - 1 + pointsToWin])) {
                        return true;
                    }
                }
            } else {
                pointsMainDiagonal22 = 0;
            }
        }
        return false;
    }


    protected static boolean isEmptyNextBoxForDiagonal(Boxes[][] boxes, int i, int j, char side, int needingPoints) {
        int pointsI;
        int pointsJ;
        switch (side) {
            case '1':
                pointsI = i + needingPoints - 1;
                pointsJ = j + needingPoints - 1;
                if (pointsI < boxes.length && pointsJ < boxes.length && (boxes[pointsI][pointsJ].isEmpty() || boxes[pointsI][pointsJ].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            case 'q':
                pointsI = i - needingPoints + 1;
                pointsJ = j - needingPoints + 1;
                if (pointsI >= 0 && pointsJ >= 0 && (boxes[pointsI][pointsJ].isEmpty() || boxes[pointsI][pointsJ].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            case '2':
                pointsI = i + needingPoints - 1;
                pointsJ = i - needingPoints + 1;
                if (pointsI < boxes.length && pointsJ >= 0 && (boxes[pointsI][pointsJ].isEmpty() || boxes[pointsI][pointsJ].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            case 'w':
                pointsI = i - needingPoints + 1;
                pointsJ = i + needingPoints - 1;
                if (pointsI >= 0 && pointsJ < boxes.length && (boxes[pointsI][pointsJ].isEmpty() || boxes[pointsI][pointsJ].isSymbol(Boxes.USER))) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
}




