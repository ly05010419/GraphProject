package com.graph;

import java.util.ArrayList;

public class Schnittstelle {

	public static void main(String[] args) throws Exception {

		// Schnittstelle.showBreitenSucheUndTiefenSuche();
		// Schnittstelle.showPrimUndKrusal();
		// Schnittstelle.travelingSalesmanProblem();
		// Schnittstelle.showKuerzesteWege();
		// Schnittstelle.showfordFulkerson();
		Schnittstelle.showCycleCanceling();
	}

	public static void showCycleCanceling() throws Exception {

		Algorithmus graph1 = new Algorithmus("./assets/kostenminimalFluss/KostenminimalTest.txt", true , true);
		graph1.cycleCanceling();

		// Graph graph1 = new
		// Graph("./assets/kostenminimalFluss/Kostenminimal1.txt", true);
		// graph1.cycleCanceling();
		//
		// Graph graph2 = new
		// Graph("./assets/kostenminimalFluss/Kostenminimal2.txt", true);
		// graph2.cycleCanceling();
		//
		// Graph graph3 = new
		// Graph("./assets/kostenminimalFluss/Kostenminimal3.txt", true);
		// graph3.cycleCanceling();
		//
		// Graph graph4 = new
		// Graph("./assets/kostenminimalFluss/Kostenminimal4.txt", true);
		// graph4.cycleCanceling();
	}

	public static void showfordFulkerson() throws Exception {

		Algorithmus graph1 = new Algorithmus("./assets/Fluss/Fluss.txt", true);
		graph1.fordFulkerson(0, 7);

		Algorithmus graph2 = new Algorithmus("./assets/MST/G_1_2.txt", true);
		graph2.fordFulkerson(0, 7);
	}

	public static void showKuerzesteWege() throws Exception {

		Algorithmus graph1 = new Algorithmus("./assets/Wege/Wege1.txt", true);
		graph1.dijkstra(2, 0);

		Algorithmus graph2 = new Algorithmus("./assets/Wege/Wege2.txt", true);
		graph2.mooreBellmanFord(2, 0);

		Algorithmus graph3 = new Algorithmus("./assets/Wege/Wege3.txt", true);
		graph3.mooreBellmanFord(2, 0);

		Algorithmus graph4 = new Algorithmus("./assets/MST/G_1_2.txt", true);
		graph4.mooreBellmanFord(0, 1);
		Algorithmus graph6 = new Algorithmus("./assets/MST/G_1_2.txt", true);
		graph6.dijkstra(0, 1);

		Algorithmus graph5 = new Algorithmus("./assets/MST/G_1_2.txt", false);
		graph5.mooreBellmanFord(0, 1);
		Algorithmus graph7 = new Algorithmus("./assets/MST/G_1_2.txt", false);
		graph7.dijkstra(0, 1);
		//

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

			Algorithmus graph = new Algorithmus(str, false);

			long startTime = System.currentTimeMillis();

			graph.nearestNeighbor();

			// graph.doppelterBaum();

			// graph.bruteForce();

			// graph.branchUndBound();

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
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

			Algorithmus graph = new Algorithmus(str, false);

			long startTime = System.currentTimeMillis();

			graph.prim();
			// graph.kruskal();

			long endTime = System.currentTimeMillis();
			System.out.println("Alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
		}
	}

	public static void showBreitenSucheUndTiefenSuche() throws Exception {

		ArrayList<String> strList = new ArrayList<String>();
		strList.add("./assets/BreitensucheUndTiefensuche/Graph1.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph2.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph3.txt");
		strList.add("./assets/BreitensucheUndTiefensuche/Graph4.txt");
		// strList.add("./assets/BreitensucheUndTiefensuche/Graph5.txt");

		for (String str : strList) {

			Schnittstelle.nameVonTxt(str);

			Algorithmus graph = new Algorithmus(str, false);

			long startTime = System.currentTimeMillis();

			graph.breitenSuche();
			// graph.tiefenSuche();

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
