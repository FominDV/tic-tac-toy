package fomin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Это непосредственно игровое поле, а так же оно преващается в финальное поле, где мы сможем переиграть партию, вернуться к стартовому окну с заданием параметров или выйти.
//Именно тут создаётся само поле и все кнопки. Данный класс запускает последовательность действий игры и проверок, обращаясь к статическим методам InterGame.
/*Содержит свойства размеров окна и размера поля*/
public class GameWindow extends JFrame {
    protected static int sizeOfMap = 10;
    protected static int height = 1000;
    protected static int width = 1000;

    GameWindow() {
        super();
        setTitle("Tic-tic-toe by Dmitriy Fomin");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        makeMap();
        setVisible(true);
    }

    JPanel map = new JPanel(new GridLayout(sizeOfMap, sizeOfMap));
    Boxes boxes[][] = new Boxes[sizeOfMap][sizeOfMap];
    //Рисуем игровое поле из ячеек-кнопок
    protected void makeMap() {
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                final Boxes box = new Boxes();
                box.setName(i + "" + j);
                boxes[i][j] = box;
                box.addActionListener(new ActionListener() {
                    //Запускаем алгоритм проверки исхода игры после каждого хода пользователя, запускаем проверку на возможный конец игры
                    public void actionPerformed(ActionEvent e) {
                        if (box.isEmpty()) {
                            box.setCross();
                            boxes[Character.getNumericValue(box.getName().charAt(0))][Character.getNumericValue(box.getName().charAt(1))] = box;
                            if (InterGame.isVictory(boxes, Boxes.USER)) {
                                JOptionPane.showMessageDialog(null, "It was last turn!");
                                endGame("VICTORY");
                                return;
                            }
                            if (InterGame.isFullMap(boxes)) {
                                JOptionPane.showMessageDialog(null, "It was last turn!");
                                endGame("draw game");
                                return;
                            } else {
                                InterGame.machineTurn(boxes);
                                if (InterGame.isVictory(boxes, Boxes.MACHINE)) {
                                    JOptionPane.showMessageDialog(null, "It was last turn!");
                                    endGame("LOSS");
                                    return;
                                }
                                if (InterGame.isFullMap(boxes)) {
                                    JOptionPane.showMessageDialog(null, "It was last turn!");
                                    endGame("draw game");
                                }
                            }
                        }
                    }
                });
                map.add(box);
            }
        }
        add(map, BorderLayout.CENTER);
    }

    JLabel ending = new JLabel();
    JPanel endButtons = new JPanel(new GridLayout(2, 1));
    JPanel endButtonsBottom = new JPanel(new GridLayout(1, 2));
    Font endText = new Font("SANS_SERIF", Font.BOLD, 100);
    Font endButton = new Font("SANS_SERIF", Font.BOLD, 60);
    JButton exit = new JButton("Exit");
    JButton restart = new JButton("Restart");
    JButton newParametersGame = new JButton("New Game");
    //переделываем наше окно по завершению игры
    protected void endGame(String word) {
        map.setVisible(false);
        ending.setFont(endText);
        ending.setHorizontalAlignment(SwingConstants.CENTER);
        ending.setText(word);
        add(ending, BorderLayout.CENTER);
        exit.setFont(endButton);
        restart.setFont(endButton);
        newParametersGame.setFont((endButton));
        setActionsEndingButtons();
        endButtonsBottom.add(restart);
        endButtonsBottom.add(exit);
        endButtons.add(newParametersGame);
        endButtons.add(endButtonsBottom);
        add(endButtons, BorderLayout.SOUTH);
    }
    //настраиваем обработчик событий к кнопкам финального окна
    protected void setActionsEndingButtons() {
        exit.addActionListener(e -> System.exit(0));
        restart.addActionListener(e -> Main.newGame());
        newParametersGame.addActionListener(e->Main.newParametersGame());
    }
}

