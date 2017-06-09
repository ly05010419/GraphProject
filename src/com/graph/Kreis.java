package com.graph;

import java.util.ArrayList;

public class Kreis {
	public ArrayList<Kante> kantenList;
	public float kreisWert;
	
	public Kreis(){
		this.kantenList = new ArrayList<Kante>();
		
	}
	
	public String toString(){
		
		return this.kantenList.toString()+" : KreisWert:"+this.kreisWert;
	}
}
