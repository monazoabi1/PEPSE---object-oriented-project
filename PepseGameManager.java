package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.util.Observer;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Fruits;

import java.util.ArrayList;
import java.util.List;
import pepse.world.trees.Leaf;

/**
 * The PepseGameManager class manages the game initialization and setup specific to the Pepse game.
 * It extends the GameManager class from the danogl package.
 */
public class PepseGameManager extends GameManager {

    private static final String AVATAR_TAG = "avatar";

    /**
     * Initializes the Pepse game by setting up game objects, terrain, avatar, trees, and fruits.
     * Overrides the initializeGame method from the GameManager class.
     *
     * @param imageReader      The ImageReader object for reading images.
     * @param soundReader      The SoundReader object for reading sounds.
     * @param inputListener    The UserInputListener object for handling user input.
     * @param windowController The WindowController object for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject skyObject = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(skyObject, Layer.BACKGROUND);
        windowController.setTargetFramerate(Constants.FOURTY);
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), Constants.THIRTY);
        List<Block> blocksArray = terrain.createInRange(Constants.ZERO, (int)
                windowController.getWindowDimensions().x());
        for (Block block : blocksArray) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        Avatar avatar = createNightAvatar(imageReader, inputListener, windowController);

        trees(windowController, terrain, avatar);
    }

    /**
     * Creates and adds tree objects, leaves, and fruits to the game world.
     *
     * @param windowController The WindowController object for managing the game window.
     * @param terrain          The Terrain object representing the game terrain.
     * @param avatar           The Avatar object representing the player's character.
     */
    private void trees(WindowController windowController, Terrain terrain, Avatar avatar) {
        Flora flora = new Flora(terrain);
        ArrayList<GameObject> treesArray = flora.addTrunks(0, (int)
                                        windowController.getWindowDimensions().x());
        for (GameObject treeTrunk : treesArray) {
            avatar.implementObserver((Observer) treeTrunk);
            gameObjects().addGameObject(treeTrunk, Layer.STATIC_OBJECTS);
        }

        flora.addLeafAndFruit();




        ArrayList<Leaf> leafsArray = flora.getLeafArray();
        for (Leaf leaf : leafsArray) {
            avatar.implementObserver(leaf);
            leaf.setLeafTransition();
            gameObjects().addGameObject(leaf, Layer.BACKGROUND);
        }

        ArrayList<Fruits> fruitsArray = flora.getFruitArray();
        for (Fruits fruit : fruitsArray) {
            avatar.implementObserver((Observer) fruit);
//
            gameObjects().addGameObject(fruit, Layer.DEFAULT);
        }
    }

    /**
     * Creates and adds the avatar object for the night scene.
     *
     * @param imageReader      The ImageReader object for reading images.
     * @param inputListener    The UserInputListener object for handling user input.
     * @param windowController The WindowController object for managing the game window.
     * @return The Avatar object representing the player's character.
     */
    private Avatar createNightAvatar(ImageReader imageReader, UserInputListener inputListener,
                                     WindowController windowController) {
        GameObject nightObject = Night.create(windowController.getWindowDimensions(), Constants.THIRTY);
        gameObjects().addGameObject(nightObject, Layer.BACKGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), Constants.THIRTY);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        Vector2 initAvatarLocation = new Vector2(0, ((windowController.getWindowDimensions().y()
                * Constants.TWO) / Constants.THREE));
        Avatar avatar = new Avatar(new Vector2(initAvatarLocation.x(),
                initAvatarLocation.y() - Constants.EIGHT_HUNDRED),
                inputListener, imageReader);
        avatar.setTag(AVATAR_TAG);
        gameObjects().addGameObject(avatar);
        gameObjects().addGameObject(avatar.getter(), Layer.BACKGROUND);
        return avatar;
    }

    /**
     * This is the main function
     * @param args
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
