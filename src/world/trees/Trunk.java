package pepse.world.trees;

import pepse.Constants;
import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Observer;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 * The Trunk class represents the trunk of a tree in the game world.
 * It extends the GameObject class and implements the Observer interface.
 */
public class Trunk extends GameObject implements Observer {
    private static final Renderable trunkRenderable = new RectangleRenderable(new Color(100, 50, 20));

    /**
     * Constructs a Trunk object with the specified top-left corner and dimensions.
     *
     * @param topLeftCorner The top-left corner position of the trunk.
     * @param dimensions    The dimensions (width and height) of the trunk.
     */
    public Trunk(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions, trunkRenderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * Implements the rotateAndChange method defined in the Observer interface.
     * This method currently has an empty implementation.
     */
    @Override
    public void rotateAndChange() {
        // Currently, no action is performed when the trunk is rotated or changed.
        // However, the renderer's renderable is updated with a rectangle renderable.
        this.renderer().setRenderable(new RectangleRenderable(
                ColorSupplier.approximateColor(new Color(Constants.ONE_HUNDRED, Constants.FIFTY,
                                                        Constants.TWENTY))));
    }
}
