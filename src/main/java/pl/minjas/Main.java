package pl.minjas;

import pl.minjas.chart.AxisRange;
import pl.minjas.chart.XYChart;
import pl.minjas.function.Function;
import pl.minjas.function.Polynomial;

import java.util.*;

public class Main {
	
	public static Function createPolynomial(Scanner scanner) {
		List<Double> factors = new ArrayList<>();
		String userInput;
		while (true) {
			System.out.println();
			System.out.println("Insert number to fill polynomial factor (from right to left). Insert 'x' " +
					"to end insertion");
			userInput = scanner.next();
			if (userInput.equals("x")) {
				break;
			} else {
				factors.add(Double.valueOf(userInput));
				System.out.println("Function: ");
				for (int i = factors.size() - 1; i >= 0; i--) {
					if (factors.get(i) != 0.0)
						System.out.print(factors.get(i) + (i != 0 ? "x^" + i : "") + (i == 0 ? "" : " + "));
				}
			}
		}
		return new Polynomial(factors);
	}
	
	public static void main(String[] args) {
		List<String> options = Arrays.asList("1", "2", "3", "4");
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			String choice;
			do {
				System.out.println("Choose function or insert 'x' to quit:");
				System.out.println("1. Polynomial");
				System.out.println("2. ");
				System.out.println("3. ");
				System.out.println("4. ");
				
				choice = scanner.next();
				if (choice.equalsIgnoreCase("x"))
					return;
			} while (!options.contains(choice));
			
			Function function = null;
			switch (choice) {
				case "1":
					function = createPolynomial(scanner);
					break;
				case "2":
					function = null;
					break;
				case "3":
					function = null;
					break;
				case "4":
					function = null;
					break;
			}
			
			System.out.println("How many steps?");
			int steps = scanner.nextInt();
			SecantMethod secantMethod = new SecantMethod(function, steps);
			
			do {
				System.out.println("Insert x1");
				int x1 = scanner.nextInt();
				System.out.println("Insert x2");
				int x2 = scanner.nextInt();
				
				SecantMethod.SecantResult result = secantMethod.getResult(x1, x2);
				System.out.println("\nResult: " + result.getResult());
				
				System.out.println("Do you want to draw a chart? (y | n)");
				choice = scanner.next();
				
				if (choice.equalsIgnoreCase("y")) {
					AxisRange axisRange = new AxisRange();
					
					System.out.println("Insert beginning of x axis");
					axisRange.xStart = scanner.nextDouble();
					System.out.println("Insert end of x axis");
					axisRange.xEnd = scanner.nextDouble();
					
					System.out.println("Insert beginning of y axis");
					axisRange.yStart = scanner.nextDouble();
					System.out.println("Insert end of y axis");
					axisRange.yEnd = scanner.nextDouble();
					
					XYChart chart = new XYChart("ChartDrawer", "Chart", result, axisRange);
					chart.pack();
					chart.setVisible(true);
					chart.toFront();
				}
				
				System.out.println("Insert 'r' to change x1, x2, 'c' to choose other function, 'x' to exit.");
				choice = scanner.next();
				if (choice.equalsIgnoreCase("x"))
					return;
			} while (choice.equalsIgnoreCase("R"));
			
		}
	}
	
}
