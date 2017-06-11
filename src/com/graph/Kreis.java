package com.graph;

import java.util.ArrayList;

public class Kreis {
	public ArrayList<Kante> kantenList = new ArrayList<Kante> ();
	public float kreisWert;
	
	
	public String toString(){
		
		return this.kantenList.toString()+" : KreisWert:"+this.kreisWert;
	}
}
