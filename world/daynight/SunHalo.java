package pepse.world.daynight;

import pepse.Constants;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The SunHalo class represents the halo effect around the sun in the game world.
 */
public class SunHalo {
    /**
     * Creates a new halo GameObject around the sun.
     *
     * @param sun The sun GameObject for which the halo is created.
     * @return A GameObject representing the halo around the sun.
     */
    public static GameObject create(GameObject sun) {
        Color sunColor = new Color(255, 255, 0, Constants.TWENTY);

        Renderable cycleColor = new OvalRenderable(sunColor);
        Vector2 haloLocation = new Vector2(220, 220);
        GameObject halo = new GameObject(Vector2.ZERO, haloLocation, cycleColor);
        halo.setCenter(sun.getCenter().mult((float) Constants.HALF));
        halo.addComponent(deltaTime -> halo.setCenter(sun.getCenter()));
        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        return halo;
    }
}
