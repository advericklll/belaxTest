package com.manrique.belaxTest.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PatternFinderObject {
	
	private URL website;
	private List<String> matches;
	
	private final char [] endChars =new char[] {'\'', '\"',';', '<','>',' ','.'};
	
	public URL getWebsite() {
		return website;
	}
	public void setWebsite(URL website) {
		this.website = website;
	}
	public List<String> getMatches() {
		return matches;
	}
	public void setMatches(List<String> matches) {
		this.matches = matches;
	}
	
	public List<String> addLineMatches(String line, String pattern) {
		
		if (matches== null) {
			matches = new ArrayList<>();
		}	
		
		int index = 0;
		while (index != -1) {
			index = line.indexOf(pattern, index);

			if (index != -1) {
				matches.add(line.substring(index,
						indexOfAny(line, endChars, (index+pattern.length()-1))));

				index++;
			}
		}
		
		return matches;
	}
	
	public int indexOfAny(String str, char[] searchChars, int fromindex) {
	      
	      for (int i = fromindex; i < str.length(); i++) {
	          char ch = str.charAt(i);
	          for (int j = 0; j < searchChars.length; j++) {
	              if (searchChars[j] == ch) {
	                  return i;
	              }
	          }
	      }
	      return -1;
	  }
}
