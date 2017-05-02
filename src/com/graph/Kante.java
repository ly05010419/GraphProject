package com.graph;

public class Kante {
	public int leftKonte;
	public int rightKonte;

	public Kante(int leftKonte, int rightKonte) {

		if (leftKonte < rightKonte) {
			this.leftKonte = leftKonte;
			this.rightKonte = rightKonte;
		} else {

			this.leftKonte = rightKonte;
			this.rightKonte = leftKonte;
		}
	}

	public String toString() {

		return "(" + leftKonte + ":" + rightKonte + ")";
	}

	@Override
	public int hashCode() {
		return leftKonte + rightKonte;
	}

	@Override
	public boolean equals(Object o) {

		Kante kante = (Kante) o;
		if (this.leftKonte == kante.leftKonte && this.rightKonte == kante.rightKonte)
			return true;
		else
			return false;
	}
	
	
//	public int compareTo(Kante o) {  
//	    if (this.leftKonte > o.leftKonte) {  
//	        return -1;  
//	    } else if (this.leftKonte == o.leftKonte) {  
//	        return 0;  
//	    } else {  
//	        return 1;  
//	    }  
//	  
//	}  
}
