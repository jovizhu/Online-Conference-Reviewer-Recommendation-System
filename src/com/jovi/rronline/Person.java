package com.jovi.rronline;

import java.util.ArrayList;

public class Person {
	public String name;
	public String university;
	public String position;
	public ArrayList<String> research_interest;
	
	public Person(){
		research_interest = new ArrayList<String>();
	}
}
