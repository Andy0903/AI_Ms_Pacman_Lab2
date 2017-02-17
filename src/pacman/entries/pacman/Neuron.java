package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants;

/**
 * Created by Andy on 2017-02-17.
 */
public class Neuron {

    final  int numberOfInput = 12;
    float[] weights = new float[numberOfInput];
    float threshold = 0.5f;

    public Neuron() {
        for (int i = 0; i < numberOfInput ; i++) {
            weights[i] = (float)Math.random();
        }
    }

    public boolean predictMove(DataTuple input) {

        float[] inputs = new float[] {
                (float)input.normalizeLevel(input.currentLevel),
                (float)input.normalizePosition(input.pacmanPosition),
                (float)input.normalizeNumberOfPills(input.numOfPillsLeft),
                (float)input.normalizeNumberOfPowerPills(input.numOfPowerPillsLeft),
                (float)input.normalizeBoolean(input.isBlinkyEdible),
                (float)input.normalizeBoolean(input.isInkyEdible),
                (float)input.normalizeBoolean(input.isPinkyEdible),
                (float)input.normalizeBoolean(input.isSueEdible),
     //           (float)input.normalizeDistance(input.blinkyDist),
     //           (float)input.normalizeDistance(input.inkyDist),
     //           (float)input.normalizeDistance(input.pinkyDist),
     //           (float)input.normalizeDistance(input.sueDist),
                input.blinkyDir.ordinal() / (float)Constants.MOVE.values().length,
                input.inkyDir.ordinal() / (float)Constants.MOVE.values().length,
                input.pinkyDir.ordinal() / (float)Constants.MOVE.values().length,
                input.sueDir.ordinal() / (float)Constants.MOVE.values().length
        };

        float sum = 0;
        for (int i = 0; i < numberOfInput; i++) {
            sum += inputs[i] * weights[i];
        }

        sum = sum / numberOfInput;

        return sum > threshold;
    }
}
