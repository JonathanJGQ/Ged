package br.com.innovaro.gd.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.innovaro.gd.model.Conteudo;

public class ConteudoDao extends GenericDao<Conteudo,Long>{
	
	@SuppressWarnings("unchecked")
	public List<Conteudo> buscaConteudosPorDocumento(Long documento_id){
		EntityManager entityManager = JpaUtil.getEntityManager();
		Query query = entityManager.createQuery("SELECT c.id, c.conteudo, c.idSecao " +
												"FROM Modelo m, Secao s, Conteudo c, Documento d " +
												"WHERE m.id = s.idTemplate AND " +
												"d.idTemplate = m.id AND " +
												"c.idSecao = s.id AND " +
												"d.id = '"+ documento_id + "'");
		
		List<Conteudo>listaConteudo = new ArrayList<>();
		List<Object[]> lista = query.getResultList();
		for (Object[] object : lista) {
			Conteudo conteudo = new Conteudo();
			conteudo.setId(Long.parseLong(object[0].toString()));
			conteudo.setConteudo(object[1].toString());
			conteudo.setIdSecao(Long.parseLong(object[2].toString()));
			listaConteudo.add(conteudo);
		}
		return listaConteudo;
	}
}
