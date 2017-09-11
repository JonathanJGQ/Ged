package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Grid.GridContextClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.model.Secao;

public class RevisarDocumentoView extends GenericView{
	
	public RevisarDocumentoView(){
		HorizontalLayout cabecalho = criarCabecalho("Revisar Documento", "revisar_documento_ajuda");
        
		Grid<Documento> grid = new Grid<>();
		
		List<Documento> lista = new ArrayList<>();
		Documento novo = new Documento();
		novo.setId((long) 1);
		novo.setNome("Documento 1");
		novo.setVersao("1.0");
		novo.setVigencia_inicio(Calendar.getInstance());
        novo.setVigencia_fim(Calendar.getInstance());
		Documento novo2 = new Documento();
		novo2.setId((long) 2);
		novo2.setNome("Documento 2");
		novo2.setVersao("2.0");
		novo2.setVigencia_inicio(Calendar.getInstance());
        novo2.setVigencia_fim(Calendar.getInstance());
		Documento novo3 = new Documento();
		novo3.setId((long) 3);
		novo3.setNome("Documento 3");
		novo3.setVersao("1.0");
		novo3.setVigencia_inicio(Calendar.getInstance());
        novo3.setVigencia_fim(Calendar.getInstance());
		Documento novo4 = new Documento();
		novo4.setId((long) 4);
		novo4.setNome("Documento 4");
		novo4.setVersao("1.0");
		novo4.setVigencia_inicio(Calendar.getInstance());
        novo4.setVigencia_fim(Calendar.getInstance());
		lista.add(novo);
		lista.add(novo2);
		lista.add(novo3);
		lista.add(novo4);
		
		grid.setItems(lista);
		grid.addColumn(Documento::getNome).setCaption("Título");
		grid.addColumn(Documento::getVigencia_inicio).setCaption("Inicio").setWidth(90);
        grid.addColumn(Documento::getVigencia_fim).setCaption("Fim").setWidth(90);
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(90);
		/*grid.addColumn(secao -> "Revisar",
			    new ButtonRenderer(clickEvent -> {
			    	UI.getCurrent().getNavigator().navigateTo("Tela Revisar Documento");
			    })).setWidth(getWidth()).setCaption("");*/
		grid.addItemClickListener(event ->
	    	UI.getCurrent().getNavigator().navigateTo("Tela Revisar Documento"));
		grid.setSizeFull();
		
		addComponents(cabecalho,grid);	      
	}
}
