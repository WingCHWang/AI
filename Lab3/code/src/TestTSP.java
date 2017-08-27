import java.io.*;

/**
 * Created by asus on 2016/12/28.
 */
public class TestTSP {
    public static void main(String[] args) throws IOException {



        TSP tsp1 = createTSP("D:/intellij/AI/Lab3/test/testtsp/TSP1.txt");
        tsp1.evolve();
        output("D:/intellij/AI/Lab3/result/TSP-1[14302010038].txt", tsp1);
        System.out.println("TSP1:");
        System.out.println("best fitness: "+ -tsp1.getBestFitness());
        System.out.println("average fitness: "+ -tsp1.getAverageFitness()+"\n");

        TSP tsp2 = createTSP("D:/intellij/AI/Lab3/test/testtsp/TSP2.txt");
        tsp2.evolve();
        output("D:/intellij/AI/Lab3/result/TSP-2[14302010038].txt", tsp2);
        System.out.println("TSP2:");
        System.out.println("best fitness: "+ -tsp2.getBestFitness());
        System.out.println("average fitness: "+ -tsp2.getAverageFitness()+"\n");

        TSP tsp3 = createTSP("D:/intellij/AI/Lab3/test/testtsp/TSP3.txt");
        tsp3.evolve();
        output("D:/intellij/AI/Lab3/result/TSP-3[14302010038].txt", tsp3);
        System.out.println("TSP3:");
        System.out.println("best fitness: "+ -tsp3.getBestFitness());
        System.out.println("average fitness: "+ -tsp3.getAverageFitness()+"\n");


        //tsp1.drawData();
        //tsp2.drawData();
        //tsp3.drawData();
    }

    static TSP createTSP(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String para = br.readLine();
        int geneLen = Integer.parseInt(para);

        double[] X = new double[geneLen];
        double[] Y = new double[geneLen];
        String[] item;
        for(int i = 0; i<geneLen; i++) {
            item = br.readLine().split(" ");
            X[i] = Double.parseDouble(item[1]);
            Y[i] = Double.parseDouble(item[2]);
        }

        return new TSP(geneLen, X, Y);
    }

    static void output(String fileName, TSP tsp) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(-tsp.getBestFitness()+"\r\n");
        int[] genes = tsp.getBestGene();
        for(int i = 0; i<genes.length; i++) {
            bw.write((i+1)+" "+genes[i]+"\r\n");
        }
        bw.flush();
        bw.close();
    }



}
