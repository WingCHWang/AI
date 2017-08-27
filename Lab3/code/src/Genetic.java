import java.util.ArrayList;

/**
 * Created by asus on 2016/12/28.
 */
public abstract class Genetic {
    protected ArrayList<Individual> population;
    protected int popSize;
    protected double[] probability;

    protected int geneLen;

    protected double crossRate;
    protected double mutationRate;

    private int preSelect = 10;

    ArrayList<Double> bestFitness = new ArrayList<Double>();
    ArrayList<Double> averageFitness = new ArrayList<Double>();

    protected int maxGeneration;

    public Genetic(int popSize, int geneLen, double crossRate, double mutationRate, int maxGeneration) {
        this.population = new ArrayList<Individual>(popSize);
        this.popSize = popSize;
        this.geneLen = geneLen;
        this.crossRate = crossRate;
        this.mutationRate = mutationRate;
        this.maxGeneration = maxGeneration;
        probability = new double[popSize];
    }


    // 计算概率拼盘
    public abstract void calProbability();

    // 根据概率做选择
    public Individual select() {
        calProbability();
        ArrayList<Individual> pop = new ArrayList<Individual>(preSelect);
        for(int i = 0; i<preSelect; i++) {
            double select = Math.random();
            for (int j = 0; j < popSize; j++) {
                if (select <= probability[j]) {
                    pop.add(population.get(j));
                    break;
                }
            }
        }
        return findBest(pop);
    }

    // 找到一个种群中适应度最高的个体
    public Individual findBest(ArrayList<Individual> population) {
        int index = 0;
        double fitness = population.get(0).getFitness();
        for(int i = 1; i<population.size(); i++) {
            if(population.get(i).getFitness() > fitness) {
                fitness  = population.get(i).getFitness();
                index = i;
            }
        }
        return population.get(index);
    }

    public abstract boolean fitness(Individual individual);

    public abstract void crossover(Individual indiv1, Individual indiv2);

    public abstract void mutate(Individual individual);

    public void evolve() {
        int generation = 0;
        while(generation < maxGeneration) {
            bestFitness.add(getBestFitness());
            averageFitness.add(getAverageFitness());

            ArrayList<Individual> newPopulation = new ArrayList<Individual>(popSize);
            newPopulation.add(findBest(population).clone());
            for (int size = 1; size < popSize; ) {
                Individual indiv1 = select().clone();
                Individual indiv2 = select().clone();
                crossover(indiv1, indiv2);
                mutate(indiv1);
                mutate(indiv2);
                if (fitness(indiv1)) {
                    newPopulation.add(indiv1);
                    size++;
                }
                if (fitness(indiv2) && size<popSize) {
                    newPopulation.add(indiv2);
                    size++;
                }
            }
            population = newPopulation;
            generation++;
        }
    }

    public double getBestFitness() {
        return findBest(population).getFitness();
    }

    public int[] getBestGene() {
        return findBest(population).getGenes();
    }

    public double getAverageFitness() {
        double average = 0;
        for(Individual indiv:population)
            average += indiv.getFitness();
        return average/popSize;
    }


}
