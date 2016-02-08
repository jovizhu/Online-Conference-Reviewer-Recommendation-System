package com.jovi.rronline;
import java.util.ArrayList;


public class Paper {

	String title;
	ArrayList<String> authors;
	String published;
	String year;
	String others;
	


	ArrayList<String> groups;
	ArrayList<String> topics;
	ArrayList<String> keyWords;
	String absContent;
	
	
	ArrayList<String> NNList;
	
	public Paper() {
		super();
		// TODO Auto-generated constructor stub
		title = "";
		authors = new ArrayList<String>();
		NNList = new ArrayList<String>();
		absContent= "";
		published = "";
		year = "";
		others = "";
		
		groups = new ArrayList<String>();
		topics = new ArrayList<String>();
		keyWords =new ArrayList<String>();
	}
	
	
	public ArrayList<String> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}

	public ArrayList<String> getTopics() {
		return topics;
	}

	public void setTopics(ArrayList<String> topics) {
		this.topics = topics;
	}

	public ArrayList<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(ArrayList<String> keyWords) {
		this.keyWords = keyWords;
	}
	
	public ArrayList<String> getNNList() {
		return NNList;
	}

	public void setNNList(ArrayList<String> nNList) {
		NNList = nNList;
	}
	
	public void addNNList(String nn){
		NNList.add(nn);
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public ArrayList<String> getAuthors() {
		return authors;
	}
	
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
	
	public void addAuthor(String author){
		this.authors.add(author);
	}
	
	public String getAbsContent() {
		return absContent;
	}
	
	public void setAbsContent(String absContect) {
		this.absContent = absContect;
	}
	
	public String getPublished() {
		return published;
	}
	
	public void setPublished(String published) {
		this.published = published;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getOthers() {
		return others;
	}
	
	public void setOthers(String others) {
		this.others = others;
	}
	
	public void addOthers(String other) {
		this.others = this.others+"\t"+other;
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder();
		ret.append(title+"\n");
		ret.append(year+"\n");
		int i = 0;
		for(i=0; i< authors.size()-1; i++){
			ret.append(authors.get(i)+"\t");
		}
		ret.append(authors.get(i)+"\n");
		ret.append(absContent+"\n\n");
		
		return ret.toString();
	}
	
}
