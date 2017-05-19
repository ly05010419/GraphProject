package com.graph;

import java.util.HashSet;

public class GraphCompenent {
	private HashSet<Knote> _knoten;
	private HashSet<Kante> _kanten;

	public String toString() {
		if (_kanten == null) {
			return "<Knoten:" + _knoten;
		} else {
			return "<Knoten:" + _knoten + "---------------Kanten:" + _kanten + ">";
		}
	}

	public HashSet<Kante> getKanten() {
		if (_kanten == null) {
			_kanten = new HashSet<Kante>();
		}
		return _kanten;
	}

	public HashSet<Knote> getKnoten() {
		if (_knoten == null) {
			_knoten = new HashSet<Knote>();
		}
		return _knoten;
	}
}
