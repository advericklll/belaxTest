package com.manrique.belaxTest.service;

import java.util.List;
import java.util.Map;

import com.manrique.belaxTest.model.PatternFinderObject;

public interface PatternFinderService {
	
	public List<String> readUrls(String pathFile);
	public List<PatternFinderObject> outputListOfPatternMatches(List<String> urls, String pattern, String pathOutput);
	
}
 