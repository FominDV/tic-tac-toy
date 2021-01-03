package fomin;
//Это окно с выбором параметром для игры: символы, размер карты, количество символов для победы, уровень сложности. Тут же происходит проверка на допустимые значения и кнопка, вызывающая следующий режим игры с выбранными параметрами.
//Тут же передаются значения татическим переменным в Boxes и InterGame
import com.sun.org.apache.bcel.internal.generic.FADD;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame implements ActionListener {
    static final String[] NAMES_OF_LEVELS = {"EASY", "MEDIUM", "HARD", "NIGHTMARE"};
    static final int WIDTH = 1000;
    static final int HEIGHT = 800;
    private JPanel radioButtonsPanel = new JPanel(new FlowLayout());
    private JPanel radioButtonsSymbolPanel = new JPanel(new FlowLayout());
    private JPanel[] menuRowPanel = new JPanel[5];
    private JPanel menuPanel = new JPanel(new GridLayout(5, 1));
    static JButton startButton = new JButton("START");
    static ButtonGroup buttonGroup = new ButtonGroup();
    static ButtonGroup buttonSymbolGroup = new ButtonGroup();
    static JRadioButton[] levelRadioButton = new JRadioButton[4];
    static JRadioButton[] symbolRadioButton = new JRadioButton[2];
    static JLabel sizeLabel = new JLabel("Select the size of the game map:");
    static JLabel numberOfSymbolsToWinLabel = new JLabel("Choose the number of symbols to win:");
    static JLabel greaterLabel = new JLabel("<html><p align='center'>Welcome to the game TIC-TAC-toe!<br> Select the necessary parameters to start the game and click 'start'" +
            "<br>Number of symbols for win should to be greater than 2 and less or equal than size of the map.<br>Size of the map should be greater than 2 and less than 24</p></html>");
    static JLabel levelLabel = new JLabel("Select the difficulty level:");
    static JLabel symbolLabel = new JLabel("Select your symbol:");
    static JTextField sizeTF = new JTextField(2);
    static JTextField numberOfSymbolsToWinTF = new JTextField(2);
    Font textMenuFont = new Font("SANS_SERIF", Font.BOLD, 25);
    Font textGreaterFont = new Font("SANS_SERIF", Font.BOLD, 30);
    Font textButton = new Font("SANS_SERIF", Font.BOLD, 100);

    StartWindow() {
        super();
        setTitle("Tic-tic-toe by Dmitriy Fomin");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        initializationElements();
        setVisible(true);
    }

    protected void initializationElements() {
        greaterLabel.setFont(textGreaterFont);
        sizeLabel.setFont(textMenuFont);
        sizeTF.setFont(textMenuFont);
        numberOfSymbolsToWinLabel.setFont(textMenuFont);
        numberOfSymbolsToWinTF.setFont(textMenuFont);
        levelLabel.setFont(textMenuFont);
        symbolLabel.setFont(textMenuFont);
        startButton.setFont(textButton);
        for (int i = 0; i < menuRowPanel.length; i++) {
            menuRowPanel[i] = new JPanel(new FlowLayout());
        }
        //рисуем графические элементы
        menuRowPanel[1].add(sizeLabel);
        menuRowPanel[1].add(sizeTF);
        menuRowPanel[2].add(numberOfSymbolsToWinLabel);
        menuRowPanel[2].add(numberOfSymbolsToWinTF);
        menuRowPanel[3].add(levelLabel);
        for (int i = 1; i <= levelRadioButton.length; i++) {
            levelRadioButton[i - 1] = new JRadioButton(NAMES_OF_LEVELS[i - 1]);
            levelRadioButton[i - 1].setFont(textMenuFont);
            buttonGroup.add(levelRadioButton[i - 1]);
            radioButtonsPanel.add(levelRadioButton[i - 1]);
        }
        levelRadioButton[2].setSelected(true);
        menuRowPanel[3].add(radioButtonsPanel);
        menuRowPanel[4].add(symbolLabel);
        symbolRadioButton[0] = new JRadioButton("x");
        symbolRadioButton[1] = new JRadioButton("O");
        symbolRadioButton[0].setFont(textMenuFont);
        symbolRadioButton[1].setFont(textMenuFont);
        buttonSymbolGroup.add(symbolRadioButton[0]);
        buttonSymbolGroup.add(symbolRadioButton[1]);
        symbolRadioButton[0].setSelected(true);
        radioButtonsSymbolPanel.add(symbolRadioButton[0]);
        radioButtonsSymbolPanel.add(symbolRadioButton[1]);
        menuRowPanel[4].add(radioButtonsSymbolPanel);
        for (int i = 0; i < menuRowPanel.length; i++) {
            menuPanel.add(menuRowPanel[i]);
        }
        greaterLabel.setForeground(new Color(0, 128, 128));
        menuPanel.setBackground(new Color(0,128,128));
        add(greaterLabel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(this);
        sizeTF.setText(null);
        numberOfSymbolsToWinTF.setText(null);
    }
    //проверяем допустимость параметров и запускаем игровое поле
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSetValidValue()) {
            Main.newStartParametersGame();
        }
    }
    //проверка введённых параметров
    private static boolean isSetValidValue() {
        int size = 0;
        int numberOfSymbols = 0;
        sizeTF.setBackground(Color.WHITE);
        numberOfSymbolsToWinTF.setBackground(Color.WHITE);
        if (sizeTF.getText().equals("") || numberOfSymbolsToWinTF.equals("")) {
            JOptionPane.showMessageDialog(null, "ERROR\nYou should enter size of the map and number of symbols for win");
            if (sizeTF.getText().equals("")) sizeTF.setBackground(Color.RED);
            if (numberOfSymbolsToWinTF.getText().equals("")) numberOfSymbolsToWinTF.setBackground(Color.RED);
            return false;
        }
        try {
            size = Integer.parseInt(sizeTF.getText());
        } catch (Exception e) {
            e.printStackTrace();
            sizeTF.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "ERROR\nYou should enter only integer numbers.\nYour entered size is not Integer value!\nTry again, please.");
            sizeTF.setText(null);
            return false;
        }
        try {
            numberOfSymbols = Integer.parseInt(numberOfSymbolsToWinTF.getText());
        } catch (Exception e) {
            e.printStackTrace();
            numberOfSymbolsToWinTF.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "ERROR\nYou should enter only integer numbers.\nYour entered number of symbols is not Integer value!\nTry again, please.");
            numberOfSymbolsToWinTF.setText(null);
            return false;
        }
        if (!(size > 2 && size <= 23)) {
            sizeTF.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "ERROR\nSize of the map should be greater than 2 and less than 24");
            sizeTF.setText(null);
            return false;
        }
        if (!(numberOfSymbols > 2 && numberOfSymbols <= size)) {
            numberOfSymbolsToWinTF.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "ERROR\nNumber of symbols for win should to be greater than 2 and less or equal than size of the map");
            numberOfSymbolsToWinTF.setText(null);
            return false;
        }
        GameWindow.sizeOfMap = size;
        InterGame.pointsToWin = numberOfSymbols;
        for (int i = 0; i < levelRadioButton.length; i++) {
            if (levelRadioButton[i].isSelected()) {
                InterGame.levelAI = i;
            }
        }
        for (int i = 0; i < symbolRadioButton.length; i++) {
            if (symbolRadioButton[i].isSelected()) {
                if (i == 0) {
                    Boxes.USER = 'X';
                    Boxes.MACHINE = 'O';
                } else {
                    Boxes.USER = 'O';
                    Boxes.MACHINE = 'X';
                }
            }
        }
        return true;
    }
}
