package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import br.com.innovaro.gd.model.Documento;

public class AprovarDocumentoView extends GenericView{
	
	public AprovarDocumentoView(){
		HorizontalLayout cabecalho = criarCabecalho("Aprovar Documento", "aprovar_documento_ajuda");
        
		Grid<Documento> grid = new Grid<>();
		
		List<Documento> lista = new ArrayList<>();
		Documento novo = new Documento();
		novo.setId((long)1);
		novo.setNome("Documento 1");
		novo.setVersao("1.0");
		novo.setVigencia_inicio(new Date());
        novo.setVigencia_fim(new Date());
		Documento novo2 = new Documento();
		novo2.setId((long)2);
		novo2.setNome("Documento 2");
		novo2.setVersao("2.0");
		novo2.setVigencia_inicio(new Date());
        novo2.setVigencia_fim(new Date());
		Documento novo3 = new Documento();
		novo3.setId((long)3);
		novo3.setNome("Documento 3");
		novo3.setVersao("1.0");
		novo3.setVigencia_inicio(new Date());
        novo3.setVigencia_fim(new Date());
		Documento novo4 = new Documento();
		novo4.setId((long)4);
		novo4.setNome("Documento 4");
		novo4.setVersao("1.0");
		novo4.setVigencia_inicio(new Date());
        novo4.setVigencia_fim(new Date());
		Documento novo5 = new Documento();
		novo5.setId((long)5);
		novo5.setNome("Documento 5");
		novo5.setVersao("2.0");
		novo5.setVigencia_inicio(new Date());
        novo5.setVigencia_fim(new Date());
		lista.add(novo);
		lista.add(novo2);
		lista.add(novo3);
		lista.add(novo4);
		lista.add(novo5);
		
		grid.setItems(lista);
		grid.addColumn(Documento::getNome).setCaption("Título");
		grid.addColumn(Documento::getVigencia_inicio).setCaption("Inicio").setWidth(90);
        grid.addColumn(Documento::getVigencia_fim).setCaption("Fim").setWidth(90);
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(90);
		grid.addItemClickListener(event ->
    	UI.getCurrent().getNavigator().navigateTo("Tela Aprovar Documento"));
		grid.setSizeFull();
		 
		addComponents(cabecalho,grid);
	}
}
