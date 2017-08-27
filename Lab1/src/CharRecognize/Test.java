package CharRecognize;

import BPNet.TanhBPNN;

import java.io.*;

/**
 * Created by asus on 2016/10/13.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        TanhBPNN net = new TanhBPNN("Lab1/src/CharRecognize/CharRecognize-conf.txt");
        double[][] testSet = DataHandler.lord_testSet("Lab1/test/testletterBP",4000);
        predict(net,testSet, "Lab1/result/LetterBP[14302010038].txt");
    }


    private static void predict(TanhBPNN net,double[][] testSet, String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for(double[] testUnit:testSet) {
            net.loadInput(testUnit);
            bw.write(vectorToChar(net.getOpt())+"\r\n");
        }
        bw.close();
        System.out.println("Finished!");
    }

    public static char vectorToChar(double[] vector) {
        int index = 0;
        for(int i = 0;i<vector.length;i++) {
            if(vector[i]>vector[index])
                index = i;
        }
        return (char)(index+65);
    }
}
