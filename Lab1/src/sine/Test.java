package sine;

import BPNet.TanhBPNN;
import java.io.*;

/**
 * Created by asus on 2016/10/11.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        TanhBPNN net = new TanhBPNN("Lab1/src/sine/sine-conf.txt");

        String testFileName = "Lab1/test/testbp/sine.txt";

        String distFileName = "Lab1/result/Sine[14302010038].txt";
        BufferedReader br = new BufferedReader(new FileReader(testFileName));
        BufferedWriter bw = new BufferedWriter(new FileWriter(distFileName));
        String str;

        while((str = br.readLine()) != null) {

            double[] input = {Double.parseDouble(str)};
            net.loadInput(input);
            double rs = net.getOpt()[0];
            bw.write(rs+"\r\n");
        }
        br.close();
        bw.close();

    }
}
