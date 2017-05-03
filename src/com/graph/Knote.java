package com.graph;

import java.util.ArrayList;

public class Knote {
	public int id;
	
	private ArrayList<Integer> nachbarKnoteList = new ArrayList<Integer>();
	private ArrayList<UngerichtetKante> nachbarKantenList = new ArrayList<UngerichtetKante>();
	
	
	
	
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

	public int compareTo(Knote o) {
		if (this.id > o.id) {
			return 1;
		} else if (this.id == o.id) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public ArrayList<UngerichtetKante> getNachbarKantenList() {
		return nachbarKantenList;
	}

	public void setNachbarKantenList(ArrayList<UngerichtetKante> kantenList) {
		this.nachbarKantenList = kantenList;
	}
	
}
