package br.com.innovaro.gd.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.innovaro.gd.model.Secao;

public class SecaoDao {
	public static List<Secao> secoes = new ArrayList<>();
	
	public void add(Secao secao) {
		secoes.add(secao);
	}
	
	public List buscaSecao(long id) {
		List<Secao> lista = new ArrayList<>();
		
		for (Secao secao : lista) {
			System.out.println(secao.getIdTemplate());
			if(secao.getIdTemplate() == id) {
				lista.add(secao);
				System.out.println("Pegou");
			}
		}
		return lista;
	}
	
	public void remove(Secao secao) {
		this.secoes.remove(secao);
	}
}
