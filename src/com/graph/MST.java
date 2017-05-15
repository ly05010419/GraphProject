package com.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MST {

	public ArrayList<UngerichtetKante> kantenList = new ArrayList<UngerichtetKante>();
	public ArrayList<Knote> knotenList = new ArrayList<Knote>();

	public MST(ArrayList<UngerichtetKante> kanten, int knoteAnzahl) {

		this.kantenList = kanten;

		for (int i = 0; i < knoteAnzahl; i++) {
			Knote knote = new Knote(i);
			knotenList.add(knote);
		}

		for (UngerichtetKante kante : kanten) {
			Knote nachgängerKnote = knotenList.get(kante.nachgängerKnote.id);
			Knote vorgängerKonte = knotenList.get(kante.vorgängerKonte.id);
			
			vorgängerKonte.getNachbarKantenList().add(kante);
			nachgängerKnote.getNachbarKantenList().add(kante);
			
			vorgängerKonte.getNachbarKnotenList().add(nachgängerKnote);
			nachgängerKnote.getNachbarKnotenList().add(vorgängerKonte);
			
			vorgängerKonte.knoteGewicht = kante.gewicht;
			nachgängerKnote.knoteGewicht = kante.gewicht;
		}

	}
}
