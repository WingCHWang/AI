package sine;

import BPNet.TanhBPNN;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * Created by asus on 2016/10/11.
 */
public class Train {
    public static void main(String[] args) throws Exception {
        TanhBPNN net = new TanhBPNN(1, 15, 1);


        /*
        * 生成训练集
        */
        HashMap<double[],double[]> trainSet = new HashMap<>();

        double alpha =0;
        for(int i = 1;i<=100;i+=2) {
            alpha += Math.PI /100;
            double[] input1 = {alpha};
            double[] desire1 = {Math.sin(alpha)};
            trainSet.put(input1,desire1);
            double[] input2 = {-alpha};
            double[] desire2 = {Math.sin(-alpha)};
            trainSet.put(input2,desire2);

        }

        for(int times = 0; times<1000000; times++) {
            net.adjustLR(0.05/(1+times/500000));
            for(double[] input : trainSet.keySet()) {
                double[] desire = trainSet.get(input);
                net.loadTrainUnit(input, desire);
                net.train();
            }

        }



        net.writeConf("Lab1/src/sine/sine-conf.txt");
        System.out.println("finish training");

    }

}
