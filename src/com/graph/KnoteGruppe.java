package com.graph;

public class KnoteGruppe {
	private KnoteGruppe nueKnoteGruppe;
	

	private int knoteGruppeId = 0;

	public KnoteGruppe(int id){
		
		this.knoteGruppeId = id;
	}
	
	public String toString(){
		
		return "knoteGruppeId:"+knoteGruppeId;
	}
	
	public KnoteGruppe getKnoteGruppe() {
		if (this.nueKnoteGruppe != null) {
			return this.nueKnoteGruppe.getKnoteGruppe();
		} else {
			return this;
		}
	}
	
	public void setKnoteGruppe(KnoteGruppe knoteGruppe){
		if (this.nueKnoteGruppe != null) {
			this.nueKnoteGruppe.setKnoteGruppe(knoteGruppe);
		}else{
			this.nueKnoteGruppe = knoteGruppe;
		}
		
	}
	
	
}
