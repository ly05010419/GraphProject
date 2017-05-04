package com.graph;

public class UngerichtetKante {
	public int rootKonte;
	public int kindKonte;
	public float gewicht;
	public Knote end;
	
	
	public UngerichtetKante(Knote end, float weight) {
		this.gewicht = weight;
		this.end = end;

	}

	public UngerichtetKante(int rootKonte, int kindKonte, float gewicht,Knote end) {

		this.rootKonte = rootKonte;
		this.kindKonte = kindKonte;
		this.gewicht = gewicht;
		this.end = end;
	}

	public String toString() {

		return "{" + gewicht + " " + "(" + rootKonte + "," + kindKonte + "," + gewicht + ")}";
	}

	@Override
	public int hashCode() {
		return rootKonte + kindKonte;
	}

	@Override
	public boolean equals(Object o) {

		UngerichtetKante kante = (UngerichtetKante) o;
		if (this.rootKonte == kante.rootKonte && this.kindKonte == kante.kindKonte) {
			return true;

		} else if (this.rootKonte == kante.kindKonte && this.kindKonte == kante.rootKonte) {
			return true;

		} else {
			return false;
		}
	}

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
