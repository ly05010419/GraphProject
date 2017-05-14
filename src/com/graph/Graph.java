package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> graphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knotenList;
	private ArrayList<UngerichtetKante> kantenList;
	public ArrayList<Knote> nichtBesuchtKnoten = new ArrayList<Knote>();
	public ArrayList<Knote> besuchtKnoten = new ArrayList<Knote>();
	private int knoteAnzahl;
	private float insgesamtGewicht = 0;
	public HashMap<String, UngerichtetKante> kantenMap;

	public Graph(String str) throws Exception {

		GraphParse graphParse = new GraphParse(str);
		knotenList = graphParse.knotenList;
		kantenList = graphParse.kantenList;
		knoteAnzahl = graphParse.knoteAnzahl;
		kantenMap = graphParse.kantenMap;
		// System.out.println("KnoteAnzahl: " + knoteAnzahl);
	}

	/**
	 * BreitenSuche durch Queue
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
	 * TiefenSuche durch rekutiv
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
				for (UngerichtetKante edge : günstigKnote.nachbarKantenList) {

					// wenn Gewicht von nachgängerKnote günstig ist, dann
					// überschreiben das aktuelles Gewicht.
					if (priorityQueue.contains(edge.nachgängerKnote)
							&& edge.gewicht < edge.nachgängerKnote.knoteGewicht) {

						// remove und add für Sortierung von akuelle
						// PriorityQueue
						priorityQueue.remove(edge.nachgängerKnote);
						edge.nachgängerKnote.knoteGewicht = edge.gewicht;
						edge.nachgängerKnote.previousKnote = günstigKnote;
						priorityQueue.add(edge.nachgängerKnote);
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
	public ArrayList<UngerichtetKante> kruskal() throws Exception {
		insgesamtGewicht = 0;

		ArrayList<UngerichtetKante> kanten = new ArrayList<UngerichtetKante>();

		for (UngerichtetKante güstigeKante : kantenList) {

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
	public boolean kreis(UngerichtetKante kante) {
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
	public void ueberpruefungVonKnotenGruppen(UngerichtetKante kante) {

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
		ArrayList<UngerichtetKante> kanten = new ArrayList<UngerichtetKante>();

		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		int startKnoteId = startKnote.id;
		nichtBesuchtKnoten.remove(startKnote);
		// iteratiert nichtBesuchtList
		while (nichtBesuchtKnoten.size() != 0) {

			// Suche aus den Kanten von vnow zu unbesuchten Knoten
			ArrayList<UngerichtetKante> nachbarKantenList = new ArrayList<UngerichtetKante>();
			ArrayList<UngerichtetKante> startKnoteKantenList = startKnote.getNachbarKantenList();
			for (UngerichtetKante kante : startKnoteKantenList) {
				if (nichtBesuchtKnoten.contains(kante.nachgängerKnote)) {
					nachbarKantenList.add(kante);
				}
			}

			// die Kante mit dem guenstigsten Gewicht
			Collections.sort(nachbarKantenList, new Comparator<UngerichtetKante>() {
				@Override
				public int compare(UngerichtetKante Kante1, UngerichtetKante Kante2) {
					return Kante1.compareTo(Kante2);
				}
			});

			UngerichtetKante kante = nachbarKantenList.get(0);
			kanten.add(kante);

			ergebenis = ergebenis + kante.gewicht;
			// gehe Kante entlang zum naechsten Knoten vnext.
			startKnote = kante.nachgängerKnote;
			nichtBesuchtKnoten.remove(startKnote);
		}

		// Falls alle Knoten als besucht markiert sind, verbinde vnow mit vstart
		// und STOPP.
		for (UngerichtetKante kante : startKnote.getNachbarKantenList()) {
			if (kante.nachgängerKnote.id == startKnoteId) {
				ergebenis = ergebenis + kante.gewicht;
				// Hamilton-Rundreise fertig!
				kanten.add(kante);
			}
		}

		System.out.println("startKnoteId:" + startKnoteId + ",ergebenis:" + ergebenis);
		System.out.println("Hamilton-Rundreise:" + kanten);

		return ergebenis;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void doppelterBaum() throws Exception {

		// MST von Krusal bekommen
		ArrayList<UngerichtetKante> kanten = kruskal();
		MST mst = new MST(kanten, knoteAnzahl);

		// System.out.println("Kanten von MST :" + mst.kantenList);
		// System.out.println("Knoten von MST :" + mst.knotenList);

		// das guestigest Ergebenis finden.
		float min = Float.MAX_VALUE;
		for (Knote startKnote : mst.knotenList) {

			// durch Tiefensuche erhalten wir die Reihenfolge von MST
			ArrayList<Knote> knoten = tiefenSuche(startKnote, mst.knotenList);

			float ergebenis = getErgebenis(knoten);
			if (ergebenis < min) {
				min = ergebenis;
			}
		}

		System.out.println("Guestige Hamilton-Rundreise von doppelterBaum:" + min);

	}

	// die Knoten von der reihenfolge verbinden, startKonte verbindet auch mit
	// letzteKnote zu Krei bekommen
	// public float getErgebinsVonReihenfolge(ArrayList<Knote> reihenFolge) {
	// float insgesamtGewichtVonDiesesMal = 0;
	//
	// Knote startKnote = null;
	// Knote previousKnote = null;
	//
	// StringBuffer msg = new StringBuffer();
	//
	// for (Knote knote : reihenFolge) {
	// if (previousKnote != null) {
	//
	// UngerichtetKante kante = UngerichtetKante.getKanteMitId(previousKnote,
	// knote, kantenMap);
	//
	// msg.append(" kante:" + kante);
	// insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal +
	// kante.gewicht;
	// } else {
	// startKnote = knote;
	// msg.append("ErsteKnote:" + startKnote.id);
	//
	// }
	// previousKnote = knote;
	// }
	//
	// UngerichtetKante kante = UngerichtetKante.getKanteMitId(startKnote,
	// previousKnote, kantenMap);
	// msg.append(" kante:" + kante);
	// insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal +
	// kante.gewicht;
	//
	// return insgesamtGewichtVonDiesesMal;
	// }

	int mal = 0;
	float min = Float.MAX_VALUE;
	String reihenFolge = null;

	public void bruteForce() throws Exception {
		insgesamtGewicht = 0;
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenliste = new ArrayList<Knote>();

		Knote startKnote = this.knotenList.get(0);

		rekusiveBruteforce(startKnote, knotenliste);

		System.out.println("min:" + min + ",mal:" + mal + ",reihenFolge:" + reihenFolge);

	}

	public void rekusiveBruteforce(Knote startKnote, ArrayList<Knote> knotenliste) throws Exception {

		nichtBesuchtKnoten.remove(startKnote);

		knotenliste.add(startKnote);

		for (Knote knote : startKnote.getNachbarKnotenList()) {
			if (nichtBesuchtKnoten.contains(knote)) {

				// System.out.println(getRekusiveZeichen(knotenliste.size())+":"
				// + knote);
				rekusiveBruteforce(knote, knotenliste);
				// System.out.println(getRekusiveZeichen(knotenliste.size())+":"
				// + knote);
			}
		}

		if (knotenliste.size() == knoteAnzahl) {

			float ergebenis = getErgebenis(knotenliste);

			if (ergebenis < min) {
				min = ergebenis;
				reihenFolge = knotenliste.toString();
			}

			mal++;
			
			// System.out.println(getRekusiveZeichen(knotenliste.size()) + ",mal:" + mal+ ",ergebenis:" + ergebenis+"," + knotenliste.toString() + ",min:" + min );
		}

		nichtBesuchtKnoten.add(startKnote);
		knotenliste.remove(startKnote);

	}

	public void branchUndBound() throws Exception {
		insgesamtGewicht = 0;
		// Initialisiereung
		nichtBesuchtKnoten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKnoten.add(konte);
		}

		ArrayList<Knote> knotenliste = new ArrayList<Knote>();

		Knote startKnote = this.knotenList.get(0);

		rekusiveBranchUndBound(startKnote, knotenliste);

		System.out.println("min:" + min + ",mal:" + mal + ",reihenFolge:" + reihenFolge);
	}

	public void rekusiveBranchUndBound(Knote startKnote, ArrayList<Knote> knotenliste) throws Exception {

		float checkErgebenis = getErgebenis(knotenliste);
		if (checkErgebenis > min) {
//			System.out.println("checkErgebenis:"+checkErgebenis);
//			System.out.println("min:"+min);
			return;

		}

		nichtBesuchtKnoten.remove(startKnote);

		knotenliste.add(startKnote);

		for (Knote knote : startKnote.getNachbarKnotenList()) {
			if (nichtBesuchtKnoten.contains(knote)) {

//				System.out.println(getRekusiveZeichen(knotenliste.size()) + ":" + knote);
				rekusiveBranchUndBound(knote, knotenliste);
//				System.out.println(getRekusiveZeichen(knotenliste.size()) + ":" + knote);
			}
		}

		if (knotenliste.size() == knoteAnzahl) {

			float ergebenis = getErgebenis(knotenliste);

			if (ergebenis < min) {
				min = ergebenis;
				reihenFolge = knotenliste.toString();
			}

			mal++;

//			System.out.println(getRekusiveZeichen(knotenliste.size()) + ",mal:" + mal+ ",ergebenis:" + ergebenis+"," + knotenliste.toString() + ",min:" + min );
		}

		nichtBesuchtKnoten.add(startKnote);
		knotenliste.remove(startKnote);

	}

	public float getErgebenis(ArrayList<Knote> knotenliste) {
		float ergebenis = 0;

		if (knotenliste.size() > 1) {
			Knote previousKnote = knotenliste.get(knotenliste.size() - 1);
			for (Knote konte : knotenliste) {

				UngerichtetKante kante = UngerichtetKante.getKanteMitId(previousKnote, konte, kantenMap);
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

}
