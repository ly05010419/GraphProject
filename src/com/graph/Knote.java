package com.graph;

import java.util.ArrayList;

public class Knote {
	public int name;
	
	private ArrayList<Integer> knoteList;
	
	
	
	
	
	public ArrayList<Integer> getKnoteList() {
		return knoteList;
	}

	public void setKnoteList(ArrayList<Integer> knoteList) {
		this.knoteList = knoteList;
	}

	public Knote(int name){
		this.name = name;
	}
	
	public String toString(){
		
		return "name:"+name+",kindList:("+knoteList+")";
	}
	
}
