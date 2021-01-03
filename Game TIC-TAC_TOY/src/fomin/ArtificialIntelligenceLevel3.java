package fomin;

//Данный класс ИИ делает всё то же, что и его суперкласс. Но теперь ИИ в строгом приоритете блокирует ходы игрока при любом количстве символов до предпологаемой победы. В приоритете старается выигрыть сам при этом.
public class ArtificialIntelligenceLevel3 extends ArtificialIntelligenceLevel2 {
    protected static void turnAI3(Boxes[][] boxes, int pointsToWin) {
        for (int i = 1; i < pointsToWin; i++) {
            if (isTurnAI2Realization(boxes, i, pointsToWin)) {
                return;
            } else {
                if (isTurnAI1Realization(boxes, i, pointsToWin)) {
                    return;
                }
            }
        }
        turnAI0(boxes);
    }
}
