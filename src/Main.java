package src;

import src.game.Game;
import src.render.Surface;
import src.utility.Constants;
import src.render.Window;
import src.utility.Vec2;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Main extends Window {
    public static void main(String[] args) {
        Window window = new Main();
        Game game = new Game(window);

        window.initWindow(Constants.WINDOW_NAME, new Vec2(Constants.BASE_WIDTH, Constants.BASE_HEIGHT));
        window.scaleWindow(Constants.RES_MUL);
        window.open();

        Thread timeStepper = Main.newTicker(Constants.DT, window, game);
        timeStepper.start();
    }

    public static Thread newTicker(double dt, Window window, Game game) {
        return new Thread() {
            double lastFrame = System.nanoTime();

            public void run() {
                while (window.open) {
                    double t = System.nanoTime();
                    double accumulated = (t - lastFrame) / 1_000_000_000.0;
                    lastFrame = t;

                    try {
                        game.mainLoop(dt);
                        Thread.sleep((long) Math.floor(dt * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Program closed while thread was asleep");
                    }
                }
            }
        };
    }

    public static Thread newTimeStepper(double dt, Window window, Game game) {
        return new Thread() {
            final double halfDt = dt * 0.5;

            double accumulator = 0;
            double lastFrame = System.nanoTime();

            public void run() {
                while (window.open) {
                    double t = System.nanoTime();
                    accumulator += (t - lastFrame) / 1_000_000_000.0;
                    accumulator = Math.min(1, accumulator);  // min 1 fps (avoid spiral of doom)
                    lastFrame = t;

                    while (accumulator >= dt) {
                        accumulator -= dt;

                        try {
                            game.mainLoop(dt);
                            Thread.sleep((long) Math.floor(halfDt * 1000));  // give it a little break *-*
                        } catch (InterruptedException e) {
                            throw new RuntimeException("Program closed while thread was asleep");
                        }
                    }
                }
            }
        };
    }
}
