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

	public Graph(String str, boolean gerichtetGraph, boolean mitBalance) throws Exception {
		Parser parser = new Parser(str, gerichtetGraph, mitBalance);
		this.knoteAnzahl = parser.knoteAnzahl;
		this.knotenList = parser.knotenList;
		this.kantenList = parser.kantenList;
		this.kantenMap = parser.kantenMap;
		this.gerichtetGraph = parser.gerichtetGraph;
		this.mitBalance = parser.mitBalance;
	}

	public void createRueckKnate(Knote rootKnote, Knote kindKnote, float gewicht) {

		Kante kante = new Kante(rootKnote, kindKnote, gewicht, this.gerichtetGraph);

		if (!kantenList.contains(kante)) {
			rootKnote.getNachbarKnotenList().add(kindKnote);
			rootKnote.getNachbarKantenList().add(kante);

			kantenList.add(kante);
			kantenMap.put(kante.kanteId, kante);
		} else {
			kante = kantenMap.get(kante.kanteId);
			kante.gewicht += gewicht;
		}

	}
}
