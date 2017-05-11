package com.graph;

import java.util.ArrayList;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {

		ArrayList<String> strList = new ArrayList<String>();
		// strList.add("./assets/G_1_2_1.txt");
//		strList.add("./assets/G_1_2.txt");
//		strList.add("./assets/G_1_20.txt");
//		strList.add("./assets/G_1_200.txt");
//		strList.add("./assets/G_10_20.txt");
//		strList.add("./assets/G_10_200.txt");
//		strList.add("./assets/G_100_200.txt");

		strList.add("./assets/Graph1.txt");
		
		for (String str : strList) {

			System.out.println(str.replace("./assets/", "").replace(".txt", ""));

			Graph graph = new Graph(str);

			long startTime = System.currentTimeMillis();

			// graph.breitenSuche();
			// graph.prim();
			 graph.tiefenSuche();
//			graph.kruskal();

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
		}

	}

}
