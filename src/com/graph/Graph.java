package com.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> GraphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knotenList;
	private ArrayList<UngerichtetKante> kantenList;
	private float ergebnis = 0;

	public Queue<Integer> queue = new LinkedList<Integer>();
	public HashSet<Integer> nichtBesuchtKonten = new HashSet<Integer>();
	public HashSet<Knote> besuchtKonten = new HashSet<Knote>();
	// public HashSet<Integer> besuchtKontenInt = new HashSet<Integer>();

	private HashMap<Integer, HashSet<Integer>> mengeMap = new HashMap<Integer, HashSet<Integer>>();
//	private ArrayList<HashSet<Integer>> schnittMengen = new ArrayList<HashSet<Integer>>();
	private HashSet<UngerichtetKante> schnittMenge = new HashSet<UngerichtetKante>();

	public Graph(String str) throws Exception {

		GraphParse graphParse = new GraphParse(str);

		knotenList = graphParse.knotenList;
		kantenList = graphParse.kantenList;

	}

	public void breitenSuche() throws Exception {

		// Initialisiereung
		nichtBesuchtKonten.clear();
		for (int i = 0; i < knotenList.size(); i++) {
			nichtBesuchtKonten.add(i);
		}

		// startKnote auswälen
		int startKonte = (Integer) nichtBesuchtKonten.toArray()[0];
		queue.add(startKonte);

		GraphCompenent graphCompenent = new GraphCompenent();
		GraphCompenentList.add(graphCompenent);

		while (queue.size() != 0) {

			Integer vaterKonte = queue.poll();
			nichtBesuchtKonten.remove(vaterKonte);

			graphCompenent.getKnoten().add(vaterKonte);

			Knote vater = knotenList.get(vaterKonte);

			ArrayList<Integer> kindList = vater.getNachbarKnotenList();

			for (int i = 0; i < kindList.size(); i++) {
				if (nichtBesuchtKonten.contains(kindList.get(i))) {
					queue.add(kindList.get(i));
					nichtBesuchtKonten.remove(kindList.get(i));
				}
			}

			// neu GraphCompenent finden
			if (queue.size() == 0 && nichtBesuchtKonten.size() != 0) {
				int neuStartKonte = (Integer) nichtBesuchtKonten.toArray()[0];
				queue.add(neuStartKonte);

				graphCompenent = new GraphCompenent();
				GraphCompenentList.add(graphCompenent);
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

			GraphCompenentList.add(g);

			this.rekusiveTiefenSuche(startKnote, g);

		}

		this.darstellenVonGraphen();
	}

	public void rekusiveTiefenSuche(int vaterKnote, GraphCompenent graph) throws Exception {
		// System.out.println("vaterKnote:" + vaterKnote);
		nichtBesuchtKonten.remove(vaterKnote);
		graph.getKnoten().add(vaterKnote);

		Knote vater = knotenList.get(vaterKnote);

		for (int i = 0; i < vater.getNachbarKnotenList().size(); i++) {

			int kindKnote = vater.getNachbarKnotenList().get(i);
			if (nichtBesuchtKonten.contains(kindKnote)) {

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

	long totalTime = 0;

	// 切里找最小的边
	public void prim() throws Exception {

		Knote minimalKnote = knotenList.get(0);
		besuchtKonten.add(minimalKnote);
		createSchnittMenge(minimalKnote);

		while (schnittMenge.size() > 0) {

			UngerichtetKante minimalKante = getMinimalKanteVonSchinnt();

			ergebnis = ergebnis + minimalKante.gewicht;

			minimalKnote = knotenList.get(minimalKante.kindKonte);
			// System.out.println(minimalKnote.id);
			// System.out.println("minimalKnote:"+minimalKnote);

			besuchtKonten.add(minimalKnote);
			createSchnittMenge(minimalKnote);
		}

		System.out.println("Zeit：		" + (totalTime) / (1000.0) + "s");

		System.out.println("ergebnis:" + ergebnis);
	}

	// 有向图
	public void createSchnittMenge(Knote knote) {
		for (UngerichtetKante kante : knote.getNachbarKantenList()) {

			Knote nachgängerKnote = knotenList.get(kante.kindKonte);

			if (!besuchtKonten.contains(nachgängerKnote)) {
				schnittMenge.add(kante);
			} else {
				schnittMenge.remove(kante);
			}
		}

	}

	public UngerichtetKante getMinimalKanteVonSchinnt() {

		ArrayList<UngerichtetKante> kanten = new ArrayList<UngerichtetKante>(schnittMenge);
		long startTime = System.currentTimeMillis();

		Collections.sort(kanten, new Comparator<UngerichtetKante>() {
			@Override
			public int compare(UngerichtetKante Kante1, UngerichtetKante Kante2) {
				return Kante1.compareTo(Kante2);
			}
		});

		long endTime = System.currentTimeMillis();
		totalTime = totalTime + (endTime - startTime);

		return kanten.get(0);
	}


	// 按最小的边分配，不要有圈
	public void kruskal() throws Exception {

//		schnittMengen = new ArrayList<HashSet<Integer>>();

		
		int i = 0;
		for (UngerichtetKante güstigeKante : kantenList) {

			if (!kreis(güstigeKante)) {
//				System.out.println(güstigeKante);
				i++;
				ergebnis = ergebnis + güstigeKante.gewicht;
				liegenInSchnittMengen(güstigeKante);
			}
		}

		System.out.println(i+" ergebnis:" + ergebnis);

	}

	public boolean kreis(UngerichtetKante k) {
		
		HashSet<Integer> s = mengeMap.get(k.rootKonte);
		HashSet<Integer> s2 = mengeMap.get(k.kindKonte);
		if(s!=null&&s2!=null&&s.hashCode()==s2.hashCode()){
			
			return true;
		}else{
			
			return false;
		}

	}

	public void liegenInSchnittMengen(UngerichtetKante k) {
		
		
		HashSet<Integer> hashSet = mengeMap.get(k.rootKonte);
		if (hashSet == null) {
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(k.rootKonte);
			s.add(k.kindKonte);
			mengeMap.put(k.rootKonte, s);
			mengeMap.put(k.kindKonte, s);
		} else {
			hashSet.add(k.rootKonte);
			hashSet.add(k.kindKonte);
			

			HashSet<Integer> hashSet2 = mengeMap.get(k.kindKonte);
			if (hashSet2 != null) {
				if (hashSet.size() >= hashSet2.size()) {
					hashSet.addAll(hashSet2);
					for (Object i : hashSet2.toArray()) {
						mengeMap.put((Integer) i, hashSet);
					}
				}else{
					hashSet2.addAll(hashSet);
					for (Object i : hashSet.toArray()) {
						mengeMap.put((Integer) i, hashSet2);
					}
					
				}
			}else{
				mengeMap.put(k.kindKonte, hashSet);
			}

		}

//		System.out.println(mengeMap);
//		System.out.println();
	}

}
