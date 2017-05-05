package com.graph;

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
			ergbnis = ergbnis + knote.kosten;
		}

		System.out.println(ergbnis);

	}

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
					if (priorityQueue.contains(edge.nachgängerKnote) && edge.gewicht < edge.nachgängerKnote.kosten) {
						priorityQueue.remove(edge.nachgängerKnote);
						edge.nachgängerKnote.kosten = edge.gewicht;
						edge.nachgängerKnote.previousKnote = minKnote;
						// System.out.println(edge.end);
						priorityQueue.add(edge.nachgängerKnote);

					}
				}
			}
		}
	}

}
