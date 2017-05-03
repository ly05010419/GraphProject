package com.graph;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GraphParse {

	private boolean adjanzenMatrix = false;

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
			if (s.length > 3) {

				adjanzenMatrix = true;

			}
			text = bufferedReader.readLine();

		}

		ArrayList<String[]> neuStringList = new ArrayList<String[]>();
		neuStringList.addAll(stringList);
		for (String[] s : stringList) {
			String x = s[0];
			s[0] = s[1];
			s[1] = x;
			neuStringList.add(s);

		}
		
		stringList = neuStringList;
		
		
		
		bufferedReader.close();

	}

	public ArrayList<Knote> parseKonte() throws Exception {
		ArrayList<Knote> knoten = new ArrayList<Knote>();

		for (int id = 0; id < knoteAnzahl; id++) {
			Knote knote = new Knote(id);

			setKindUndKanteList(stringList, knote);

			knoten.add(knote);
		}

		return knoten;
	}

	public void setKindUndKanteList(ArrayList<String[]> stringList, Knote knote) {

		HashSet<Integer> kindKonten = new HashSet<Integer>();
		ArrayList<UngerichtetKante> kindKanten = new ArrayList<UngerichtetKante>();

		if (adjanzenMatrix) {

			for (int i = 0; i < stringList.get(knote.id).length; i++) {
				if (stringList.get(knote.id)[i].equals("1")) {
					kindKonten.add(i);
				}
			}

		} else {
			for (int i = 0; i < stringList.size(); i++) {
				String kindKnote = null;
				String[] s = stringList.get(i);

				if (s[0].equals("" + knote.id)) {

					kindKnote = s[1];

				} else if (s[1].equals("" + knote.id)) {
					kindKnote = s[0];
				}

				if (kindKnote != null) {

					kindKonten.add(Integer.parseInt(kindKnote));

					if (s.length == 3) {
						UngerichtetKante kante = new UngerichtetKante(knote.id, Integer.parseInt(kindKnote),
								Float.parseFloat(s[2]));
						if (!kindKanten.contains(kante)) {
							kindKanten.add(kante);
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

		knote.setNachbarKantenList(kindKanten);
		knote.setNachbarKnotenList(new ArrayList<Integer>(kindKonten));

	}

}
