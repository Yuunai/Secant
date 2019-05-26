package pl.minjas.chart;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.*;
import pl.minjas.SecantMethod;
import pl.minjas.common.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class XYChart extends JFrame {
	
	public XYChart(String frameName, String chartName, SecantMethod.SecantResult secantResult, AxisRange axisRange) {
		super(frameName);
		XYDataset dataset = createDataset(secantResult);
		JFreeChart chart = createChart(dataset, chartName, axisRange);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMouseZoomable(false);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setPopupMenu(null);
		chartPanel.setPreferredSize(new java.awt.Dimension(1200, 800));
		setContentPane(chartPanel);
	}
	
	private XYDataset createDataset(SecantMethod.SecantResult data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries series1 = new XYSeries("Function");
		for (Pair<Double> pair : data.getFunctionPairs(20))
			series1.add(pair.getX(), pair.getY());
		dataset.addSeries(series1);
		
		XYSeries result = new XYSeries("Result");
		result.add(data.getResult(), -0.5);
		result.add(data.getResult(), 0.5);
		dataset.addSeries(result);
		
		
		List<Pair<Pair<Double>>> secants = data.getSecants();
		for (int i = 0; i < secants.size(); i++) {
			Pair<Pair<Double>> secant = secants.get(i);
			XYSeries series =
					new XYSeries("Secant " + i, true, true);
			series.add(secant.getX().getX(), secant.getX().getY());
			series.add(secant.getY().getX(), secant.getY().getY());
			dataset.addSeries(series);
		}
		
		return dataset;
	}
	
	private JFreeChart createChart(XYDataset dataset, String chartTitle, AxisRange axisRange) {
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
				xAxisLabel, yAxisLabel, dataset);
		
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.GRAY);
		plot.setRangePannable(true);
		plot.setDomainPannable(true);

//		Main function should be tougher
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(new Range(axisRange.xStart, axisRange.xEnd), true, false);
		domain.setAutoRange(false);
//		domain.setTickUnit(TickUninew NumberTickUnit(1));
		NumberAxis range = (NumberAxis) plot.getRangeAxis();
		range.setRange(new Range(axisRange.yStart, axisRange.yEnd), true, false);
		range.setAutoRange(false);
//		range.setTickUnit(new NumberTickUnit(1));
		
		return chart;
	}
	
}
