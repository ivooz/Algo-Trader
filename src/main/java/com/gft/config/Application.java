/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gft.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.gft.component.PredictionAlgorithm;
import com.gft.component.SimpleMovingAverage;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.gft")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public List<Integer> simpleMovingAverageIntervals() {
		return Arrays.asList(5,10,15,20,25,50,100,200);
	}
	
	@Bean(name="getSimpleMovingAverages")
	public List<SimpleMovingAverage> getSimpleMovingAverages(List<Integer> simpleMovingAverageIntervals){
		ArrayList<SimpleMovingAverage>  simpleMovingAverages = new ArrayList<SimpleMovingAverage>();
		
		for(int interval: simpleMovingAverageIntervals){
			SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
			simpleMovingAverage.setInterval(interval);
			simpleMovingAverage.setName("SimpleMovingAverage"+interval);
			simpleMovingAverages.add(simpleMovingAverage);
		}
		return simpleMovingAverages;
	}
	
	@Bean
	public SimpleMovingAverage simpleMovingAverage(){
		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
		simpleMovingAverage.setInterval(5);
		simpleMovingAverage.setName("SimpleMovingAverage");
		return simpleMovingAverage;	
	}
	
	
	
}
