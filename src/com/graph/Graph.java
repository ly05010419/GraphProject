package com.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

	public int knoteAnzahl;
	public ArrayList<Knote> knotenList;
	public HashMap<String, Kante> kantenMap;
	public ArrayList<Kante> kantenList;
	boolean gerichtetGraph = false;
	boolean mitBalance = false;
	boolean mitKapazität = false;
	

	public Graph(String str, boolean gerichtetGraph,boolean mitKapazität, boolean mitBalance) throws Exception {
		Parser parser = new Parser(str, gerichtetGraph, mitKapazität,mitBalance);
		this.knoteAnzahl = parser.knoteAnzahl;
		this.knotenList = parser.knotenList;
		this.kantenList = parser.kantenList;
		this.kantenMap = parser.kantenMap;
		this.gerichtetGraph = parser.gerichtetGraph;
		this.mitBalance = parser.mitBalance;
		this.mitKapazität = parser.mitKapazität;
	}
	
	
	public Graph(String str) throws Exception {
		Parser parser = new Parser(str, false, false,false);
		this.knoteAnzahl = parser.knoteAnzahl;
		this.knotenList = parser.knotenList;
		this.kantenList = parser.kantenList;
		this.kantenMap = parser.kantenMap;
		this.gerichtetGraph = parser.gerichtetGraph;
		this.mitBalance = parser.mitBalance;
	}

	
	public Kante createEineKnate(Knote rootKnote, Knote kindKnote, float gewicht, float kapazität, float flussWert) {

		Kante kante = new Kante(rootKnote, kindKnote, gewicht, kapazität, flussWert, this.gerichtetGraph);

		if (!kantenList.contains(kante)) {
			rootKnote.getNachbarKnotenList().add(kindKnote);
			rootKnote.getNachbarKantenList().add(kante);

			kantenList.add(kante);
			kantenMap.put(kante.kanteId, kante);
		} else {
			kante = kantenMap.get(kante.kanteId);
			kante.setFlussWert(flussWert);
		}
		return kante;
	}

	//删除这个节点，和于这个节点有关的所有边
	public void removeKnoteMitSeinKante(Knote knote) {
		knotenList.remove(knote);
		
		ArrayList<Kante> removeList = new ArrayList<Kante>();
		for (Kante kante : kantenList) {
			if(kante.nachgängerKnote.id==knote.id||kante.vorgängerKonte.id==knote.id){
				removeList.add(kante);
			}
		}
		
		kantenList.removeAll(removeList);
		
	}
	

	public Kante findKante(int id, int id2) {
		String kanteId = "" + id + "" + id2;
		return this.kantenMap.get(kanteId);
	}
}
