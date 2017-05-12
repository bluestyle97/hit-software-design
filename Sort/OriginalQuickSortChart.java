package sort;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.*;
import java.util.Scanner;

public class OriginalQuickSortChart extends Application {
    public void start(Stage stage) {
        Pane pane = new StackPane();
        SwingNode chartNode = new SwingNode();
        chartNode.setContent(new ChartPanel(createChart(createDataSet())));
        pane.getChildren().add(chartNode);

        Scene scene = new Scene(pane);
        stage.setTitle("原始快速排序性能曲线");
        stage.setScene(scene);
        stage.show();
    }

    private static JFreeChart createChart(DefaultCategoryDataset lineDataSet){
        JFreeChart chart = ChartFactory.createLineChart(
                "Original QuickSort Performance Chart",
                "Data Scale",
                "Time/ns",
                lineDataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);
        plot.setBackgroundAlpha(0.3f);
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
        return chart;
    }

    private static DefaultCategoryDataset createDataSet() {
        DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();
        String series1 = "Random Data Set";
        String series2 = "Ascending Data Set";
        String series3 = "Descending Data Set";
        String series4 = "Static Data Set";

        try {
            Scanner in = new Scanner(new FileInputStream("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceRandom.txt"));
            while (in.hasNextLong()) {
                Long count = in.nextLong();
                Long time = in.nextLong();
                lineDataSet.addValue(time, series1, count);
            }
            in = new Scanner(new FileInputStream("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceAscending.txt"));
            while (in.hasNextLong()) {
                Long count = in.nextLong();
                Long time = in.nextLong();
                lineDataSet.addValue(time, series2, count);
            }
            in = new Scanner(new FileInputStream("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceDescending.txt"));
            while (in.hasNextLong()) {
                Long count = in.nextLong();
                Long time = in.nextLong();
                lineDataSet.addValue(time, series3, count);
            }
            in = new Scanner(new FileInputStream("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceSame.txt"));
            while (in.hasNextLong()) {
                Long count = in.nextLong();
                Long time = in.nextLong();
                lineDataSet.addValue(time, series4, count);
            }
            in.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return lineDataSet;
    }
}
