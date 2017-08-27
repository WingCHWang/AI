package BPNet;

import java.io.*;

/**
 * Created by asus on 2016/10/12.
 */
public class TanhBPNN {

    private int inputNum ;
    private int outputNum;
    private int hiddenNum;

    private double learnRate = 0.05; //learning rate
    private double[] bias1;
    private double[] bias2;

    private double[] input;
    private double[] hidden;
    private double[] output;
    private double[] desire;
    private double[][] weight_ih;
    private double[][] weight_ho;

    private double err = 0;
    private double errLimit = 0.00000001;


    public TanhBPNN(int iptNum, int hdnNum, int optNum) {
        this.inputNum = iptNum;
        this.hiddenNum = hdnNum;
        this.outputNum = optNum;
        initNet();
        randomWeights();
        randomBias();
        //System.out.println(bias1+"\n"+bias2);
    }

    public TanhBPNN(String fileName) throws IOException {
        readConf(fileName);
    }

    private void initNet() {
        input = new double[inputNum];
        hidden = new double[hiddenNum];
        output = new double[outputNum];
        desire = new double[outputNum];
        weight_ih = new double[inputNum][hiddenNum];
        weight_ho = new double[hiddenNum][outputNum];
        bias1 = new double[hiddenNum];
        bias2 = new double[outputNum];
        /**/
    }

    private void randomWeights() {
        //System.out.println(inputNum+","+hiddenNum+","+outputNum);
        for(int i = 0; i < inputNum; i++) {
            for(int j = 0; j< hiddenNum; j++) {
                weight_ih[i][j] = (Math.random()-0.5)*2;
                //System.out.println(weight_ih[i][j]);
            }
        }
        for(int i = 0; i < hiddenNum; i++) {
            for(int j = 0; j< outputNum; j++) {
                weight_ho[i][j] = (Math.random()-0.5)*2;
                //System.out.println(weight_ho[i][j]);
            }
        }
    }

    private void randomBias() {
        for(int i = 0;i<hiddenNum;i++) {
            bias1[i] = 2*(Math.random()-0.5) - 0.2;
        }
        for(int i = 0;i<outputNum;i++) {
            bias2[i] = 2*(Math.random()-0.5) * 0.2;
        }

    }

    private void calculate() {
        for(int i = 0; i<hiddenNum; i++) {
            double temp = 0;
            for(int j = 0;j<inputNum;j++) {
                temp += weight_ih[j][i]*input[j];
            }
            temp += bias1[i];
            hidden[i] = sigmoid(temp);
        }
        for(int i = 0; i<outputNum; i++) {
            double temp = 0;
            for(int j = 0;j<hiddenNum;j++) {
                temp += weight_ho[j][i]*hidden[j];
            }
            temp += bias2[i];
            //output[i] = temp;
            output[i] = sigmoid(temp);
        }

    }

    private double sigmoid(double val) {
        return ( Math.exp(val)-Math.exp(-val) )/( Math.exp(val)+Math.exp(-val) );
    }

    private void calErr() {
        err = 0;
        for(int i = 0;i<outputNum;i++) {
            err += (output[i]-desire[i])*(output[i]-desire[i]);
        }
        err /= outputNum;
    }

    private void adjustW_ih() {

        for(int i = 0; i < inputNum; i++) {

            for(int j = 0; j< hiddenNum; j++) {
                double sum = 0;
                for(int k = 0; k< outputNum;k++) {

                    sum += weight_ho[j][k]*(1-output[k]*output[k])*(desire[k]-output[k]);
                }
                weight_ih[i][j] += learnRate*(1-hidden[j]*hidden[j])*input[i]*sum;
            }
        }
    }

    private void adjustW_ho() {

        for(int i = 0; i < outputNum; i++) {
            for(int j = 0; j< hiddenNum; j++) {
                weight_ho[j][i] += learnRate*(desire[i]-output[i])*(1-output[i]*output[i])*hidden[j];
            }
        }
    }

    private void adjustBias1() {
        for(int j = 0; j< hiddenNum; j++) {
            double sum = 0;
            for(int k = 0; k< outputNum;k++) {
                sum += weight_ho[j][k]*(1-output[k]*output[k])*(desire[k]-output[k]);
            }
            bias1[j] += learnRate*(1-hidden[j]*hidden[j])*sum;
        }

    }

    private void adjustBias2() {
        for(int i = 0; i < outputNum; i++) {
            bias2[i] += learnRate*(desire[i]-output[i])*(1-output[i]*output[i]);
        }
    }

    public void train() throws Exception{
        calculate();
        calErr();
        if(err > errLimit) {
            adjustW_ih();
            adjustW_ho();
            adjustBias1();
            adjustBias2();
        }
    }

    public void loadTrainUnit(double[] input, double[] desire) {
        this.input = input;
        this.desire = desire;
    }

    public void loadInput(double[] input) {
        this.input = input;
    }

    public double[] getOpt() {
        calculate();
        return output;

    }


    public void adjustLR(double val) {
        this.learnRate = val;
    }

    public void readConf(String fileName) throws IOException {
        /*
        将训练结果写入文件，便于下次读入
         */
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String str = br.readLine();
        String[] strs = str.split(" ");
        inputNum = Integer.parseInt(strs[0]);
        hiddenNum = Integer.parseInt(strs[1]);
        outputNum = Integer.parseInt(strs[2]);

        initNet();

        br.readLine();




        for(int i = 0; i < inputNum; i++) {
            for(int j = 0; j< hiddenNum; j++) {
                weight_ih[i][j] = Double.parseDouble(br.readLine());
            }
        }
        br.readLine();
        for(int i = 0; i < hiddenNum; i++) {
            for(int j = 0; j< outputNum; j++) {
                weight_ho[i][j] = Double.parseDouble(br.readLine());
            }
        }
        br.readLine();
        for(int j = 0; j< hiddenNum; j++) {
            bias1[j] = Double.parseDouble(br.readLine());
        }
        br.readLine();
        for(int j = 0; j< outputNum; j++) {
            bias2[j] = Double.parseDouble(br.readLine());
        }
    }

    public void writeConf(String fileName) throws IOException {
        /*
        将训练结果写入文件，便于下次读入
         */
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.write(inputNum+" "+hiddenNum+" "+outputNum+"\r\n");
        bufferedWriter.write("weight i-h:\r\n");

        for(int i = 0; i < inputNum; i++) {
            for(int j = 0; j< hiddenNum; j++) {
                bufferedWriter.write(weight_ih[i][j]+"\r\n");
            }
        }
        bufferedWriter.write("weight h-o:\r\n");
        for(int i = 0; i < hiddenNum; i++) {
            for(int j = 0; j< outputNum; j++) {
                bufferedWriter.write(weight_ho[i][j]+"\r\n");
            }
        }
        bufferedWriter.write("bias1:\r\n");
        for(int j = 0; j< hiddenNum; j++) {
            bufferedWriter.write(bias1[j]+"\r\n");
        }
        bufferedWriter.write("bias2:\r\n");
        for(int j = 0; j< outputNum; j++) {
            bufferedWriter.write(bias2[j]+"\r\n");
        }

        bufferedWriter.close();
    }

}

