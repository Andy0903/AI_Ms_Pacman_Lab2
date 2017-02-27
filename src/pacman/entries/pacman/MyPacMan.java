package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static pacman.game.Constants.MOVE.NEUTRAL;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
    public static final int NUMBER_OF_INPUTS = 13;
    public static final int NUMBER_OF_HIDDEN = (NUMBER_OF_INPUTS);
    public static final int NUMBER_OF_OUTPUT = MOVE.values().length;
    private Neuron[] hidden = new Neuron[NUMBER_OF_HIDDEN];
    private Neuron[] output = new Neuron[NUMBER_OF_OUTPUT];

    public MyPacMan(Gene g)
    {
        for (int i = 0; i < hidden.length; i++) {
            int srcPos = (NUMBER_OF_INPUTS + 1) * i;
            float[] weights = new float[NUMBER_OF_INPUTS];
            System.arraycopy(g.mChromosome, srcPos, weights, 0, weights.length);
            float threshold = g.mChromosome[srcPos + weights.length];
            hidden[i] = new Neuron(NUMBER_OF_INPUTS, weights, threshold);
        }

        for (int i = 0; i < output.length; i++) {
            int srcPos = (NUMBER_OF_HIDDEN + 1) * i + NUMBER_OF_HIDDEN * (NUMBER_OF_INPUTS + 1);
            float[] weights = new float[NUMBER_OF_HIDDEN];
            System.arraycopy(g.mChromosome, srcPos, weights, 0, weights.length);
            float threshold = g.mChromosome[srcPos + weights.length];
            output[i] = new Neuron(NUMBER_OF_HIDDEN, weights, threshold);
        }
    }

    public static HashMap<MOVE, Integer> choice = new HashMap<MOVE, Integer>();

	public MOVE getMove(Game game, long timeDue) 
	{
        float biggestVal = Float.NEGATIVE_INFINITY;
        MOVE move = NEUTRAL;

        DataTuple input = new DataTuple(game, NEUTRAL);

        float[] inputs = new float[] {
                //(float)input.normalizeLevel(input.currentLevel),
                (float)input.normalizePosition(input.pacmanPosition),
                //(float)input.normalizeNumberOfPills(input.numOfPillsLeft),
                //(float)input.normalizeNumberOfPowerPills(input.numOfPowerPillsLeft),
                (float)input.normalizeBoolean(input.isBlinkyEdible),
                (float)input.normalizeBoolean(input.isInkyEdible),
                (float)input.normalizeBoolean(input.isPinkyEdible),
                (float)input.normalizeBoolean(input.isSueEdible),
                input.blinkyDir.ordinal() / (float) Constants.MOVE.values().length,
                input.inkyDir.ordinal() / (float)Constants.MOVE.values().length,
                input.pinkyDir.ordinal() / (float)Constants.MOVE.values().length,
                input.sueDir.ordinal() / (float)Constants.MOVE.values().length,
                //(float)Math.abs(input.normalizeDistance(input.blinkyDist)),
                //(float)Math.abs(input.normalizeDistance(input.inkyDist)),
                //(float)Math.abs(input.normalizeDistance(input.pinkyDist)),
                //(float)Math.abs(input.normalizeDistance(input.sueDist)),
                (float)input.normalizePosition(game.getGhostCurrentNodeIndex(Constants.GHOST.BLINKY)),
                (float)input.normalizePosition(game.getGhostCurrentNodeIndex(Constants.GHOST.INKY)),
                (float)input.normalizePosition(game.getGhostCurrentNodeIndex(Constants.GHOST.PINKY)),
                (float)input.normalizePosition(game.getGhostCurrentNodeIndex(Constants.GHOST.SUE))
        };



        float[] hiddenOutputs = new float[hidden.length];
        for (int i = 0; i < hidden.length; i++) {
            hiddenOutputs[i] = hidden[i].predictMove(inputs);
        }

        for (int i = 0; i < output.length; i++) {
            float moveVal = output[i].predictMove(hiddenOutputs);
            if (moveVal > biggestVal) {
                move = MOVE.values()[i];
                biggestVal = moveVal;
            }
        }

        if (choice.containsKey(move) == false)
        {
            choice.put(move, 0);
        }
        choice.put(move, choice.get(move) + 1);

        return move;
	}
}