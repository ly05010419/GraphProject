package com.graph;

import java.util.ArrayList;

public class Knote {
	public int id;
	
	private ArrayList<Integer> nachbarKnoteList;
	private ArrayList<UngerichtetKante> nachbarKantenList;
	
	
	
	
	public ArrayList<Integer> getNachbarKnotenList() {
		return nachbarKnoteList;
	}

	public void setNachbarKnotenList(ArrayList<Integer> knoteList) {
		this.nachbarKnoteList = knoteList;
	}

	public Knote(int id){
		this.id = id;
	}
	
	public String toString(){
		
		return "id:"+id+",kindList:("+nachbarKnoteList+")";
	}

	public ArrayList<UngerichtetKante> getNachbarKantenList() {
		return nachbarKantenList;
	}

	public void setNachbarKantenList(ArrayList<UngerichtetKante> kantenList) {
		this.nachbarKantenList = kantenList;
	}
	
}
