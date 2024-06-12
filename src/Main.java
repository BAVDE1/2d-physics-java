package src;

import src.render.Window;

public class Main extends Window {
    public static void main(String[] args) {
        Window window = new Main();
        window.initWindow("some window", 400, 300);
        window.open();
    }
}
