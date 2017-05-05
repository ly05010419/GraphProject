package com.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> graphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knotenList;
	private ArrayList<UngerichtetKante> kantenList;
	private float ergebnis = 0;

	
	public HashSet<Integer> nichtBesuchtKonten = new HashSet<Integer>();
	public HashSet<Knote> besuchtKonten = new HashSet<Knote>();
	private ArrayList<HashSet<Integer>> schnittMengen = new ArrayList<HashSet<Integer>>();
	
	
	

	public Graph(String str) throws Exception {

		GraphParse graphParse = new GraphParse(str);
		knotenList = graphParse.knotenList;
		kantenList = graphParse.kantenList;
		
	}

	public void breitenSuche() throws Exception {
		Queue<Integer> queue = new LinkedList<Integer>();
		
		// Initialisiereung
		for (int i = 0; i < knotenList.size(); i++) {
			nichtBesuchtKonten.add(i);
		}

		// StartKnote auswälen
		int startKonte = (Integer) nichtBesuchtKonten.toArray()[0];
		queue.add(startKonte);

		GraphCompenent graphCompenent = new GraphCompenent();
		graphCompenentList.add(graphCompenent);

		while (!queue.isEmpty()) {

			Integer vaterKonte = queue.poll();
			nichtBesuchtKonten.remove(vaterKonte);

			graphCompenent.getKnoten().add(vaterKonte);
			Knote vater = knotenList.get(vaterKonte);

			ArrayList<Knote> kindList = vater.getNachbarKnotenList();

			for (Knote knote: kindList) {
				if (nichtBesuchtKonten.contains(knote.id)) {
					queue.add(knote.id);
					nichtBesuchtKonten.remove(knote.id);
				}
			}

			// neu GraphCompenent finden
			if (queue.size() == 0 && nichtBesuchtKonten.size() != 0) {
				int neuStartKonte = (Integer) nichtBesuchtKonten.toArray()[0];
				queue.add(neuStartKonte);

				graphCompenent = new GraphCompenent();
				graphCompenentList.add(graphCompenent);
			}
		}

		this.darstellenVonGraphen();
	}

	public void tiefenSuche() throws Exception {

		// Initialisiereung
		nichtBesuchtKonten.clear();
		for (int i = 0; i < knotenList.size(); i++) {
			nichtBesuchtKonten.add(i);
		}

		// iteratiert nichtBesuchtList
		while (nichtBesuchtKonten.size() != 0) {
			int startKnote = (int) nichtBesuchtKonten.toArray()[0];
			// System.out.println("i:"+i);

			GraphCompenent g = new GraphCompenent();

			graphCompenentList.add(g);

			this.rekusiveTiefenSuche(startKnote, g);

		}

		this.darstellenVonGraphen();
	}

	public void rekusiveTiefenSuche(int vaterKnote, GraphCompenent graph) throws Exception {
		// System.out.println("vaterKnote:" + vaterKnote);
		nichtBesuchtKonten.remove(vaterKnote);
		graph.getKnoten().add(vaterKnote);

		Knote vater = knotenList.get(vaterKnote);

		for (Knote kindKnote: vater.getNachbarKnotenList()) {

			if (nichtBesuchtKonten.contains(kindKnote)) {

				this.rekusiveTiefenSuche(kindKnote.id, graph);
			}
		}

	}

	public void darstellenVonGraphen() {
		System.out.println("\nGraphenAnzahl:	" + graphCompenentList.size() + "\n");

		for (int i = 0; i < graphCompenentList.size(); i++) {
			System.out.println("Graph" + i + ":" + graphCompenentList.get(i));
		}
		System.out.println();
	}

	long totalTime = 0;
	
	
//	按最小边深度遍历 找MST minimal spanning tree
	public void prim() throws Exception {

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
		
		
		for (Knote knote : knotenList) {
			if (knote.kosten != Float.MAX_VALUE) {
				ergebnis = ergebnis + knote.kosten;
			}
		}
		System.out.println("ergebnis:" + ergebnis);
	}

//	// 切里找最小的边
//	public void prim() throws Exception {
//
//		Knote minimalKnote = knotenList.get(0);
//		besuchtKonten.add(minimalKnote);
//		createSchnittMenge(minimalKnote);
//
//		while (schnittMenge.size() > 0) {
//
//			UngerichtetKante minimalKante = getMinimalKanteVonSchinnt();
//
//			ergebnis = ergebnis + minimalKante.gewicht;
//
//			minimalKnote = knotenList.get(minimalKante.kindKonte);
//			// System.out.println(minimalKnote.id);
//			// System.out.println("minimalKnote:"+minimalKnote);
//
//			besuchtKonten.add(minimalKnote);
//			
//			long startTime = System.currentTimeMillis();
//
//
//			
//			createSchnittMenge(minimalKnote);
//			long endTime = System.currentTimeMillis();
//			totalTime = totalTime + (endTime - startTime);
//		}
//
////		System.out.println("Zeit：		" + (totalTime) / (1000.0) + "s");
//
//		System.out.println("ergebnis:" + ergebnis);
//	}
//
//	// 有向图
//	public void createSchnittMenge(Knote knote) {
//		for (UngerichtetKante kante : knote.getNachbarKantenList()) {
//
//			Knote nachgängerKnote = knotenList.get(kante.kindKonte);
//
//			if (!besuchtKonten.contains(nachgängerKnote)) {
//				schnittMenge.add(kante);
//			} else {
//				schnittMenge.remove(kante);
//			}
//		}
//
//	}
//
//	public UngerichtetKante getMinimalKanteVonSchinnt() {
//
//		return schnittMenge.peek();
//	}
	
	
	
	
	

	// 按最小的边分配，不要有圈
	public void kruskal() throws Exception {

		schnittMengen = new ArrayList<HashSet<Integer>>();

		int i = 0;

		for (UngerichtetKante güstigeKante : kantenList) {

			if (!kreis(güstigeKante)) {
				i++;
				ergebnis = ergebnis + güstigeKante.gewicht;

				liegenInSchnittMengen(güstigeKante);
			}
		}

		System.out.println(i + " ergebnis:" + ergebnis);

	}

	public boolean kreis(UngerichtetKante k) {
		boolean flag = false;
		for (HashSet<Integer> s : schnittMengen) {
			if (s.contains(k.vorgängerKonte) && s.contains(k.nachgängerKnote.id)) {
				s.add(k.vorgängerKonte.id);
				flag = true;
				break;
			}
		}

		return flag;

	}

	public void liegenInSchnittMengen(UngerichtetKante k) {
		boolean gefunden = false;

		ArrayList<HashSet<Integer>> connecteHashSetArrayList = new ArrayList<HashSet<Integer>>();

		for (HashSet<Integer> s : schnittMengen) {
			if (s.contains(k.vorgängerKonte) || s.contains(k.nachgängerKnote.id)) {
				s.add(k.vorgängerKonte.id);
				s.add(k.nachgängerKnote.id);
				gefunden = true;
				connecteHashSetArrayList.add(s);

				if (connecteHashSetArrayList.size() == 2) {

					HashSet<Integer> menge = new HashSet<Integer>();
					for (HashSet<Integer> set : connecteHashSetArrayList) {
						schnittMengen.remove(set);
						for (Object i : set.toArray()) {
							menge.add((Integer) i);
						}
					}
					schnittMengen.add(menge);
					break;
				}
			}
		}

		if (!gefunden) {
			HashSet<Integer> menge = new HashSet<Integer>();
			menge.add(k.vorgängerKonte.id);
			menge.add(k.nachgängerKnote.id);
			schnittMengen.add(menge);
		}
		// System.out.println("baumMenge"+baumMenge);
	}

}
