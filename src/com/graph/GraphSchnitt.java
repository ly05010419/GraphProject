package com.graph;

public class GraphSchnitt {
	private GraphSchnitt vaterGraphSchnitt;
	

	private int graphSchnittId = 0;

	public GraphSchnitt(int id){
		
		this.graphSchnittId = id;
	}
	
	public String toString(){
		
		return "SchnittMengeId:"+graphSchnittId;
	}
	
	public GraphSchnitt getGraphSchnitt() {
		if (this.vaterGraphSchnitt != null) {
			return this.vaterGraphSchnitt.getGraphSchnitt();
		} else {
			return this;
		}
	}
	
	public void setSchnittMenge(GraphSchnitt schnittMenge){
		if (this.vaterGraphSchnitt != null) {
			this.vaterGraphSchnitt.setSchnittMenge(schnittMenge);
		}else{
			this.vaterGraphSchnitt = schnittMenge;
		}
		
	}
	
	
}
