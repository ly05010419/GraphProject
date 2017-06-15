package com.graph;

import java.io.Serializable;

public class Kante implements Comparable<Kante> ,Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 41869960674878176L;
	public Knote vorgängerKonte;
	public Knote nachgängerKnote;
	public float kosten;//oder die Kosten
	public float kapazität;
	private float flussWert;
	public String kanteId;
	public boolean residualKante;
	
	boolean gerichtetGraph = false;

	public Kante(Knote end, float weight, boolean gerichtetGraph) {
		this.kosten = weight;
		this.nachgängerKnote = end;
		this.gerichtetGraph = gerichtetGraph;
	}

	public Kante(Knote vorgängerKonte, Knote nachgängerKnote, float gewicht,float kapazität, boolean gerichtetGraph) {
		this.gerichtetGraph = gerichtetGraph;
		this.vorgängerKonte = vorgängerKonte;
		this.nachgängerKnote = nachgängerKnote;
		this.kosten = gewicht;
		this.kapazität = kapazität; 
		if (gerichtetGraph) {
			kanteId = "" + vorgängerKonte.id + "" + nachgängerKnote.id;
		} else {
			if (vorgängerKonte.id < nachgängerKnote.id) {
				kanteId = "" + vorgängerKonte.id + "" + nachgängerKnote.id;
			} else {
				kanteId = "" + nachgängerKnote.id + "" + vorgängerKonte.id;
			}
		}
	}
	
	
	public Kante(Knote vorgängerKonte, Knote nachgängerKnote, float gewicht, float kapazität, float flussWert,
			boolean gerichtetGraph) {
		this( vorgängerKonte,  nachgängerKnote,  gewicht,  kapazität, gerichtetGraph);
		this.flussWert = flussWert;
	}
	
	
	
	public boolean isAvailable() {

		if (this.getVerfügebarKapazität() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {

		return " [" + vorgängerKonte.id + "," + nachgängerKnote.id + ", (" + flussWert + "/" + kapazität + "/" + kosten
				+ ") ]";
	}

	// Prüfung für zwei kante nach Reihenfolge,ob es gleich ist. z.B. Kante(2-9)
	// ist gleich Kante(9-2)
	@Override
	public int hashCode() {
		if (gerichtetGraph) {
			if (vorgängerKonte.id < nachgängerKnote.id) {
				return vorgängerKonte.id * 10 + nachgängerKnote.id;
			} else {
				return nachgängerKnote.id * 10 + vorgängerKonte.id;
			}
		} else {
			return vorgängerKonte.id * 10 + nachgängerKnote.id;
		}
	}

	@Override
	public boolean equals(Object o) {
		Kante kante = (Kante) o;

		if (gerichtetGraph) {

			if (this.vorgängerKonte == kante.vorgängerKonte && this.nachgängerKnote.id == kante.nachgängerKnote.id) {
				return true;

			} else {

				return false;
			}

		} else {

			if (this.vorgängerKonte == kante.vorgängerKonte && this.nachgängerKnote.id == kante.nachgängerKnote.id) {
				return true;

			} else if (this.vorgängerKonte.id == kante.nachgängerKnote.id
					&& this.nachgängerKnote.id == kante.vorgängerKonte.id) {
				return true;

			} else {
				return false;
			}
		}

	}

	// Sortieren nach Gewicht
	@Override
	public int compareTo(Kante o) {
		if (this.kosten > o.kosten) {
			return 1;
		} else if (this.kosten == o.kosten) {
			return 0;
		} else {
			return -1;
		}
	}

	public float getVerfügebarKapazität() {
		return this.kapazität - this.flussWert;
	}
	
	public float getFlussWert() {
		return flussWert;
	}

	public void setFlussWert(float flussWert) {
		this.flussWert = flussWert;
	}
	
	public float getKapazität() {
		return kapazität;
	}
	
	public void setKapazität(float kapazität) {
		this.kapazität = kapazität;
	}

}
