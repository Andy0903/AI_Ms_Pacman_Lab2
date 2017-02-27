package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants;

/**
 * Created by Andy on 2017-02-17.
 */
public class Neuron {

    int numberOfInputs;
    float[] weights;
    float threshold;

   /*
    public Neuron() {
        weights = new float[NUMBER_OF_INPUTS];
        threshold = 0.2f;

        for (int i = 0; i < NUMBER_OF_INPUTS; i++) {
            weights[i] = (float)Math.random();
        }
    }
    */

    public Neuron(int inputNumber, float[] weights, float threshold)
    {
        numberOfInputs = inputNumber;
        if (weights.length != numberOfInputs)
        {
            throw new IllegalArgumentException();
        }

        this.weights = weights;
        this.threshold = threshold;
    }

    public float predictMove(float[] inputs)
    {
        if (inputs.length != numberOfInputs)
        {
            throw new IllegalArgumentException();
        }

        float sum = 0;
        for (int i = 0; i < numberOfInputs; i++) {
            sum += inputs[i] * weights[i];
        }

        sum -= threshold;
        float sigmoid = (float) (1 / (1 + Math.pow(Math.E, -sum)));

        return sigmoid;
    }
}
