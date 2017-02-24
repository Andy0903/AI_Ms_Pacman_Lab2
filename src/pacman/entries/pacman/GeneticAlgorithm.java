package pacman.entries.pacman;

import pacman.controllers.examples.RandomGhosts;
import pacman.controllers.examples.RandomPacMan;
import pacman.game.Game;

import java.util.ArrayList;     // arrayLists are more versatile than arrays
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static pacman.game.Constants.DELAY;


/**
 * Genetic Algorithm sample class <br/>
 * <b>The goal of this GA sample is to maximize the number of capital letters in a String</b> <br/>
 * compile using "javac GeneticAlgorithm.java" <br/>
 * test using "java GeneticAlgorithm" <br/>
 *
 * @author A.Liapis
 */

public class GeneticAlgorithm {
    // --- constants
   public static int CHROMOSOME_SIZE= (Neuron.NUMBER_OF_INPUTS + 1) * MyPacMan.NUMBER_OF_NEURONS; // +1 for threshold.
   public static int POPULATION_SIZE=10;

    // --- variables:

    /**
     * The population contains an ArrayList of genes (the choice of arrayList over
     * a simple array is due to extra functionalities of the arrayList, such as sorting)
     */
    ArrayList<Gene> mPopulation;

    // --- functions:

    /**
     * Creates the starting population of Gene classes, whose chromosome contents are random
     * @param size: The size of the popultion is passed as an argument from the main class
     */
    public GeneticAlgorithm(int size){
        // initialize the arraylist and each gene's initial weights HERE
        mPopulation = new ArrayList<Gene>();
        for(int i = 0; i < size; i++){
            Gene entry = new Gene();
            entry.randomizeChromosome();
            mPopulation.add(entry);
        }
    }
    /**
     * For all members of the population, runs a heuristic that evaluates their fitness
     * based on their phenotype. The evaluation of this problem's phenotype is fairly simple,
     * and can be done in a straightforward manner. In other cases, such as agent
     * behavior, the phenotype may need to be used in a full simulation before getting
     * evaluated (e.g based on its performance)
     */
    public void evaluateGeneration(){
      //  for(int i = 0; i < mPopulation.size(); i++) {
            // evaluation of the fitness function for each gene in the population goes HERE
      //      mPopulation.get(i).setFitness(CalculateFitness(mPopulation.get(i)));
      //  }
        mPopulation.stream().forEach((a)->a.setFitness(CalculateFitness(a)));
    }

    private float CalculateFitness(Gene g) {
        int trials = 20;
        float avgScore=0;

        Random rnd = new Random(0);
        Game game;

        for(int i=0;i<trials;i++)
        {
            game=new Game(rnd.nextLong());
            MyPacMan pac = new MyPacMan(g);
            RandomGhosts ghosts = new RandomGhosts();

            while(!game.gameOver())
            {
                game.advanceGame(pac.getMove(game.copy(),System.currentTimeMillis()+DELAY),
                        ghosts.getMove(game.copy(),System.currentTimeMillis()+DELAY));
            }

            avgScore+=game.getScore();
            //System.out.println(i+"\t"+game.getScore());
        }

        return avgScore/trials;
    }


    /**
     * With each gene's fitness as a guide, chooses which genes should mate and produce offspring.
     * The offspring are added to the population, replacing the previous generation's Genes either
     * partially or completely. The population size, however, should always remain the same.
     * If you want to use mutation, this function is where any mutation chances are rolled and mutation takes place.
     */

    boolean sak2 = false;

    public void produceNextGeneration(){
        // use one of the offspring techniques suggested in class (also applying any mutations) HERE

        int numberToKill = (int)(POPULATION_SIZE * 0.6);
        int numbersAlive = POPULATION_SIZE - numberToKill;

        Collections.sort(mPopulation);  //Ascending Order.
        if (sak2 == false)
        {
            sak2 = true;
            mPopulation.get(mPopulation.size()-1).sak = true;
        }

        for (int m = 0; m < mPopulation.size(); m++) {
            if (mPopulation.get(m).sak)
            {
                System.out.println(mPopulation.get(m).getFitness());
            }
        }


        mPopulation.subList(0, numberToKill).clear();

        for (int i = 0; i < numberToKill / 2; i++) {
          Gene[] children = mPopulation.get(i % numbersAlive).reproduce(mPopulation.get(ThreadLocalRandom.current().nextInt(numbersAlive)));
     //       Gene[] children = new Gene[2];
     //       children[0] = new Gene();
     //       children[1] = new Gene();

     //       children[0].randomizeChromosome();
     //       children[1].randomizeChromosome();

          if (i % 10 == 0) // 10% chance of mutation.
          {
              children[0].mutate();
              children[1].mutate();
          }

          mPopulation.add(children[0]);
          mPopulation.add(children[1]);
        }

    }

    // accessors
    /**
     * @return the size of the population
     */
    public int size(){ return mPopulation.size(); }
    /**
     * Returns the Gene at position <b>index</b> of the mPopulation arrayList
     * @param index: the position in the population of the Gene we want to retrieve
     * @return the Gene at position <b>index</b> of the mPopulation arrayList
     */
    public Gene getGene(int index){ return mPopulation.get(index); }

    /*
    // Genetic Algorithm maxA testing method
    public static void main( String[] args ){
        // Initializing the population (we chose 500 genes for the population,
        // but you can play with the population size to try different approaches)
        GeneticAlgorithm population = new GeneticAlgorithm(POPULATION_SIZE);
        int generationCount = 0;
        // For the sake of this sample, evolution goes on forever.
        // If you wish the evolution to halt (for instance, after a number of
        //   generations is reached or the maximum fitness has been achieved),
        //   this is the place to make any such checks
        while(true){
            // --- evaluate current generation:
            population.evaluateGeneration();
            // --- print results here:
            // we choose to print the average fitness,
            // as well as the maximum and minimum fitness
            // as part of our progress monitoring
            float avgFitness=0.f;
            float minFitness=Float.POSITIVE_INFINITY;
            float maxFitness=Float.NEGATIVE_INFINITY;
            String bestIndividual="";
		String worstIndividual="";
            for(int i = 0; i < population.size(); i++){
                float currFitness = population.getGene(i).getFitness();
                avgFitness += currFitness;
                if(currFitness < minFitness){
                    minFitness = currFitness;
                    worstIndividual = population.getGene(i).getPhenotype();
                }
                if(currFitness > maxFitness){
                    maxFitness = currFitness;
                    bestIndividual = population.getGene(i).getPhenotype();
                }
            }
            if(population.size()>0){ avgFitness = avgFitness/population.size(); }
            String output = "Generation: " + generationCount;
            output += "\t AvgFitness: " + avgFitness;
            output += "\t MinFitness: " + minFitness + " (" + worstIndividual +")";
            output += "\t MaxFitness: " + maxFitness + " (" + bestIndividual +")";
            System.out.println(output);
            // produce next generation:
            population.produceNextGeneration();
            generationCount++;
        }
    }
    */
};

