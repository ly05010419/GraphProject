package com.graph;

import java.util.ArrayList;

public class Knote implements Comparable<Knote>{
	public int id;
	
	private ArrayList<Integer> nachbarKnoteList = new ArrayList<Integer>();
	public ArrayList<UngerichtetKante> nachbarKantenList = new ArrayList<UngerichtetKante>();
	public Knote previous;
	float kosten;
	
	

	public Knote(int id){
		this.id = id;
	}
	
	public String toString(){
		
		if(previous==null){
			return "id:"+id+",kosten"+kosten+",previous:null";
		}else{
		return "id:"+id+",kosten"+kosten+",previous:"+previous.id;
		}
//		return "id:"+id+",kindList:("+nachbarKnoteList+"),kosten"+kosten+",previous:"+previous;
	}

	public int compareTo(Knote o) {
		if (this.kosten > o.kosten) {
			return 1;
		} else if (this.kosten == o.kosten) {
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
	
	public ArrayList<Integer> getNachbarKnotenList() {
		return nachbarKnoteList;
	}

	public void setNachbarKnotenList(ArrayList<Integer> knoteList) {
		this.nachbarKnoteList = knoteList;
	}
	
}
