package com.graph;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


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
			Knote k = new Knote(i);
			knotenList.add(k);
		}

		text = bufferedReader.readLine();

		while (text != null) {
			String[] s = text.split("\\t");
			createKonte(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Float.parseFloat(s[2]));
			createKonte(Integer.parseInt(s[1]), Integer.parseInt(s[0]), Float.parseFloat(s[2]));

			text = bufferedReader.readLine();
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
		
//		System.out.println("knotenList:"+knotenList);
//		System.out.println("kantenList:"+kantenList);
	}

	public void createKonte(int rootKnote, int kindKnote, Float gewicht) {

		Knote rootK = knotenList.get(rootKnote);
		rootK.getNachbarKnotenList().add(kindKnote);

		Knote kindK = knotenList.get(kindKnote);
		UngerichtetKante kante = new UngerichtetKante(rootKnote, kindKnote, gewicht,kindK);
		rootK.getNachbarKantenList().add(kante);
		
		kantenSet.add(kante);

	}
}
