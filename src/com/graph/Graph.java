package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> graphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knotenList;
	private ArrayList<UngerichtetKante> kantenList;

	public ArrayList<Knote> nichtBesuchtKonten = new ArrayList<Knote>();
	public ArrayList<Knote> besuchtKonten = new ArrayList<Knote>();
	private ArrayList<HashSet<Integer>> schnittMengen = new ArrayList<HashSet<Integer>>();

	private float insgesamtGewicht = 0;

	public Graph(String str) throws Exception {

		GraphParse graphParse = new GraphParse(str);
		knotenList = graphParse.knotenList;
		kantenList = graphParse.kantenList;
	}

	// // BreitenSuche durch Queue
	public void breitenSuche() throws Exception {
		Queue<Knote> queue = new LinkedList<Knote>();

		Knote startKnote = knotenList.get(0);
		queue.add(startKnote);
		besuchtKonten.add(startKnote);

		GraphCompenent graphCompenent = new GraphCompenent();
		graphCompenentList.add(graphCompenent);

		while (!queue.isEmpty()) {

			// poll 这里可以得到广度优先遍历的顺序
			Knote vater = queue.poll();
			// System.out.println(vater);

			graphCompenent.getKnoten().add(vater);

			for (Knote knote : vater.getNachbarKnotenList()) {
				if (!besuchtKonten.contains(knote)) {
					queue.add(knote);
					besuchtKonten.add(knote);
				}
			}

			if (queue.size() == 0 && besuchtKonten.size() != knotenList.size()) {

				nichtBesuchtKonten = diffKnotenList(knotenList, besuchtKonten);

				startKnote = nichtBesuchtKonten.get(0);
				queue.add(startKnote);
				besuchtKonten.add(startKnote);

				graphCompenent = new GraphCompenent();
				graphCompenentList.add(graphCompenent);
			}
		}

		// besuchtKonten记录了广度优先遍历的顺序
		// System.out.println(besuchtKonten);

		this.darstellenVonGraphen();
	}

	// 两个集合 差运算
	public ArrayList<Knote> diffKnotenList(ArrayList<Knote> listA, ArrayList<Knote> listB) {
		ArrayList<Knote> list = new ArrayList<Knote>(Arrays.asList(new Knote[listA.size()]));
		Collections.copy(list, listA);
		list.removeAll(listB);
		return list;
	}

	// 两个集合 差运算
	public ArrayList<UngerichtetKante> diffKantenList(ArrayList<UngerichtetKante> listA,
			ArrayList<UngerichtetKante> listB) {
		ArrayList<UngerichtetKante> list = new ArrayList<UngerichtetKante>(
				Arrays.asList(new UngerichtetKante[listA.size()]));
		Collections.copy(list, listA);
		list.removeAll(listB);
		return list;
	}

	// public void tiefenSuche() throws Exception {
	//
	// // Initialisiereung
	// nichtBesuchtKonten.clear();
	// for (int i = 0; i < knotenList.size(); i++) {
	// nichtBesuchtKonten.add(i);
	// }
	//
	// // iteratiert nichtBesuchtList
	// while (nichtBesuchtKonten.size() != 0) {
	// int startKnote = (int) nichtBesuchtKonten.toArray()[0];
	// // System.out.println("i:"+i);
	//
	// GraphCompenent g = new GraphCompenent();
	//
	// graphCompenentList.add(g);
	//
	// this.rekusiveTiefenSuche(startKnote, g);
	//
	// }
	//
	// this.darstellenVonGraphen();
	// }
	//
	// public void rekusiveTiefenSuche(int vaterKnote, GraphCompenent graph)
	// throws Exception {
	// // System.out.println("vaterKnote:" + vaterKnote);
	// nichtBesuchtKonten.remove(vaterKnote);
	// graph.getKnoten().add(vaterKnote);
	//
	// Knote vater = knotenList.get(vaterKnote);
	//
	// for (Knote kindKnote : vater.getNachbarKnotenList()) {
	//
	// if (nichtBesuchtKonten.contains(kindKnote)) {
	//
	// this.rekusiveTiefenSuche(kindKnote.id, graph);
	// }
	// }
	//
	// }

	public void darstellenVonGraphen() {
		System.out.println("\nGraphenAnzahl:	" + graphCompenentList.size() + "\n");

		for (int i = 0; i < graphCompenentList.size(); i++) {
			System.out.println("Graph" + i + ":" + graphCompenentList.get(i));
		}
		System.out.println();
	}

	long totalTime = 0;

	// 按最小边深度遍历 找MST minimal spanning tree
	public void prim() throws Exception {

		PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
		for (Knote v : knotenList) {
			v.kosten = Float.MAX_VALUE;
			priorityQueue.add(v);
		}

		Knote peek = priorityQueue.peek();
		peek.kosten = 0;
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
			insgesamtGewicht = insgesamtGewicht + knote.kosten;
		}
		System.out.println("insgesamtGewicht:" + insgesamtGewicht);
	}

	// 按最小的边分配，不要有圈
	public void kruskal() throws Exception {

		schnittMengen = new ArrayList<HashSet<Integer>>();

		int i = 0;

		for (UngerichtetKante güstigeKante : kantenList) {

			if (!kreis(güstigeKante)) {
				i++;
				insgesamtGewicht = insgesamtGewicht + güstigeKante.gewicht;

				liegenInSchnittMengen(güstigeKante);
			}
		}

		System.out.println(i + " insgesamtGewicht:" + insgesamtGewicht);

	}

	public boolean kreis(UngerichtetKante k) {
		boolean flag = false;
		for (HashSet<Integer> s : schnittMengen) {
			if (s.contains(k.vorgängerKonte.id) && s.contains(k.nachgängerKnote.id)) {
				s.add(k.vorgängerKonte.id);
				flag = true;
				break;
			}
		}

		return flag;

	}

	public void liegenInSchnittMengen(UngerichtetKante kante) {
		boolean gefunden = false;

		ArrayList<HashSet<Integer>> connecteHashSetArrayList = new ArrayList<HashSet<Integer>>();

//		System.out.println("kante:"+kante);
		
		
		for (HashSet<Integer> s : schnittMengen) {
			if (s.contains(kante.vorgängerKonte.id) || s.contains(kante.nachgängerKnote.id)) {
				if (s.contains(kante.vorgängerKonte.id)) {
					s.add(kante.nachgängerKnote.id);

				} else {
					s.add(kante.vorgängerKonte.id);
				}
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
			menge.add(kante.vorgängerKonte.id);
			menge.add(kante.nachgängerKnote.id);
			schnittMengen.add(menge);
		}
//		System.out.println("schnittMengen:"+schnittMengen);
	}

	public ArrayList union(ArrayList<Integer> ls, ArrayList<Integer> ls2) {
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(new Integer[ls.size()]));
		Collections.copy(list, ls);
		list.addAll(ls2);
		return list;
	}

}
