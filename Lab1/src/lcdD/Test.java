package lcdD;

import BPNet.TanhBPNN;

import java.io.*;

/**
 * Created by asus on 2016/10/23.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        TanhBPNN lcdD = new TanhBPNN("Lab1/src/lcdD/lcdD-conf.txt");

        String testFileName = "Lab1/test/testbp/lcdd.txt";

        String distFileName = "Lab1/result/LcdD[14302010038].txt";
        BufferedReader br = new BufferedReader(new FileReader(testFileName));
        BufferedWriter bw = new BufferedWriter(new FileWriter(distFileName));
        String str;

        while((str = br.readLine()) != null) {
            String[] strs = str.split(" ");
            double[] input = new double[7];
            for (int i = 0; i < 7; i++) {
                input[i] = (Integer.parseInt(strs[i])-0.5) *2;
            }
            lcdD.loadInput(input);
            int rs =  vectorToNum(lcdD.getOpt());
            bw.write(rs+"\r\n");
        }
        br.close();
        bw.close();


    }

    private static int vectorToNum(double[] vector) {
        int index = 0;
        for(int i = 0;i<vector.length;i++) {
            if(vector[i]>vector[index])
                index = i;
        }
        return index;
    }
}
