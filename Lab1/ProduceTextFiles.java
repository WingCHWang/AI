import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;

public class ProduceTextFiles {
    public static void main(String[] args) throws Exception {
        ProduceTextFiles produceTextFiles = new ProduceTextFiles();
        String inputDirectoryPath = "dataset_image/train/A";
        produceTextFiles.writeGrey(inputDirectoryPath);
    }

    // 该方法读入一个文件夹，文件夹名为字母，对文件夹中的每个图片文件转成灰度值写入文本文件
    public void writeGrey(String directoryPath) throws Exception {
        File directory = new File(directoryPath);
        File[] imageFiles = directory.listFiles();
        for (File imageFile : imageFiles) {
			System.out.println(imageFile);
            writeGrey(imageFile, directoryPath);
        }
    }

    // 文件名后缀为.png，该方法读入图片文件，将灰度值写入文本文件
    public void writeGrey(File file, String directoryPath) throws Exception {
        String letter = directoryPath.charAt(directoryPath.length() - 1) + "";
        String filePath = file.getPath();
        File imageFile = new File(filePath);
        String outputFilePath = filePath.replace(".png", ".txt");
        File outputFile = new File(outputFilePath);
        FileOutputStream fos = new FileOutputStream(outputFile);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedImage bi = (BufferedImage) ImageIO.read(imageFile);
        int width = bi.getWidth();
        int height = bi.getHeight();
        for (int x = 0; x < width; x++) {
            String line = "";
            for (int y = 0; y < height; y++) {
                int pixel = bi.getRGB(x, y);
                int red = (pixel & 0xff0000) >> 16;
                int green = (pixel & 0xff00) >> 8;
                int blue = (pixel & 0xff);
                int grey = (red * 299 + green * 587 + blue * 114) / 1000;
                line += grey + " ";
            }
            line = line.trim();
            osw.write(line + "\r\n");
        }
        osw.write(letter);
        osw.close();
    }
}