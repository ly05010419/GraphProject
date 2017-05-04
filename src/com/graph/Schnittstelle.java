package com.graph;

import java.util.ArrayList;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {

		// Graph graph = new Graph("./assets/G_1_2_1.txt");

		ArrayList<String> strList = new ArrayList<String>();
//		strList.add("./assets/G_1_2.txt");
//		strList.add("./assets/G_1_20.txt");
//		strList.add("./assets/G_1_200.txt");
//		strList.add("./assets/G_10_20.txt");
//		strList.add("./assets/G_10_200.txt");
		strList.add("./assets/G_100_200.txt");
//		long startTime1 = System.currentTimeMillis();
		for (String str : strList) {
			
			System.out.println(str.replace("./assets/", ""));
			long startTime = System.currentTimeMillis();
			Graph graph = new Graph(str);

			long endTime = System.currentTimeMillis();
			System.out.println("init Zeit：		" + (endTime - startTime) / (1000.0) + "s");

			startTime = System.currentTimeMillis();

//			graph.kruskal();
			graph.prim();

			endTime = System.currentTimeMillis();
			System.out.println("alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
		}
//		long endTime1 = System.currentTimeMillis();
//		System.out.println("insgesamte Zeit：		" + (endTime1 - startTime1) / (1000.0) + "s");
	}

}
