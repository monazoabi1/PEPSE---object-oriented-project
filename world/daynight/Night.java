package pepse.world.daynight;

import pepse.Constants;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The Night class represents the night time in the game world.
 */
public class Night {
    /**
     * The opacity value for midnight.
     */
    private static final Float MIDNIGHT_OPACITY = (float) Constants.HALF;

    /**
     * Creates a new night GameObject with the specified window dimensions and cycle length.
     * The night GameObject transitions from transparent to opaque and back in a continuous cycle.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the day-night cycle, in seconds.
     * @return A GameObject representing the night.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        Renderable renderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, renderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");

        new Transition<Float>(night, night.renderer()::setOpaqueness, 0f, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength / Constants.TWO,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        return night;
    }
}
