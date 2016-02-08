package com.jovi.rronline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class Reviewer {

	
	public static DoccatModel model = null;
	HashMap<String, Integer> key_words = new HashMap<String, Integer>();
	
	
	public static Person getPersonInfo(String file_name){
		BufferedReader br = null;
		Person reviewer = new Person();
		
		try {
			
			File file = new File(file_name);
			if (!file.exists()) {
				file = new File("Wei_Zhu.txt");
			}
			
			br = new BufferedReader(new FileReader(file));
		    
		    
		    String line = br.readLine();
		    reviewer.name = line;
		    
		    line = br.readLine();
		    reviewer.position = line;
		    
		    line = br.readLine();
		    reviewer.university = line;
		    
		    line = br.readLine();
		    while ((line != null)) {
		    	
		    	reviewer.research_interest.add(line);
		    	line = br.readLine();
		    }
		    
		    br.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return reviewer;
	}
	
	
	public void parseAbstract(String training_file){
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(training_file));
		    
		    String line = br.readLine();
		    
		    while ((line != null)) {
		    	String words[] = line.toLowerCase().split("\\s");
		    	for(int i=1; i<words.length; i++){
		    		if(! key_words.containsKey(words[i])){
		    			key_words.put(words[i],1);
		    		}else{
		    			int count = key_words.get(words[i]);
		    			key_words.put(words[i],count+1);
		    		}
		    	}
		    	line = br.readLine();
		    }
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<Entry<String, Integer>> it_entry = key_words.entrySet().iterator();
		while(it_entry.hasNext()){
			Entry<String, Integer> entry = it_entry.next();
			if(entry.getValue() >50)
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}
	
	public static void getTerm(){
		
	}
	public static String docClassify(String text){
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
		double[] outcomes = myCategorizer.categorize(text);
		for(int i=0; i< outcomes.length; i++){
			
			System.out.println(outcomes[i]);
		}
		
		String category = myCategorizer.getBestCategory(outcomes);
		return category;
	}
	
	public static DoccatModel trainAPI(String traing_file){

		InputStream dataIn = null;
		try {
		  dataIn = new FileInputStream(traing_file);
		  ObjectStream<String> lineStream =
				new PlainTextByLineStream(dataIn, "UTF-8");
		  ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
		  model = DocumentCategorizerME.train("en", sampleStream);
		}
		catch (IOException e) {
		  // Failed to read or parse training data, training failed
		  e.printStackTrace();
		}
		finally {
		  if (dataIn != null) {
		    try {
		      dataIn.close();
		    }
		    catch (IOException e) {
		      // Not an issue, training already finished.
		      // The exception should be logged and investigated
		      // if part of a production system.
		      e.printStackTrace();
		    }
		  }
		}
		
		return model;
	}
	
	public static void main (String [] args){
		Reviewer rw = new Reviewer();
		//Person per = rw.getPersonInfo("Eric_Cambria.txt");
		rw.parseAbstract("category_training.txt");
	}
}
