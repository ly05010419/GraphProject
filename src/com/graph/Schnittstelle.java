package com.graph;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {
		
		
		Graph graph = new Graph("./assets/G_1_2.txt");
		
		long startTime=System.currentTimeMillis();
//		graph.breitenSuche();
		graph.kruskal();
//		graph.prim();
		
		long endTime=System.currentTimeMillis();   
		System.out.println("Zeit：		"+(endTime-startTime)+"ms");  		 
   
	}

}
