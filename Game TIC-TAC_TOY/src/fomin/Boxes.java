package fomin;

import javax.swing.*;
import java.awt.*;
//Этот класс описывает наши игровые ячейки-кнопки, расширяет JButton. Сдесь хранятся изначальные значения символов, а так же методы по работе с данными в каждом экземпляре.
public class Boxes extends JButton {
    protected static boolean flagTurnAI2=false;
    protected char symbol;
    static final char EMPTY = 'e';
    protected static char USER;
    protected static char MACHINE;
    private int SIZE_OF_TEXT_INTO_BOX = getFontSize();

    //'e' is 'empty'
    Boxes() {
        setFont(new Font("SANS_SERIF", Font.BOLD, SIZE_OF_TEXT_INTO_BOX));
        symbol = EMPTY;
    }

    protected boolean isEmpty() {
        if (symbol == EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    protected void setCircle() {
        if(flagTurnAI2){
            symbol = USER;
            setText(String.valueOf(USER));
        }else{
            symbol = MACHINE;
            setText(String.valueOf(MACHINE));
        }
    }

    protected void setCross() {
        symbol = USER;
        setText(String.valueOf(USER));
    }

    protected void setColor() {
        setBackground(Color.RED);
    }

    protected boolean isSymbol(char symbol) {
        if (this.symbol == symbol) {
            return true;
        } else {
            return false;
        }
    }

    protected static int getFontSize() {
        if (GameWindow.sizeOfMap <= 15) return 600 / GameWindow.sizeOfMap;
        if (GameWindow.sizeOfMap <= 19) return 400 / GameWindow.sizeOfMap;
        if (GameWindow.sizeOfMap <= 22) return 300 / GameWindow.sizeOfMap;
        if (GameWindow.sizeOfMap <= 23) return 250 / GameWindow.sizeOfMap;
        return 1;
    }
}

