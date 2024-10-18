package pepse.world.trees;

import pepse.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Observer;
import pepse.world.Avatar;
import danogl.gui.rendering.OvalRenderable;
import java.awt.*;
import java.util.Objects;

/**
 * The Fruits class represents fruit objects in the game world.
 * It extends the GameObject class from the danogl package and implements the Observer interface.
 */
public class Fruits extends GameObject implements Observer {
    private Color[] fruitsColors = { Color.red,Color.PINK};
    private int colorFlag =0;

    /**
     * Constructs a new Fruits object with the specified position, dimensions, and renderable.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Fruits(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * Determines if the Fruits object should collide with another GameObject.
     *
     * @param other The GameObject to check collision with.
     * @return true if the Fruits object should collide with the other GameObject, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (this.renderer().getOpaqueness() != 0) {
            return Objects.equals(other.getTag(), "avatar");
        }
        return false;
    }

    /**
     * Handles the event when a collision occurs with another GameObject.
     *
     * @param other     The GameObject with which the collision occurred.
     * @param collision The Collision object representing the details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("avatar")) {
            Avatar.energy += Constants.TEN;
        }
        if (this.renderer().getOpaqueness() != 0) {
            renderer().setOpaqueness(0);
            new ScheduledTask(this, Constants.THIRTY,
                    false, () -> this.renderer().setOpaqueness(1));
        }
    }

    /**
     * Implements the rotateAndChange method from the Observer interface.
     * Changes the appearance of the Fruits object when notified.
     */
    @Override
    public void rotateAndChange() {
        this.renderer().setRenderable(new OvalRenderable(fruitsColors[colorFlag% Constants.TWO]));
        colorFlag++;
    }
}
