package pepse.world.trees;

import pepse.Constants;
import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Observer;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

/**
 * The Leaf class represents a leaf GameObject in the game world.
 * It extends the GameObject class and implements the Observer interface.
 */
public class Leaf extends GameObject implements Observer {



    /**
     * Constructs a new Leaf object with the specified top-left corner position.
     *
     * @param topLeftCorner The position of the top-left corner of the leaf, in window coordinates (pixels).
     */
    Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, new Vector2(Constants.THIRTY, Constants.THIRTY),
                new RectangleRenderable(new Color(Constants.FIFTY, Constants.TWO_HUNDRED, Constants.THIRTY)));

        Random random = new Random();
        int randomLeaf = random.nextInt(Constants.TWO);

        if (randomLeaf == 1) {
            Renderable leafRender = new RectangleRenderable(new Color(Constants.FIFTY,
                    Constants.TWO_HUNDRED, Constants.THIRTY));
            GameObject leaf = new GameObject(topLeftCorner, new Vector2(Constants.THIRTY, Constants.THIRTY),
                    leafRender);
//            setLeafTransition();
            setTag("leaf");

            new ScheduledTask(leaf, (float) Constants.FIVE,
                    true,  this::setLeafTransition);
                }
            }





    /**
     * Sets the transition for leaf movement.
     */

    public void setLeafTransition() {
        Random random = new Random();
        int randomAngle = random.nextInt(Constants.NINETY) + 20;

        new Transition<>(this, this.renderer()::setRenderableAngle,
                (float) randomAngle,
                 ((float)Constants.ONE_HUNDRED_TWENTY) , Transition.LINEAR_INTERPOLATOR_FLOAT, Constants.FIVE,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
//        counter++;
        float initialWidth = Math.max((float) (Constants.THIRTY)*random.nextFloat(),Constants.TWENTY);
        float finalWidth = Math.min((1+random.nextFloat())*((float) Constants.FOURTY),Constants.FOURTY);
        new Transition<>(this, (x) -> this.setDimensions(new Vector2
                (x,this.getDimensions().y())), initialWidth,finalWidth
                ,
                Transition.LINEAR_INTERPOLATOR_FLOAT, Constants.TWO,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    /**
     * Rotates and changes the state of the flora, triggered by external events.
     */
    @Override
    public void rotateAndChange() {
            new Transition<Float>(this, this.renderer()::setRenderableAngle,
                    0f, (float) Constants.NINETY, Transition.LINEAR_INTERPOLATOR_FLOAT,
                    Constants.TWO, Transition.TransitionType.TRANSITION_ONCE, null);

    }
}
