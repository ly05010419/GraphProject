package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	public ArrayList<GraphCompenent> graphCompenentList = new ArrayList<GraphCompenent>();

	private ArrayList<Knote> knotenList;
	private ArrayList<UngerichtetKante> kantenList;
	public ArrayList<Knote> nichtBesuchtKonten = new ArrayList<Knote>();
	public ArrayList<Knote> besuchtKonten = new ArrayList<Knote>();

	private float insgesamtGewicht = 0;

	public Graph(String str) throws Exception {

		GraphParse graphParse = new GraphParse(str);
		knotenList = graphParse.knotenList;
		kantenList = graphParse.kantenList;
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
	public void tiefenSuche() throws Exception {

		// Initialisiereung
		nichtBesuchtKonten.clear();
		for (Knote konte : knotenList) {
			nichtBesuchtKonten.add(konte);
		}

		// iteratiert nichtBesuchtList
		while (nichtBesuchtKonten.size() != 0) {
			Knote startKnote = (Knote) nichtBesuchtKonten.toArray()[0];
			// System.out.println("i:"+i);

			GraphCompenent g = new GraphCompenent();

			graphCompenentList.add(g);

			this.rekusiveTiefenSuche(startKnote, g);

		}

		this.darstellenVonGraphen();
	}

	public void rekusiveTiefenSuche(Knote vaterKnote, GraphCompenent graph) throws Exception {
		// System.out.println("vaterKnote:" + vaterKnote);
		nichtBesuchtKonten.remove(vaterKnote);
		graph.getKnoten().add(vaterKnote);

		for (Knote kindKnote : vaterKnote.getNachbarKnotenList()) {

			if (nichtBesuchtKonten.contains(kindKnote)) {

				this.rekusiveTiefenSuche(kindKnote, graph);
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
	public void prim() throws Exception {
		insgesamtGewicht = 0;
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
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */

	/**
	 * Günstig kante finden， aber keine Kreis ！
	 */
	public void kruskal() throws Exception {
		insgesamtGewicht = 0;

		for (UngerichtetKante güstigeKante : kantenList) {

			if (!kreis(güstigeKante)) {

				insgesamtGewicht = insgesamtGewicht + güstigeKante.gewicht;

				ueberpruefungVonKnotenGruppen(güstigeKante);
			}

		}

		System.out.println("kruskal insgesamtGewicht:" + insgesamtGewicht);

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

}
