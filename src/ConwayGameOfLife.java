import engine.AbstractEngine;
import engine.Engine;
import engine.Renderer;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class ConwayGameOfLife extends AbstractEngine {

    private boolean[][] positions;
    private boolean[][] newPositions;
    private boolean isPlaying = false;

    public ConwayGameOfLife() {

    }

    @Override
    public void start(Engine engine) {

        positions = new boolean[engine.getWidth()][engine.getHeight()];
        newPositions = new boolean[engine.getWidth()][engine.getHeight()];

        for (int x = 0; x < engine.getWidth(); x++) {
            for (int y = 0; y < engine.getHeight(); y++) {
                positions[x][y] = false;
                newPositions[x][y] = false;
            }
        }

    }

    double step = 0.0;

    @Override
    public void update(Engine engine, float delta) {

        if (engine.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
            isPlaying = !isPlaying;
        }

        if (engine.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {

            for (int y = 0; y < engine.getHeight(); y++) {
                for (int x = 0; x < engine.getWidth(); x++) {
                    positions[x][y] = false;
                    newPositions[x][y] = false;
                }
            }

        }


        if (isPlaying) {

            //TODO: conway game of life

            step += delta * 10;

            if (step >= 2.0) {
                for (int y = 0; y < engine.getHeight(); y++) {
                    for (int x = 0; x < engine.getWidth(); x++) {
                        int n = getNeighbourCount(x, y, engine);
                        boolean isAlive = positions[x][y];

                        if (isAlive) {

                            if (n < 2 || n > 3) {
                                die(x, y);
                            }

                            if (n == 2 || n == 3) {
                                live(x, y);
                            }

                        }

                        if (!isAlive) {

                            if (n == 3) {
                                live(x, y);
                            }

                        }

                    }
                }

                positions = deepCopy(newPositions);
                step = 0.0;
            }

        }
        else {
            if (engine.getInput().isButton(1)) {

                int x = engine.getInput().getMouseX();
                int y = engine.getInput().getMouseY();

                positions[x][y] = true;
            }

            if (engine.getInput().isButton(3)) {
                int x = engine.getInput().getMouseX();
                int y = engine.getInput().getMouseY();

                positions[x][y] = false;
            }
        }

    }

    @Override
    public void render(Engine engine, Renderer renderer, float delta) {

        for (int x = 0; x < engine.getWidth(); x++) {
            for (int y = 0; y < engine.getHeight(); y++) {
                renderer.setPixel(x, y, positions[x][y] ? 0xffffffff : 0xff000000);
            }
        }

    }

    public static void main(String[] args) {

        Engine engine = new Engine(new ConwayGameOfLife());

        engine.setHeight(64);
        engine.setWidth(64);

        engine.setScale(12f);

        engine.start();
    }

    private void die(int x, int y) {
        newPositions[x][y] = false;
    }

    private void live(int x, int y) {
        newPositions[x][y] = true;
    }

    private int getNeighbourCount(int xPos, int yPos, Engine engine) {

        int neighbours = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (x == 0 && y == 0) { continue; }

                int xV = xPos + x;
                int yV = yPos + y;

                if (xV < 0 || xV >= engine.getWidth() || yV < 0 || yV >= engine.getHeight()) {
                    continue;
                }

                boolean value = positions[xV][yV];

                if (value == true) {
                    neighbours++;
                }

            }
        }

        return neighbours;

    }

    public static boolean[][] deepCopy(boolean[][] original) {
        if (original == null) {
            return null;
        }

        final boolean[][] result = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }

}
