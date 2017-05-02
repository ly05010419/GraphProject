package com.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> GraphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knoten;

	public Queue<Integer> queue = new LinkedList<Integer>();
	public HashSet<Integer> nichtBesuchtList = new HashSet<Integer>();

	public Graph(String str) throws Exception {
		GraphParse graphParse = new GraphParse();
		knoten = graphParse.parse(str);
	}

	public void breitenSuche() throws Exception {

		// Initialisiereung
		nichtBesuchtList.clear();
		for (int i = 0; i < knoten.size(); i++) {
			nichtBesuchtList.add(i);
		}

		// startKnote auswälen
		int startKonte = (Integer) nichtBesuchtList.toArray()[0];
		queue.add(startKonte);


		GraphCompenent graphCompenent = new GraphCompenent();
		GraphCompenentList.add(graphCompenent);

		while (queue.size() != 0) {

			Integer vaterKonte = queue.poll();
			nichtBesuchtList.remove(vaterKonte);
			
			graphCompenent.getKnoten().add(vaterKonte);


			Knote vater = knoten.get(vaterKonte);

			ArrayList<Integer> kindList = vater.getKnoteList();

			for (int i = 0; i < kindList.size(); i++) {
				if (nichtBesuchtList.contains(kindList.get(i))) {
					queue.add(kindList.get(i));
					nichtBesuchtList.remove(kindList.get(i));
				}
			}

			// neu GraphCompenent finden
			if (queue.size() == 0 && nichtBesuchtList.size() != 0) {
				int neuStartKonte = (Integer) nichtBesuchtList.toArray()[0];
				queue.add(neuStartKonte);

				graphCompenent = new GraphCompenent();
				GraphCompenentList.add(graphCompenent);
			}
		}

		this.darstellenVonGraphen();
	}

	public void tiefenSuche() throws Exception {

		// Initialisiereung
		nichtBesuchtList.clear();
		for (int i = 0; i < knoten.size(); i++) {
			nichtBesuchtList.add(i);
		}

		//iteratiert nichtBesuchtList
		while (nichtBesuchtList.size() != 0) {
			int startKnote = (int) nichtBesuchtList.toArray()[0];
			// System.out.println("i:"+i);

			GraphCompenent g = new GraphCompenent();

			GraphCompenentList.add(g);

			this.rekusiveTiefenSuche(startKnote, g);

		}

		this.darstellenVonGraphen();
	}

	public void rekusiveTiefenSuche(int vaterKnote, GraphCompenent graph) throws Exception {
		// System.out.println("vaterKnote:" + vaterKnote);
		nichtBesuchtList.remove(vaterKnote);
		graph.getKnoten().add(vaterKnote);

		Knote vater = knoten.get(vaterKnote);

		for (int i = 0; i < vater.getKnoteList().size(); i++) {

			int kindKnote = vater.getKnoteList().get(i);
			if (nichtBesuchtList.contains(kindKnote)) {

				this.rekusiveTiefenSuche(kindKnote, graph);
			}
		}

	}

	public void darstellenVonGraphen() {
		System.out.println("\nGraphenAnzahl:	" + GraphCompenentList.size() + "\n");

		for (int i = 0; i < GraphCompenentList.size(); i++) {
			System.out.println("Graph" + i + ":" + GraphCompenentList.get(i));
		}
		System.out.println();
	}

}
