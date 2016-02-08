package com.jovi.rronline;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class AbstractParser {

    public AbstractParser() {
		super();
		// TODO Auto-generated constructor stub
		//paperList = new ArrayList<Paper>();
		
	}
    
    public static String [] authorAffir ={"Unverisity of Texas", "Unverisity of Dallas","Unverisity of Plano", "Unverisity of Richardson", "Unverisity of Allen"}; 
    public static String [] positions ={"Phd Students", "Associate Professor","Professor", "Director", "Research Scientist"}; 
    public static String [] researches ={"AI", "Nteworking","Natural Language Processing", "Machine Learning", "Gaming"}; 
    
    
    public static HashSet<String> categoryList = new HashSet<String>();
    public static HashSet<Person> authorList = new HashSet<Person>();
	public static ArrayList<Paper> paperList = new ArrayList<Paper>();
	
	public ArrayList<Paper> parserAbstract(String file){

		ArrayList<Paper> paperList = new ArrayList<Paper>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		    
		    String line = br.readLine();
            int count = 0;
            int count_10k = 0;
            Paper paper = new Paper();
		    while ((line != null)) {
		        if(count%10000 == 0){
		        	
		        	count_10k++;
		        	System.out.print(count_10k+" 10K\n");
		        	count =0;
		        	
		        }
		        
		        if(line.startsWith("#*")){
		        	//System.out.println("Title: "+line.substring(2));
		        	paper.setTitle(line.substring(2));
		        }else if(line.startsWith("#@")){
		        	//System.out.println("Author: "+line.substring(2));
		        	String [] aut = line.substring(2).split(",");
		        	for(int i=0; i<aut.length; i++){
		        		paper.addAuthor(aut[i]);
		        	}
		        }else if(line.startsWith("#t")){
		        	//System.out.println("Year: "+line.substring(2));
		        	paper.setYear(line.substring(2));
		        }else if(line.startsWith("#c")){
		        	//System.out.println("Book/Conference/Journal: "+line.substring(2));
		        	paper.setPublished(line.substring(2));
		        }else if (line.startsWith("#!")){
		        	if(line.startsWith("#!:")){
		        		paper.setAbsContent(line.substring(3));
		        	}else{
		        		paper.setAbsContent(line.substring(2));
		        	}
		        }else if(line.startsWith("#")){
		        	//System.out.println("Others: "+line.substring(1));
		        	paper.addOthers(line.substring(1));
		        }else if(line.length() == 0){
		        	//System.out.println("\n\n");
		        	if(paper.getAbsContent().length() > 100){
		        		paperList.add(paper);
		        	}
		        	paper = new Paper();
		        }
		        line = br.readLine();
		        count++;
		    }
		    
		    System.out.println(paperList.size()/10000);
		    br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return paperList;
	
	}
	
	public void createCategroyTraingFile( String category_file) throws IOException{
		
		File file = new File(category_file);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		Iterator<Paper> it_paper = paperList.iterator();
		
		while (it_paper.hasNext()) {
			Paper paper = (Paper) it_paper.next();
			
			for(int i=0; i<paper.groups.size();i++){
				bw.write(paper.groups.get(i)+" "+paper.absContent+"\n");
				System.out.println(paper.groups.get(i)+" "+paper.absContent);
			}
		}
		
		bw.close();
	}
	
	public ArrayList<Paper> parserAAAI14Abstract() throws IOException{// open file input stream
        
		Random rand = new Random();
		
		ArrayList<Paper> paper_list = new ArrayList<Paper>();
		//..
		FileInputStream file = new FileInputStream(new File("AAAI-14.xls"));
		             
		//Get the workbook instance for XLS file 
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		//Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheetAt(0);
		 
		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			Paper paper = new Paper();
			Iterator<Cell> cellIterator = row.cellIterator();
			
			paper.setTitle(row.getCell(0).toString());
			paper.setAbsContent(row.getCell(4).toString().replace("\n", ""));
			ArrayList<String> author_area = new ArrayList<String>();
			
			if(row.getCell(2) != null){
				String [] groups = row.getCell(2).toString().split("\\n");
				
				for(int i=0; i<groups.length; i++){
					//System.out.println("i="+i+" "+groups[i]);
					author_area.add(groups[i]);
					String group = groups[i].split("\\(|\\)")[1];
					paper.groups.add(group);
					categoryList.add(group);
				}
			}else{
				continue;
			}
			
			//System.out.println(row.getCell(0).toString());
			
			//System.out.println(row.getCell(4).toString());
			//System.out.println(row.getCell(1).toString());
			
			String [] authors = row.getCell(1).toString().split(",|and");
			for(int i=0; i< authors.length; i++){
				paper.authors.add(authors[i].trim());
				String author_name = authors[i].trim().replace(" ", "_");
				Person author = new Person();
				author.name = author_name;
				author.research_interest = author_area;
				
				int ind = rand.nextInt(100)%5;
				author.university = authorAffir[ind];
				ind = rand.nextInt(100)%5;
				author.position = positions[ind];
				authorList.add(author);
				
				System.out.println(authors[i].trim());
			}
			paper_list.add(paper);
			
			
		}
		//Get iterator to all cells of current row
		
		
        return paper_list;
	}
	
	public void createAuthorFile(){

		Iterator<Person> it_author = authorList.iterator();
		Random rand = new Random();

		try {
			while (it_author.hasNext()) {
				Person author = it_author.next();
				File file = new File(".//AUTHOR//"+author.name+".txt");

				if (!file.exists()) {
					file.createNewFile();
				}
				
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(author.name+"\n");
				int ind = rand.nextInt(100)%5;
				bw.write(author.position+"\n");
				ind = rand.nextInt(100)%5;
				bw.write(author.university+"\n");
				
				bw.write("jovi.zhu@hotmail.com"+"\n");
				
				Iterator<String> it_research_interest = author.research_interest.iterator();
				while(it_research_interest.hasNext()){
					String one_interest = it_research_interest.next();
					bw.write(one_interest+"\n");
				}

				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void abstractFilter( String reducedFile) throws IOException{
		ArrayList<Paper> new_paper_list = new ArrayList<Paper>();
		
		
		File file = new File(reducedFile);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		Iterator<Paper> it_paper = paperList.iterator();
		
		while (it_paper.hasNext()) {
			Paper paper = (Paper) it_paper.next();
			String abs_contenct = paper.getAbsContent();
			if(abs_contenct.length() > 400){
				bw.write(paper.toString());
				new_paper_list.add(paper);
			}
		}
		
		bw.close();
		paperList = new_paper_list;
	}
	
	public void createAuthorTrainingFile() {

		Iterator<String> it_group = categoryList.iterator();

		try {
			while (it_group.hasNext()) {
				String category = it_group.next();
				File file = new File(".//CAT//"+category+".txt");

				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);

				Iterator<Paper> it_paper = paperList.iterator();

				while (it_paper.hasNext()) {
					Paper paper = (Paper) it_paper.next();

					bw.write(paper.authors.get(0).replace(" ", "_") + " " + paper.absContent
							+ "\n");

					System.out.println(paper.groups.get(0) + " "
							+ paper.absContent);
				}

				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createReducedAuthorTrainingFile(){

		Iterator<String> it_group = categoryList.iterator();

		try {
			while (it_group.hasNext()) {
				String category = it_group.next();
				File file = new File(".//CAT//"+category+".txt");
				File output_file = new File(".//NEW-CAT//"+category+".txt");
				if (!file.exists()) {
					continue;
				}
				BufferedReader br = null;		
				br = new BufferedReader(new FileReader(file));
				
				if (!output_file.exists()) {
					output_file.createNewFile();
				}
				FileWriter fw = new FileWriter(output_file);
				BufferedWriter bw = new BufferedWriter(fw);
				
				String line = br.readLine();
				while( line !=null){
					String name = line.split("\\s")[0];
					
					String [] sentences = POSTagger.getSentence(line);
					bw.write(name);
					System.out.print(name + ":");
					for(int i=0; i<sentences.length; i++){
						String [] tokens = POSTagger.getToken(sentences[i]);
						String [] taggers = POSTagger.getPOSTag(tokens);
						for(int j=0; j< taggers.length; j++){
							if(taggers[j].startsWith("NN") || taggers[j].equals("JJ")){
								bw.write(" "+tokens[j]);
								System.out.print(taggers[j]+" "+tokens[j]);
							}
						}	
					}
					bw.write("\n");
					System.out.println();
					line = br.readLine();
				}
				
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void main(String [] args) {
		
		AbstractParser abstract_parser = new AbstractParser();
		try {
			paperList = abstract_parser.parserAAAI14Abstract();
			//abstract_parser.createCategroyTraingFile("category_training.txt");
			//abstract_parser.createAuthorTrainingFile();
			abstract_parser.createAuthorFile();
			//abstract_parser.createReducedAuthorTrainingFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*paperList = abstract_parser.parserAbstract("DBLPOnlyCitationOct19 2.txt");
		try {
			abstract_parser.abstractFilter("reducedpaper.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*
		LexicalParser lex_parser = new LexicalParser();
		Iterator<Paper> it_paper = abstract_parser.paperList.iterator();
	
		while (it_paper.hasNext()) {
			Paper paper = (Paper) it_paper.next();
			String abs_contenct = paper.getAbsContent();
			String [] abstracts = abs_contenct.split("\\. ");
			
			for(int i=0; i<abstracts.length; i++){
				System.out.println(abstracts[i]);
				Tree tree = lex_parser.parse(abstracts[i]);
				
				List<Tree> leaves = tree.getLeaves();
				// Print words and Pos Tags
				for (Tree leaf : leaves) {
					Tree parent = leaf.parent(tree);
					System.out.print(leaf.label().value() + "-"
							+ parent.label().value() + " ");
				}
			}
		}*/
	}
}
