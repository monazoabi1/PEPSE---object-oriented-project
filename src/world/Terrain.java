package pepse.world;

import pepse.Constants;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Terrain class represents the ground and terrain features in the game world.
 * It generates and manages the terrain blocks based on noise generation.
 */
public class Terrain {
    private final float groundHeightAtX0; // Desired ground height at x=0
    private final Vector2 windowDimensions;
    private final NoiseGenerator noiseGenerator;
    private static final Color BASE_GROUND_COLOR = new Color(Constants.GROUND_COLOR_R,
            Constants.GROUND_COLOR_G, Constants.GROUND_COLOR_B);

    /**
     * Constructs a Terrain object with the specified window dimensions and seed for noise generation.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed used for noise generation.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.windowDimensions = windowDimensions;
        this.groundHeightAtX0 = (Constants.TWO * windowDimensions.y()) / Constants.THREE;
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Returns the height of the ground at a specific x-coordinate based on noise generation.
     *
     * @param x The x-coordinate.
     * @return The height of the ground at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        if (x == 0) {
            return groundHeightAtX0;
        }
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * Constants.SEVEN);
        return groundHeightAtX0 + noise;
    }

    /**
     * Creates a list of terrain blocks within the specified x-coordinate range.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A list of terrain blocks within the specified x-coordinate range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blockList = new ArrayList<>();
        int startPosition = (minX / Block.SIZE) * Block.SIZE;

        for (int i = startPosition; i < maxX; i += Block.SIZE) {
            int startPositionY = (int) Math.floor(groundHeightAt(i) / Block.SIZE) * Block.SIZE;
            for (int j = startPositionY; j < windowDimensions.y(); j += Block.SIZE) {
                Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor
                                                (BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(i, j), renderable);
                block.setTag("ground");
                blockList.add(block);
            }
        }

        return blockList;
    }
}
