package com.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

//缺Adjanzmatrix
public class GraphParse {

	public int knoteAnzahl;
	public ArrayList<Knote> knotenList = new ArrayList<Knote>();
	public HashSet<Kante> kantenSet = new HashSet<Kante>();
	public HashMap<String, Kante> kantenMap = new HashMap<String, Kante>();
	public ArrayList<Kante> kantenList;
	boolean gerichtetGraph = false;

	public GraphParse(String str, boolean gerichtetGraph) throws Exception {

		this.gerichtetGraph = gerichtetGraph;

		FileReader reader = new FileReader(str);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String text = bufferedReader.readLine();
		knoteAnzahl = Integer.parseInt(text);

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

			// AdjanzenMatrix
			if (s.length > 3) {

				for (int j = 0; j < s.length; j++) {
					if (s[j].equals("1")) {
						createKonte(knotenList.get(i), knotenList.get(j), 0);
					}
				}
			} else {

				createKonte(knotenList.get(Integer.parseInt(s[0])), knotenList.get(Integer.parseInt(s[1])), gewicht);

				if (!gerichtetGraph) {
					createKonte(knotenList.get(Integer.parseInt(s[1])), knotenList.get(Integer.parseInt(s[0])),
							gewicht);
				}
			}

			text = bufferedReader.readLine();
			i++;
		}

		bufferedReader.close();

		kantenList = new ArrayList<Kante>(kantenSet);
		Collections.sort(kantenList, new Comparator<Kante>() {
			@Override
			public int compare(Kante Kante1, Kante Kante2) {
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

		// System.out.println("kantenList:"+kantenList);
	}

	public void createKonte(Knote rootKnote, Knote kindKnote, float gewicht) {

		rootKnote.getNachbarKnotenList().add(kindKnote);

		Kante kante = new Kante(rootKnote, kindKnote, gewicht,this.gerichtetGraph);
		// UngerichtetKante kante = new UngerichtetKante(rootKnote,kindKnote,
		// (int)(1+Math.random()*(10-1+1)));
		rootKnote.getNachbarKantenList().add(kante);

		kantenSet.add(kante);
		kantenMap.put(kante.kanteId, kante);
	}
}
