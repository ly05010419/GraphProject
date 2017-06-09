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

public class Algorithmus {
	public ArrayList<ArrayList<Knote>> graphCompenentList = new ArrayList<ArrayList<Knote>>();

	public ArrayList<Knote> nichtBesuchtKnoten = new ArrayList<Knote>();
	public ArrayList<Knote> besuchtKnoten = new ArrayList<Knote>();
	public HashSet<String> besuchtHamiltonKreis = new HashSet<String>();
	private float insgesamtGewicht = 0;
	private float insgesamtFluswert = 0;
	public Graph graph;
	boolean gerichtetGraph = false;

	public Algorithmus(String str) throws Exception {
		this.graph = new Graph(str, false, false);
	}

	public Algorithmus(String str, boolean gerichtetGraph) throws Exception {

		this.gerichtetGraph = gerichtetGraph;

		this.graph = new Graph(str, gerichtetGraph, false);
	}

	public Algorithmus(String str, boolean gerichtetGraph, boolean mitBalance) throws Exception {

		this.gerichtetGraph = gerichtetGraph;

		this.graph = new Graph(str, gerichtetGraph, mitBalance);
	}

	/**
	 * BreitenSuche durch Queue Bestimmung der Anzahl von Grapen
	 */
	public void breitenSuche() throws Exception {
		Queue<Knote> queue = new LinkedList<Knote>();

		Knote startKnote = graph.knotenList.get(0);
		queue.add(startKnote);
		besuchtKnoten.add(startKnote);

		ArrayList<Knote> graphCompenent = new ArrayList<Knote>();
		graphCompenentList.add(graphCompenent);

		while (!queue.isEmpty()) {

			// poll 这里可以得到广度优先遍历的顺序
			Knote vater = queue.poll();
			// System.out.println(vater);

			graphCompenent.add(vater);

			for (Knote knote : vater.getNachbarKnotenList()) {
				if (!besuchtKnoten.contains(knote)) {
					queue.add(knote);
					besuchtKnoten.add(knote);
				}
			}

			// 多个图的情况 计算一共有几个图
			if (queue.size() == 0 && besuchtKnoten.size() != graph.knotenList.size()) {

				nichtBesuchtKnoten = diffKnotenList(graph.knotenList, besuchtKnoten);

				startKnote = nichtBesuchtKnoten.get(0);
				queue.add(startKnote);
				besuchtKnoten.add(startKnote);

				graphCompenent = new ArrayList<Knote>();
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

			ArrayList<Knote> g = new ArrayList<Knote>();

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
		for (Knote v : graph.knotenList) {
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

		for (Knote knote : graph.knotenList) {
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

		for (Kante güstigeKante : graph.kantenList) {

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

		for (Knote konte : graph.knotenList) {
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
		for (Knote konte : graph.knotenList) {
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
		MST mst = new MST(kanten, this.graph.knoteAnzahl);

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
		for (Knote konte : graph.knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();

		Knote startKnote = this.graph.knotenList.get(0);

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

		if (knotenReihenfolge.size() == this.graph.knoteAnzahl) {

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
		for (Knote konte : graph.knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();

		Knote startKnote = this.graph.knotenList.get(0);

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
		if (knotenReihenfolge.size() == this.graph.knoteAnzahl) {

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

	// public String getRekusiveZeichen(int i) {
	// StringBuffer s = new StringBuffer();
	// for (int j = 0; j < i; j++) {
	// s.append("----");
	// }
	// return s.toString();
	// }

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void dijkstra(int startKnoteId, int endKnoteId) throws Exception {

		insgesamtGewicht = 0;

		PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
		for (Knote v : graph.knotenList) {
			v.knoteGewicht = Float.MAX_VALUE;
			priorityQueue.add(v);
		}

		Knote startKnote = graph.knotenList.get(startKnoteId);
		startKnote.knoteGewicht = 0;

		priorityQueue.remove(startKnote);
		priorityQueue.add(startKnote);

		while (!priorityQueue.isEmpty()) {
			Knote günstigKnote = priorityQueue.poll();

			if (günstigKnote.id == endKnoteId) {
				break;
			}

			for (Kante kante : günstigKnote.nachbarKantenList) {

				if (priorityQueue.contains(kante.nachgängerKnote)) {

					float aktuellGewicht = kante.gewicht + günstigKnote.knoteGewicht;

					if (aktuellGewicht < kante.nachgängerKnote.knoteGewicht) {

						kante.nachgängerKnote.knoteGewicht = aktuellGewicht;
						kante.nachgängerKnote.previousKnote = günstigKnote;

						priorityQueue.remove(kante.nachgängerKnote);
						priorityQueue.add(kante.nachgängerKnote);
					}
				}
			}

		}

		Knote endKnote = graph.knotenList.get(endKnoteId);
		System.out.println("dijkstra	:" + startKnote + "--" + endKnote + "	Länge:	" + endKnote.knoteGewicht);

	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void mooreBellmanFord(int startKnoteId, int endKnoteId) throws Exception {

		for (Knote v : graph.knotenList) {
			if (v.id == startKnoteId) {
				v.knoteGewicht = 0;
			} else {
				v.knoteGewicht = Float.MAX_VALUE;
			}
		}

		for (int i = 0; i < graph.knotenList.size() - 1; i++) {
			mooreBellmanFordSchleife();
		}

		ArrayList<Kante> negativZykel = mooreBellmanFordSchleife();

		if (negativZykel.size() > 0) {
			System.out.println("BellmanFord	:" + startKnoteId + "--" + endKnoteId + "	kanten:" + negativZykel
					+ " in dem negativen Zyklus");
		} else {
			Knote endKnote = graph.knotenList.get(endKnoteId);
			System.out.println(
					"BellmanFord	:" + startKnoteId + "--" + endKnoteId + "	Länge:	" + endKnote.knoteGewicht);

		}

	}

	public ArrayList<Kante> mooreBellmanFordSchleife() throws Exception {
		ArrayList<Kante> negativZykel = new ArrayList<Kante>();
		for (Kante kante : graph.kantenList) {
			Knote vorgängerKonte = kante.vorgängerKonte;
			Knote nachgängerKnote = kante.nachgängerKnote;
			if (vorgängerKonte.knoteGewicht + kante.gewicht < nachgängerKnote.knoteGewicht) {
				nachgängerKnote.knoteGewicht = vorgängerKonte.knoteGewicht + kante.gewicht;
				nachgängerKnote.previousKnote = vorgängerKonte;
				negativZykel.add(kante);
			}
		}

		return negativZykel;

	}

	/**
	 * ------------------------------------------------------------------------------------
	 */
	public void fordFulkerson(int startKnoteId, int endKnoteId) throws Exception {

		int i = 0;
		Fluss fluss = flussSucheDurchBreitenSuche(startKnoteId, endKnoteId);

		while (fluss != null) {

			fluss = flussSucheDurchBreitenSuche(startKnoteId, endKnoteId);

			i++;
		}

		System.out.println(i + " Fluss gefunden und InsgesamtFluswert:" + insgesamtFluswert);
	}

	public Fluss flussSucheDurchBreitenSuche(int startKnoteId, int endKnoteId) throws Exception {

		besuchtKnoten.clear();

		Queue<Knote> queue = new LinkedList<Knote>();

		Knote startKnote = graph.knotenList.get(startKnoteId);
		queue.add(startKnote);
		besuchtKnoten.add(startKnote);

		while (!queue.isEmpty()) {

			Knote vater = queue.poll();

			for (Kante kante : vater.getNachbarKantenList()) {
				if (!besuchtKnoten.contains(kante.nachgängerKnote)) {
					queue.add(kante.nachgängerKnote);
					besuchtKnoten.add(kante.nachgängerKnote);

					kante.nachgängerKnote.previousKnote = kante.vorgängerKonte;
				}
			}
		}

		Knote endKonte = graph.knotenList.get(endKnoteId);

		// find Fluss von 7 nach 0, durch previousKnote,z.B 7-4,4-3,3-0 diese
		// drei KantenReihfolge
		Fluss fluss = getFlussDurchEndKnote(endKonte);

		if (fluss != null) {

			insgesamtFluswert += fluss.flussWert;

			createResidualGraph(fluss);
		}

		return fluss;
	}

	public Fluss getFlussDurchEndKnote(Knote aktuellKnote) {

		Fluss fluss = new Fluss();

		Knote previousKnote = aktuellKnote.previousKnote;

		float minimalFluswert = Float.MAX_VALUE;
		while (previousKnote != null) {

			// kante von previousKnote nach aktuellKnote
			Kante kante = previousKnote.getKanteMitId(aktuellKnote);

			if (kante != null) {
				fluss.kantenList.add(kante);

				if (kante.gewicht < minimalFluswert) {
					minimalFluswert = kante.gewicht;
				}

				aktuellKnote = previousKnote;
				previousKnote = previousKnote.previousKnote;

			} else {
				// keine Kante von previousKnote nach aktuellKnote existiert.
				// fertig! alle Fluss gefunden!
				return null;
			}
		}

		fluss.flussWert = minimalFluswert;

		return fluss;
	}

	public void createResidualGraph(Fluss fluss) {
		for (Kante kante : fluss.kantenList) {

			// alte Kante aktualisiert
			kante.gewicht = kante.gewicht - fluss.flussWert;
			// neu Kante erstellen
			this.graph.createRueckKnate(kante.nachgängerKnote, kante.vorgängerKonte, fluss.flussWert);

			if (kante.gewicht == 0) {
				kante.vorgängerKonte.removeKnoteUndKante(kante.nachgängerKnote);
			}
		}
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */
	public void cycleCanceling() throws Exception {

		System.out.println(this.graph.knotenList);
		System.out.println(this.graph.kantenList);
	}

}
