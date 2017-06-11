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
	//
	//
	// /**
	// * BreitenSuche durch Queue Bestimmung der Anzahl von Grapen
	// */
	// public void breitenSuche() throws Exception {
	// Queue<Knote> queue = new LinkedList<Knote>();
	//
	// Knote startKnote = graph.knotenList.get(0);
	// queue.add(startKnote);
	// besuchtKnoten.add(startKnote);
	//
	// ArrayList<Knote> graphCompenent = new ArrayList<Knote>();
	// graphCompenentList.add(graphCompenent);
	//
	// while (!queue.isEmpty()) {
	//
	// // poll 这里可以得到广度优先遍历的顺序
	// Knote vater = queue.poll();
	// // System.out.println(vater);
	//
	// graphCompenent.add(vater);
	//
	// for (Knote knote : vater.getNachbarKnotenList()) {
	// if (!besuchtKnoten.contains(knote)) {
	// queue.add(knote);
	// besuchtKnoten.add(knote);
	// }
	// }
	//
	// // 多个图的情况 计算一共有几个图
	// if (queue.size() == 0 && besuchtKnoten.size() != graph.knotenList.size())
	// {
	//
	// nichtBesuchtKnoten = diffKnotenList(graph.knotenList, besuchtKnoten);
	//
	// startKnote = nichtBesuchtKnoten.get(0);
	// queue.add(startKnote);
	// besuchtKnoten.add(startKnote);
	//
	// graphCompenent = new ArrayList<Knote>();
	// graphCompenentList.add(graphCompenent);
	// }
	// }
	//
	// // besuchtKonten记录了广度优先遍历的顺序
	// // System.out.println(besuchtKonten);
	//
	// this.darstellenVonGraphen();
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// /**
	// * TiefenSuche durch Rekution Bestimmung der Anzahl von Grapen
	// */
	// public ArrayList<Knote> tiefenSuche(Knote startKnote, ArrayList<Knote>
	// knotenList) throws Exception {
	//
	// ArrayList<Knote> reihenFolgeVonKnoten = new ArrayList<Knote>();
	// // Initialisiereung
	// nichtBesuchtKnoten.clear();
	// for (Knote konte : knotenList) {
	// nichtBesuchtKnoten.add(konte);
	// }
	//
	// // iteratiert nichtBesuchtList
	// while (nichtBesuchtKnoten.size() != 0) {
	//
	// ArrayList<Knote> g = new ArrayList<Knote>();
	//
	// graphCompenentList.add(g);
	//
	// this.rekusiveTiefenSuche(startKnote, reihenFolgeVonKnoten);
	//
	// }
	//
	// // this.darstellenVonGraphen();
	//
	// return reihenFolgeVonKnoten;
	// }
	//
	// public void rekusiveTiefenSuche(Knote vaterKnote, ArrayList<Knote>
	// reihenFolgeVonKnoten) throws Exception {
	// // System.out.println("vaterKnote:" + vaterKnote);
	// nichtBesuchtKnoten.remove(vaterKnote);
	// reihenFolgeVonKnoten.add(vaterKnote);
	//
	// for (Knote kindKnote : vaterKnote.getNachbarKnotenList()) {
	//
	// if (nichtBesuchtKnoten.contains(kindKnote)) {
	//
	// this.rekusiveTiefenSuche(kindKnote, reihenFolgeVonKnoten);
	//
	// }
	// }
	//
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// /**
	// * 按最小边深度遍历 找MST (Minimal Spanning Tree) Makieren Gewicht von Konte, dann
	// * zällen alle Gewicht von Knoten.
	// */
	// public ArrayList<Knote> prim() throws Exception {
	// insgesamtGewicht = 0;
	//
	// ArrayList<Knote> knoten = new ArrayList<Knote>();
	//
	// PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
	// for (Knote v : graph.knotenList) {
	// v.knoteGewicht = Float.MAX_VALUE;
	// priorityQueue.add(v);
	// }
	//
	// Knote startKnote = priorityQueue.peek();
	// startKnote.knoteGewicht = 0;
	// // Durch priorityQueue können wir immer die günstigKnote von
	// // nachbarKantenList finden.
	// while (!priorityQueue.isEmpty()) {
	// Knote günstigKnote = priorityQueue.poll();
	// knoten.add(günstigKnote);
	// // ist NachbarKantenList vorhanden?
	// if (günstigKnote.nachbarKantenList != null &&
	// günstigKnote.nachbarKantenList.size() > 0) {
	// for (Kante kante : günstigKnote.nachbarKantenList) {
	//
	// // wenn Gewicht von nachgängerKnote günstig ist, dann
	// // überschreiben das aktuelles Gewicht.
	// if (priorityQueue.contains(kante.nachgängerKnote)
	// && kante.gewicht < kante.nachgängerKnote.knoteGewicht) {
	//
	// // remove und add für Sortierung von akuelle
	// // PriorityQueue
	// priorityQueue.remove(kante.nachgängerKnote);
	// kante.nachgängerKnote.knoteGewicht = kante.gewicht;
	// kante.nachgängerKnote.previousKnote = günstigKnote;
	// priorityQueue.add(kante.nachgängerKnote);
	// }
	//
	// }
	// }
	// }
	//
	// for (Knote knote : graph.knotenList) {
	// insgesamtGewicht = insgesamtGewicht + knote.knoteGewicht;
	// }
	// System.out.println("Prim insgesamtGewicht:" + insgesamtGewicht);
	//
	// return knoten;
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// /**
	// * Günstig kante finden， aber keine Kreis ！
	// */
	// public ArrayList<Kante> kruskal() throws Exception {
	// insgesamtGewicht = 0;
	//
	// ArrayList<Kante> kanten = new ArrayList<Kante>();
	//
	// for (Kante güstigeKante : graph.kantenList) {
	//
	// if (!kreis(güstigeKante)) {
	//
	// insgesamtGewicht = insgesamtGewicht + güstigeKante.gewicht;
	// kanten.add(güstigeKante);
	//
	// ueberpruefungVonKnotenGruppen(güstigeKante);
	// }
	//
	// }
	//
	// System.out.println("kruskal insgesamtGewicht:" + insgesamtGewicht);
	// // System.out.println("kanten:" + kanten);
	//
	// return kanten;
	// }
	//
	// // Ob es Kreis gibt
	// public boolean kreis(Kante kante) {
	// // Wenn die beide KnotenGruppe gleich sind, dann es Kreis gibt!
	// if (kante.vorgängerKonte.getKnoteGruppe() != null &&
	// kante.nachgängerKnote.getKnoteGruppe() != null) {
	// if (kante.vorgängerKonte.getKnoteGruppe() ==
	// kante.nachgängerKnote.getKnoteGruppe()) {
	// return true;
	// }
	// }
	//
	// return false;
	// }
	//
	// int knoteGruppeId = 0;
	//
	// // KnoteGruppe von Konte erstellen und überprüfen.
	// public void ueberpruefungVonKnotenGruppen(Kante kante) {
	//
	// // Knote wurde schon noch in keine Kontegruppe zugeordnet
	// if (!kante.vorgängerKonte.hatKnoteGruppe() &&
	// !kante.nachgängerKnote.hatKnoteGruppe()) {
	//
	// KnoteGruppe knoteGruppe = new KnoteGruppe(knoteGruppeId++);
	// kante.vorgängerKonte.setKnoteGruppe(knoteGruppe);
	// kante.nachgängerKnote.setKnoteGruppe(knoteGruppe);
	// // wenn nachgängerKnote kein KnotenGruppe hat,dann nachgängerKnote
	// // in KontenGruppe von vorgängerKonte hinzufügen.
	// } else if (kante.vorgängerKonte.hatKnoteGruppe() &&
	// !kante.nachgängerKnote.hatKnoteGruppe()) {
	//
	// kante.nachgängerKnote.setKnoteGruppe(kante.vorgängerKonte.getKnoteGruppe());
	// // wenn vorgängerKonte kein KnotenGruppe hat,dann vorgängerKonte in
	// // KontenGruppe von nachgängerKnote hinzufügen.
	// } else if (kante.nachgängerKnote.hatKnoteGruppe() &&
	// !kante.vorgängerKonte.hatKnoteGruppe()) {
	//
	// kante.vorgängerKonte.setKnoteGruppe(kante.nachgängerKnote.getKnoteGruppe());
	// } else {
	// // zwei KnotenGruppen zusammenfassen!
	// kante.nachgängerKnote.setKnoteGruppe(kante.vorgängerKonte.getKnoteGruppe());
	//
	// }
	//
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// public void darstellenVonGraphen() {
	// System.out.println("\nGraphenAnzahl: " + graphCompenentList.size() +
	// "\n");
	//
	// for (int i = 0; i < graphCompenentList.size(); i++) {
	// System.out.println("Graph" + i + ":" + graphCompenentList.get(i));
	// }
	// System.out.println();
	// }
	//
	// // Diff operation 两个集合 差运算
	// public ArrayList<Knote> diffKnotenList(ArrayList<Knote> listA,
	// ArrayList<Knote> listB) {
	// ArrayList<Knote> list = new ArrayList<Knote>(Arrays.asList(new
	// Knote[listA.size()]));
	// Collections.copy(list, listA);
	// list.removeAll(listB);
	// return list;
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// /**
	// * Hamilton-Rundreise finden， jede Knote als StartKonte versuchen. Suche
	// * nach guestige Gewicht von der Kante
	// */
	//
	// public void nearestNeighbor() throws Exception {
	// float min = Float.MAX_VALUE;
	//
	// for (Knote konte : graph.knotenList) {
	// float ergebenis = nearestNeighbor(konte);
	// if (ergebenis < min) {
	// min = ergebenis;
	// }
	// }
	//
	// System.out.println("Guestige Hamilton-Rundreise von nearestNeighbor:" +
	// min);
	// }
	//
	// public float nearestNeighbor(Knote startKnote) throws Exception {
	// float ergebenis = 0;
	// ArrayList<Kante> kanten = new ArrayList<Kante>();
	//
	// nichtBesuchtKnoten.clear();
	// for (Knote konte : graph.knotenList) {
	// nichtBesuchtKnoten.add(konte);
	// }
	//
	// Knote anfangsknote = startKnote;
	// Knote endKnote = null;
	// nichtBesuchtKnoten.remove(startKnote);
	//
	// // Iteratiert nichtBesuchtList
	// while (nichtBesuchtKnoten.size() != 0) {
	//
	// // Die besucht Kante wegnehmen
	// ArrayList<Kante> nichtBesuchtKanten = new ArrayList<Kante>();
	//
	// for (Kante kante : startKnote.getNachbarKantenList()) {
	// if (nichtBesuchtKnoten.contains(kante.nachgängerKnote)) {
	// nichtBesuchtKanten.add(kante);
	// }
	// }
	//
	// // Sortierung
	// Collections.sort(nichtBesuchtKanten, new Comparator<Kante>() {
	// @Override
	// public int compare(Kante Kante1, Kante Kante2) {
	// return Kante1.compareTo(Kante2);
	// }
	// });
	//
	// // Die guestigste Kante
	// Kante kante = nichtBesuchtKanten.get(0);
	// kanten.add(kante);
	//
	// ergebenis = ergebenis + kante.gewicht;
	//
	// startKnote = kante.nachgängerKnote;
	// endKnote = startKnote;
	// nichtBesuchtKnoten.remove(startKnote);
	// }
	//
	// // mit startKonte und letzteKnote bekommen wir die letzte Knate.
	// Kante kante = anfangsknote.getKanteMitId(endKnote);
	//
	// ergebenis = ergebenis + kante.gewicht;
	//
	// kanten.add(kante);
	//
	// // System.out.println("startKnoteId:" + ursprungKnote.id + ",ergebenis:"
	// // +
	// // ergebenis);
	// // System.out.println("Hamilton-Rundreise:" + kanten);
	//
	// return ergebenis;
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// public float doppelterBaum() throws Exception {
	//
	// // MST von Krusal bekommen
	// ArrayList<Kante> kanten = kruskal();
	// MST mst = new MST(kanten, this.graph.knoteAnzahl);
	//
	// // das guestigest Ergebenis finden.
	// float min = Float.MAX_VALUE;
	// for (Knote startKnote : mst.knotenList) {
	//
	// // durch Tiefensuche erhalten wir die Reihenfolge von MST
	// ArrayList<Knote> knoten = tiefenSuche(startKnote, mst.knotenList);
	//
	// float ergebenis = getErgebenisVonKonten(knoten);
	//
	// if (ergebenis < min) {
	// min = ergebenis;
	// }
	// }
	//
	// System.out.println("Guestige Hamilton-Rundreise von doppelterBaum:" +
	// min);
	// return min;
	//
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// int mal = 0;
	// float minimalErgebenis = Float.MAX_VALUE;
	// String minimalReihenFolge = null;
	//
	// public void bruteForce() throws Exception {
	// insgesamtGewicht = 0;
	// // Initialisiereung
	// nichtBesuchtKnoten.clear();
	// for (Knote konte : graph.knotenList) {
	// nichtBesuchtKnoten.add(konte);
	// }
	//
	// ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();
	//
	// Knote startKnote = this.graph.knotenList.get(0);
	//
	// rekusiveBruteforce(startKnote, knotenReihenfolge);
	//
	// System.out.println("min:" + minimalErgebenis + ",mal:" + mal +
	// ",reihenFolge:" + minimalReihenFolge);
	//
	// }
	//
	// // Tiefensuche durch Rekusion
	// public void rekusiveBruteforce(Knote startKnote, ArrayList<Knote>
	// knotenReihenfolge) throws Exception {
	// // if (knotenReihenfolge.size() == 2) {
	// // if (knotenReihenfolge.get(1).id > knoteAnzahl / 2) {
	// // return;
	// // }
	// // }
	// nichtBesuchtKnoten.remove(startKnote);
	//
	// knotenReihenfolge.add(startKnote);
	//
	// for (Knote knote : startKnote.getNachbarKnotenList()) {
	// if (nichtBesuchtKnoten.contains(knote)) {
	// rekusiveBruteforce(knote, knotenReihenfolge);
	// }
	// }
	//
	// if (knotenReihenfolge.size() == this.graph.knoteAnzahl) {
	//
	// float ergebenis = getErgebenisVonKonten(knotenReihenfolge);
	//
	// if (ergebenis < minimalErgebenis) {
	// minimalErgebenis = ergebenis;
	// minimalReihenFolge = knotenReihenfolge.toString();
	// }
	//
	// mal++;
	//
	// // System.out.println(getRekusiveZeichen(knotenReihenfolge.size()) +
	// // ",mal:" + mal+ ",ergebenis:" + ergebenis+"," +
	// // knotenReihenfolge.toString() + ",min:" + minimalErgebenis );
	// }
	//
	// nichtBesuchtKnoten.add(startKnote);
	// knotenReihenfolge.remove(startKnote);
	//
	// }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// public void branchUndBound() throws Exception {
	//
	// insgesamtGewicht = 0;
	// // Initialisiereung
	// nichtBesuchtKnoten.clear();
	// for (Knote konte : graph.knotenList) {
	// nichtBesuchtKnoten.add(konte);
	// }
	//
	// ArrayList<Knote> knotenReihenfolge = new ArrayList<Knote>();
	//
	// Knote startKnote = this.graph.knotenList.get(0);
	//
	// rekusiveBranchUndBound(startKnote, knotenReihenfolge);
	//
	// System.out.println("min:" + minimalErgebenis + ",mal:" + mal +
	// ",reihenFolge:" + minimalReihenFolge);
	// }
	//
	// // Tiefensuche durch Rekusion
	// public void rekusiveBranchUndBound(Knote startKnote, ArrayList<Knote>
	// knotenReihenfolge) throws Exception {
	//
	// float checkErgebenis = getErgebenisVonKonten(knotenReihenfolge);
	// if (checkErgebenis > minimalErgebenis) {
	// // schneiden ab
	// return;
	// }
	//
	// nichtBesuchtKnoten.remove(startKnote);
	//
	// knotenReihenfolge.add(startKnote);
	//
	// for (Knote knote : startKnote.getNachbarKnotenList()) {
	// if (nichtBesuchtKnoten.contains(knote)) {
	// rekusiveBranchUndBound(knote, knotenReihenfolge);
	// }
	// }
	//
	// // Ein Mal durchgeführt. für 10 Knoten insgesamt 3628800 Möglichkeit
	// // muss man durchführen
	// if (knotenReihenfolge.size() == this.graph.knoteAnzahl) {
	//
	// float ergebenis = getErgebenisVonKonten(knotenReihenfolge);
	//
	// if (ergebenis < minimalErgebenis) {
	// minimalErgebenis = ergebenis;
	// minimalReihenFolge = knotenReihenfolge.toString();
	// // System.out.println(minimalErgebenis);
	// }
	//
	// mal++;
	//
	// // System.out.println(getRekusiveZeichen(knotenReihenfolge.size()) +
	// // ",mal:" + mal+ ",ergebenis:" + ergebenis+"," +
	// // knotenReihenfolge.toString() + ",min:" + minimalErgebenis );
	// }
	//
	// nichtBesuchtKnoten.add(startKnote);
	// knotenReihenfolge.remove(startKnote);
	//
	// }
	//
	// // bekommen Ergebenis von Kanten der Konten,
	// public float getErgebenisVonKonten(ArrayList<Knote> knotenliste) {
	// float ergebenis = 0;
	//
	// if (knotenliste.size() > 1) {
	// Knote previousKnote = knotenliste.get(knotenliste.size() - 1);
	// for (Knote konte : knotenliste) {
	//
	// Kante kante = previousKnote.getKanteMitId(konte);
	// ergebenis = ergebenis + kante.gewicht;
	// previousKnote = konte;
	// }
	// }
	//
	// return ergebenis;
	// }
	//
	// // public String getRekusiveZeichen(int i) {
	// // StringBuffer s = new StringBuffer();
	// // for (int j = 0; j < i; j++) {
	// // s.append("----");
	// // }
	// // return s.toString();
	// // }
	//
	// /**
	// *
	// ------------------------------------------------------------------------------------
	// */
	//
	// public void dijkstra(int startKnoteId, int endKnoteId) throws Exception {
	//
	// insgesamtGewicht = 0;
	//
	// PriorityQueue<Knote> priorityQueue = new PriorityQueue<Knote>();
	// for (Knote v : graph.knotenList) {
	// v.knoteGewicht = Float.MAX_VALUE;
	// priorityQueue.add(v);
	// }
	//
	// Knote startKnote = graph.knotenList.get(startKnoteId);
	// startKnote.knoteGewicht = 0;
	//
	// priorityQueue.remove(startKnote);
	// priorityQueue.add(startKnote);
	//
	// while (!priorityQueue.isEmpty()) {
	// Knote günstigKnote = priorityQueue.poll();
	//
	// if (günstigKnote.id == endKnoteId) {
	// break;
	// }
	//
	// for (Kante kante : günstigKnote.nachbarKantenList) {
	//
	// if (priorityQueue.contains(kante.nachgängerKnote)) {
	//
	// float aktuellGewicht = kante.gewicht + günstigKnote.knoteGewicht;
	//
	// if (aktuellGewicht < kante.nachgängerKnote.knoteGewicht) {
	//
	// kante.nachgängerKnote.knoteGewicht = aktuellGewicht;
	// kante.nachgängerKnote.previousKnote = günstigKnote;
	//
	// priorityQueue.remove(kante.nachgängerKnote);
	// priorityQueue.add(kante.nachgängerKnote);
	// }
	// }
	// }
	//
	// }
	//
	// Knote endKnote = graph.knotenList.get(endKnoteId);
	// System.out.println("dijkstra :" + startKnote + "--" + endKnote + " Länge:
	// " + endKnote.knoteGewicht);
	//
	// }

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public Kreis mooreBellmanFord(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		for (Knote v : graph.knotenList) {
			if (v.id == startKnoteId) {
				v.knoteGewicht = 0;
				v.previousKnote = v;
			} else {
				v.knoteGewicht = Float.MAX_VALUE;
			}
		}

		for (int i = 0; i < graph.knotenList.size() - 1; i++) {
			mooreBellmanFordSchleife(graph, false);
		}

		Kreis negativKreis = mooreBellmanFordSchleife(graph, true);

		return negativKreis;

	}

	public Kreis mooreBellmanFordSchleife(Graph graph, boolean letzteMal) {
		ArrayList<Kante> kantenInNegativKreis = new ArrayList<Kante>();
		for (Kante kante : graph.kantenList) {
			if(!kante.isAvailable()){
				continue;
			}
			
			Knote vorgängerKonte = kante.vorgängerKonte;
			Knote nachgängerKnote = kante.nachgängerKnote;
			float atkualGewicht = vorgängerKonte.knoteGewicht + kante.gewicht;
			if (atkualGewicht < nachgängerKnote.knoteGewicht) {
				// System.out.println("atkualGewicht:" + atkualGewicht + "," +
				// nachgängerKnote.knoteGewicht);

				nachgängerKnote.knoteGewicht = vorgängerKonte.knoteGewicht + kante.gewicht;
				nachgängerKnote.previousKnote = vorgängerKonte;
				kantenInNegativKreis.add(kante);
			}
		}
		// System.out.println(graph.knotenList);

		if (letzteMal) {
			if (!kantenInNegativKreis.isEmpty()) {
				return this.findNegativKreis(kantenInNegativKreis);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	public Kreis findNegativKreis(ArrayList<Kante> kantenInNegativKreis) {

		ArrayList<Kante> kantenInNegativKreis2 = CloneUtils.clone(kantenInNegativKreis);

		LinkedList<Kante> linkList = new LinkedList<Kante>();

		while (!kantenInNegativKreis2.isEmpty()) {

			Kante k = kantenInNegativKreis2.get(0);
			kantenInNegativKreis2.remove(k);

			Kante previousKante = findPreviousKante(k, kantenInNegativKreis);

			if (previousKante != null && !linkList.contains(previousKante)) {
				linkList.add(previousKante);
			}

			Kante aktualKante = findKante(k, kantenInNegativKreis);
			if (!linkList.contains(aktualKante)) {
				linkList.add(aktualKante);
			}

			Kante nextKante = findNextKante(k, kantenInNegativKreis);

			if (nextKante != null && !linkList.contains(nextKante)) {
				linkList.add(nextKante);
			}

			if (linkList.getFirst().vorgängerKonte.id == linkList.getLast().nachgängerKnote.id) {

				return getKreisVonLinkList(linkList);
			}
		}

		Knote firstKnote = linkList.getFirst().vorgängerKonte;
		Knote lastKnote = linkList.getLast().nachgängerKnote;

		Kante eineKante = lastKnote.getKanteMitId(firstKnote);
		linkList.add(eineKante);

		return getKreisVonLinkList(linkList);

	}

	public Kreis getKreisVonLinkList(LinkedList<Kante> linkList) {

		Kreis kreis = new Kreis();
		float minimalKreisWert = Float.MAX_VALUE;
		for (Kante k : linkList) {
			if (k.getVerfügebarKapazität() < minimalKreisWert) {
				minimalKreisWert = k.getVerfügebarKapazität();
			}
			kreis.kantenList.add(k);
		}

		kreis.kreisWert = minimalKreisWert;
		return kreis;

	}

	public Kante findPreviousKante(Kante kante, ArrayList<Kante> kantenInNegativKreis) {

		Knote vorgängerKonte = kante.vorgängerKonte;

		for (int i = 0; i < kantenInNegativKreis.size(); i++) {
			Kante k = kantenInNegativKreis.get(i);

			if (vorgängerKonte.id == k.nachgängerKnote.id) {
				return k;
			}
		}

		return null;
	}

	public Kante findNextKante(Kante kante, ArrayList<Kante> kantenInNegativKreis) {

		Knote nachgängerKnote = kante.nachgängerKnote;

		for (int i = 0; i < kantenInNegativKreis.size(); i++) {
			Kante k = kantenInNegativKreis.get(i);

			if (nachgängerKnote.id == k.vorgängerKonte.id) {

				return k;
			}
		}

		return null;
	}

	public Kante findKante(Kante kante, ArrayList<Kante> kantenInNegativKreis) {

		for (int i = 0; i < kantenInNegativKreis.size(); i++) {
			Kante k = kantenInNegativKreis.get(i);

			if (kante.kanteId.equals(k.kanteId)) {
				return k;
			}
		}

		return null;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */
	public ArrayList<Fluss> fordFulkerson(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		ArrayList<Fluss> flussList = new ArrayList<Fluss>();

		Fluss fluss = flussSucheDurchBreitenSuche(startKnoteId, endKnoteId, graph);

		while (fluss != null) {

			// System.out.println(fluss);
			// System.out.println(graph.kantenList);
			flussList.add(fluss);
			fluss = flussSucheDurchBreitenSuche(startKnoteId, endKnoteId, graph);

		}

		System.out.println(" Fluss gefunden und InsgesamtFluswert:" + insgesamtFluswert);

		return flussList;
	}

	public Fluss flussSucheDurchBreitenSuche(int startKnoteId, int endKnoteId, Graph graph) throws Exception {

		besuchtKnoten.clear();

		Queue<Knote> queue = new LinkedList<Knote>();

		Knote startKnote = graph.knotenList.get(startKnoteId);
		queue.add(startKnote);
		besuchtKnoten.add(startKnote);

		if (startKnote.mitBalance) {
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
		Fluss fluss = getFlussDurchEndKnote(endKonte, graph);

		if (startKnote.mitBalance) {
			if (startKnote.balance >= fluss.flussWert) {
				startKnote.balance = startKnote.balance - fluss.flussWert;
			} else {
				fluss.flussWert = startKnote.balance;
				startKnote.balance = 0;
			}
		}

		if (fluss != null) {

			insgesamtFluswert += fluss.flussWert;

			createResidualGraph(fluss, graph);
		}

		return fluss;
	}

	public Fluss getFlussDurchEndKnote(Knote aktuellKnote, Graph graph) {

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
			graph.createRueckKnate(kante.nachgängerKnote, kante.vorgängerKonte, -kante.gewicht, kante.getKapazität(),
					kante.getKapazität() - kante.getFlussWert());

			if (kante.getVerfügebarKapazität() == 0) {
				graph.removeKante(kante);
			}
		}
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */
	public void cycleCanceling(Graph graph) throws Exception {

		System.out.println("graph.kantenList:" + graph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		erstellenSuperQuelleUndSenke();

		Graph residualGraph = CloneUtils.clone(graph);
		ArrayList<Fluss> flussList = fordFulkerson(0, 3, residualGraph);
		System.out.println("flussList:" + flussList);
		System.out.println("------------------------------------------------------------------------------------");

		System.out.println("residualGraph.kantenList:" + residualGraph.kantenList);
		System.out.println("------------------------------------------------------------------------------------");

		Kreis negativKreis = mooreBellmanFord(0, 3, residualGraph);

		while (negativKreis != null) {
			System.out.println("negativKreis:" + negativKreis);
			System.out.println("------------------------------------------------------------------------------------");

			aktualisertGraph(negativKreis, residualGraph);

			System.out.println("residualGraph.kantenList:" + residualGraph.kantenList);
			System.out.println("------------------------------------------------------------------------------------");

			createResidualGraph(residualGraph);

			System.out.println("residualGraph.kantenList:" + residualGraph.kantenList);
			System.out.println("------------------------------------------------------------------------------------");

			negativKreis = mooreBellmanFord(0, 3, residualGraph);
		}

		float kosten = 0;
		
		for (Kante k : residualGraph.kantenList) {
			if (k.gewicht > 0) {
				kosten = kosten + k.getFlussWert() * k.gewicht;
			}
		}

		System.out.println("kosten:" + kosten);

	}

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

	public void createResidualGraph(Graph graph) {

		ArrayList<Kante> rueckKnatenList = new ArrayList<Kante>();
		ArrayList<Kante> removeKnatenList = new ArrayList<Kante>();

		for (Kante kante : graph.kantenList) {

			if (kante.getFlussWert() > 0) {

				Kante rueckKnate = graph.findKante(kante.nachgängerKnote.id, kante.vorgängerKonte.id);

				if (rueckKnate == null) {

					rueckKnate = new Kante(kante.nachgängerKnote, kante.vorgängerKonte, -kante.gewicht,
							kante.getKapazität(), kante.getKapazität() - kante.getFlussWert(), kante.gerichtetGraph);

					rueckKnatenList.add(rueckKnate);

				}
				if (kante.getVerfügebarKapazität() == 0) {

					removeKnatenList.add(kante);
				}

				// System.out.println("kante:" + kante);
				// System.out.println("rueckKnate:" + rueckKnate);
			}
		}

		for (Kante kante : rueckKnatenList) {

			kante.nachgängerKnote.getNachbarKnotenList().add(kante.vorgängerKonte);
			kante.nachgängerKnote.getNachbarKantenList().add(kante);
			graph.kantenList.add(kante);
			graph.kantenMap.put(kante.kanteId, kante);

		}

		for (Kante kante : removeKnatenList) {
			kante.nachgängerKnote.getNachbarKnotenList().remove(kante.vorgängerKonte);
			kante.nachgängerKnote.getNachbarKantenList().remove(kante);
//			graph.kantenList.remove(kante);
//			graph.kantenMap.remove(kante.kanteId);

		}

	}

	public void erstellenSuperQuelleUndSenke() {

	}

	public void aktualisiertGraphDurchNegativKreis(Graph graph, Kreis kreis) {
		for (Kante kante : kreis.kantenList) {

			// alte Kante aktualisiert
			kante.setFlussWert(kante.getFlussWert() + kreis.kreisWert);
			Kante kanteVonGraph = graph.findKante(kante.vorgängerKonte.id, kante.nachgängerKnote.id);
			if (kanteVonGraph != null) {
				kanteVonGraph.gewicht += kante.gewicht;
			}
			Kante rueckKanteVonGraph = graph.findKante(kante.nachgängerKnote.id, kante.vorgängerKonte.id);
			if (rueckKanteVonGraph != null) {
				rueckKanteVonGraph.gewicht -= kante.gewicht;
			}
		}
	}

}
