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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gft.component.PredictionAlgorithm;
import com.gft.component.MovingAverage;

@Configuration
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.gft")
@SpringBootApplication
@EnableJpaRepositories("com.gft")
@EnableTransactionManagement
@EntityScan("com.gft")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name="simpleMovingAverages")
	public List<PredictionAlgorithm> getSimpleMovingAverages(){
		List<Integer> simpleMovingAverageIntervals = Arrays.asList(5, 10, 15, 20, 25, 50, 100, 200);
		List<PredictionAlgorithm>  simpleMovingAverages = new ArrayList<PredictionAlgorithm>();
		for(int interval: simpleMovingAverageIntervals){
			MovingAverage simpleMovingAverage = new MovingAverage();
			simpleMovingAverage.setInterval(interval);
			simpleMovingAverage.setName("SimpleMovingAverage"+interval);
			simpleMovingAverages.add(simpleMovingAverage);
		}
		return simpleMovingAverages;
	}
	
	@Bean
	public MovingAverage simpleMovingAverage(){
		MovingAverage simpleMovingAverage = new MovingAverage();
		simpleMovingAverage.setInterval(5);
		simpleMovingAverage.setName("SimpleMovingAverage");
		return simpleMovingAverage;	
	}
}
