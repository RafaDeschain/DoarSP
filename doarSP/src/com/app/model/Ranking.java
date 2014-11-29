package com.app.model;

public class Ranking {

	private int qtdDoacao;
	private String nome;
	
	public Ranking(int qtdDoacao, String nome)
	{
		this.qtdDoacao = qtdDoacao;
		this.nome = nome;	
	}
	
	public Ranking()
	{
		
	}
	
	public void setQtdDoacao(int qtdDoacao)
	{
		this.qtdDoacao = qtdDoacao;
		
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
		
	}
	
	public int getQtdDoacao()
	{
		return qtdDoacao;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	
}
