package pl.minjas.chart;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;
import pl.minjas.SecantMethod;
import pl.minjas.common.Pair;

import javax.swing.*;
import java.awt.*;

public class XYChart extends JFrame {
	
	public XYChart(String frameName, String chartName, SecantMethod.SecantResult secantResult) {
		super(frameName);
		XYDataset dataset = createDataset(secantResult);
		JFreeChart chart = createChart(dataset, chartName);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}
	
	private XYDataset createDataset(SecantMethod.SecantResult data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries series1 = new XYSeries("Function");
		for (Pair<Double> pair : data.getFunctionPairs(20))
			series1.add(pair.getX(), pair.getY());
		dataset.addSeries(series1);
		
		for (Pair<Pair<Double>> secant : data.getSecants()) {
			XYSeries series = new XYSeries("Secant " + secant.getX().getX() + " - " + secant.getY().getX());
			series.add(secant.getX().getX(), secant.getX().getY());
			series.add(secant.getY().getX(), secant.getY().getY());
			dataset.addSeries(series);
		}
		
		return dataset;
	}
	
	private JFreeChart createChart(XYDataset dataset, String chartTitle) {
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
				xAxisLabel, yAxisLabel, dataset);
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.DARK_GRAY);
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(-20, 20);
		domain.setTickUnit(new NumberTickUnit(1));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) plot.getRangeAxis();
		range.setRange(-20, 20);
		range.setTickUnit(new NumberTickUnit(1));
		
		return chart;
	}
	
}
