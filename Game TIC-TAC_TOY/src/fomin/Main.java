package fomin;

//Содержит методы для преключения между режимами игры. Запускает стартовое окно с выбором параметров. Содержит экземпляры GameWindow и StartWindow
public class Main {
    static StartWindow startWindow;
    static GameWindow window;

    public static void main(String[] args) {
        startWindow = new StartWindow();
    }
    //Рестарт игры
    protected static void newGame() {
        window.dispose();
        window = new GameWindow();
    }
    //Первая игра
    protected static void newStartParametersGame() {
        startWindow.dispose();
        window = new GameWindow();
    }
    //Задание новых пораметров после игры
    protected static void newParametersGame() {
        window.dispose();
        startWindow = new StartWindow();
    }
}

