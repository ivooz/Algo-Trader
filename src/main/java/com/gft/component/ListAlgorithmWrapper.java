package com.gft.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class ListAlgorithmWrapper {
	private HashMap<String, PredictionAlgorithm> algorithms;

	public ListAlgorithmWrapper() {
		this.algorithms = new HashMap<>();
		List<Integer> simpleMovingAverageIntervals = Arrays.asList(5, 10, 15,
				20, 25, 50, 100, 200);

		for (int interval : simpleMovingAverageIntervals) {
			SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
			simpleMovingAverage.setInterval(interval);
			simpleMovingAverage.setName("SimpleMovingAverage" + interval);
			this.algorithms.put(simpleMovingAverage.getName(),
					simpleMovingAverage);
		}

	}

	public HashMap<String, PredictionAlgorithm> getAlgorithms() {
		return algorithms;
	}

}
