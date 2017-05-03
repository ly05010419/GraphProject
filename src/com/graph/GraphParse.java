package com.graph;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GraphParse {

	private boolean adjanzenMatrix = false;
	private boolean gerichtet = false;
	private int knoteAnzahl;
	private ArrayList<String[]> stringList;
	public ArrayList<UngerichtetKante> kanten = new ArrayList<UngerichtetKante>();

	public GraphParse(String str) throws Exception {

		FileReader reader = new FileReader(str);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String text = bufferedReader.readLine();
		knoteAnzahl = Integer.parseInt(text);

		stringList = new ArrayList<String[]>();

		System.out.println("KnoteAnzahl:	" + knoteAnzahl);

		text = bufferedReader.readLine();

		while (text != null) {
			String[] s = text.split("\\t");

			stringList.add(s);
			if (s.length > 2) {
				if (s.length == 3) {
					gerichtet = true;
				} else {
					adjanzenMatrix = true;
				}
			}
			text = bufferedReader.readLine();

		}

		bufferedReader.close();

	}

	public ArrayList<Knote> parseKonte() throws Exception {
		ArrayList<Knote> knoten = new ArrayList<Knote>();

		for (int i = 0; i < knoteAnzahl; i++) {
			Knote k = new Knote(i);
			knoten.add(k);
			setKindUndKanteList(i, stringList, knoten.get(i));
		}

		return knoten;
	}

	public void setKindUndKanteList(int vaterKonte, ArrayList<String[]> stringList, Knote knoten1) {

		ArrayList<Integer> kindKonten = new ArrayList<Integer>();
		ArrayList<UngerichtetKante> kindKanten = new ArrayList<UngerichtetKante>();

		if (adjanzenMatrix) {

			for (int i = 0; i < stringList.get(vaterKonte).length; i++) {
				if (stringList.get(vaterKonte)[i].equals("1")) {
					kindKonten.add(i);
				}
			}

		} else {
			for (int i = 0; i < stringList.size(); i++) {
				String kindKnote = null;
				String[] s = stringList.get(i);

				if (s[0].equals("" + vaterKonte)) {

					kindKnote = s[1];

				} else if (!gerichtet && s[1].equals("" + vaterKonte)) {
					kindKnote = s[0];
				}

				if (kindKnote != null) {

					kindKonten.add(Integer.parseInt(kindKnote));

					if (s.length == 3) {
						UngerichtetKante k = new UngerichtetKante(vaterKonte, Integer.parseInt(s[1]), Float.parseFloat(s[2]));
						if (!kindKanten.contains(k)) {
							kindKanten.add(k);
						}
					}
				}
			}

		}
		
		Collections.sort(kindKanten, new Comparator<UngerichtetKante>() {
			@Override
			public int compare(UngerichtetKante Kante1, UngerichtetKante Kante2) {
				return Kante1.compareTo(Kante2);
			}
		});
		
		kanten.addAll(kindKanten);
		
		Collections.sort(kanten, new Comparator<UngerichtetKante>() {
			@Override
			public int compare(UngerichtetKante Kante1, UngerichtetKante Kante2) {
				return Kante1.compareTo(Kante2);
			}
		});
		
		knoten1.setNachbarKantenList(kindKanten);
		knoten1.setNachbarKnotenList(kindKonten);

	}

}
