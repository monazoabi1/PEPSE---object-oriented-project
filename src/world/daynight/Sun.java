package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The Sun class represents the sun in the game world.
 */
public class Sun {
    /**
     * Creates a new sun GameObject with the specified window dimensions and cycle length.
     * The sun GameObject moves in a circular path around a fixed center point.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the sun's cycle, in seconds.
     * @return A GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        // Create a circular renderable representing the sun
        Renderable cycleColor = new OvalRenderable(Color.YELLOW);

        // Create the sun GameObject with the circular renderable
        GameObject sun = new GameObject(Vector2.ZERO, new Vector2(200, 200), cycleColor);

        // Set the center of the sun to be at the center of the window
        sun.setCenter(windowDimensions.mult(0.5f));

        // Set the coordinate space of the sun to be in camera coordinates
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Set a tag for the sun GameObject
        sun.setTag("sun");

        // Define the initial center and cycle center of the sun
        Vector2 initialSunCenter = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2, (2 * windowDimensions.y()) / 3);

        // Create a transition to move the sun in a circular path around the cycle center
        new Transition<Float>(sun, (Float angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter)
                .rotated(angle).add(cycleCenter)), 0f, 360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);

        return sun;
    }
}
