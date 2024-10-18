package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The Sky class represents the sky object in the game world.
 */
public class Sky {
    /**
     * The basic color of the sky.
     */
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * Constructs a new Sky object.
     * This class is not meant to be instantiated.
     */
    private Sky() {}

    /**
     * Creates a new sky GameObject with the specified window dimensions.
     * The sky rectangle moves with the camera.
     *
     * @param windowDimensions The dimensions of the game window.
     * @return A GameObject representing the sky.
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag("sky");
        return sky;
    }
}
