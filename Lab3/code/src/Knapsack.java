import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


/**
 * Created by asus on 2016/12/28.
 */
public class Knapsack extends Genetic {
    private double[] weight;
    private double[] value;
    private double capacity;

    public Knapsack(int geneLen, double[] weight, double[] value, double capacity) {
        super(500, geneLen, 0.5, 0.025, 500);
        this.value = value;
        this.weight = weight;
        this.capacity = capacity;
        initPopulation(geneLen, popSize);
    }

    public void initPopulation(int geneLen, int popSize) {
        for(int size = 0; size<popSize; ) {
            Individual individual = new Individual(geneLen);
            for(int j = 0; j<geneLen; j++) {
                individual.setGene(j, (int)(Math.random()*2));
            }
            while(!fitness(individual)) {
                int point = (int)(Math.random()*geneLen);
                individual.setGene(point, 0);
            }
            population.add(individual);
            size++;
        }
    }

    @Override
    public void calProbability() {
        double total = 0;
        for(int i = 0; i<popSize; i++) {
            probability[i] = population.get(i).getFitness();
            total += probability[i];
        }
        probability[0] /= total;
        for(int i = 1; i<popSize; i++)
            probability[i] = probability[i]/total + probability[i-1];

    }


    @Override
    public boolean fitness(Individual individual) {
        double totalValue = 0;
        double totalWeight = 0;
        int geneLen = individual.getGeneLen();
        for(int i = 0; i < geneLen; i++) {
            totalValue += value[i] * individual.getGene(i);
            totalWeight += weight[i] * individual.getGene(i);
        }
        individual.setFitness(Math.round(totalValue * 100) * 0.01d);
        if(Math.round(totalWeight * 100) * 0.01d > capacity)
            return false;
        else
            return true;
    }

    @Override
    // 每隔位点交叉
    /*
    public void crossover(Individual indiv1, Individual indiv2) {
        int geneLen = indiv1.getGeneLen();
        for(int i = 0; i<geneLen; i++) {
            if(Math.random() <= crossRate) {
                int temp = indiv2.getGene(i);
                indiv2.setGene(i, indiv1.getGene(i));
                indiv1.setGene(i, temp);
            }
        }
    }
    */

    // 两点交叉

    public void crossover(Individual indiv1, Individual indiv2) {
        if(Math.random() > crossRate) {
            return;
        }
        int geneLen = indiv1.getGeneLen();
        int point1 = (int)(Math.random()*geneLen);
        int point2 = (int)(Math.random()*geneLen);
        if(point1 == point2)
            return;
        int left = point1<point2 ? point1:point2;
        int right = point1>point2 ? point1:point2;

        for(int i = left; i<=right; i++) {
            int temp = indiv2.getGene(i);
            indiv2.setGene(i, indiv1.getGene(i));
            indiv1.setGene(i, temp);
        }
    }


    @Override
    public void mutate(Individual individual) {
        int geneLen = individual.getGeneLen();
        for(int i = 0; i<geneLen; i++) {
            if(Math.random() <= mutationRate)
                individual.setGene(i, 1-individual.getGene(i));

        }
    }

    public void drawData() {

        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        // 各曲线名称
        String series1 = "best";
        String series2 = "average";
        // 横轴名称(列名称)
        for(int i = 0; i < maxGeneration; i++) {
            linedataset.addValue(bestFitness.get(i), series1, i+"");
            linedataset.addValue(averageFitness.get(i), series2, i+"");
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
