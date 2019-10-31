package com.manrique.belaxTest.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manrique.belaxTest.model.PatternFinderObject;

public class PatternFinderCallable implements Callable<PatternFinderObject> {

	private Logger log = LoggerFactory.getLogger(PatternFinderCallable.class);

	private String urlPage;
	private String pattern;
	private String path;

	@Override
	public PatternFinderObject call() throws Exception {

		PatternFinderObject patternFinderObject = this.findMatchesOnUrl(urlPage, pattern, path);

		return patternFinderObject;
	}

	public PatternFinderCallable(String urlPage, String pattern, String path) {
		super();
		this.urlPage = urlPage;
		this.pattern = pattern;
		this.path = path;
	}

	private PatternFinderObject findMatchesOnUrl(String urlPage2, String pattern2, String Path) throws IOException {

		PatternFinderObject patternFinderObject = this.getWebsitePatternMatches(urlPage2, pattern2);
		
		this.writeMatchesOnOutputFile(patternFinderObject);

		return patternFinderObject;
	}

	private void writeMatchesOnOutputFile(PatternFinderObject patternFinderObject) throws IOException {
		File fout = new File(path + "/" + patternFinderObject.getWebsite().getHost());
		FileOutputStream fos;

		fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		if (patternFinderObject.getMatches()!=null && patternFinderObject.getMatches().size() > 0) {
			patternFinderObject.getMatches().stream().forEach(m -> {
				try {
					bw.write(m);
					bw.newLine();
				} catch (IOException e) {
					log.error("Error while trying to write matches on output file :" + fout.getAbsolutePath());
				}

			});
		} else {
			bw.write("Pattern Not Found on Site.");
			bw.newLine();
		}
		bw.close();

	}

	private PatternFinderObject getWebsitePatternMatches(String website, String pattern2) {

		InputStream is = null;
		BufferedReader br;
		String line;

		URL url = null;
		
		PatternFinderObject patternFinderObject = new PatternFinderObject();

		try {
			url = new URL(website);

			patternFinderObject.setWebsite(url);

			is = url.openStream(); // throws an IOException
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {

				patternFinderObject.addLineMatches(line, pattern2);
			}
		} catch (MalformedURLException mue) {
			log.error("Error while trying to get URl (Malformed)" + website);
		} catch (IOException ioe) {
			log.error("Error while trying to get URl IO :"+ website);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				log.error("Error while trying to close connection from: "+ website);
			}
		}
		return patternFinderObject;
	}

}
