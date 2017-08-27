import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.*;

/**
 * Created by asus on 2016/12/28.
 */
public class TSP extends Genetic {
    private double[] X;
    private double[] Y;

    public TSP(int geneLen, double[] X, double[] Y) {
        super(1000, geneLen, 0.95, 0.1, 500);
        this.X = X;
        this.Y = Y;
        initPopulation(geneLen, popSize);
    }

    public void initPopulation(int geneLen, int popSize) {
        ArrayList<Integer> list = new ArrayList<Integer>(geneLen);
        for (int j = 1; j <= geneLen; j++) {
            list.add(j);
        }
        for (int size = 0; size < popSize; size++) {
            Individual individual = new Individual(geneLen);
            Collections.shuffle(list);
            for (int j = 0; j < geneLen; j++) {
                individual.setGene(j, list.get(j).intValue());
            }

            fitness(individual);
            population.add(individual);
        }
    }


    @Override
    public void calProbability() {
        double total = 0;
        for(int i = 0; i<popSize; i++) {
            probability[i] = population.get(i).getFitness();
            total += probability[i];
        }
        probability[0] = (1 - probability[0]/total) / (popSize-1);
        for(int i = 1; i<popSize; i++)
            probability[i] = (1 - probability[i]/total) / (popSize-1) + probability[i-1];
    }

    @Override
    public boolean fitness(Individual individual) {
        double fitness = 0;
        int geneLen = individual.getGeneLen();
        double x1 = X[individual.getGene(0)-1];
        double y1 = Y[individual.getGene(0)-1];
        double x2;
        double y2;
        for(int i = 1; i<geneLen; i++) {
            x2 = X[individual.getGene(i)-1];
            y2 = Y[individual.getGene(i)-1];
            fitness += Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
            x1 = x2;
            y1 = y2;
        }
        x2 = X[individual.getGene(0)-1];
        y2 = Y[individual.getGene(0)-1];
        fitness += Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        individual.setFitness(-fitness);
        return true;
    }

    @Override
    //顺序交叉
/*
    public void crossover(Individual indiv1, Individual indiv2) {
        if(Math.random() > crossRate)
            return;
        int point1 = (int)(Math.random()*geneLen);
        int point2 = (int)(Math.random()*geneLen);
        if(point1 == point2)
            return;
        int left = point1<point2 ? point1:point2;
        int right = point1>point2 ? point1:point2;

        int[] slice1 = new int[right-left+1];
        int[] slice2 = new int[right-left+1];
        for(int i = left; i<=right; i++) {
            slice1[i-left] = indiv1.getGene(i);
            slice2[i-left] = indiv2.getGene(i);
        }

        int[] temp1 = new int[geneLen];
        int[] temp2 = new int[geneLen];
        for(int i = right+1; i<geneLen; i++) {
            temp1[i-right-1] = indiv1.getGene(i);
            temp2[i-right-1] = indiv2.getGene(i);
        }
        for(int i = 0; i<= right; i++) {
            temp1[i+geneLen-right-1] = indiv1.getGene(i);
            temp2[i+geneLen-right-1] = indiv2.getGene(i);
        }

        int index = 0;
        for(int i = 0; i<left; ) {
            if(contains(temp1[index], slice1))
                index++;
            else {
                indiv1.setGene(i, temp1[index]);
                index++;
                i++;
            }
        }
        for(int i = right+1; i<geneLen; ) {
            if(contains(temp1[index], slice1))
                index++;
            else {
                indiv1.setGene(i, temp1[index]);
                index++;
                i++;
            }
        }

        index = 0;
        for(int i = 0; i<left; ) {
            if(contains(temp2[index], slice2))
                index++;
            else {
                indiv2.setGene(i, temp2[index]);
                index++;
                i++;
            }
        }
        for(int i = right+1; i<geneLen; ) {
            if(contains(temp2[index], slice2))
                index++;
            else {
                indiv2.setGene(i, temp2[index]);
                index++;
                i++;
            }
        }
    }
*/

    // 启发式交叉，两个个体交叉过后会得到两个更加优秀的个体

    public void crossover(Individual indiv1, Individual indiv2) {
        if(Math.random() > crossRate)
            return;
        int begin = (int)(Math.random()*2);
        int gene;
        if(begin == 0) {
            gene = indiv1.getGene(0);
            int index = indexOfGene(gene, indiv2);
            if(index != 0) {
                rotate(indiv2, 0, index);
            }
        }
        else {
            gene = indiv2.getGene(0);
            int index = indexOfGene(gene, indiv1);
            if(index != 0) {
                rotate(indiv1, 0, index);
            }
        }
        int geneLen = indiv1.getGeneLen();
        for(int i = 1; i<geneLen; i++) {
            if(distance(indiv1, i-1, i) <= distance(indiv2, i-1, i)) {
                gene = indiv1.getGene(i);
                int index = indexOfGene(gene, indiv2);
                if(index != i) {
                    rotate(indiv2, i, index);
                }
            }
            else {
                gene = indiv2.getGene(i);
                int index = indexOfGene(gene, indiv1);
                if(index != i) {
                    rotate(indiv1, i, index);
                }
            }
            //individual.setGene(i, gene);
        }

        rotate(indiv2, 0, (int)(Math.random()*(geneLen-1))+1);
    }


    private boolean contains(int integer, int[] arr) {
        for(int i = 0; i<arr.length; i++) {
            if(arr[i] == integer)
                return true;
        }
        return false;
    }

    // 得到某个基因在个体中的位置
    private int indexOfGene(int gene, Individual individual) {
        int index;
        for(index = 0; index<geneLen; index++) {
            if(individual.getGene(index) == gene)
                break;
        }
        return index;
    }

    // 将individual中的基因序列从位点index2开始，之后部分移到位点index1处，并将被替换部分放到末端。
    private void rotate(Individual individual, int index1, int index2) {
        int[] slice = new int[index2-index1];
        for(int i = 0; i<slice.length; i++) {
            slice[i] = individual.getGene(index1+i);
        }
        for(int i = index2; i<geneLen; i++) {
            individual.setGene(i-index2+index1, individual.getGene(i));
        }
        for(int i = geneLen-slice.length; i<geneLen; i++) {
            individual.setGene(i, slice[i+slice.length-geneLen]);
        }
    }

    public double distance(Individual individual, int i, int j) {
        double x1 = X[individual.getGene(i)-1];
        double y1 = Y[individual.getGene(i)-1];
        double x2 = X[individual.getGene(j)-1];
        double y2 = Y[individual.getGene(j)-1];

        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }

    @Override
    public void mutate(Individual individual) {
        if(Math.random() > mutationRate)
            return;
        int point1 = (int)(Math.random()*geneLen);
        int point2 = (int)(Math.random()*geneLen);
        if(point1 == point2)
            return;
        int left = point1<point2 ? point1:point2;
        int right = point1>point2 ? point1:point2;
        for(int i = left; i<=(left+right)/2; i++) {
            int temp = individual.getGene(i);
            individual.setGene(i, individual.getGene(right-i+left));
            individual.setGene(right-i+left, temp);

        }


    }

    public void drawData() {

        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        // 各曲线名称
        String series1 = "best";
        String series2 = "average";
        // 横轴名称(列名称)
        for(int i = 0; i < maxGeneration; i++) {
            linedataset.addValue(-bestFitness.get(i), series1, i+"");
            linedataset.addValue(-averageFitness.get(i), series2, i+"");
        }

        JFreeChart chart = ChartFactory.createLineChart("fitness - generation", //折线图名称
                "generation", // 横坐标名称
                "fitness", // 纵坐标名称
                linedataset, // 数据
                PlotOrientation.VERTICAL, // 水平显示图像
                true, // include legend
                true, // tooltips
                false // urls
        );
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true); //是否显示格子线
        plot.setBackgroundAlpha(0.3f); //设置背景透明度
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setUpperMargin(0.1);
        rangeAxis.setLabelAngle(Math.PI );

        ApplicationFrame frame = new ApplicationFrame("line");
        frame.setSize(1600,900);
        frame.setContentPane(new ChartPanel(chart));
        //frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);

    }
}
