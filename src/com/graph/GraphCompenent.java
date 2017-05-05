package com.graph;

import java.util.HashSet;

public class GraphCompenent {
	private HashSet<Knote> _knoten;
	private HashSet<UngerichtetKante> _kanten;

	public String toString() {
		if (_kanten == null) {
			return "<Knoten:" + _knoten;
		} else {
			return "<Knoten:" + _knoten + "---------------Kanten:" + _kanten + ">";
		}
	}

	public HashSet<UngerichtetKante> getKanten() {
		if (_kanten == null) {
			_kanten = new HashSet<UngerichtetKante>();
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
