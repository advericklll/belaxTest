package com.manrique.belaxTest.serviceImpl;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 
 * @author lucas
 *	This is not for unit testing but for test the functionality working on getting real matches to end files.
 */
@SpringBootTest
class PatternFinderServiceFileTest {
	
	@InjectMocks
	private PatternFinderServiceFile patternFinderTest;
	
	private String pathFile;
	private String pathOutputDirectory;
	
	@BeforeEach
    public void setUp() {		
		pathFile = "/home/lucas/Documents/file.txt";
		pathOutputDirectory = "/home/lucas/Documents/matches";
	}

	@Test
	void testSucessReadAllUrlTwitterMatches() {
		List<String> urls = patternFinderTest.readUrls(pathFile);
		
		urls.forEach(System.out::println);	
		
		patternFinderTest.outputListOfPatternMatches(urls, "http://twitter.com/", pathOutputDirectory);
	}
	
	@Test
	void testSucessReadProperNameMatches() {
		List<String> urls = patternFinderTest.readUrls(pathFile);
		
		urls.forEach(System.out::println);	
		
		patternFinderTest.outputListOfPatternMatches(urls, "Erick", pathOutputDirectory);
	}
	
	@Test
	void testSucessReadAllUrlHashTagMatches() {
		List<String> urls = patternFinderTest.readUrls(pathFile);
		
		urls.forEach(System.out::println);	
		
		patternFinderTest.outputListOfPatternMatches(urls, "#lucas", pathOutputDirectory);
	}

}
