package com.graph;

import java.util.ArrayList;


public class Knote implements Comparable<Knote> {
	
	public int id;
	private ArrayList<Knote> nachbarKnoteList = new ArrayList<Knote>();
	public ArrayList<UngerichtetKante> nachbarKantenList = new ArrayList<UngerichtetKante>();
	public Knote previousKnote;
	float kosten;
	public SchnittMenge schnittMenge;
	
	public SchnittMenge getSchnittMenge(){
		if (this.schnittMenge != null) {
			return this.schnittMenge.getSchnittMenge();
		} else {
			return this.schnittMenge;
		}
	}
	

	public Knote(int id) {
		this.id = id;
		
	}

	public String toString() {

		if (previousKnote == null) {
			return "id:" + id ;
		} else {
			return "id:" + id + ",kosten" + kosten + ",previous:" + previousKnote.id;
		}

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

	public ArrayList<Knote> getNachbarKnotenList() {
		return nachbarKnoteList;
	}

	public void setNachbarKnotenList(ArrayList<Knote> knoteList) {
		this.nachbarKnoteList = knoteList;
	}

}
