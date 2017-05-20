package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> graphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knotenList;
	private ArrayList<Kante> kantenList;
	public ArrayList<Knote> nichtBesuchtKnoten = new ArrayList<Knote>();
	public ArrayList<Knote> besuchtKnoten = new ArrayList<Knote>();
	public HashSet<String> besuchtHamiltonKreis = new HashSet<String>();
	private int knoteAnzahl;
	private float insgesamtGewicht = 0;
	public HashMap<String, Kante> kantenMap;

	boolean gerichtetGraph = false;

	public Graph(String str, boolean gerichtetGraph) throws Exception {

		this.gerichtetGraph = gerichtetGraph;

		GraphParse graphParse = new GraphParse(str, gerichtetGraph);
		knotenList = graphParse.knotenList;
		kantenList = graphParse.kantenList;
		knoteAnzahl = graphParse.knoteAnzahl;
		kantenMap = graphParse.kantenMap;
		// System.out.println("KnoteAnzahl: " + knoteAnzahl);
	}

	/**
	 * BreitenSuche durch Queue Bestimmung der Anzahl von Grapen
	 */
	public void breitenSuche() throws Exception {
		Queue<Knote> queue = new LinkedList<Knote>();

		Knote startKnote = knotenList.get(0);
		queue.add(startKnote);
		besuchtKnoten.add(startKnote);

		GraphCompenent graphCompenent = new GraphCompenent();
		graphCompenentList.add(graphCompenent);

		while (!queue.isEmpty()) {

			// poll 这里可以得到广度优先遍历的顺序
			Knote vater = queue.poll();
			// System.out.println(vater);

			graphCompenent.getKnoten().add(vater);

			for (Knote knote : vater.getNachbarKnotenList()) {
				if (!besuchtKnoten.contains(knote)) {
					queue.add(knote);
					besuchtKnoten.add(knote);
				}
			}

			if (queue.size() == 0 && besuchtKnoten.size() != knotenList.size()) {

				nichtBesuchtKnoten = diffKnotenList(knotenList, besuchtKnoten);

				startKnote = nichtBesuchtKnoten.get(0);
				queue.add(startKnote);
				besuchtKnoten.add(startKnote);

				graphCompenent = new GraphCompenent();
				graphCompenentList.add(graphCompenent);
			}
		}

		// besuchtKonten记录了广度优先遍历的顺序
		// System.out.println(besuchtKonten);

		this.darstellenVonGraphen();
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	/**
	 * TiefenSuche durch Rekution Bestimmung der Anzahl von Grapen
	 */
	public ArrayList<Knote> tiefenSuche(Knote startKnote, ArrayList<Knote> knotenList) throws Exception {

		ArrayList<Knote> reihenFolgeVonKnoten = new ArrayList<Knote>();
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		// iteratiert nichtBesuchtList
		while (nichtBesuchtKnoten.size() != 0) {

			GraphCompenent g = new GraphCompenent();

			graphCompenentList.add(g);

			this.rekusiveTiefenSuche(startKnote, reihenFolgeVonKnoten);

		}

		// this.darstellenVonGraphen();

		return reihenFolgeVonKnoten;
	}

	public void rekusiveTiefenSuche(Knote vaterKnote, ArrayList<Knote> reihenFolgeVonKnoten) throws Exception {
		// System.out.println("vaterKnote:" + vaterKnote);
		nichtBesuchtKnoten.remove(vaterKnote);
		reihenFolgeVonKnoten.add(vaterKnote);

		for (Knote kindKnote : vaterKnote.getNachbarKnotenList()) {

			if (nichtBesuchtKnoten.contains(kindKnote)) {

				this.rekusiveTiefenSuche(kindKnote, reihenFolgeVonKnoten);

			}
		}

	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	/**
	 * 按最小边深度遍历 找MST (Minimal Spanning Tree) Makieren Gewicht von Konte, dann
	 * zällen alle Gewicht von Knoten.
	 */
	public ArrayList<Knote> prim() throws Exception {
		insgesamtGewicht = 0;

		ArrayList<Knote> knoten = new ArrayList<Knote>();

		PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
		for (Knote v : knotenList) {
			v.knoteGewicht = Float.MAX_VALUE;
			priorityQueue.add(v);
		}

		Knote startKnote = priorityQueue.peek();
		startKnote.knoteGewicht = 0;
		// Durch priorityQueue können wir immer die günstigKnote von
		// nachbarKantenList finden.
		while (!priorityQueue.isEmpty()) {
			Knote günstigKnote = priorityQueue.poll();
			knoten.add(günstigKnote);
			// ist NachbarKantenList vorhanden?
			if (günstigKnote.nachbarKantenList != null && günstigKnote.nachbarKantenList.size() > 0) {
				for (Kante kante : günstigKnote.nachbarKantenList) {

					// wenn Gewicht von nachgängerKnote günstig ist, dann
					// überschreiben das aktuelles Gewicht.
					if (priorityQueue.contains(kante.nachgängerKnote)
							&& kante.gewicht < kante.nachgängerKnote.knoteGewicht) {

						// remove und add für Sortierung von akuelle
						// PriorityQueue
						priorityQueue.remove(kante.nachgängerKnote);
						kante.nachgängerKnote.knoteGewicht = kante.gewicht;
						kante.nachgängerKnote.previousKnote = günstigKnote;
						priorityQueue.add(kante.nachgängerKnote);
					}

				}
			}
		}

		for (Knote knote : knotenList) {
			insgesamtGewicht = insgesamtGewicht + knote.knoteGewicht;
		}
		System.out.println("Prim insgesamtGewicht:" + insgesamtGewicht);

		return knoten;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	/**
	 * Günstig kante finden， aber keine Kreis ！
	 */
	public ArrayList<Kante> kruskal() throws Exception {
		insgesamtGewicht = 0;

		ArrayList<Kante> kanten = new ArrayList<Kante>();

		for (Kante güstigeKante : kantenList) {

			if (!kreis(güstigeKante)) {

				insgesamtGewicht = insgesamtGewicht + güstigeKante.gewicht;
				kanten.add(güstigeKante);

				ueberpruefungVonKnotenGruppen(güstigeKante);
			}

		}

		System.out.println("kruskal insgesamtGewicht:" + insgesamtGewicht);
		// System.out.println("kanten:" + kanten);

		return kanten;
	}

	// Ob es Kreis gibt
	public boolean kreis(Kante kante) {
		// Wenn die beide KnotenGruppe gleich sind, dann es Kreis gibt!
		if (kante.vorgängerKonte.getKnoteGruppe() != null && kante.nachgängerKnote.getKnoteGruppe() != null) {
			if (kante.vorgängerKonte.getKnoteGruppe() == kante.nachgängerKnote.getKnoteGruppe()) {
				return true;
			}
		}

		return false;
	}

	int knoteGruppeId = 0;

	// KnoteGruppe von Konte erstellen und überprüfen.
	public void ueberpruefungVonKnotenGruppen(Kante kante) {

		// Knote wurde schon noch in keine Kontegruppe zugeordnet
		if (!kante.vorgängerKonte.hatKnoteGruppe() && !kante.nachgängerKnote.hatKnoteGruppe()) {

			KnoteGruppe knoteGruppe = new KnoteGruppe(knoteGruppeId++);
			kante.vorgängerKonte.setKnoteGruppe(knoteGruppe);
			kante.nachgängerKnote.setKnoteGruppe(knoteGruppe);
			// wenn nachgängerKnote kein KnotenGruppe hat,dann nachgängerKnote
			// in KontenGruppe von vorgängerKonte hinzufügen.
		} else if (kante.vorgängerKonte.hatKnoteGruppe() && !kante.nachgängerKnote.hatKnoteGruppe()) {

			kante.nachgängerKnote.setKnoteGruppe(kante.vorgängerKonte.getKnoteGruppe());
			// wenn vorgängerKonte kein KnotenGruppe hat,dann vorgängerKonte in
			// KontenGruppe von nachgängerKnote hinzufügen.
		} else if (kante.nachgängerKnote.hatKnoteGruppe() && !kante.vorgängerKonte.hatKnoteGruppe()) {

			kante.vorgängerKonte.setKnoteGruppe(kante.nachgängerKnote.getKnoteGruppe());
		} else {
			// zwei KnotenGruppen zusammenfassen!
			kante.nachgängerKnote.setKnoteGruppe(kante.vorgängerKonte.getKnoteGruppe());

		}

	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void darstellenVonGraphen() {
		System.out.println("\nGraphenAnzahl:	" + graphCompenentList.size() + "\n");

		for (int i = 0; i < graphCompenentList.size(); i++) {
			System.out.println("Graph" + i + ":" + graphCompenentList.get(i));
		}
		System.out.println();
	}

	// Diff operation 两个集合 差运算
	public ArrayList<Knote> diffKnotenList(ArrayList<Knote> listA, ArrayList<Knote> listB) {
		ArrayList<Knote> list = new ArrayList<Knote>(Arrays.asList(new Knote[listA.size()]));
		Collections.copy(list, listA);
		list.removeAll(listB);
		return list;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	/**
	 * Hamilton-Rundreise finden， jede Knote als StartKonte versuchen. Suche
	 * nach guestige Gewicht von der Kante
	 */

	public void nearestNeighbor() throws Exception {
		float min = Float.MAX_VALUE;

		for (Knote konte : knotenList) {
			float ergebenis = nearestNeighbor(konte);
			if (ergebenis < min) {
				min = ergebenis;
			}
		}

		System.out.println("Guestige Hamilton-Rundreise von nearestNeighbor:" + min);
	}

	public float nearestNeighbor(Knote startKnote) throws Exception {
		float ergebenis = 0;
		ArrayList<Kante> kanten = new ArrayList<Kante>();

		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		Knote anfangsknote = startKnote;
		Knote endKnote = null;
		nichtBesuchtKnoten.remove(startKnote);

		// Iteratiert nichtBesuchtList
		while (nichtBesuchtKnoten.size() != 0) {

			// Die besucht Kante wegnehmen
			ArrayList<Kante> nichtBesuchtKanten = new ArrayList<Kante>();

			for (Kante kante : startKnote.getNachbarKantenList()) {
				if (nichtBesuchtKnoten.contains(kante.nachgängerKnote)) {
					nichtBesuchtKanten.add(kante);
				}
			}

			// Sortierung
			Collections.sort(nichtBesuchtKanten, new Comparator<Kante>() {
				@Override
				public int compare(Kante Kante1, Kante Kante2) {
					return Kante1.compareTo(Kante2);
				}
			});

			// Die guestigste Kante
			Kante kante = nichtBesuchtKanten.get(0);
			kanten.add(kante);

			ergebenis = ergebenis + kante.gewicht;

			startKnote = kante.nachgängerKnote;
			endKnote = startKnote;
			nichtBesuchtKnoten.remove(startKnote);
		}

		// mit startKonte und letzteKnote bekommen wir die letzte Knate.
		Kante kante = anfangsknote.getKanteMitId(endKnote);

		ergebenis = ergebenis + kante.gewicht;

		kanten.add(kante);

		// System.out.println("startKnoteId:" + ursprungKnote.id + ",ergebenis:"
		// +
		// ergebenis);
		// System.out.println("Hamilton-Rundreise:" + kanten);

		return ergebenis;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public float doppelterBaum() throws Exception {

		// MST von Krusal bekommen
		ArrayList<Kante> kanten = kruskal();
		MST mst = new MST(kanten, knoteAnzahl);

		// das guestigest Ergebenis finden.
		float min = Float.MAX_VALUE;
		for (Knote startKnote : mst.knotenList) {

			// durch Tiefensuche erhalten wir die Reihenfolge von MST
			ArrayList<Knote> knoten = tiefenSuche(startKnote, mst.knotenList);

			float ergebenis = getErgebenisVonKonten(knoten);

			if (ergebenis < min) {
				min = ergebenis;
			}
		}

		System.out.println("Guestige Hamilton-Rundreise von doppelterBaum:" + min);
		return min;

	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	int mal = 0;
	float minimalErgebenis = Float.MAX_VALUE;
	String minimalReihenFolge = null;

	public void bruteForce() throws Exception {
		insgesamtGewicht = 0;
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();

		Knote startKnote = this.knotenList.get(0);

		rekusiveBruteforce(startKnote, knotenReihenfolge);

		System.out.println("min:" + minimalErgebenis + ",mal:" + mal + ",reihenFolge:" + minimalReihenFolge);

	}

	// Tiefensuche durch Rekusion
	public void rekusiveBruteforce(Knote startKnote, ArrayList<Knote> knotenReihenfolge) throws Exception {
		// if (knotenReihenfolge.size() == 2) {
		// if (knotenReihenfolge.get(1).id > knoteAnzahl / 2) {
		// return;
		// }
		// }
		nichtBesuchtKnoten.remove(startKnote);

		knotenReihenfolge.add(startKnote);

		for (Knote knote : startKnote.getNachbarKnotenList()) {
			if (nichtBesuchtKnoten.contains(knote)) {
				rekusiveBruteforce(knote, knotenReihenfolge);
			}
		}

		if (knotenReihenfolge.size() == knoteAnzahl) {

			float ergebenis = getErgebenisVonKonten(knotenReihenfolge);

			if (ergebenis < minimalErgebenis) {
				minimalErgebenis = ergebenis;
				minimalReihenFolge = knotenReihenfolge.toString();
			}

			mal++;

			// System.out.println(getRekusiveZeichen(knotenReihenfolge.size()) +
			// ",mal:" + mal+ ",ergebenis:" + ergebenis+"," +
			// knotenReihenfolge.toString() + ",min:" + minimalErgebenis );
		}

		nichtBesuchtKnoten.add(startKnote);
		knotenReihenfolge.remove(startKnote);

	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void branchUndBound() throws Exception {

		insgesamtGewicht = 0;
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();

		Knote startKnote = this.knotenList.get(0);

		rekusiveBranchUndBound(startKnote, knotenReihenfolge);

		System.out.println("min:" + minimalErgebenis + ",mal:" + mal + ",reihenFolge:" + minimalReihenFolge);
	}

	// Tiefensuche durch Rekusion
	public void rekusiveBranchUndBound(Knote startKnote, ArrayList<Knote> knotenReihenfolge) throws Exception {

		float checkErgebenis = getErgebenisVonKonten(knotenReihenfolge);
		if (checkErgebenis > minimalErgebenis) {
			// schneiden ab
			return;
		}

		nichtBesuchtKnoten.remove(startKnote);

		knotenReihenfolge.add(startKnote);

		for (Knote knote : startKnote.getNachbarKnotenList()) {
			if (nichtBesuchtKnoten.contains(knote)) {
				rekusiveBranchUndBound(knote, knotenReihenfolge);
			}
		}

		// Ein Mal durchgeführt. für 10 Knoten insgesamt 3628800 Möglichkeit
		// muss man durchführen
		if (knotenReihenfolge.size() == knoteAnzahl) {

			float ergebenis = getErgebenisVonKonten(knotenReihenfolge);

			if (ergebenis < minimalErgebenis) {
				minimalErgebenis = ergebenis;
				minimalReihenFolge = knotenReihenfolge.toString();
				// System.out.println(minimalErgebenis);
			}

			mal++;

			// System.out.println(getRekusiveZeichen(knotenReihenfolge.size()) +
			// ",mal:" + mal+ ",ergebenis:" + ergebenis+"," +
			// knotenReihenfolge.toString() + ",min:" + minimalErgebenis );
		}

		nichtBesuchtKnoten.add(startKnote);
		knotenReihenfolge.remove(startKnote);

	}

	// bekommen Ergebenis von Kanten der Konten,
	public float getErgebenisVonKonten(ArrayList<Knote> knotenliste) {
		float ergebenis = 0;

		if (knotenliste.size() > 1) {
			Knote previousKnote = knotenliste.get(knotenliste.size() - 1);
			for (Knote konte : knotenliste) {

				Kante kante = previousKnote.getKanteMitId(konte);
				ergebenis = ergebenis + kante.gewicht;
				previousKnote = konte;
			}
		}

		return ergebenis;
	}

	public String getRekusiveZeichen(int i) {
		StringBuffer s = new StringBuffer();
		for (int j = 0; j < i; j++) {
			s.append("----");
		}
		return s.toString();
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	//druch priorityQueue die güstige Kante finden. ähnlich wie Prim.
	public ArrayList<Knote> dijkstra(int startKnoteId, int endKnoteId) throws Exception {

		insgesamtGewicht = 0;
		ArrayList<Knote> knoten = new ArrayList<Knote>();

		PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
		for (Knote v : knotenList) {
			v.knoteGewicht = Float.MAX_VALUE;
			priorityQueue.add(v);
		}

		Knote startKnote = knotenList.get(startKnoteId);
		startKnote.knoteGewicht = 0;
		priorityQueue.remove(startKnote);
		priorityQueue.add(startKnote);
		while (!priorityQueue.isEmpty()) {
			Knote günstigKnote = priorityQueue.poll();
			knoten.add(günstigKnote);

			if (günstigKnote.nachbarKantenList != null && günstigKnote.nachbarKantenList.size() > 0) {
				for (Kante kante : günstigKnote.nachbarKantenList) {

					if (priorityQueue.contains(kante.nachgängerKnote)) {

						float aktuellGewicht = kante.gewicht + günstigKnote.knoteGewicht;

						if (aktuellGewicht < kante.nachgängerKnote.knoteGewicht) {

							priorityQueue.remove(kante.nachgängerKnote);
							kante.nachgängerKnote.knoteGewicht = aktuellGewicht;
							kante.nachgängerKnote.previousKnote = günstigKnote;

							priorityQueue.add(kante.nachgängerKnote);
						}
					}
				}
			}
		}

		Knote endKnote = knotenList.get(endKnoteId);
		System.out.println("dijkstra	:" + startKnote + "--" + endKnote + "	Länge:	" + endKnote.knoteGewicht);

		return knoten;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void mooreBellmanFord(int startKnoteId, int endKnoteId) throws Exception {

		for (Knote v : knotenList) {
			if (v.id == startKnoteId) {
				v.knoteGewicht = 0;
			} else {
				v.knoteGewicht = Float.MAX_VALUE;
			}
		}

		ArrayList<Kante> negativZykel = null; 
		
		//Anzahl von Interation ist Anzahl von Konten
		for (int i = 0; i < knotenList.size(); i++) {
			if (i < knotenList.size() - 1) {
				mooreBellmanFordSchleife(false);
			} else {
				negativZykel = mooreBellmanFordSchleife(true);
			}
		}

		Knote endKnote = knotenList.get(endKnoteId);

		if(negativZykel.size()>0){
			System.out.println("BellmanFord	:" + startKnoteId + "--" + endKnoteId + "	kanten:"+negativZykel+" in dem negativen Zyklus");
		}else{
			System.out.println("BellmanFord	:" + startKnoteId + "--" + endKnoteId + "	Länge:	" + endKnote.knoteGewicht);
			
		}

	}

	public ArrayList<Kante> mooreBellmanFordSchleife(boolean letzteMal) throws Exception {
		ArrayList<Kante> negativZykel = new ArrayList<Kante>(); 
		for (Kante kante : kantenList) {
			Knote vorgängerKonte = kante.vorgängerKonte;
			Knote nachgängerKnote = kante.nachgängerKnote;
			if (vorgängerKonte.knoteGewicht + kante.gewicht < nachgängerKnote.knoteGewicht) {
				nachgängerKnote.knoteGewicht = vorgängerKonte.knoteGewicht + kante.gewicht;
				nachgängerKnote.previousKnote = vorgängerKonte;
				
				//Wenn in der letzte Interation es immer noch eine Änderung gibt,dann gibt es einen negativen Zyklus
				if(letzteMal){
//					System.out.println("kante:"+kante+" in dem negativen Zyklus");
					negativZykel.add(kante);
				}
			}

		}
		
		return negativZykel;

	}

}
