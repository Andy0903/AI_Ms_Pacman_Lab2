package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
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
    public static final int NUMBER_OF_NEURONS = MOVE.values().length;
    private Neuron[] neurons = new Neuron[NUMBER_OF_NEURONS];

    public MyPacMan(Gene g)
    {
        //for (int s = 0; s < g.mChromosome.length; s += Neuron.NUMBER_OF_INPUTS + 1) {   //for each neuron in gene (+1 for threshold)
            for (int i = 0; i < neurons.length; i++) {
                int srcPos = (Neuron.NUMBER_OF_INPUTS + 1) * i;
                float[] weights = new float[Neuron.NUMBER_OF_INPUTS];
                System.arraycopy(g.mChromosome, srcPos, weights, 0, weights.length);
                float threshold = g.mChromosome[srcPos + weights.length];
                neurons[i] = new Neuron(weights, threshold);
            }
    }

    public static HashMap<MOVE, Integer> choice = new HashMap<MOVE, Integer>();

	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		//Hämta input, skicka in till predict metoden. Ta outputten och gör movet som den sa. Här ska du ha 5 neuroner.

        DataTuple input = new DataTuple(game, NEUTRAL);
        //ArrayList<MOVE> moves = new ArrayList<>();
        float biggestVal = Float.NEGATIVE_INFINITY;
        MOVE move = NEUTRAL;

        for (int i = 0; i < neurons.length; i++) {
            float moveVal = neurons[i].predictMove(input);
            if (moveVal > biggestVal) {
                move = MOVE.values()[i];

                if (i != 0)
                {
                    int a = 5;
                }
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