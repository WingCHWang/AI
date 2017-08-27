import java.io.*;

/**
 * Created by asus on 2016/12/29.
 */
public class TestKnap {
    public static void main(String[] args) throws IOException {



        Knapsack knapsack1 = createKnap("D:/intellij/AI/Lab3/test/testknapsack/Knapsack1.txt");
        knapsack1.evolve();
        output("D:/intellij/AI/Lab3/result/Knapsack-1[14302010038].txt", knapsack1);
        System.out.println("knapsack1: ");
        System.out.println("best fitness: "+ knapsack1.getBestFitness());
        System.out.println("average fitness: "+ knapsack1.getAverageFitness()+"\n");

        Knapsack knapsack2 = createKnap("D:/intellij/AI/Lab3/test/testknapsack/Knapsack2.txt");
        knapsack2.evolve();
        output("D:/intellij/AI/Lab3/result/Knapsack-2[14302010038].txt", knapsack2);
        System.out.println("knapsack2: ");
        System.out.println("best fitness: "+ knapsack2.getBestFitness());
        System.out.println("average fitness: "+ knapsack2.getAverageFitness()+"\n");

        Knapsack knapsack3 = createKnap("D:/intellij/AI/Lab3/test/testknapsack/Knapsack3.txt");
        knapsack3.evolve();
        output("D:/intellij/AI/Lab3/result/Knapsack-3[14302010038].txt", knapsack3);
        System.out.println("knapsack3: ");
        System.out.println("best fitness: "+ knapsack3.getBestFitness());
        System.out.println("average fitness: "+ knapsack3.getAverageFitness()+"\n");


        //knapsack1.drawData();

    }

    static Knapsack createKnap(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String[] para = br.readLine().split(" ");
        double capacity = Double.parseDouble(para[0]);
        int geneLen = Integer.parseInt(para[1]);

        double[] weight = new double[geneLen];
        double[] value = new double[geneLen];
        String[] item;
        for(int i = 0; i<geneLen; i++) {
            item = br.readLine().split(" ");
            weight[i] = Double.parseDouble(item[0]);
            value[i] = Double.parseDouble(item[1]);
        }
        return new Knapsack(geneLen, weight, value, capacity);
    }

    static void output(String fileName, Knapsack knapsack) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(knapsack.getBestFitness()+"\r\n");
        int[] genes = knapsack.getBestGene();
        for(int i = 0; i<genes.length; i++) {
            bw.write((i+1)+" "+genes[i]+"\r\n");
        }
        bw.flush();
        bw.close();
    }

}
