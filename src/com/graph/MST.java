package com.graph;

import java.util.ArrayList;

//MinimalSpannendeBäume
public class MST {

	public ArrayList<Kante> kantenList = new ArrayList<Kante>();
	public ArrayList<Knote> knotenList = new ArrayList<Knote>();

	public MST(ArrayList<Kante> kanten, int knoteAnzahl) {

		this.kantenList = kanten;

		for (int i = 0; i < knoteAnzahl; i++) {
			Knote knote = new Knote(i);
			knotenList.add(knote);
		}

		for (Kante kante : kanten) {
			Knote nachgängerKnote = knotenList.get(kante.nachgängerKnote.id);
			Knote vorgängerKonte = knotenList.get(kante.vorgängerKonte.id);
			
			vorgängerKonte.getNachbarKantenList().add(kante);
			nachgängerKnote.getNachbarKantenList().add(kante);
			
			vorgängerKonte.getNachbarKnotenList().add(nachgängerKnote);
			nachgängerKnote.getNachbarKnotenList().add(vorgängerKonte);
			
			vorgängerKonte.knoteGewicht = kante.kosten;
			nachgängerKnote.knoteGewicht = kante.kosten;
		}

	}
}
