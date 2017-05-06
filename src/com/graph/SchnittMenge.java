package com.graph;

public class SchnittMenge {
	public SchnittMenge schnittMenge;
	private int SchnittMengeId = 0;

	public SchnittMenge(int id){
		
		this.SchnittMengeId = id;
	}
	
	public String toString(){
		
		return "SchnittMengeId:"+SchnittMengeId;
	}
	
	public SchnittMenge getSchnittMenge() {
		if (this.schnittMenge != null) {
			return this.schnittMenge.getSchnittMenge();
		} else {
			return this;
		}
	}
}
