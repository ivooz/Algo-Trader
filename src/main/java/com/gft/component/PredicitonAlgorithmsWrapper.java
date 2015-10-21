package com.gft.component;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class PredicitonAlgorithmsWrapper {

	private HashMap<String, PredictionAlgorithm> algorithms;

	public PredicitonAlgorithmsWrapper() {
		this.algorithms = new HashMap<>();
		List<Integer> averagesInterval = Arrays.asList(5, 10, 15, 20, 25, 50, 100, 200, 1000);
		Average simpleAverage = (Average) new SimpleAverage();
		Average exponentialAverage = (Average) new ExponentialAverage();
		for (int interval : averagesInterval) {
			MovingAverage simpleMovingAverage = new MovingAverage(interval, simpleAverage);
			MovingAverage exponentialMovingAverage = new MovingAverage(interval, exponentialAverage);
			this.algorithms.put(simpleMovingAverage.getName(), simpleMovingAverage);
			this.algorithms.put(exponentialMovingAverage.getName(), exponentialMovingAverage);
		}

		for (int i = 0; i < averagesInterval.size() - 1; i++) {
			for (int j = i + 1; j < averagesInterval.size(); j++) {
				DoubleMovingAverage doubleSimpleMovingAverage = new DoubleMovingAverage(averagesInterval.get(i),
						averagesInterval.get(j), simpleAverage);
				DoubleMovingAverage doubleExponentialMovingAverage = new DoubleMovingAverage(averagesInterval.get(i),
						averagesInterval.get(j), exponentialAverage);
				this.algorithms.put(doubleSimpleMovingAverage.getName(), doubleSimpleMovingAverage);
				this.algorithms.put(doubleExponentialMovingAverage.getName(), doubleExponentialMovingAverage);
			}
		}

	}

	public Map<String, PredictionAlgorithm> getAlgorithms() {
		return algorithms;
	}
}
