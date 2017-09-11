package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Registro;

public class ModeloRegistroView extends GenericView{
	
	public ModeloRegistroView() {
		
		HorizontalLayout cabecalho = criarCabecalho("Modelo de Registro", "modelo_registro_ajuda");
        
        Button novoRegistro = new Button(VaadinIcons.PLUS);
        novoRegistro.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        novoRegistro.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("novoRegistro");
			}
		}); 
        
        
        List<Registro> lista = new ArrayList<>();
        Registro novo = new Registro();
        novo.setNome("Registro 1");
        Registro novo2 = new Registro();
        novo2.setNome("Registro 2");
        Registro novo3 = new Registro();
        novo3.setNome("Registro 3");
        
        lista.add(novo);
        lista.add(novo2);
        lista.add(novo3);
        
        Grid<Registro> grid = new Grid<>();
        grid.setItems(lista);
        grid.addColumn(Registro::getNome).setCaption("Nome");
        grid.setSizeFull();
        
        addComponents(cabecalho,novoRegistro,grid);
        
	}
}
