package com.manrique.belaxTest.service;

import java.util.List;
import java.util.Map;

public interface PatternFinderService {
	
	public List<String> readUrls();
	public Map<String, List<String>> outputListOfPatternMatches(List<String> urls, List<String> patterns);
	
}
 