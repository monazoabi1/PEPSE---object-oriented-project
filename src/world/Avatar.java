package pepse.world;
import pepse.Constants;
import danogl.*;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Vector2;
import pepse.EnergyRenderer;
import pepse.util.Observer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * The Avatar class represents the player-controlled character in the game world.
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private final UserInputListener inputListener;
    /**
     * energy represents avatar's energy
     */
    public static double energy;
    private final AnimationRenderable animationNotMoving;
    private final AnimationRenderable animationRun;
    private final AnimationRenderable animationJump;
    private ArrayList<Observer> observers;
    private final EnergyRenderer energyRenderer;



    /**
     * Constructs an Avatar object with the specified parameters.
     *
     * @param pos           The initial position of the avatar.
     * @param inputListener The user input listener for controlling the avatar.
     * @param imageReader   The image reader for loading avatar images.
     */

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {

        super(pos, Vector2.ONES.mult(Constants.FIFTY), imageReader.readImage("assets/idle_0.png",
                true));

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        this.inputListener = inputListener;
        double timeToChange = Constants.TIME_TO_CHANGE_ANIMATION;
        this.energy = Constants.ONE_HUNDRED;
        setTag("avatar");
        Renderable[] jumpRender = new Renderable[]
                {imageReader.readImage(Constants.JUMP0, true),
                        imageReader.readImage(Constants.JUMP1, true),
                        imageReader.readImage(Constants.JUMP2, true),
                        imageReader.readImage(Constants.JUMP3, true)};
        Renderable[] notMovingRender = new Renderable[]
                {imageReader.readImage(Constants.IDLE0, true),
                        imageReader.readImage(Constants.IDLE1, true),
                        imageReader.readImage(Constants.IDLE2, true),
                        imageReader.readImage(Constants.IDLE3, true)};
        Renderable[] runRender = new Renderable[]
                {imageReader.readImage(Constants.RUN0, true),
                        imageReader.readImage(Constants.RUN1, true),
                        imageReader.readImage(Constants.RUN2, true),
                        imageReader.readImage(Constants.RUN3, true),
                        imageReader.readImage(Constants.RUN4, true),
                        imageReader.readImage(Constants.RUN5, true)};

        animationNotMoving = new AnimationRenderable(notMovingRender, timeToChange);
        animationJump = new AnimationRenderable(jumpRender, timeToChange);
        animationRun = new AnimationRenderable(runRender, timeToChange);
        this.energyRenderer = new EnergyRenderer(new TextRenderable(String.valueOf(energy)));
        this.observers = new ArrayList<>();
//        EnergyRendererInner inner = .get
    }

    /**
     * Adds an observer to the avatar.
     *
     * @param obs The observer to be added.
     */

    public void implementObserver(Observer obs) {
        this.observers.add(obs);
    }

    /**
     * Notifies all observers.
     */
    private void notifyObs() {
        for (Observer obs : observers) {
            obs.rotateAndChange();
        }
    }

    /**
     * Updates the state of the avatar based on user input and game logic.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        energy = Math.min(100, energy);
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= VELOCITY_X;
            renderer().setIsFlippedHorizontally(true);
            renderer().setRenderable(animationRun);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += VELOCITY_X;
            renderer().setIsFlippedHorizontally(false);
            renderer().setRenderable(animationRun);
        }
        if (xVel != 0 && energy - Constants.HALF >= 0) {
            energy -= Constants.HALF;
        } else if (xVel != 0) {
            xVel = 0;
        }
        transform().setVelocityX(xVel);
        continueUpdating();
    }

    /**
     * Handles avatar's actions and energy management.
     */

    private void continueUpdating() {
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
            if (energy - Constants.TEN >= 0) {
                notifyObs();
                energy -= Constants.TEN;
                transform().setVelocityY(VELOCITY_Y);
                renderer().setRenderable(animationJump);
            } else {
                transform().setVelocityY(Constants.ZERO);
            }
        }
        if (transform().getVelocity().equals(Vector2.ZERO) && (energy != Constants.ONE_HUNDRED)) {
            energy += Math.min(1, Constants.ONE_HUNDRED - energy);
        }

        energyRenderer.setEnergy(energy);
        if (transform().getVelocity().equals(Vector2.ZERO)) {
            renderer().setRenderable(animationNotMoving);
        }
    }


    /**
     * Retrieves the energy renderer associated with the avatar.
     *
     * @return The energy renderer.
     */
    public EnergyRenderer getter() {
        return energyRenderer;
    }



    class EnergyRendererInner extends GameObject {
        private TextRenderable textRenderable;

        /**
         * Construct a new GameObject instance.
         *
         * @param topLeftCorner Position of the object, in window coordinates (pixels).
         *                      Note that (0,0) is the top-left corner of the window.
         * @param dimensions    Width and height in window coordinates.
         * @param renderable    The renderable representing the object. Can be null, in which case
         *                      the GameObject will not be rendered.
         */
        private Function<Integer, TextRenderable> create;
        public EnergyRendererInner(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
            super(topLeftCorner, dimensions, renderable);

           this.create = (energy)->{
                TextRenderable text = new TextRenderable(energy.toString());
                text.setColor(Color.BLACK);
                return text;
            };
            }

        public Function<Integer,TextRenderable> getRender(){
            return create;


        }
    }
}
