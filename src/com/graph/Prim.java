package com.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {
	public static void main(String[] args) throws Exception {

		Prim primsDriver = new Prim();
		GraphParse graphParse = new GraphParse("./assets/G_100_200.txt");

		long startTime = System.currentTimeMillis();

		primsDriver.primsMST(graphParse.knotenList);

		long endTime = System.currentTimeMillis();
		System.out.println("alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");

		float ergbnis = 0;
		for (Knote knote : graphParse.knotenList) {
			if (knote.kosten != Float.MAX_VALUE) {
				ergbnis = ergbnis + knote.kosten;
			}
		}

		System.out.println(ergbnis);

		// while (T is not empty) do
		// find edge (u,v) with lowest weight such that (u in S) and (v in T)
		// add (u,v) to MST
		// remove v from T
		// add v to S
		// return MST
		// System.out.println(ergbnis);
	}

	/**
	 * COde for breadth first traversal given an adjacency list.
	 * 
	 * @param knotenList
	 * @return
	 */
	private void primsMST(List<Knote> knotenList) {

		PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
		for (Knote v : knotenList) {
			v.kosten = Float.MAX_VALUE;
			priorityQueue.add(v);
		}

		Knote peek = priorityQueue.peek();
		peek.kosten = 0;
		priorityQueue.remove(peek);
		priorityQueue.add(peek);
		while (!priorityQueue.isEmpty()) {
			Knote minKnote = priorityQueue.poll();
			if (minKnote.nachbarKantenList != null && minKnote.nachbarKantenList.size() > 0) {
				for (UngerichtetKante edge : minKnote.nachbarKantenList) {
					if (priorityQueue.contains(edge.end) && edge.gewicht < edge.end.kosten) {
						priorityQueue.remove(edge.end);
						edge.end.kosten = edge.gewicht;
						edge.end.previous = minKnote;
						// System.out.println(edge.end);
						priorityQueue.add(edge.end);

					} 
				}
			}
		}
	}

}
