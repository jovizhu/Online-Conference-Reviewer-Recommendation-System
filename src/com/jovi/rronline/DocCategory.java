package com.jovi.rronline;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


public class DocCategory {
	
	
	public static DoccatModel model = null;
	
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
	
	public static String docClassify(String text){
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
		double[] outcomes = myCategorizer.categorize(text);
		for(int i=0; i< outcomes.length; i++){
			
			System.out.println(outcomes[i]);
		}
		
		String category = myCategorizer.getBestCategory(outcomes);
		return category;
	}
	
	public static void main(String [] args){
		DocCategory doc_category = new DocCategory();
		DocCategory.trainAPI("category_training.txt");
		String reviewer = DocCategory.docClassify("In web search, users queries are formulated using only few terms and term-matching retrieval functions could fail at retrieving relevant documents. Given a user query, the technique of query expansion (QE) consists in selecting related terms that could enhance the likelihood of retrieving relevant documents. Selecting such expansion terms is challenging and requires a computational framework capable of encoding complex semantic relationships. In this paper, we propose a novel method for learning, in a supervised way, semantic representations for words and phrases. By embedding queries and documents in special matrices, our model disposes of an increased representational power with respect to existing approaches adopting a vector representation. We show that our model produces high-quality query expansion terms. Our expansion increase IR mesures beyond expansion from current word-embeddings models and well-established traditional QE methods.");
	
		/*Crawler obj = new Crawler();
		Set<String> result = obj.getDataFromGoogle(reviewer);
		for(String temp : result){
			System.out.println(temp);
		}
		System.out.println(result.size());
		*/
	}
}
