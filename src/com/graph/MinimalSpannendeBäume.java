package com.graph;

import java.util.HashSet;

public class MinimalSpannendeBäume {
	private HashSet<Integer> _knoten;
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

	public HashSet<Integer> getKnoten() {
		if (_knoten == null) {
			_knoten = new HashSet<Integer>();
		}
		return _knoten;
	}
}
