package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
    private Neuron[] neurons = new Neuron[MOVE.values().length];

	public MyPacMan()
	{
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron();
        }
	}

	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		//Hämta input, skicka in till predict metoden. Ta outputten och gör movet som den sa. Här ska du ha 5 neuroner.

        DataTuple input = new DataTuple(game, MOVE.NEUTRAL);

        for (int i = 0; i < neurons.length; i++) {
            if (neurons[i].predictMove(input)) {
                return MOVE.values()[i];
            }
        }
        return MOVE.NEUTRAL;
	}
}