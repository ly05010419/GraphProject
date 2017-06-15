package com.graph;

import java.util.ArrayList;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {

		// Schnittstelle.showBreitenSuche();

		// Schnittstelle.showTiefenSuche();

		
		// Schnittstelle.showPrimUndKrusal();
		// Schnittstelle.travelingSalesmanProblem();
//		 Schnittstelle.showKuerzesteWege();
//		 Schnittstelle.showfordFulkerson();
		Schnittstelle.showCycleCanceling();
	}

	public static void showCycleCanceling() throws Exception {

		Algorithmus algorithmus = new Algorithmus();
//		Graph graph = new Graph("./assets/kostenminimalFluss/KostenminimalTest.txt", true, true, true);
//		Graph graph = new Graph("./assets/kostenminimalFluss/KostenminimalTest2.txt", true, true, true);
		Graph graph = new Graph("./assets/kostenminimalFluss/KostenminimalTest3.txt", true, true, true);
//		Graph graph = new Graph("./assets/kostenminimalFluss/Kostenminimal1.txt", true, true, true);
//		Graph graph = new Graph("./assets/kostenminimalFluss/Kostenminimal2.txt", true, true, true);
//		Graph graph = new Graph("./assets/kostenminimalFluss/Kostenminimal3.txt", true, true, true);
//		Graph graph = new Graph("./assets/kostenminimalFluss/Kostenminimal4.txt", true, true, true);
		algorithmus.cycleCanceling(graph);

		// Graph graph1 = new
		// Graph("./assets/kostenminimalFluss/Kostenminimal1.txt", true);
		// graph1.cycleCanceling();
	}

	public static void showfordFulkerson() throws Exception {

		Algorithmus algorithmus = new Algorithmus();
		Graph graph1 = new Graph("./assets/Fluss/Fluss.txt", true, true, false);
		algorithmus.fordFulkerson(0, 7, graph1);

		Graph graph2 = new Graph("./assets/MST/G_1_2.txt", true, true, false);
		algorithmus.fordFulkerson(0, 7, graph2);
	}

	//
	public static void showKuerzesteWege() throws Exception {

		Algorithmus algorithmus = new Algorithmus();
		Graph graph1 = new Graph("./assets/Wege/Wege1.txt", true, false, false);
		algorithmus.dijkstra(2, 0, graph1);

		Graph graph2 = new Graph("./assets/Wege/Wege2.txt", true, false, false);
		algorithmus.mooreBellmanFord(2, 0, graph2);

		Graph graph3 = new Graph("./assets/Wege/Wege2.txt", true, false, false);
		algorithmus.dijkstra(2, 0, graph3);

		Graph graph4 = new Graph("./assets/MST/G_1_2.txt", true, false, false);
		algorithmus.dijkstra(0, 1, graph4);
		algorithmus.mooreBellmanFord(0, 1, graph4);

		Graph graph5 = new Graph("./assets/MST/G_1_2.txt", false, false, false);
		algorithmus.dijkstra(0, 1, graph5);
		algorithmus.mooreBellmanFord(0, 1, graph5);

	}

	public static void travelingSalesmanProblem() throws Exception {

		ArrayList<String> strList = new ArrayList<String>();

		// strList.add("./assets/Traveling-Salesman-Problem/K_10_1.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_10.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_10e.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_12.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_12e.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_15.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_15e.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_20.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_30.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_50.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_70.txt");
		strList.add("./assets/Traveling-Salesman-Problem/K_100.txt");

		for (String str : strList) {

			Schnittstelle.nameVonTxt(str);

			Algorithmus algorithmus = new Algorithmus();

			Graph graph = new Graph(str, false, false, false);

			long startTime = System.currentTimeMillis();

			algorithmus.nearestNeighbor(graph);

			// algorithmus.doppelterBaum(graph);

			// algorithmus.bruteForce(graph);

			// algorithmus.branchUndBound(graph);

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit： " + (endTime - startTime) / (1000.0) + "s");
		}

	}

	public static void showPrimUndKrusal() throws Exception {

		ArrayList<String> strList = new ArrayList<String>();

		// strList.add("./assets/MST/G_1_2_1.txt");
		strList.add("./assets/MST/G_1_2.txt");
		strList.add("./assets/MST/G_1_20.txt");
		strList.add("./assets/MST/G_1_200.txt");
		strList.add("./assets/MST/G_10_20.txt");
		strList.add("./assets/MST/G_10_200.txt");
		strList.add("./assets/MST/G_100_200.txt");

		for (String str : strList) {

			Schnittstelle.nameVonTxt(str);

			Algorithmus algorithmus = new Algorithmus();
			Graph graph = new Graph(str, false, false, false);

			long startTime = System.currentTimeMillis();

			algorithmus.prim(graph);
			// algorithmus.kruskal(graph);

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
		}
	}

	public static void showBreitenSuche() throws Exception {

		ArrayList<String> strList = new ArrayList<String>();
		strList.add("./assets/BreitensucheUndTiefensuche/Graph1.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph2.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph3.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph4.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph5.txt");

		for (String str : strList) {

			Schnittstelle.nameVonTxt(str);

			Algorithmus algorithmus = new Algorithmus();
			Graph graph = new Graph(str, false, false, false);
			long startTime = System.currentTimeMillis();

			algorithmus.breitenSuche(graph);

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
		}

	}

	public static void showTiefenSuche() throws Exception {

		ArrayList<String> strList = new ArrayList<String>();
		strList.add("./assets/BreitensucheUndTiefensuche/Graph1.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph2.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph3.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph4.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph5.txt");

		for (String str : strList) {

			Schnittstelle.nameVonTxt(str);

			Algorithmus algorithmus = new Algorithmus();
			Graph graph = new Graph(str, false, false, false);
			long startTime = System.currentTimeMillis();

			algorithmus.tiefenSuche(graph);

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
		} else if (str.contains("./assets/BreitensucheUndTiefensuche/")) {
			System.out.println(str.replace("./assets/BreitensucheUndTiefensuche/", "").replace(".txt", ""));
		}
	}

}
