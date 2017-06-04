package com.graph;

import java.util.ArrayList;

public class Fluss {
	public ArrayList<Kante> kantenList = new ArrayList<Kante>();
	public float flussWert;
	
	public Fluss(float flussWert,ArrayList<Kante> kantenList){
		this.kantenList = kantenList;
		this.flussWert = flussWert;
	}
}
