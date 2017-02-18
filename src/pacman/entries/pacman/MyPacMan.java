package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
        for (int s = 0; s < g.mChromosome.length; s += Neuron.NUMBER_OF_INPUTS + 1) {
            for (int i = 0; i < neurons.length; i++) {
                float[] weights = new float[Neuron.NUMBER_OF_INPUTS];
                System.arraycopy(g.mChromosome, s, weights, 0, weights.length);
                float threshold = g.mChromosome[s + weights.length];
                neurons[i] = new Neuron(weights, threshold);
            }
        }
    }

	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		//Hämta input, skicka in till predict metoden. Ta outputten och gör movet som den sa. Här ska du ha 5 neuroner.

        DataTuple input = new DataTuple(game, MOVE.NEUTRAL);
        ArrayList<MOVE> moves = new ArrayList<>();

        for (int i = 0; i < neurons.length; i++) {
            if (neurons[i].predictMove(input)) {
                moves.add(MOVE.values()[i]);
            }
        }
        if (moves.size() > 0)
        {
            return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
        }

        return MOVE.NEUTRAL;
	}
}