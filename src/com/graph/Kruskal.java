package com.graph;

import java.util.*;

public class Kruskal {
	
	
	static class EDGE implements Comparable<EDGE> {
		String from, to;
		float weight;

		EDGE(String f, String t, float w) {
			from = f;
			to = t;
			weight = w;
		}


		@Override
		public int compareTo(EDGE o) {
			return weight < o.weight ? -1 : (weight > o.weight ? 1 : 0);
		}

		@Override
		public String toString() {
			return "[" + from + ", " + to + "]";
		}
	}

	private static Map<String, String> PARENT;
	private static Map<String, Integer> RANKS; // to store the depths

	public static void initialize(String[] universe) {
		PARENT = new HashMap<>();
		RANKS = new HashMap<>();
		for (String x : universe) {
			PARENT.put(x, x);
			RANKS.put(x, 1);
		}
	}

	public static String FindSet(String item) {
		String parent = PARENT.get(item);
		if (parent == item)
			return item;
		else
			return FindSet(parent);
	}

	public static void Union(String setA, String setB) {
		String pA, pB;
		while ((pA = PARENT.get(setA)) != setA) {
			setA = pA;
		}
		while ((pB = PARENT.get(setB)) != setB) {
			setB = pB;
		}

		int rankFirst = RANKS.get(setA), rankSecond = RANKS.get(setB);
		if (rankFirst > rankSecond) {
			PARENT.put(setB, setA);
			updateRanksUpward(setB);
		} else if (rankSecond > rankFirst) {
			PARENT.put(setA, setB);
			updateRanksUpward(setA);
		} else {
			PARENT.put(setB, setA);
			updateRanksUpward(setB);
		}
	}

	public static void updateRanksUpward(String current) {
		int currentDepth = RANKS.get(current);
		String currentsParent = PARENT.get(current);
		int parentsDepth = RANKS.get(currentsParent);
		if (!(currentDepth < parentsDepth || currentsParent == current)) {
			RANKS.put(currentsParent, currentDepth + 1);
			updateRanksUpward(currentsParent);
		}
	}

	public static void main(String[] args) throws Exception {

		GraphParse graphParse = new GraphParse("./assets/G_100_200.txt");


		EDGE[] edges = new EDGE[graphParse.kantenList.size()];
		int i = 0;
		for (UngerichtetKante kante : graphParse.kantenList) {

			edges[i] = new EDGE(kante.rootKonte+"", kante.kindKonte+"", kante.gewicht);
			i++;
		}

		// Test data
		// CLRS Example p632
		String[] vertices = new String[graphParse.knotenList.size()];
		int j = 0;
		for (Knote knote : graphParse.knotenList) {

			vertices[j] =  knote.id+"";
			j++;
		}

		long startTime = System.currentTimeMillis();
		// Call Kruskal Algorithm
		Kruskal(vertices, edges);
		long endTime = System.currentTimeMillis();
		System.out.println("alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
	}

	// CLSR p631 Algorithm
	public static ArrayList<EDGE> Kruskal(String[] vertices, EDGE[] edges) {
		
		float ergbnis = 0;
		// Initialize A = empty set
		ArrayList<EDGE> mst = new ArrayList<>();

		// for each vertex v belongs to G.V MAKE-SET(v)
		initialize(vertices);

		// sort the edges of G.E into non decreasing order by weight w
		Arrays.sort(edges);

		// For each edge (u,v) belongs to G.E taken in non decreasing order by
		// weight
		for (EDGE edge : edges) {
			// If (find-set(u)!=find-set(v)
			if (FindSet(edge.from) != FindSet(edge.to)) {
				// A = A union (u, v)
				mst.add(edge);
				ergbnis = ergbnis+edge.weight;
				// UNION(u, v)
				Union(edge.from, edge.to);
			}
		}
		// Display contents
		System.out.println(ergbnis+",MST contains the edges: " + mst.size());
		return mst;
	}
}