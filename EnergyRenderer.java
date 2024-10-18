package pepse;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/**
 * The EnergyRenderer class represents a game object responsible for rendering energy levels.
 * It displays the current energy level as text on the game screen.
 */
public class EnergyRenderer extends GameObject {
    private TextRenderable textRenderable;

    /**
     * Constructs an EnergyRenderer object with the specified text renderable.
     *
     * @param textRenderable The text renderable object representing the energy level.
     */
    public EnergyRenderer(TextRenderable textRenderable) {
        super(new Vector2(Constants.TWENTY, Constants.TWENTY),
                new Vector2(Constants.THIRTY, Constants.THIRTY), textRenderable);
        this.textRenderable = textRenderable;
    }

    /**
     * Sets the energy level to be displayed by updating the text renderable.
     *
     * @param energy The energy level to be displayed.
     */
    public void setEnergy(double energy) {
        textRenderable.setString(String.valueOf(energy));
    }
}
