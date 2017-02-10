package de.jhs.oldenburg.gg.rdf.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.jena.ext.com.google.common.annotations.Beta;

/**
 * 
 * 
 * 
 * @author Fred Bohlmann
 * @author Stefan Buescher
 * @author Philipp Grashorn
 *
 */
public class AccessOutPutConverter {

	public AccessOutPutConverter() {
		// TODO Auto-generated constructor stub
	}

	public void converToRDF(String path) {
		StringBuilder sb = new StringBuilder();
		int semiCounter = 0;

		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(new File(path)));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				int firstSemi = line.indexOf(';');
				String subject = line.substring(0, firstSemi);
				sb.append(subject);
				String pred = line;
				System.out.println(sb);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		AccessOutPutConverter a = new AccessOutPutConverter();
		a.converToRDF("test.txt");
	}
}
