package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Block class represents a block object in the game world.
 * It extends the GameObject class from the danogl package.
 */
public class Block extends GameObject {
    /**
     * The size of the block.
     */
    public static final int SIZE = 30;

    /**
     * Constructs a new Block object with the specified position and renderable.
     *
     * @param topLeftCorner The position of the top-left corner of the block, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param renderable    The renderable representing the block. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
