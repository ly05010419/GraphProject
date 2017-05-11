package com.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

//缺Adjanzmatrix
public class GraphParse {
	
	private int knoteAnzahl;
	public ArrayList<Knote> knotenList = new ArrayList<Knote>();
	public HashSet<UngerichtetKante> kantenSet = new HashSet<UngerichtetKante>();
	public ArrayList<UngerichtetKante> kantenList;

	public GraphParse(String str) throws Exception {

		FileReader reader = new FileReader(str);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String text = bufferedReader.readLine();
		knoteAnzahl = Integer.parseInt(text);

		System.out.println("KnoteAnzahl:	" + knoteAnzahl);

		for (int i = 0; i < knoteAnzahl; i++) {
			Knote knote = new Knote(i);
			knotenList.add(knote);
		}

		text = bufferedReader.readLine();

		int i = 0;
		while (text != null) {
			String[] s = text.split("\\t");

			float gewicht = 0;
			if (s.length == 3) {

				gewicht = Float.parseFloat(s[2]);

			}

			//AdjanzenMatrix
			if (s.length > 3) {

				for (int j = 0; j < s.length; j++) {
					if (s[j].equals("1")) {
						createKonte(knotenList.get(i), knotenList.get(j), 0);
					}
				}
			} else {

				createKonte(knotenList.get(Integer.parseInt(s[0])), knotenList.get(Integer.parseInt(s[1])), gewicht);
				createKonte(knotenList.get(Integer.parseInt(s[1])), knotenList.get(Integer.parseInt(s[0])), gewicht);
			}

			text = bufferedReader.readLine();
			i++;
		}

		bufferedReader.close();

		kantenList = new ArrayList<UngerichtetKante>(kantenSet);
		Collections.sort(kantenList, new Comparator<UngerichtetKante>() {
			@Override
			public int compare(UngerichtetKante Kante1, UngerichtetKante Kante2) {
				return Kante1.compareTo(Kante2);
			}
		});

		Collections.sort(knotenList, new Comparator<Knote>() {
			@Override
			public int compare(Knote Kante1, Knote Kante2) {
				if (Kante1.id > Kante2.id) {
					return 1;
				} else if (Kante1.id == Kante2.id) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		// System.out.println("knotenList:"+knotenList);
		// System.out.println("kantenList:"+kantenList);
	}

	public void createKonte(Knote rootKnote, Knote kindKnote, float gewicht) {

		rootKnote.getNachbarKnotenList().add(kindKnote);

		UngerichtetKante kante = new UngerichtetKante(rootKnote, kindKnote, gewicht);
		// UngerichtetKante kante = new UngerichtetKante(rootKnote,kindKnote,
		// (int)(1+Math.random()*(10-1+1)));
		rootKnote.getNachbarKantenList().add(kante);

		kantenSet.add(kante);

	}
}
