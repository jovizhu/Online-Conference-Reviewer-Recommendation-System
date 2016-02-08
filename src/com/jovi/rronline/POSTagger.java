package com.jovi.rronline;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class POSTagger { 
	
	
	public static SentenceDetector sentenceDetector = null;
	public static Tokenizer tokenizer = null;
	public static POSTaggerME posTagger = null;
	
	
	public static String [] getSentence(String text){
		InputStream modelIn = null;
		SentenceModel model = null;
		
		try {
			modelIn = new FileInputStream("en-sent.bin");
		   model = new SentenceModel(modelIn);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
				
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
						
		String sentences[] = sentenceDetector.sentDetect(text);
		
		return sentences;
	}
	
	
	public static String[] getToken(String sentence){
		
		InputStream modelIn = null;
		TokenizerModel model = null;
		
		try {
			modelIn = new FileInputStream("en-token.bin");
		   model = new TokenizerModel(modelIn);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}

					
		tokenizer = new TokenizerME(model);
				 
					
		String tokens[] = tokenizer.tokenize(sentence);
		
		return tokens;
	}
	
	public static String[] getPOSTag(final String[] tokens) {
    if (posTagger == null) {
        // lazy initialize
        InputStream modelIn = null;
        try {
           
        	modelIn = new FileInputStream("en-pos-maxent.bin");
        	
           POSModel posModel = new POSModel(modelIn);
           modelIn.close();
           posTagger = new POSTaggerME(posModel);
           
        } catch (final IOException ioe) {
        	ioe.printStackTrace();
        } finally {
           if (modelIn != null) {
              try {
                 modelIn.close();
              } catch (final IOException e) {}
           }
        }
     }
     return posTagger.tag(tokens);
  }
	
	public static void main(String [] args){
		POSTagger postagger = new POSTagger();
		String text = "We consider the task of grouping doctors with respect to communication patterns exhibited in outpatient visits. We propose a novel approach toward this end in which we model speech act transitions in conversations via a log-linear model incorporating physician specific components. We train this model over transcripts of outpatient visits annotated with speech act codes and then cluster physicians in (a transformation of) this parameter space. We find significant correlations between the induced groupings and patient survey response data comprising ratings of physician communication. Furthermore, the novel sequential component model we leverage to induce this clustering allows us to explore differences across these groups. This work demonstrates how statistical AI might be used to better understand (and ultimately improve) physician communication.";
			String [] sentences = POSTagger.getSentence(text);
			for(int i=0; i<sentences.length; i++){
				String [] tokens = POSTagger.getToken(sentences[i]);
				String [] taggers = POSTagger.getPOSTag(tokens);
				for(int j=0; j< taggers.length; j++){
					System.out.println("Token "+tokens[j]+" tagger "+taggers[j]);
				}
			}
	}
	
}
