package com.graph;

import java.util.ArrayList;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {

		ArrayList<String> strList = new ArrayList<String>();
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph1.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph2.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph3.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph4.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph5.txt");

		// strList.add("./assets/MST/G_1_2_1.txt");
		// strList.add("./assets/MST/G_1_2.txt");
		// strList.add("./assets/MST/G_1_20.txt");
		// strList.add("./assets/MST/G_1_200.txt");
		// strList.add("./assets/MST/G_10_20.txt");
		// strList.add("./assets/MST/G_10_200.txt");
		// strList.add("./assets/MST/G_100_200.txt");

//		 strList.add("./assets/Traveling-Salesman-Problem/K_10_2.txt");
		 strList.add("./assets/Traveling-Salesman-Problem/K_10.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_10e.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_12.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_12e.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_15.txt");
//		strList.add("./assets/Traveling-Salesman-Problem/K_15e.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_20.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_30.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_50.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_70.txt");
//		 strList.add("./assets/Traveling-Salesman-Problem/K_100.txt");

		for (String str : strList) {

			Schnittstelle.nameVonTxt(str);

			Graph graph = new Graph(str);

			long startTime = System.currentTimeMillis();

			// graph.breitenSuche();
			// graph.tiefenSuche();

			// graph.prim();
			// graph.kruskal();
			
//			 graph.nearestNeighbor();
			 graph.doppelterBaum();

//			 graph.bruteForce();

//			graph.branchUndBound();

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
		}

	}

	public static void nameVonTxt(String str) {

		if (str.contains("./assets/Traveling-Salesman-Problem/")) {
			System.out.println(str.replace("./assets/Traveling-Salesman-Problem/", "").replace(".txt", ""));
		} else if (str.contains("./assets/MST/")) {
			System.out.println(str.replace("./assets/MST/", "").replace(".txt", ""));
		} else if (str.contains("./assets/BreitensucheUndTiefensuche/")) {
			System.out.println(str.replace("./assets/BreitensucheUndTiefensuche/", "").replace(".txt", ""));
		}
	}

}
