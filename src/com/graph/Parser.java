package com.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Parser {

	public int knoteAnzahl;
	public ArrayList<Knote> knotenList = new ArrayList<Knote>();
	public HashSet<Kante> kantenSet = new HashSet<Kante>();
	public HashMap<String, Kante> kantenMap = new HashMap<String, Kante>();
	public ArrayList<Kante> kantenList;
	boolean gerichtetGraph = false;
	boolean mitBalance = false;

	public Parser(String str, boolean gerichtetGraph, boolean mitBalance) throws Exception {

		this.gerichtetGraph = gerichtetGraph;
		this.mitBalance = mitBalance;

		FileReader reader = new FileReader(str);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String text = bufferedReader.readLine();
		knoteAnzahl = Integer.parseInt(text);

		if (this.mitBalance) {
			for (int i = 0; i < knoteAnzahl; i++) {
				text = bufferedReader.readLine();

				float balance = Float.parseFloat(text);
				Knote knote = new Knote(i);
				knote.balance = balance;
				knotenList.add(knote);
			}

		} else {

			for (int i = 0; i < knoteAnzahl; i++) {
				Knote knote = new Knote(i);
				knotenList.add(knote);
			}

		}

		text = bufferedReader.readLine();

		int i = 0;
		while (text != null) {
			String[] s = text.split("\\t");

			float gewicht = 0;
			float Kapazität = -1;
			if (s.length == 3) {

				gewicht = Float.parseFloat(s[2]);

			} else if (s.length == 4) {
				gewicht = Float.parseFloat(s[2]);
				Kapazität = Float.parseFloat(s[3]);
			}

			// AdjanzenMatrix
			if (s.length > 3 && !this.mitBalance) {

				for (int j = 0; j < s.length; j++) {
					if (s[j].equals("1")) {
						createKnate(knotenList.get(i), knotenList.get(j), 0, -1);
					}
				}
			} else {

				createKnate(knotenList.get(Integer.parseInt(s[0])), knotenList.get(Integer.parseInt(s[1])), gewicht,
						Kapazität);

				if (!gerichtetGraph) {
					createKnate(knotenList.get(Integer.parseInt(s[1])), knotenList.get(Integer.parseInt(s[0])), gewicht,
							Kapazität);
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

	private void createKnate(Knote rootKnote, Knote kindKnote, float gewicht, float kapazität) {

		rootKnote.getNachbarKnotenList().add(kindKnote);

		Kante kante = new Kante(rootKnote, kindKnote, gewicht, this.gerichtetGraph, kapazität);
		rootKnote.getNachbarKantenList().add(kante);

		kantenSet.add(kante);
		kantenMap.put(kante.kanteId, kante);
	}

}
