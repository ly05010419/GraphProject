package com.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> GraphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knoten;
	private ArrayList<UngerichtetKante> kanten;
	private float ergebnis = 0;

	public Queue<Integer> queue = new LinkedList<Integer>();
	public HashSet<Integer> nichtBesuchtKonten = new HashSet<Integer>();
	public HashSet<Knote> besuchtKonten = new HashSet<Knote>();
	// public HashSet<Integer> besuchtKontenInt = new HashSet<Integer>();

	private ArrayList<UngerichtetKante> besuchtKanten = new ArrayList<UngerichtetKante>();
	private ArrayList<HashSet<Integer>> schnittMengen = new ArrayList<HashSet<Integer>>();
	private HashSet<UngerichtetKante> schnittMenge = new HashSet<UngerichtetKante>();

	public Graph(String str) throws Exception {
		
		long startTime=System.currentTimeMillis();
		GraphParse graphParse = new GraphParse(str);
		
		
		long endTime=System.currentTimeMillis();   
		System.out.println("parseKonte Zeit：		"+(endTime-startTime)/(1000.0)+"s");  	
		knoten = graphParse.parseKonte();
		kanten = graphParse.kanten;

	}

	public void breitenSuche() throws Exception {

		// Initialisiereung
		nichtBesuchtKonten.clear();
		for (int i = 0; i < knoten.size(); i++) {
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

			Knote vater = knoten.get(vaterKonte);

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
		for (int i = 0; i < knoten.size(); i++) {
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

		Knote vater = knoten.get(vaterKnote);

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

		Knote minimalKnote = knoten.get(0);
		besuchtKonten.add(minimalKnote);
		createSchnittMenge(minimalKnote);

		while (schnittMenge.size() > 0) {

			UngerichtetKante minimalKante = getMinimalKanteVonSchinnt();

			ergebnis = ergebnis + minimalKante.gewicht;

			minimalKnote = knoten.get(minimalKante.kindKonte);
//			System.out.println(minimalKnote.id);
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

			Knote nachgängerKnote = knoten.get(kante.kindKonte);

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
	
	
//	
//	public static void bubbleSort(ArrayList<UngerichtetKante> kanten)
//	{
//		UngerichtetKante temp = null;
//	    int size = kanten.size();
//	    for(int i = 0 ; i < size-1; i ++)
//	    {
//	    for(int j = 0 ;j < size-1-i ; j++)
//	    {
//	        if(kanten.get(j).gewicht > kanten.get(j+1).gewicht)  //交换两数位置
//	        {
//	        temp = kanten.get(j);
//	        kanten.set(j, kanten.get(j+1));
//	        kanten.set(j+1,temp);
//	        }
//	    }
//	    }
//	}
	
	
	

	// 按最小的边分配，不要有圈
	public void kruskal() throws Exception {

		schnittMengen = new ArrayList<HashSet<Integer>>();

		for (UngerichtetKante güstigeKante : kanten) {

			if (!kreis(güstigeKante)) {
				ergebnis = ergebnis + güstigeKante.gewicht;
				liegenInSchnittMengen(güstigeKante);
			}
		}

		System.out.println("ergebnis:" + ergebnis);
		
	}

	public boolean kreis(UngerichtetKante k) {
		boolean flag = false;
		for (HashSet<Integer> s : schnittMengen) {
			if (s.contains(k.rootKonte) && s.contains(k.kindKonte)) {
				s.add(k.rootKonte);
				flag = true;
			}
		}

		return flag;

	}

	public void liegenInSchnittMengen(UngerichtetKante k) {
		boolean flag = false;

		ArrayList<HashSet<Integer>> connecteHashSetArrayList = new ArrayList<HashSet<Integer>>();

		for (HashSet<Integer> s : schnittMengen) {
			if (s.contains(k.rootKonte) || s.contains(k.kindKonte)) {
				s.add(k.rootKonte);
				s.add(k.kindKonte);
				flag = true;
				connecteHashSetArrayList.add(s);
			}
		}

		if (connecteHashSetArrayList.size() == 2) {

			HashSet<Integer> menge = new HashSet<Integer>();
			for (HashSet<Integer> s : connecteHashSetArrayList) {
				schnittMengen.remove(s);
				for (Object i : s.toArray()) {

					menge.add((Integer) i);
				}
			}
			schnittMengen.add(menge);

		}

		if (!flag) {
			HashSet<Integer> menge = new HashSet<Integer>();
			menge.add(k.rootKonte);
			menge.add(k.kindKonte);
			schnittMengen.add(menge);
		}
		// System.out.println("baumMenge"+baumMenge);
	}

}
