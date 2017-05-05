package com.graph;

public class UngerichtetKante implements Comparable<UngerichtetKante> {
	
	public Knote vorgängerKonte;
	public Knote nachgängerKnote;
	public float gewicht;
	
	public UngerichtetKante(Knote end, float weight) {
		this.gewicht = weight;
		this.nachgängerKnote = end;
	}

	public UngerichtetKante(Knote vorgängerKonte,Knote nachgängerKnote, float gewicht) {

		this.vorgängerKonte = vorgängerKonte;
		this.nachgängerKnote = nachgängerKnote;
		this.gewicht = gewicht;
	}

	public String toString() {

		return "{" + gewicht + " " + "(" + vorgängerKonte + "," + nachgängerKnote.id + "," + gewicht + ")}";
	}

	@Override
	public int hashCode() {
		if(vorgängerKonte.id<nachgängerKnote.id){
			return vorgängerKonte.id*10 + nachgängerKnote.id;
		}else{
			return nachgängerKnote.id*10+vorgängerKonte.id;
		}
	}

	@Override
	public boolean equals(Object o) {

		UngerichtetKante kante = (UngerichtetKante) o;
		if (this.vorgängerKonte == kante.vorgängerKonte && this.nachgängerKnote.id == kante.nachgängerKnote.id) {
			return true;

		} else if (this.vorgängerKonte.id == kante.nachgängerKnote.id && this.nachgängerKnote.id == kante.vorgängerKonte.id) {
			return true;

		} else {
			return false;
		}
	}

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

}
