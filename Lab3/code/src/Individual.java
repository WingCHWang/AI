/**
 * Created by asus on 2016/12/27.
 */
public class Individual {
    private int[] genes;
    private int geneLen;
    private double fitness;


    public Individual(int geneLen) {
        this.geneLen = geneLen;
        genes = new int[geneLen];
    }

    public Individual clone() {
        Individual indiv = new Individual(this.geneLen);
        for(int i = 0; i<geneLen; i++) {
            indiv.setGene(i, this.getGene(i));
        }
        indiv.setFitness(this.getFitness());
        return indiv;
    }

    public void setGeneLen(int geneLen) {
        this.geneLen = geneLen;
    }

    public int getGeneLen() {return geneLen;}

    public int[] getGenes() {return genes;}

    public int getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, int gene) {
        genes[index] = gene;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
