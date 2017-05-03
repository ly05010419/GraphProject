package com.graph;

public class UngerichtetKante {
	public int rootKonte;
	public int kindKonte;
	public float gewicht;


	public UngerichtetKante(int rootKonte, int kindKonte, float gewicht) {
		if (rootKonte < kindKonte) {
			this.rootKonte = rootKonte;
			this.kindKonte = kindKonte;
		} else {

			this.rootKonte = kindKonte;
			this.kindKonte = rootKonte;
		}
		
		this.gewicht = gewicht;
	}

	public String toString() {

		return "(" + rootKonte + "," + kindKonte + "," + gewicht + ")";
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
