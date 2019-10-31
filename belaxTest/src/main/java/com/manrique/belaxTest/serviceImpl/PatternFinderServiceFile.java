package com.manrique.belaxTest.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manrique.belaxTest.model.PatternFinderObject;
import com.manrique.belaxTest.service.PatternFinderService;
import com.manrique.belaxTest.thread.PatternFinderCallable;

@Service
public class PatternFinderServiceFile implements PatternFinderService{
	
	private Logger log = LoggerFactory.getLogger(PatternFinderServiceFile.class);

	@Override
	public List<String> readUrls(String pathFile) {
		
		List<String> urls = new ArrayList<String>();

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(pathFile))) {

			stream.forEach(l -> urls.add(l));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return urls;
	}

	@Override
	public List<PatternFinderObject> outputListOfPatternMatches(List<String> urls, String pattern, String pathOutput) {
		
		log.info("Begin method : outputListOfPatternMatches");
		
		ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(10));
		
		List<Future<PatternFinderObject>> list = new ArrayList<Future<PatternFinderObject>>();
		
		List<PatternFinderObject> patternFinderObjectList = new ArrayList<PatternFinderObject>();

		for (String url : urls) {
			PatternFinderCallable worker = new PatternFinderCallable(url, pattern, pathOutput);
			
			Future<PatternFinderObject> submit = executor.submit(worker);
			
			list.add(submit);
		}
		
		executor.shutdown();
		
		while (!executor.isTerminated()) {
			try {
				executor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (Future<PatternFinderObject> future : list) {
			try {
				
				patternFinderObjectList.add(future.get());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		log.info("Ending method : outputListOfPatternMatches");
		
		return patternFinderObjectList;
	}

}
