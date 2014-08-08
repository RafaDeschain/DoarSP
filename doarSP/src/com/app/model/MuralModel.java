package com.app.model;



public class MuralModel {
	
	private int codMural, codSolicitacao, imagem;
	private String comentario ;
	
	public MuralModel(int codMural, int codSolicitacao, String comentario, int imagem  ){
		setCodMural(codMural);
		setCodSolicitacao(codSolicitacao);
		setComentario(comentario);
		setImagem(imagem);
	}
	
	public MuralModel(){
		
	}

	public int getCodMural() {
		return codMural;
	}

	public void setCodMural(int codMural) {
		this.codMural = codMural;
	}

	public int getCodSolicitacao() {
		return codSolicitacao;
	}

	public void setCodSolicitacao(int codSolicitacao) {
		this.codSolicitacao = codSolicitacao;
	}

	public int getImagem() {
		return imagem;
	}

	public void setImagem(int imagem) {
		this.imagem = imagem;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}