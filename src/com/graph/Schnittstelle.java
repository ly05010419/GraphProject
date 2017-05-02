package com.graph;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {
		
		
		Graph graph = new Graph("./assets/Graph3.txt");
		
		long startTime=System.currentTimeMillis();
		graph.breitenSuche();
//		graph.tiefenSuche();
		long endTime=System.currentTimeMillis();   
		System.out.println("Zeit：		"+(endTime-startTime)+"ms");  		 
   
	}

}
