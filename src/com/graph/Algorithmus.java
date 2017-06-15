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

	/**
	 * BreitenSuche durch Queue Bestimmung der Anzahl von Grapen
	 */
	public void breitenSuche(Graph graph) throws Exception {
		reset();

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

	// Diff operation 两个集合 差运算
	public ArrayList<Knote> diffKnotenList(ArrayList<Knote> listA, ArrayList<Knote> listB) {
		ArrayList<Knote> list = new ArrayList<Knote>(Arrays.asList(new Knote[listA.size()]));
		Collections.copy(list, listA);
		list.removeAll(listB);
		return list;
	}

	/**
	 * TiefenSuche durch Rekution Bestimmung der Anzahl von Grapen
	 */

	public void tiefenSuche(Graph graph) throws Exception {
		reset();
		tiefenSuche(graph.knotenList.get(0), graph.knotenList);
	}

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

		this.darstellenVonGraphen();

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
	 * 按最小边深度遍历 找MST (Minimal Spanning Tree) Makieren Gewicht von Konte, dann
	 * zällen alle Gewicht von Knoten.
	 */
	public ArrayList<Knote> prim(Graph graph) throws Exception {
		reset();

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
							&& kante.kosten < kante.nachgängerKnote.knoteGewicht) {

						// remove und add für Sortierung von akuelle
						// PriorityQueue
						priorityQueue.remove(kante.nachgängerKnote);
						kante.nachgängerKnote.knoteGewicht = kante.kosten;
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
	 * Günstig kante finden， aber keine Kreis ！
	 */
	public ArrayList<Kante> kruskal(Graph graph) throws Exception {
		reset();

		ArrayList<Kante> kanten = new ArrayList<Kante>();

		for (Kante güstigeKante : graph.kantenList) {

			if (!kreis(güstigeKante)) {

				insgesamtGewicht = insgesamtGewicht + güstigeKante.kosten;
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
	 *
	 * ------------------------------------------------------------------------------------
	 */

	public void darstellenVonGraphen() {
		System.out.println("\nGraphenAnzahl:	" + graphCompenentList.size() + "\n");

		for (int i = 0; i < graphCompenentList.size(); i++) {
			System.out.println("Graph" + i + ":" + graphCompenentList.get(i));
		}
		System.out.println();
	}
	//

	/**
	 * Hamilton-Rundreise finden， jede Knote als StartKonte versuchen. Suche
	 * nach guestige Gewicht von der Kante
	 */

	public void nearestNeighbor(Graph graph) throws Exception {

		reset();

		float min = Float.MAX_VALUE;

		for (Knote konte : graph.knotenList) {
			float ergebenis = nearestNeighbor(konte, graph);
			if (ergebenis < min) {
				min = ergebenis;
			}
		}

		System.out.println("Guestige Hamilton-Rundreise von nearestNeighbor:" + min);
	}

	public float nearestNeighbor(Knote startKnote, Graph graph) throws Exception {
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

			ergebenis = ergebenis + kante.kosten;

			startKnote = kante.nachgängerKnote;
			endKnote = startKnote;
			nichtBesuchtKnoten.remove(startKnote);
		}

		// mit startKonte und letzteKnote bekommen wir die letzte Knate.
		Kante kante = anfangsknote.getKanteMitId(endKnote);

		ergebenis = ergebenis + kante.kosten;

		kanten.add(kante);

		// System.out.println("startKnoteId:" + ursprungKnote.id + ",ergebenis:"
		// +
		// ergebenis);
		// System.out.println("Hamilton-Rundreise:" + kanten);

		return ergebenis;
	}

	/**
	 *
	 * ------------------------------------------------------------------------------------
	 */

	public float doppelterBaum(Graph graph) throws Exception {
		reset();
		// MST von Krusal bekommen
		ArrayList<Kante> kanten = kruskal(graph);
		MST mst = new MST(kanten, graph.knoteAnzahl);

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
	 *
	 * ------------------------------------------------------------------------------------
	 */

	int mal = 0;
	float minimalErgebenis = Float.MAX_VALUE;
	String minimalReihenFolge = null;

	public void bruteForce(Graph graph) throws Exception {
		insgesamtGewicht = 0;
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : graph.knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();

		Knote startKnote = graph.knotenList.get(0);

		rekusiveBruteforce(startKnote, knotenReihenfolge, graph);

		System.out.println("min:" + minimalErgebenis + ",mal:" + mal + ",reihenFolge:" + minimalReihenFolge);

	}

	// Tiefensuche durch Rekusion
	public void rekusiveBruteforce(Knote startKnote, ArrayList<Knote> knotenReihenfolge, Graph graph) throws Exception {
		// if (knotenReihenfolge.size() == 2) {
		// if (knotenReihenfolge.get(1).id > knoteAnzahl / 2) {
		// return;
		// }
		// }
		nichtBesuchtKnoten.remove(startKnote);

		knotenReihenfolge.add(startKnote);

		for (Knote knote : startKnote.getNachbarKnotenList()) {
			if (nichtBesuchtKnoten.contains(knote)) {
				rekusiveBruteforce(knote, knotenReihenfolge, graph);
			}
		}

		if (knotenReihenfolge.size() == graph.knoteAnzahl) {

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
	 *
	 * ------------------------------------------------------------------------------------
	 */

	public void branchUndBound(Graph graph) throws Exception {

		insgesamtGewicht = 0;
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : graph.knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();

		Knote startKnote = graph.knotenList.get(0);

		rekusiveBranchUndBound(startKnote, knotenReihenfolge, graph);

		System.out.println("min:" + minimalErgebenis + ",mal:" + mal + ",reihenFolge:" + minimalReihenFolge);
	}

	// Tiefensuche durch Rekusion
	public void rekusiveBranchUndBound(Knote startKnote, ArrayList<Knote> knotenReihenfolge, Graph graph)
			throws Exception {

		float checkErgebenis = getErgebenisVonKonten(knotenReihenfolge);
		if (checkErgebenis > minimalErgebenis) {
			// schneiden ab
			return;
		}

		nichtBesuchtKnoten.remove(startKnote);

		knotenReihenfolge.add(startKnote);

		for (Knote knote : startKnote.getNachbarKnotenList()) {
			if (nichtBesuchtKnoten.contains(knote)) {
				rekusiveBranchUndBound(knote, knotenReihenfolge, graph);
			}
		}

		// Ein Mal durchgeführt. für 10 Knoten insgesamt 3628800 Möglichkeit
		// muss man durchführen
		if (knotenReihenfolge.size() == graph.knoteAnzahl) {

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
				ergebenis = ergebenis + kante.kosten;
				previousKnote = konte;
			}
		}

		return ergebenis;
	}

	//
	//
	//
	/**
	 * ------------------------------------------------------------------------------------
	 */

	public float dijkstra(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		reset();

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

					float aktuellGewicht = kante.kosten + günstigKnote.knoteGewicht;

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

		return endKnote.knoteGewicht;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public float mooreBellmanFord(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		for (Knote v : graph.knotenList) {
			if (v.id == startKnoteId) {
				v.knoteGewicht = 0;
			} else {
				v.knoteGewicht = Float.MAX_VALUE;
			}
		}

		for (int i = 0; i < graph.knotenList.size() - 1; i++) {
			mooreBellmanFordSchleife(graph);
		}

		ArrayList<Kante> negativZykel = mooreBellmanFordSchleife(graph);

		if (negativZykel.size() > 0) {
			System.out.println("BellmanFord	:" + startKnoteId + "--" + endKnoteId + "	kanten:" + negativZykel
					+ " in dem negativen Zyklus");
			return -1;
		} else {
			Knote endKnote = graph.knotenList.get(endKnoteId);
			System.out.println(
					"BellmanFord	:" + startKnoteId + "--" + endKnoteId + "	Länge:	" + endKnote.knoteGewicht);
			return endKnote.knoteGewicht;
		}

	}

	public ArrayList<Kante> mooreBellmanFordSchleife(Graph graph) throws Exception {
		ArrayList<Kante> negativZykel = new ArrayList<Kante>();
		for (Kante kante : graph.kantenList) {
			if (!kante.isAvailable()) {
				continue;
			}
			Knote vorgängerKonte = kante.vorgängerKonte;
			Knote nachgängerKnote = kante.nachgängerKnote;
			if (vorgängerKonte.knoteGewicht + kante.kosten < nachgängerKnote.knoteGewicht) {
				nachgängerKnote.knoteGewicht = vorgängerKonte.knoteGewicht + kante.kosten;
				nachgängerKnote.previousKnote = vorgängerKonte;
				negativZykel.add(kante);
			}
		}

		return negativZykel;
	}

	/**
	 * 通过广度优先算法先谁便找个流 然后使用ResidualGraph不断优化直到找到最大流
	 */
	public ArrayList<Fluss> fordFulkerson(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		reset();

		ArrayList<Fluss> flussList = new ArrayList<Fluss>();

		Fluss fluss = flussSucheDurchBreitenSuche(startKnoteId, endKnoteId, graph);

		while (fluss != null) {

			flussList.add(fluss);
			fluss = flussSucheDurchBreitenSuche(startKnoteId, endKnoteId, graph);
		}

		// System.out.println("Fluss gefunden und InsgesamtFluswert:" +
		// insgesamtFluswert);

		return flussList;
	}

	public Fluss flussSucheDurchBreitenSuche(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		besuchtKnoten.clear();

		Queue<Knote> queue = new LinkedList<Knote>();

		Knote startKnote = graph.knotenList.get(startKnoteId);
		queue.add(startKnote);
		besuchtKnoten.add(startKnote);

		if (graph.mitBalance) {
			if (startKnote.balance == 0) {
				return null;
			}
		}

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
			if (graph.mitBalance) {
				if (fluss.flussWert < startKnote.balance) {
					startKnote.balance = startKnote.balance - fluss.flussWert;
				} else {
					fluss.flussWert = startKnote.balance;
					startKnote.balance = 0;
				}
			}

			insgesamtFluswert += fluss.flussWert;

			createResidualGraph(fluss, graph);
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

				if (kante.getVerfügebarKapazität() < minimalFluswert) {
					minimalFluswert = kante.getVerfügebarKapazität();
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

	public void createResidualGraph(Fluss fluss, Graph graph) {

		for (Kante kante : fluss.kantenList) {

			// alte Kante aktualisiert
			kante.setFlussWert(kante.getFlussWert() + fluss.flussWert);
			// neu Kante erstellen
			graph.createEineKnate(kante.nachgängerKnote, kante.vorgängerKonte, -kante.kosten, kante.getKapazität(),
					kante.getKapazität() - kante.getFlussWert(), true);

			if (kante.getVerfügebarKapazität() == 0) {
				graph.removeKante(kante);
			}
		}
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */
	public void cycleCanceling(Graph graph) throws Exception {

		System.out.println("original Graph:" + graph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		// Erstellen superQuelle und superSenke für mit mehrere Quellen und
		// Senken Graph zu Fluss finden

		Knote superQuelle = erstellenSuperQuelle(graph);
		Knote superSenke = erstellenSuperSenke(graph);

		float balance = superQuelle.balance;

		System.out.println("graph mit SuperQuelle:" + graph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		// durch fordFulkerson fluss finden
		ArrayList<Fluss> flussList = fordFulkerson(superQuelle.id, superSenke.id, graph);

		float maximalFlussWert = getBalanceVonFluss(flussList);

		if (maximalFlussWert < balance) {
			System.out.println("Keine B-Fluss");
			return;
		}

		// Fluss gefunden, fertig brauchen wir nicht mehr superQuelle und
		// superSenke
		removeSuperQuelleUndSenke(graph, superQuelle, superSenke);

		System.out.println("FlussList: " + flussList);
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("ResidualGraph durch fordFulkerson:" + graph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		// Erstellen superKnote,die mit alle andere Knote verbindet, helfen uns
		// zu negative Kreis finden
		Knote superKnote = erstellenSuperKnote(graph);

		System.out.println("ResidualGraph durch fordFulkerson:" + graph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		// finden wir negative Kreis, dann aktualisiert Kante, Die Knaten in
		// gleich Richtung addieren minimale Wert von Kreis, in umgekehrte
		// Richtung subtrahieren.bis keine negative Kreis
		findKreisUndAktualisiertGraph(graph, superKnote.id);

		// brauchen wir nicht mehr SuperKnote, alle negative Kreis haben wir
		// gefunden!
		graph.removeKnoteMitSeinKante(superKnote);

		System.out.println("ResidualGraph durch fordFulkerson:" + graph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		// Kosten rechenen
		rechnenKosten(graph);
	}

	public float getBalanceVonFluss(ArrayList<Fluss> flussList) {

		float balance = 0;
		for (Fluss fluss : flussList) {
			balance += fluss.flussWert;
		}

		return balance;
	}

	public void findKreisUndAktualisiertGraph(Graph graph, int superQuelleId) throws Exception {

		// 只通过修改Knote找到负圈 对边的值没影响
		// negativKreis durch MooreBellmanFord finden
		Kreis negativKreis = findNegativZykeldurchMooreBellmanFord(superQuelleId, graph);

		while (negativKreis != null) {
			System.out.println("negativKreis:" + negativKreis);
			System.out.println("------------------------------------------------------------------------------------");

			// Die Knaten in gleich Richtung addieren minimale Wert von Kreis,
			// in umgekehrte Richtung subtrahieren.
			aktualisertGraph(negativKreis, graph);

			System.out.println("aktualisiertGraph durch negativKreis:" + graph.kantenList);
			System.out.println("------------------------------------------------------------------------------------");

			// erstellen ResidualGraph um negative Kreis weiter finden, bis
			// keine negative Kreis
			createResidualGraph(graph);

			System.out.println("ResidualGraph:" + graph.kantenList);
			System.out.println("------------------------------------------------------------------------------------");

			negativKreis = findNegativZykeldurchMooreBellmanFord(superQuelleId, graph);
		}

	}

	public void rechnenKosten(Graph graph) {
		float kosten = 0;

		for (Kante k : graph.kantenList) {
			if (!k.residualKante) {
				kosten = kosten + k.getFlussWert() * k.kosten;
			}
		}

		System.out.println("kosten:" + kosten);

	}

	public Knote erstellenSuperKnote(Graph graph) {

		Knote superQulle = new Knote(graph.knotenList.size());
		graph.knotenList.add(superQulle);

		for (Knote k : graph.knotenList) {
			graph.createEineKnate(superQulle, k, 1, 1, 0, false);
		}

		return superQulle;
	}

	public Knote erstellenSuperQuelle(Graph graph) {
		Knote superQulle = null;
		ArrayList<Knote> quellenList = new ArrayList<Knote>();
		for (Knote knote : graph.knotenList) {
			if (knote.balance > 0) {
				quellenList.add(knote);
			}
		}

		if (quellenList.size() > 0) {
			superQulle = new Knote(graph.knotenList.size());
			graph.knotenList.add(superQulle);

			for (Knote k : quellenList) {
				superQulle.balance += k.balance;
				graph.createEineKnate(superQulle, k, 0, k.balance, 0, false);
				k.balance = 0;
			}
		}

		return superQulle;
	}

	public Knote erstellenSuperSenke(Graph graph) {
		Knote superSenke = null;

		ArrayList<Knote> senkenList = new ArrayList<Knote>();
		for (Knote knote : graph.knotenList) {
			if (knote.balance < 0) {
				senkenList.add(knote);
			}
		}

		if (senkenList.size() > 0) {
			superSenke = new Knote(graph.knotenList.size());
			graph.knotenList.add(superSenke);

			for (Knote k : senkenList) {
				superSenke.balance += k.balance;

				graph.createEineKnate(k, superSenke, 0, -k.balance, 0, false);
				k.balance = 0;
			}
		}

		return superSenke;
	}

	public void removeSuperQuelleUndSenke(Graph graph, Knote superQuelle, Knote superSenke) {

		if (superQuelle != null) {
			graph.removeKnoteMitSeinKante(superQuelle);
		}

		if (superSenke != null) {
			graph.removeKnoteMitSeinKante(superSenke);
		}

	}

	// 通过N-1次更新遍历边 第N次发现负圈
	// Wie MooreBellmanFord
	public Kreis findNegativZykeldurchMooreBellmanFord(int startKnoteId, Graph graph) throws Exception {

		reset();

		for (Knote v : graph.knotenList) {
			if (v.id == startKnoteId) {
				v.knoteGewicht = 0;
			} else {
				v.knoteGewicht = Float.MAX_VALUE;
			}
		}

		for (int i = 0; i < graph.knotenList.size() - 1; i++) {
			mooreBellmanFordSchleife(graph);
		}

		ArrayList<Kante> negativZykel = mooreBellmanFordSchleife(graph);

		Kreis negativKreis = this.findNegativKreis(graph, negativZykel);

		return negativKreis;

	}

	// Die Kanten, die richtung wie NegativKreis sind, sollen addiert mit
	// NegativKreis.kreisWert, sonst subtrahiert.
	public void aktualisertGraph(Kreis negativKreis, Graph graph) {

		for (int i = 0; i < negativKreis.kantenList.size(); i++) {
			Kante kante = negativKreis.kantenList.get(i);

			kante.setFlussWert(kante.getFlussWert() + negativKreis.kreisWert);

			Kante rueckKante = graph.findKante(kante.nachgängerKnote.id, kante.vorgängerKonte.id);

			if (rueckKante != null) {
				rueckKante.setFlussWert(rueckKante.getFlussWert() - negativKreis.kreisWert);
			}
		}

	}

	// Rückkanten ertellen, und die kanten mit kein VerfügebarKapazität
	// entfernen.
	public void createResidualGraph(Graph graph) {

		for (Kante kante : CloneUtils.clone(graph.kantenList)) {

			if (kante.getFlussWert() > 0) {

				Kante rueckKnate = graph.findKante(kante.nachgängerKnote.id, kante.vorgängerKonte.id);

				if (rueckKnate == null) {

					graph.createEineKnate(kante.nachgängerKnote, kante.vorgängerKonte, -kante.kosten,
							kante.getKapazität(), kante.getKapazität() - kante.getFlussWert(), true);
				}
				if (kante.getVerfügebarKapazität() == 0) {
					graph.removeKante(kante);
				}
			}
		}

	}

	public Kreis findNegativKreis(Graph graph, ArrayList<Kante> kantenInNegativKreis) {

		if (kantenInNegativKreis.size() == 0) {

			return null;
		}

		Kreis kreis = new Kreis();

		Kante kante = kantenInNegativKreis.get(kantenInNegativKreis.size()-1);

		// durch previousKnote immer zurück laufen (N Mal) N Anzahl von Konten
		Knote previousKnote = kante.nachgängerKnote;

		for (int i = 0; i < graph.knotenList.size(); i++) {

			previousKnote = previousKnote.previousKnote;
		}

		ArrayList<Knote> knotenVonKreis = new ArrayList<Knote>();
		// wechle Knoten mehr als einmal besucht, diese Konte bestimmt in Kreis
		for (int i = 0; i < graph.knotenList.size(); i++) {
			if (!knotenVonKreis.contains(previousKnote)) {
				knotenVonKreis.add(previousKnote);
			}
			previousKnote = previousKnote.previousKnote;
		}

		System.out.println("knotenVonKreis :" + knotenVonKreis);

		float minimal = Float.MAX_VALUE;

		// um Kreis mit Kanten zu erstellen, die richtung ist umgekehrt
		Knote startKnote = knotenVonKreis.get(0);
		for (int i = knotenVonKreis.size() - 1; i >= 0; i--) {
			Knote knote = knotenVonKreis.get(i);
			Kante k = graph.findKante(startKnote.id, knote.id);

			if (k.getVerfügebarKapazität() < minimal) {
				minimal = k.getVerfügebarKapazität();
			}

			kreis.kantenList.add(k);
			kreis.kreisWert = minimal;
			startKnote = knote;
		}

		return kreis;
	}

	public void reset() {
		nichtBesuchtKnoten = new ArrayList<Knote>();
		besuchtKnoten = new ArrayList<Knote>();
		besuchtHamiltonKreis = new HashSet<String>();
		insgesamtGewicht = 0;
		insgesamtFluswert = 0;
	}
}
