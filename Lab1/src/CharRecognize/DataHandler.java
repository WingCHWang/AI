package CharRecognize;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by asus on 2016/10/24.
 */
public class DataHandler {
    public static HashMap<double[],double[]> lord_trainSet(String dir, int num) throws IOException {

        HashMap<double[],double[]> trainSet = new HashMap<>(num);
        File directory = new File(dir);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            File[] subFiles = files[i].listFiles();
            double[] desire = {-1,-1,-1,-1,-1,-1,-1,-1};
            desire[files[i].getName().charAt(0)-65] = 1;
            for(int j = 0; j< subFiles.length; j++) {
                double[] input = imgToVector(subFiles[j]);
                trainSet.put(input,desire);
            }
            //System.out.println(files[i]);


        }
        System.out.println("Finish lording Set("+dir+")!");
        return trainSet;
    }

    public static double[][] lord_testSet(String dir, int num) throws IOException {
        double[][] set = new double[num][];
        File directory = new File(dir);
        File[] files = directory.listFiles();
        Arrays.sort(files, (f1, f2)->{
            int num1 = Integer.parseInt(f1.getName().split("\\.")[0]);
            int num2 = Integer.parseInt(f2.getName().split("\\.")[0]);
            if(num1 < num2)
                return -1;
            else if(num1 == num2)
                return 0;
            else
                return 1;
        });
        for (int i = 0; i < num; i++) {
            //System.out.println(files[i]);
            double[] input = imgToVector(files[i]);
            set[i] = input;
        }
        System.out.println("Finish lording Set("+dir+")!");
        return set;
    }

    // 文件名后缀为.png，该方法读入图片文件，将灰度值写入文本文件
    private static double[] imgToVector(File file) throws IOException {
        double[] vector;

        String filePath = file.getPath();
        File imageFile = new File(filePath);
        BufferedImage bi =  ImageIO.read(imageFile);
        int width = bi.getWidth();
        int height = bi.getHeight();
        vector = new double[width*height];

        int index = 0;
        for (int x = 0; x < width; x++) {
            //String line = "";
            for (int y = 0; y < height; y++) {
                int pixel = bi.getRGB(x, y);
                int red = (pixel & 0xff0000) >> 16;
                int green = (pixel & 0xff00) >> 8;
                int blue = (pixel & 0xff);
                int grey = (red * 299 + green * 587 + blue * 114) / 1000;

                vector[index] = ((double)grey/255 - 0.5)*2;
                index++;
                //line += grey + " ";
            }
        }
        return vector;
    }
}
