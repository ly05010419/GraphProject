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
	public ArrayList<Knote> nichtBesuchtKonten = new ArrayList<Knote>();
	public ArrayList<Knote> besuchtKonten = new ArrayList<Knote>();
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

	/**
	 * ------------------------------------------------------------------------------------
	 */

	/**
	 * TiefenSuche durch rekutiv
	 */
	public ArrayList<Knote> tiefenSuche(Knote startKnote, ArrayList<Knote> knotenList) throws Exception {

		ArrayList<Knote> reihenFolgeVonKnoten = new ArrayList<Knote>();
		// Initialisiereung
		nichtBesuchtKonten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKonten.add(konte);
		}

		// iteratiert nichtBesuchtList
		while (nichtBesuchtKonten.size() != 0) {

			GraphCompenent g = new GraphCompenent();

			graphCompenentList.add(g);

			this.rekusiveTiefenSuche(startKnote, reihenFolgeVonKnoten);

		}

		// this.darstellenVonGraphen();

		return reihenFolgeVonKnoten;
	}

	public void rekusiveTiefenSuche(Knote vaterKnote, ArrayList<Knote> reihenFolgeVonKnoten) throws Exception {
		// System.out.println("vaterKnote:" + vaterKnote);
		nichtBesuchtKonten.remove(vaterKnote);
		reihenFolgeVonKnoten.add(vaterKnote);

		for (Knote kindKnote : vaterKnote.getNachbarKnotenList()) {

			if (nichtBesuchtKonten.contains(kindKnote)) {

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
			float nearestNeighborInsgesamtGewicht = nearestNeighbor(konte);
			if (nearestNeighborInsgesamtGewicht < min)
				min = nearestNeighborInsgesamtGewicht;
		}

		System.out.println("Guestige Hamilton-Rundreise von nearestNeighbor:" + min);
	}

	public float nearestNeighbor(Knote startKnote) throws Exception {
		float insgesamtGewichtVonDiesesMal = 0;
		ArrayList<UngerichtetKante> kanten = new ArrayList<UngerichtetKante>();

		nichtBesuchtKonten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKonten.add(konte);
		}

		int startKnoteId = startKnote.id;
		nichtBesuchtKonten.remove(startKnote);
		// iteratiert nichtBesuchtList
		while (nichtBesuchtKonten.size() != 0) {

			// Suche aus den Kanten von vnow zu unbesuchten Knoten
			ArrayList<UngerichtetKante> nachbarKantenList = new ArrayList<UngerichtetKante>();
			ArrayList<UngerichtetKante> startKnoteKantenList = startKnote.getNachbarKantenList();
			for (UngerichtetKante kante : startKnoteKantenList) {
				if (nichtBesuchtKonten.contains(kante.nachgängerKnote)) {
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

			insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal + kante.gewicht;
			// gehe Kante entlang zum naechsten Knoten vnext.
			startKnote = kante.nachgängerKnote;
			nichtBesuchtKonten.remove(startKnote);
		}

		// Falls alle Knoten als besucht markiert sind, verbinde vnow mit vstart
		// und STOPP.
		for (UngerichtetKante kante : startKnote.getNachbarKantenList()) {
			if (kante.nachgängerKnote.id == startKnoteId) {
				insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal + kante.gewicht;
				// Hamilton-Rundreise fertig!
				kanten.add(kante);
			}
		}

		System.out.println(
				"startKnoteId:" + startKnoteId + ",insgesamtGewichtVonDiesesMal:" + insgesamtGewichtVonDiesesMal);
		System.out.println("Hamilton-Rundreise:" + kanten);

		return insgesamtGewichtVonDiesesMal;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	public void doppelterBaum() throws Exception {
		float insgesamtGewichtVonDiesesMal = 0;

		ArrayList<UngerichtetKante> kanten = kruskal();
		MST mst = new MST(kanten, knoteAnzahl);

		for (UngerichtetKante kante : mst.kantenList) {
			insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal + kante.gewicht;
		}
//		System.out.println("Kanten von MST :" + mst.kantenList);
//		System.out.println("Knoten von MST :" + mst.knotenList);
		

		float min = Float.MAX_VALUE;
		for (Knote startKnote : mst.knotenList) {
			ArrayList<Knote> reihenFolge = tiefenSuche(startKnote, mst.knotenList);

			float nearestNeighborInsgesamtGewicht = getErgebinsVonReihenfolge(reihenFolge);
			if (nearestNeighborInsgesamtGewicht < min)
				min = nearestNeighborInsgesamtGewicht;
		}

		System.out.println("Guestige Hamilton-Rundreise von doppelterBaum:" + min);

	}

	public float getErgebinsVonReihenfolge(ArrayList<Knote> reihenFolge) {
		float insgesamtGewichtVonDiesesMal = 0;

		Knote startKnote = null;
		Knote previousKnote = null;
		
		StringBuffer msg = new StringBuffer();
		
		for (Knote knote : reihenFolge) {
			if (previousKnote != null) {

				UngerichtetKante kante = UngerichtetKante.getKanteMitId(previousKnote, knote, kantenMap);

				msg.append(" kante:" + kante);
				insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal + kante.gewicht;
			} else {
				startKnote = knote;
				msg.append("ErsteKnote:" + startKnote.id);

			}
			previousKnote = knote;
		}

		UngerichtetKante kante = UngerichtetKante.getKanteMitId(startKnote, previousKnote, kantenMap);
		msg.append(" kante:" + kante);
		insgesamtGewichtVonDiesesMal = insgesamtGewichtVonDiesesMal + kante.gewicht;

//		System.out.println(msg.toString());
//		System.out.println("ErsteKnote:" + startKnote.id+"insgesamtGewichtVonDiesesMal:" + insgesamtGewichtVonDiesesMal);
		return insgesamtGewichtVonDiesesMal;
	}

}
