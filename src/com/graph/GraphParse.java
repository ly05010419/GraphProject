package com.graph;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;

public class GraphParse {

	boolean adjanzenMatrix = false;

	public ArrayList<Knote> parse(String str) throws Exception {
		ArrayList<Knote> knoten = new ArrayList<Knote>();

		FileReader reader = new FileReader(str);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String text = bufferedReader.readLine();
		int knoteAnzahl = Integer.parseInt(text);

		ArrayList<String[]> stringList = new ArrayList<String[]>();

		System.out.println("KnoteAnzahl:	" + knoteAnzahl);

		text = bufferedReader.readLine();

		while (text != null) {
			String[] s = text.split("\\t");
			stringList.add(s);
			if (s.length > 2) {
				adjanzenMatrix = true;
			}
			text = bufferedReader.readLine();

		}

		bufferedReader.close();

		for (int i = 0; i < knoteAnzahl; i++) {
			Knote k = new Knote(i);
			knoten.add(k);
			knoten.get(i).setKnoteList(getKindList(i, stringList, knoten));
		}

		return knoten;
	}

	public ArrayList<Integer> getKindList(int vaterKonte, ArrayList<String[]> stringList, ArrayList<Knote> knoten) {

		ArrayList<Integer> kindList = new ArrayList<Integer>();

		if (adjanzenMatrix) {

			for (int i = 0; i < stringList.get(vaterKonte).length; i++) {
				if (stringList.get(vaterKonte)[i].equals("1")) {
					kindList.add(i);
				}
			}

		} else {
			for (int i = 0; i < stringList.size(); i++) {
				String kindKnote = null;
				String[] s = stringList.get(i);

				if (s[0].equals("" + vaterKonte)) {

					kindKnote = s[1];

				} else if (s[1].equals("" + vaterKonte)) {

					kindKnote = s[0];
				}

				if (kindKnote != null) {
					kindList.add(Integer.parseInt(kindKnote));
				}
			}

		}

		return kindList;
	}

}
