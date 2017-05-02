package com.graph;

public class Kante {
	public int leftKonte;
	public int rightKonte;
	public float gewicht;

	public Kante(int leftKonte, int rightKonte) {

		if (leftKonte < rightKonte) {
			this.leftKonte = leftKonte;
			this.rightKonte = rightKonte;
		} else {

			this.leftKonte = rightKonte;
			this.rightKonte = leftKonte;
		}
	}
	
	public Kante(int leftKonte, int rightKonte, float gewicht) {
		
		this.gewicht = gewicht;
		
		if (leftKonte < rightKonte) {
			this.leftKonte = leftKonte;
			this.rightKonte = rightKonte;
		} else {

			this.leftKonte = rightKonte;
			this.rightKonte = leftKonte;
		}
	}

	public String toString() {

		return "(" + leftKonte + "," + rightKonte +"," + gewicht + ")";
	}

	@Override
	public int hashCode() {
		return leftKonte + rightKonte;
	}

	@Override
	public boolean equals(Object o) {

		Kante kante = (Kante) o;
		if (this.gewicht == kante.leftKonte && this.rightKonte == kante.rightKonte && this.gewicht == kante.gewicht)
			return true;
		else
			return false;
	}
	
	
	public int compareTo(Kante o) {  
	    if (this.gewicht > o.gewicht) {  
	        return -1;  
	    } else if (this.gewicht == o.gewicht) {  
	        return 0;  
	    } else {  
	        return 1;  
	    }  
	  
	}  
}
