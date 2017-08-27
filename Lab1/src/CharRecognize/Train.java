package CharRecognize;

import BPNet.TanhBPNN;

import java.util.HashMap;

/**
 * Created by asus on 2016/10/23.
 */
public class Train {
    public static void main(String[] args) throws Exception {

        TanhBPNN net = new TanhBPNN("Lab1/src/CharRecognize/CharRecognize-conf.v7.txt");
       // TanhBPNN net = new TanhBPNN(28*28, 224, 8);

        String directory = "Lab1/dataset_image/train";
        int num = 7780;

        HashMap<double[],double[]> trainSet = DataHandler.lord_trainSet(directory, num);
        HashMap<double[],double[]> validationSet = DataHandler.lord_trainSet("Lab1/dataset_image/validation", 3200);



        for(int times = 0; times<20; times++) {
            net.adjustLR(0.05/(1+times/20));
            for(double[] input : trainSet.keySet()) {
                double[] desire = trainSet.get(input);
                net.loadTrainUnit(input, desire);
                net.train();
            }
            //System.out.println(selected);
        }



        net.writeConf("Lab1/src/CharRecognize/CharRecognize-conf.txt");
        System.out.println("Finish training!");

        System.out.println("right rate(train set):"+validate(net,trainSet));
        System.out.println("right rate(validation set):"+validate(net,validationSet));

    }

    private static double validate(TanhBPNN net, HashMap<double[],double[]> set) {
        double right = 0.0;
        for(double[] input : set.keySet()) {
            double[] desire = set.get(input);
            net.loadInput(input);
            if(Test.vectorToChar(net.getOpt()) == Test.vectorToChar(desire)) {
                right++;
            }
        }
        return right/set.size();

    }
}
