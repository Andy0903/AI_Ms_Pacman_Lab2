package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants;

/**
 * Created by Andy on 2017-02-17.
 */
public class Neuron {

    public static final  int NUMBER_OF_INPUTS = 2;
    float[] weights;
    float threshold;

    public Neuron() {
        weights = new float[NUMBER_OF_INPUTS];
        threshold = 0.2f;

        for (int i = 0; i < NUMBER_OF_INPUTS; i++) {
            weights[i] = (float)Math.random();
        }
    }

    public Neuron(float[] weights, float threshold)
    {
        if (weights.length != NUMBER_OF_INPUTS)
        {
            throw new IllegalArgumentException();
        }

        this.weights = weights;
        this.threshold = threshold;
    }

    public boolean predictMove(DataTuple input) {

        float[] inputs = new float[] {
                (float)input.normalizeLevel(input.currentLevel),
                (float)input.normalizePosition(input.pacmanPosition),
            //    (float)input.normalizeNumberOfPills(input.numOfPillsLeft),
            //    (float)input.normalizeNumberOfPowerPills(input.numOfPowerPillsLeft),
            //    (float)input.normalizeBoolean(input.isBlinkyEdible),
            //    (float)input.normalizeBoolean(input.isInkyEdible),
            //    (float)input.normalizeBoolean(input.isPinkyEdible),
            //    (float)input.normalizeBoolean(input.isSueEdible),
            //    input.blinkyDir.ordinal() / (float)Constants.MOVE.values().length,
            //    input.inkyDir.ordinal() / (float)Constants.MOVE.values().length,
            //    input.pinkyDir.ordinal() / (float)Constants.MOVE.values().length,
            //    input.sueDir.ordinal() / (float)Constants.MOVE.values().length
        };

        float sum = 0;
        for (int i = 0; i < NUMBER_OF_INPUTS; i++) {
            sum += inputs[i] * weights[i];
        }

        sum = sum / NUMBER_OF_INPUTS;

        return sum > threshold;
    }
}
