package com.graph;

import java.util.HashMap;

public class UngerichtetKante implements Comparable<UngerichtetKante> {

	public Knote vorgängerKonte;
	public Knote nachgängerKnote;
	public float gewicht;
	public String kanteId;

	public UngerichtetKante(Knote end, float weight) {
		this.gewicht = weight;
		this.nachgängerKnote = end;
	}

	public UngerichtetKante(Knote vorgängerKonte, Knote nachgängerKnote, float gewicht) {

		this.vorgängerKonte = vorgängerKonte;
		this.nachgängerKnote = nachgängerKnote;
		this.gewicht = gewicht;
		if (vorgängerKonte.id < nachgängerKnote.id) {
			kanteId = "" + vorgängerKonte.id + "" + nachgängerKnote.id;
		} else {
			kanteId = "" + nachgängerKnote.id + "" + vorgängerKonte.id;
		}
	}

	public String toString() {

		return "{" + gewicht + " " + "(" + vorgängerKonte.id + "," + nachgängerKnote.id + ")}";
	}

	// Prüfung für zwei kante nach Reihenfolge,ob es gleich ist. z.B. Kante(2-9)
	// ist gleich Kante(9-2)
	@Override
	public int hashCode() {
		if (vorgängerKonte.id < nachgängerKnote.id) {
			return vorgängerKonte.id * 10 + nachgängerKnote.id;
		} else {
			return nachgängerKnote.id * 10 + vorgängerKonte.id;
		}
	}

	@Override
	public boolean equals(Object o) {

		UngerichtetKante kante = (UngerichtetKante) o;
		if (this.vorgängerKonte == kante.vorgängerKonte && this.nachgängerKnote.id == kante.nachgängerKnote.id) {
			return true;

		} else if (this.vorgängerKonte.id == kante.nachgängerKnote.id
				&& this.nachgängerKnote.id == kante.vorgängerKonte.id) {
			return true;

		} else {
			return false;
		}
	}

	// Sortieren nach Gewicht
	@Override
	public int compareTo(UngerichtetKante o) {
		if (this.gewicht > o.gewicht) {
			return 1;
		} else if (this.gewicht == o.gewicht) {
			return 0;
		} else {
			return -1;
		}
	}
	
	
	public static UngerichtetKante getKanteMitId(Knote knoteA, Knote knoteB,HashMap<String,UngerichtetKante> kantenMap){
		String kanteId = null;
		if (knoteA.id < knoteB.id) {
			kanteId = knoteA.id + "" + knoteB.id;
		} else {
			kanteId = knoteB.id + "" + knoteA.id;
		}
		UngerichtetKante kante = kantenMap.get(kanteId);
		return kante;
	}

}
