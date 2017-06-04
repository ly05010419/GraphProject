package com.graph;

import java.util.ArrayList;

public class Fluss {
	public ArrayList<Kante> kantenList;
	public float flussWert;
	
	public Fluss(){
		this.kantenList = new ArrayList<Kante>();
		
	}
	
	public String toString(){
		
		return this.kantenList.toString()+" : FlussWert:"+this.flussWert;
	}
}
