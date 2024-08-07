package src;

import src.game.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    /**
     * Slow, (very) inaccurate ticker, but light on cpu.
     * Runs program (negligibly) slower.
     * Kind of a hybrid time stepper as it still returns a static dt haha.
     */
    public static Thread newTicker(double dt, Game game) {
        return new Thread() {
            double lastFrame = System.nanoTime();

            public void run() {
                while (game.running) {
                    double t = System.nanoTime();
                    double accumulated = (t - lastFrame) / 1_000_000_000.0;  // kept for debugging purposes
                    lastFrame = t;

                    try {
                        game.mainLoop(dt, accumulated);
                        Thread.sleep((long) Math.floor(dt * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Program closed while thread was asleep");
                    }
                }
            }
        };
    }

    /**
     * Proper, far more accurate time stepper.
     * Heavier on the cpu for second half of time between frames (as thread sleeps for half of dt once stepped).
     */
    public static Thread newTimeStepper(double dt, Game game) {
        return new Thread() {
            final double halfDt = dt * 0.5;

            double accumulator = 0;
            double lastFrame = System.nanoTime();

            public void run() {
                while (game.running) {
                    double t = System.nanoTime();
                    accumulator += (t - lastFrame) / 1_000_000_000.0;
                    accumulator = Math.min(1, accumulator);  // min of 1 fps (avoid spiral of doom)
                    lastFrame = t;

                    while (accumulator >= dt) {
                        accumulator -= dt;

                        try {
                            game.mainLoop(dt, accumulator + dt);
                            if (game.optimiseTimeStepper && accumulator < dt) {  // only sleep if done taking from accumulated
                                Thread.sleep((long) Math.floor(halfDt * 1000));  // give it a little break *-*
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException("Program closed while thread was asleep (between frames)");
                        }
                    }
                }
            }
        };
    }
}
