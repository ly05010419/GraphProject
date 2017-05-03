package com.graph;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {
		
		long startTime=System.currentTimeMillis();

//		Graph graph = new Graph("./assets/G_1_2_1.txt");
		Graph graph = new Graph("./assets/G_1_2.txt");
		
		
		long endTime=System.currentTimeMillis(); 
		System.out.println("init Zeit：		"+(endTime-startTime)/(1000.0)+"s");  	
		
		
		
		 startTime=System.currentTimeMillis();
		

//		graph.breitenSuche();
//		graph.tiefenSuche();
		graph.kruskal();
//		graph.prim();
//		
		 endTime=System.currentTimeMillis();   
		System.out.println("alle Zeit：		"+(endTime-startTime)/(1000.0)+"s");  	
		 
   
	}

}
