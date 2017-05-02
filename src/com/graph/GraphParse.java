package com.graph;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GraphParse {

	private boolean adjanzenMatrix = false;
	private int knoteAnzahl;
	private ArrayList<String[]> stringList;
	
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
			if(s.length>3){
				adjanzenMatrix = true;
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
				
				float gewicht;
				
				

				if (kindKnote != null) {
					if(s.length>2){
						gewicht = Float.parseFloat(s[2]);
					}
					kindList.add(Integer.parseInt(kindKnote));
				}
			}

		}

		return kindList;
	}
	
	
	
	
	
	
	public ArrayList<Kante> parseKante() throws Exception {
		ArrayList<Kante> kanten = new ArrayList<Kante>();

		for (int i = 0; i < stringList.size(); i++) {
			String[] s = stringList.get(i);
			Kante k = new Kante(Integer.parseInt(s[0]) ,Integer.parseInt(s[1]),Float.parseFloat(s[2]));
			kanten.add(k);
		}
//		System.out.println(kanten);
		// Sorting
		Collections.sort(kanten, new Comparator<Kante>() {
		        @Override
		        public int compare(Kante Kante1, Kante Kante2)
		        {
		            return  Kante1.compareTo(Kante2);
		        }
		    });
		 
//		 System.out.println(kanten);
		

		return kanten;
	}

}
