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

	public void createRueckKnate(Knote rootKnote, Knote kindKnote, float gewicht, float kapazität) {

		Kante kante = new Kante(rootKnote, kindKnote, gewicht ,kapazität, this.gerichtetGraph);

		if (!kantenList.contains(kante)) {
			rootKnote.getNachbarKnotenList().add(kindKnote);
			rootKnote.getNachbarKantenList().add(kante);

			kantenList.add(kante);
			kantenMap.put(kante.kanteId, kante);
		} else {
			kante = kantenMap.get(kante.kanteId);
			kante.kapazität += kapazität;
		}

	}


	public void removeKante(Kante kante) {
		kante.vorgängerKonte.removeKnoteUndKante(kante.nachgängerKnote);
		this.kantenList.remove(kante);
		this.kantenMap.remove(kante.kanteId);
	}
}
