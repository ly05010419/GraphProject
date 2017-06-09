package com.other;

import java.util.List;
import java.util.PriorityQueue;

import com.graph.Graph;
import com.graph.Kante;
import com.graph.Knote;

public class Prim {
	public static void main(String[] args) throws Exception {

		Prim primsDriver = new Prim();
		Graph graphParse = new Graph("./assets/G_100_200.txt",false,false);

		long startTime = System.currentTimeMillis();

		primsDriver.primsMST(graphParse.knotenList);

		long endTime = System.currentTimeMillis();
		System.out.println("alle Zeit：		" + (endTime - startTime) / (1000.0) + "s");

		float ergbnis = 0;
		for (Knote knote : graphParse.knotenList) {
			ergbnis = ergbnis + knote.knoteGewicht;
		}

		System.out.println(ergbnis);

	}

	private void primsMST(List<Knote> knotenList) {

		PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
		for (Knote v : knotenList) {
			v.knoteGewicht = Float.MAX_VALUE;
			priorityQueue.add(v);
		}

		Knote peek = priorityQueue.peek();
		peek.knoteGewicht = 0;
	
		while (!priorityQueue.isEmpty()) {
			Knote minKnote = priorityQueue.poll();
			if (minKnote.nachbarKantenList != null && minKnote.nachbarKantenList.size() > 0) {
				for (Kante edge : minKnote.nachbarKantenList) {
					if (priorityQueue.contains(edge.nachgängerKnote) && edge.gewicht < edge.nachgängerKnote.knoteGewicht) {
						priorityQueue.remove(edge.nachgängerKnote);
						edge.nachgängerKnote.knoteGewicht = edge.gewicht;
						edge.nachgängerKnote.previousKnote = minKnote;
						// System.out.println(edge.end);
						priorityQueue.add(edge.nachgängerKnote);

					}
				}
			}
		}
	}

}
