package com.gft.component;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class ListAlgorithmWrapper {

	private HashMap<String, PredictionAlgorithm> algorithms;

	public ListAlgorithmWrapper() {
		this.algorithms = new HashMap<>();
		List<Integer> averagesInterval = Arrays.asList(5, 10, 15, 20, 25, 50, 100, 200, 1000);
		for (int interval : averagesInterval) {
			SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
			simpleMovingAverage.setInterval(interval);
			simpleMovingAverage.setName("SimpleMovingAverage" + interval);
			this.algorithms.put(simpleMovingAverage.getName(), simpleMovingAverage);
		}

		for (int i = 0; i < averagesInterval.size() - 1; i++) {
			for (int j = i+1; j < averagesInterval.size(); j++) {
				DoubleSimpleMovingAverage doubleSimpleMovingAverage = new DoubleSimpleMovingAverage(
						averagesInterval.get(i), averagesInterval.get(j));
				this.algorithms.put(doubleSimpleMovingAverage.getName(), doubleSimpleMovingAverage);
			}
		}

	}

	public Map<String, PredictionAlgorithm> getAlgorithms() {
		return algorithms;
	}
}
