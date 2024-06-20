package src;

import src.game.Game;
import src.render.Surface;
import src.utility.Constants;
import src.render.Window;
import src.utility.Vec2;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public static Thread newTicker(double dt, Game game) {
        return new Thread() {
            double lastFrame = System.nanoTime();

            public void run() {
                while (game.running) {
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

    public static Thread newTimeStepper(double dt, Game game) {
        return new Thread() {
            final double halfDt = dt * 0.5;

            double accumulator = 0;
            double lastFrame = System.nanoTime();

            public void run() {
                while (game.running) {
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
