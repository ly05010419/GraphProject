package com.graph;

import java.util.ArrayList;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {

		// Schnittstelle.showBreitenSuche();
		// Schnittstelle.showTiefenSuche();
		// Schnittstelle.showPrimUndKrusal();
		// Schnittstelle.travelingSalesmanProblem();
		// Schnittstelle.showKuerzesteWege();
		// Schnittstelle.showfordFulkerson();
		Schnittstelle.showCycleCanceling();
	}

	public static void showCycleCanceling() throws Exception {


		
		Algorithmus algorithmus0 = new Algorithmus();
//		Graph graph0 = new Graph("./assets/kostenminimalFluss/KostenminimalTestMitSuperQuelle.txt", true, true, true);
		Graph graph0 = new Graph("./assets/kostenminimalFluss/KostenminimalTest.txt", true, true, true);
		algorithmus0.cycleCanceling(graph0);

//		for (int i = 1; i < 5; i++) {
//			Algorithmus algorithmus = new Algorithmus();
//			Graph graph = new Graph("./assets/kostenminimalFluss/Kostenminimal" + i + ".txt", true, true, true);
//			algorithmus.cycleCanceling(graph);
//		}

	}

	public static void showfordFulkerson() throws Exception {

		Algorithmus algorithmus1 = new Algorithmus();
		Graph graph1 = new Graph("./assets/Fluss/Fluss.txt", true, true, false);
		algorithmus1.fordFulkerson(0, 7, graph1);

		Algorithmus algorithmus2 = new Algorithmus();
		Graph graph2 = new Graph("./assets/MST/G_1_2.txt", true, true, false);
		algorithmus2.fordFulkerson(0, 7, graph2);
	}
	//
	// public static void showKuerzesteWege() throws Exception {
	//
	// Algorithmus algorithmus1 = new Algorithmus("./assets/Wege/Wege1.txt",
	// true);
	// algorithmus1.dijkstra(2, 0);
	//
	// Algorithmus algorithmus2 = new Algorithmus("./assets/Wege/Wege2.txt",
	// true);
	// algorithmus2.mooreBellmanFord(2, 0,algorithmus2.graph);
	//
	// Algorithmus algorithmus3 = new Algorithmus("./assets/Wege/Wege3.txt",
	// true);
	// algorithmus3.mooreBellmanFord(2, 0,algorithmus2.graph);
	//
	// Algorithmus algorithmus4 = new Algorithmus("./assets/MST/G_1_2.txt",
	// true);
	// algorithmus4.mooreBellmanFord(0, 1,algorithmus2.graph);
	//
	// Algorithmus algorithmus6 = new Algorithmus("./assets/MST/G_1_2.txt",
	// true);
	// algorithmus6.dijkstra(0, 1);
	//
	// Algorithmus algorithmus5 = new Algorithmus("./assets/MST/G_1_2.txt",
	// false);
	// algorithmus5.mooreBellmanFord(0, 1,algorithmus2.graph);
	//
	// Algorithmus algorithmus7 = new Algorithmus("./assets/MST/G_1_2.txt",
	// false);
	// algorithmus7.dijkstra(0, 1);
	// //
	//
	// }
	//
	// public static void travelingSalesmanProblem() throws Exception {
	//
	// ArrayList<String> strList = new ArrayList<String>();
	//
	// // strList.add("./assets/Traveling-Salesman-Problem/K_10_1.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_10.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_10e.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_12.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_12e.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_15.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_15e.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_20.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_30.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_50.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_70.txt");
	// strList.add("./assets/Traveling-Salesman-Problem/K_100.txt");
	//
	// for (String str : strList) {
	//
	// Schnittstelle.nameVonTxt(str);
	//
	// Algorithmus graph = new Algorithmus(str, false);
	//
	// long startTime = System.currentTimeMillis();
	//
	// graph.nearestNeighbor();
	//
	// // graph.doppelterBaum();
	//
	// // graph.bruteForce();
	//
	// // graph.branchUndBound();
	//
	// long endTime = System.currentTimeMillis();
	// System.out.println("Alle Zeit： " + (endTime - startTime) / (1000.0) +
	// "s");
	// }
	//
	// }
	//
	// public static void showPrimUndKrusal() throws Exception {
	//
	// ArrayList<String> strList = new ArrayList<String>();
	//
	// // strList.add("./assets/MST/G_1_2_1.txt");
	// strList.add("./assets/MST/G_1_2.txt");
	// strList.add("./assets/MST/G_1_20.txt");
	// strList.add("./assets/MST/G_1_200.txt");
	// strList.add("./assets/MST/G_10_20.txt");
	// strList.add("./assets/MST/G_10_200.txt");
	// strList.add("./assets/MST/G_100_200.txt");
	//
	// for (String str : strList) {
	//
	// Schnittstelle.nameVonTxt(str);
	//
	// Algorithmus graph = new Algorithmus(str, false);
	//
	// long startTime = System.currentTimeMillis();
	//
	// graph.prim();
	// // graph.kruskal();
	//
	// long endTime = System.currentTimeMillis();
	// System.out.println("Alle Zeit： " + (endTime - startTime) / (1000.0) +
	// "s");
	// }
	// }
	//
	// public static void showBreitenSuche() throws Exception {
	//
	// ArrayList<String> strList = new ArrayList<String>();
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph1.txt");
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph2.txt");
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph3.txt");
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph4.txt");
	// // strList.add("./assets/BreitensucheUndTiefensuche/Graph5.txt");
	//
	// for (String str : strList) {
	//
	// Schnittstelle.nameVonTxt(str);
	//
	// Algorithmus graph = new Algorithmus(str, false);
	//
	// long startTime = System.currentTimeMillis();
	//
	// graph.breitenSuche();
	//
	// long endTime = System.currentTimeMillis();
	// System.out.println("Alle Zeit： " + (endTime - startTime) / (1000.0) +
	// "s");
	// }
	//
	// }
	//
	// public static void showTiefenSuche() throws Exception {
	//
	// ArrayList<String> strList = new ArrayList<String>();
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph1.txt");
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph2.txt");
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph3.txt");
	// strList.add("./assets/BreitensucheUndTiefensuche/Graph4.txt");
	// // strList.add("./assets/BreitensucheUndTiefensuche/Graph5.txt");
	//
	// for (String str : strList) {
	//
	// Schnittstelle.nameVonTxt(str);
	//
	// Algorithmus graph = new Algorithmus(str, false);
	//
	// long startTime = System.currentTimeMillis();
	//
	// // graph.tiefenSuche();
	//
	// long endTime = System.currentTimeMillis();
	// System.out.println("Alle Zeit： " + (endTime - startTime) / (1000.0) +
	// "s");
	// }
	//
	// }
	//
	// public static void nameVonTxt(String str) {
	//
	// if (str.contains("./assets/Traveling-Salesman-Problem/")) {
	// System.out.println(str.replace("./assets/Traveling-Salesman-Problem/",
	// "").replace(".txt", ""));
	// } else if (str.contains("./assets/MST/")) {
	// System.out.println(str.replace("./assets/MST/", "").replace(".txt", ""));
	// } else if (str.contains("./assets/BreitensucheUndTiefensuche/")) {
	// System.out.println(str.replace("./assets/BreitensucheUndTiefensuche/",
	// "").replace(".txt", ""));
	// } else if (str.contains("./assets/BreitensucheUndTiefensuche/")) {
	// System.out.println(str.replace("./assets/BreitensucheUndTiefensuche/",
	// "").replace(".txt", ""));
	// }
	// }

}
