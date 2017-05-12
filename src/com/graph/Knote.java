package com.graph;

import java.util.ArrayList;


public class Knote implements Comparable<Knote> {
	
	public int id;
	private ArrayList<Knote> nachbarKnoteList = new ArrayList<Knote>();
	public ArrayList<UngerichtetKante> nachbarKantenList = new ArrayList<UngerichtetKante>();
	public Knote previousKnote;
	float knoteGewicht;
	private KnoteGruppe knoteGruppe;
	
	
	public boolean hatKnoteGruppe(){
		
		if (this.knoteGruppe != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setKnoteGruppe(KnoteGruppe knoteGruppe){
		if (this.knoteGruppe != null) {
			this.knoteGruppe.setKnoteGruppe(knoteGruppe);
		}else{
			this.knoteGruppe = knoteGruppe;
		}
		
	}
	
	public KnoteGruppe getKnoteGruppe(){
		
		if (this.knoteGruppe != null) {
			return this.knoteGruppe.getKnoteGruppe();
		} else {
			return this.knoteGruppe;
		}
	}
	

	public Knote(int id) {
		this.id = id;
		
	}

	public String toString() {

		if (previousKnote == null) {
			return "id:" + id ;
		} else {
			return "id:" + id + ",kosten" + knoteGewicht + ",previous:" + previousKnote.id;
		}

	}

	public int compareTo(Knote o) {

		if (this.knoteGewicht > o.knoteGewicht) {
			return 1;
		} else if (this.knoteGewicht == o.knoteGewicht) {
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
