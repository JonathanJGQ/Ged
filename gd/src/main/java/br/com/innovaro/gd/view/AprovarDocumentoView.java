package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Documento;

public class AprovarDocumentoView extends VerticalLayout implements View{
	
	public AprovarDocumentoView(){
		Label title = new Label("Aprovar Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
		Grid<Documento> grid = new Grid<>();
		
		List<Documento> lista = new ArrayList<>();
		Documento novo = new Documento();
		novo.setId(1);
		novo.setNome("Documento 1");
		Documento novo2 = new Documento();
		novo2.setId(2);
		novo2.setNome("Documento 2");
		Documento novo3 = new Documento();
		novo3.setId(3);
		novo3.setNome("Documento 3");
		Documento novo4 = new Documento();
		novo4.setId(4);
		novo4.setNome("Documento 4");
		Documento novo5 = new Documento();
		novo5.setId(5);
		novo5.setNome("Documento 5");
		lista.add(novo);
		lista.add(novo2);
		lista.add(novo3);
		lista.add(novo4);
		lista.add(novo5);
		
		grid.setItems(lista);
		grid.addColumn(Documento::getId).setCaption("Código").setWidth(getWidth());;
		grid.addColumn(Documento::getNome).setCaption("Título");
		grid.addItemClickListener(event ->
    	UI.getCurrent().getNavigator().navigateTo("Tela Aprovar Documento"));
		grid.setSizeFull();
		
		addComponents(title,grid);	      
	}
}
