package com.graph;

import java.util.ArrayList;


public class Knote implements Comparable<Knote> {
	
	public int id;
	private ArrayList<Knote> nachbarKnoteList = new ArrayList<Knote>();
	public ArrayList<Kante> nachbarKantenList = new ArrayList<Kante>();
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

//		if (previousKnote == null) {
//			
//			 StringBuffer sBuffer = new StringBuffer("(id:" + id+",[");
//			 
//			for(Knote knote:this.getNachbarKnotenList()){
//				
//				sBuffer.append(knote.id+",");
//			}
//			sBuffer.append("])");
//			return  sBuffer.toString();
//		} else {
//			return "(id:" + id  + ",previous:" + previousKnote.id+")";
//		}
		return ""+id;
	}
	
	
	public Kante getKanteMitId(Knote endKnote){
		
		Kante kante = null;
		
		for(Kante k: this.nachbarKantenList){
			
			if(k.nachgängerKnote==endKnote){
				
				kante = k;
				break;
			}
		}
		return kante;
		
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

	public ArrayList<Kante> getNachbarKantenList() {
		return nachbarKantenList;
	}
	
	

	public void setNachbarKantenList(ArrayList<Kante> kantenList) {
		this.nachbarKantenList = kantenList;
	}

	public ArrayList<Knote> getNachbarKnotenList() {
		return nachbarKnoteList;
	}

	public void setNachbarKnotenList(ArrayList<Knote> knoteList) {
		this.nachbarKnoteList = knoteList;
	}

}
