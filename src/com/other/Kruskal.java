package com.other;

import java.util.*;

import com.graph.Graph;
import com.graph.Kante;
import com.graph.Knote;

public class Kruskal {

	private static Map<String, String> PARENT = new HashMap<>();
	private static Map<String, Integer> RANKS = new HashMap<>();

	public static String FindSet(String item) {
		String parent = PARENT.get(item);
		if (parent == item)
			return item;
		else
			return FindSet(parent);
	}

	public static void Union(String vorgängerKonteId, String nachgängerKnoteId) {
		String parentSetA, parentSetB;

		while (true) {
			parentSetA = PARENT.get(vorgängerKonteId);
			
			if (parentSetA != vorgängerKonteId) {
				vorgängerKonteId = parentSetA;
			} else {
				break;
			}
		}

		while (true) {
			parentSetB = PARENT.get(nachgängerKnoteId);
			if (parentSetB != nachgängerKnoteId) {
				nachgängerKnoteId = parentSetB;
			} else {
				break;
			}
		}

		int rankFirst = RANKS.get(vorgängerKonteId), rankSecond = RANKS.get(nachgängerKnoteId);
		if (rankFirst > rankSecond) {
			PARENT.put(nachgängerKnoteId, vorgängerKonteId);
			updateRanksUpward(nachgängerKnoteId);
		} else if (rankSecond > rankFirst) {
			PARENT.put(vorgängerKonteId, nachgängerKnoteId);
			updateRanksUpward(vorgängerKonteId);
		} else {
			PARENT.put(nachgängerKnoteId, vorgängerKonteId);
			updateRanksUpward(nachgängerKnoteId);
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

	// CLSR p631 Algorithm
	public static ArrayList<Kante> Kruskal(ArrayList<Knote> vertices,
			ArrayList<Kante> kantenList) {

		float ergbnis = 0;
		// Initialize A = empty set
		ArrayList<Kante> mst = new ArrayList<>();

		for (Knote knote : vertices) {
			PARENT.put(knote.id + "", knote.id + "");
			RANKS.put(knote.id + "", 1);
		}

		// For each edge (u,v) belongs to G.E taken in non decreasing order by
		// weight
		for (Kante kante : kantenList) {
			// If (find-set(u)!=find-set(v)
			String vorgängerKonteFindSet = FindSet(kante.vorgängerKonte.id + "");
			String nachgängerKnoteFindSet = FindSet(kante.nachgängerKnote.id + "");
			if (vorgängerKonteFindSet != nachgängerKnoteFindSet) {
				// A = A union (u, v)
				mst.add(kante);
				ergbnis = ergbnis + kante.kosten;
				// UNION(u, v)
				Union(kante.vorgängerKonte.id + "", kante.nachgängerKnote.id + "");
				// System.out.println("PARENT"+PARENT);
				// System.out.println("RANKS"+RANKS);
			}
		}

		// Display contents
		System.out.println(ergbnis + ",MST contains the edges: " + mst.size());
		return mst;
	}

	public static void main(String[] args) throws Exception {

//		GraphParse graphParse = new GraphParse("./assets/G_100_200.txt");
		 Graph graphParse = new Graph("./assets/G_1_2_1.txt");

		long startTime = System.currentTimeMillis();

		Kruskal(graphParse.knotenList, graphParse.kantenList);

		long endTime = System.currentTimeMillis();
		System.out.println("alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");
	}
}