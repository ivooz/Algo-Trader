package com.gft.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class ListAlgorithmWrapper {
	public HashMap<String,SimpleMovingAverage> getSimpleMovingAverages(){
		List<Integer> simpleMovingAverageIntervals = Arrays.asList(5, 10, 15, 20, 25, 50, 100, 200);
		HashMap<String,SimpleMovingAverage>  simpleMovingAverages = new HashMap<>();
		for(int interval: simpleMovingAverageIntervals){
			SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
			simpleMovingAverage.setInterval(interval);
			simpleMovingAverage.setName("SimpleMovingAverage"+interval);
			simpleMovingAverages.put(simpleMovingAverage.getName(),simpleMovingAverage);
		}
		return simpleMovingAverages;
	}
}
