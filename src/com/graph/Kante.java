package com.graph;

import java.io.Serializable;

public class Kante implements Comparable<Kante>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2252238274991575615L;
	public Knote vorgängerKonte;
	public Knote nachgängerKnote;
	public float gewicht;// oder die Kosten
	private float kapazität;
	private float flussWert;
	private float verfügebarKapazität;

	public float getKapazität() {
		return kapazität;
	}

	public boolean isAvailable() {

		if (this.getVerfügebarKapazität() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setKapazität(float kapazität) {
		this.kapazität = kapazität;
	}

	public float getFlussWert() {
		return flussWert;
	}

	public void setFlussWert(float flussWert) {
		this.flussWert = flussWert;
	}

	public float getVerfügebarKapazität() {
		return this.kapazität - this.flussWert;
	}

	public void setVerfügebarKapazität(float verfügebarKapazität) {
		this.verfügebarKapazität = verfügebarKapazität;
	}

	public String kanteId;
	boolean gerichtetGraph = false;

	public Kante(Knote end, float weight, boolean gerichtetGraph) {
		this.gewicht = weight;
		this.nachgängerKnote = end;
		this.gerichtetGraph = gerichtetGraph;
	}

	public Kante(Knote vorgängerKonte, Knote nachgängerKnote, float gewicht, float kapazität, float flussWert,
			boolean gerichtetGraph) {
		this.gerichtetGraph = gerichtetGraph;
		this.vorgängerKonte = vorgängerKonte;
		this.nachgängerKnote = nachgängerKnote;
		this.gewicht = gewicht;
		this.kapazität = kapazität;
		this.flussWert = flussWert;

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

	public String toString() {

		return " [" + vorgängerKonte.id + "," + nachgängerKnote.id + ", (" + flussWert + "/" + kapazität + "/" + gewicht
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
		if (this.gewicht > o.gewicht) {
			return 1;
		} else if (this.gewicht == o.gewicht) {
			return 0;
		} else {
			return -1;
		}
	}

	// public static UngerichtetKante getKanteMitId(Knote knoteA, Knote knoteB,
	// HashMap<String, UngerichtetKante> kantenMap) {
	// String kanteId = null;
	//
	// if (knoteA.id < knoteB.id) {
	// kanteId = knoteA.id + "" + knoteB.id;
	// } else {
	// kanteId = knoteB.id + "" + knoteA.id;
	// }
	// UngerichtetKante kante = kantenMap.get(kanteId);
	// return kante;
	// }

}
