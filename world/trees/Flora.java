package pepse.world.trees;

import pepse.Constants;
import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Flora class represents the vegetation in the game world.
 * It generates and manages trees, leaves, and fruits.
 */
public class Flora{
    private final ArrayList<GameObject> treesArray;
    private final ArrayList<Leaf> leafsArray;
    private final ArrayList<Fruits> fruitsArray;

    private final Terrain terrain;

    /**
     * Constructs a Flora object with the specified terrain.
     *
     * @param terrain The terrain object used for tree generation.
     */
    public Flora(Terrain terrain) {
        this.treesArray = new ArrayList<>();
        this.leafsArray = new ArrayList<>();
        this.fruitsArray = new ArrayList<Fruits>();

        this.terrain = terrain;
    }

    /**
     * Adds tree trunks to the flora within the specified x-coordinate range.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return The list of tree trunks added to the flora.
     */
    public ArrayList<GameObject> addTrunks(int minX, int maxX) {
        int lastAdded = 0;
        Random randomForHeight = new Random();
        Random randomForTreeX = new Random();

        for (int i = minX; i < maxX; ++i) {
            int randomNumber = randomForTreeX.nextInt(Constants.ONE_HUNDRED * Constants.TWO);
            // Ensure minimum distance between two trees is maintained
            if (randomNumber == 1 && i - i % Constants.THIRTY > lastAdded + Constants.FOURTY) {
                int height = randomForHeight.nextInt(Constants.ONE_HUNDRED) + Constants.ONE_HUNDRED;
                double startPositionY = (double) Math.floor(terrain.groundHeightAt
                                        (i - i % Constants.THIRTY)
                        / Block.SIZE) * Block.SIZE;
                Vector2 topLeftCorner = new Vector2(i - i % Constants.THIRTY, (float)
                                        (startPositionY - height));
                Vector2 dimensions = new Vector2(Constants.THIRTY, height);
                Trunk trunk = new Trunk(topLeftCorner, dimensions);
                trunk.setTag("trunk");
                treesArray.add(trunk);
                lastAdded = i - i % Constants.THIRTY;
            }
        }
        return treesArray;
    }

    /**
     * Adds leaves and fruits to the trees within the flora.
     *
     * @return An array containing the lists of leaves and fruits added to the flora.
     */
    public void addLeafAndFruit() {
//        ArrayList<Fruits> fruitsArray = new ArrayList<>();
        Renderable fruitRender = new OvalRenderable(Color.PINK);

        for (GameObject trunk : treesArray) {
            Vector2 topLeftCorner = trunk.getTopLeftCorner();
            Random random = new Random();

            for (int i = (int) (topLeftCorner.x() - Constants.TWO * Constants.THIRTY); i < topLeftCorner.x()+
                    Constants.THREE * Constants.THIRTY; i += Constants.THIRTY) {
                for (int j = (int) (topLeftCorner.y() - Constants.FIVE * Constants.THIRTY);
                     j < topLeftCorner.y(); j += Constants.THIRTY) {
                    int randomLeaf = random.nextInt(Constants.TWO);
                    int randomFruit = random.nextInt(Constants.FIVE);
                    if (randomFruit == 1) {
                        Fruits fruit = new Fruits(new Vector2(i, j),
                                new Vector2(Constants.FRUIT_SIZE, Constants.FRUIT_SIZE), fruitRender);
                        this.fruitsArray.add(fruit);
                    }

                    if (randomLeaf == 1) {
                        Leaf leaf = new Leaf(new Vector2(i,j));
                        leafsArray.add(leaf);
                    }
                }
            }
        }
    }
    /**
     * Retrieves the list of fruits contained within the Flora object.
     *
     * @return An ArrayList containing the fruits in the Flora object.
     */
    public ArrayList<Fruits> getFruitArray() {
        return fruitsArray;
    }

    /**
     * Retrieves the list of leaves contained within the Flora object.
     *
     * @return An ArrayList containing the leaves in the Flora object.
     */
    public ArrayList<Leaf> getLeafArray() {
        return leafsArray;
    }


}
