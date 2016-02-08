package com.jovi.rronline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class AbstractSimularity {
	
	public static String getMaxCosSimularity(String file_name, String text){
		
		System.out.println("file "+file_name);
		File file = new File(file_name);
		
		if (!file.exists()) {
			return null;
		}
		
		BufferedReader br = null;		
		float max_simularity = 0;
		String author_with_max_simularity = null;
		
		String line;
		try {
			
			br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			while( line !=null){
				String name = line.split("\\s")[0];
				float cos_simularity = cosSimularity( text,  line);
				if(cos_simularity > max_simularity ){
					max_simularity= cos_simularity;
					author_with_max_simularity=name;
					System.out.println(" cosine simularity id updated to "+ max_simularity +" with name " + author_with_max_simularity);
				}
				
				line = br.readLine();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return author_with_max_simularity;
		
	}
	
	public static float cosSimularity(String text1, String text2){
		HashMap<String, Integer> count_text1= new HashMap<String, Integer>();
		HashMap<String, Integer> count_text2 = new HashMap<String, Integer>();
		HashSet<String> tokens = new HashSet<String>();
		
		String [] tokens_in_text1 = text1.split("\\s");
		String [] tokens_in_text2 = text2.split("\\s");
		
		//System.out.println(text2);
		
		for(int i=0 ;i<tokens_in_text1.length; i++){
			String token = tokens_in_text1[i];
			tokens.add(token);
			if(count_text1.containsKey(token)){
				int count = count_text1.get(token);
				count_text1.put(token, count+1);
			}else{
				count_text1.put(token, 1);
			}
			
		}
		
		for(int i=0 ;i<tokens_in_text2.length; i++){
			String token = tokens_in_text2[i];
			tokens.add(token);
			if(count_text2.containsKey(token)){
				int count = count_text2.get(token);
				count_text2.put(token, count+1);
			}else{
				count_text2.put(token, 1);
			}
			
		}
		
		// cal cos simularity
		float weight_A = 0;
		float weight_B = 0;
		float AB = 0;
		int a =0;
		int b =0;
		
		Iterator<String> it_token = tokens.iterator();
		while(it_token.hasNext()){
			String token = it_token.next();

			if(count_text1.containsKey(token)){
				a = count_text1.get(token);
			}else{
				a=0;
			}
			
			if(count_text2.containsKey(token)){
				b = count_text2.get(token);
			}else{
				b=0;
			}
			
			System.out.println(token+" "+a+" "+b);
			
			AB += a*b;
			weight_A += a*a;
			weight_B += b*b;
		}
		
		float cos_simularity = AB/(weight_A+weight_B);
		System.out.println(cos_simularity);
		return cos_simularity;
	}
	
	public static void main(String [] args){
		AbstractSimularity asim  = new AbstractSimularity();
		String author_max_sim = AbstractSimularity.getMaxCosSimularity("AIW", "Celso_de_Melo Agency capacity ‰ÛÒ experience capacity ‰ÛÒ critical aspects people non-human entities such autonomous agents mind evidence absence cooperation experiment necessity cooperation agents experiment people‰Ûªs perceptions cognitive affective abilities agents ultimatum game results people money agents decisions intentions results people money agents emotion agents implications agency-experience theoretical framework design intelligent decision makers");
		System.out.println("final name " + author_max_sim);
	}
	
}
