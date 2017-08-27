package lcdD;

import BPNet.TanhBPNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created by asus on 2016/10/23.
 */
public class Train {
    public static void main(String[] args) throws Exception {
        TanhBPNN net = new TanhBPNN(7, 24, 10);

        /*
         生成训练集
        */
        HashMap<double[],double[]> trainSet = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("Lab1/dataset_bp/train/lcdD.txt"));
        String str;
        while((str = br.readLine()) != null) {
            String[] strs = str.split(" ");
            double[] input = new double[7];
            double[] desire = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
            for(int i = 0; i< 7; i++) {
                input[i] = (Integer.parseInt(strs[i])-0.5)*2;
            }
            desire[Integer.parseInt(strs[7])] = 1;
            trainSet.put(input,desire);

        }
        br.close();

        /*

         */
        /*
        int selected;
        for(int times = 0; times<10000000; times++) {
            selected = (int)(Math.random()*10);
            //System.out.println(selected);
            BPNet.loadTrainUnit(inputSet[selected], desireSet[selected]);
            BPNet.train();

        }

        BPNet.adjustLR(0.05);
        for(int times = 0; times<10000000; times++) {
            selected = (int)(Math.random()*10);
            BPNet.loadTrainUnit(inputSet[selected], desireSet[selected]);
            BPNet.train();

        }
*/
        /*
        hidden = 24,5000000
        v1.lr = 0.05
        v2 lr = 0.03
        v3 lr = 0.08
        v4 lr = 0.04

        hidden = 12
        v5 lr = 0.05,5000000

        hidden = 36
        v5 lr = 0.05,5000000

         */

        for(int times = 0; times<100000; times++) {
            net.adjustLR(0.05/(1+times/25000));
            for(double[] input : trainSet.keySet()) {
                double[] desire = trainSet.get(input);
                net.loadTrainUnit(input, desire);
                net.train();
            }
        }




/*
        net.adjustLR(0.03);
        for(int times = 0; times<5000000; times++) {
            selected = (int)(Math.random()*10);
            net.loadTrainUnit(inputSet[selected], desireSet[selected]);
            net.train();

        }
/*
        net.adjustLR(0.03);
        for(int times = 0; times<10000000; times++) {
            selected = (int)(Math.random()*10);
            net.loadTrainUnit(inputSet[selected], desireSet[selected]);
            net.train();

        }
*/





        net.writeConf("Lab1/src/lcdD/lcdD-conf.txt");
        System.out.println("finish training");

    }
}
