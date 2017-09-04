package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.model.Modelo;
import br.com.innovaro.gd.model.Secao;

public class ModeloView extends VerticalLayout implements View{
	
	private Grid<Modelo> grid;
	List<Modelo> lista;
	ModeloDao dao;
	TextField novoTemplate;
	Button btnAdicionarTemplate;
	CssLayout layout;
	
	public ModeloView() {
		
		dao = new ModeloDao();
		lista = new ArrayList<>();
		
		Label title = new Label("Modelo de Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		criarGrid();
		addComponents(title,adicionarTemplate());
		addComponents(grid);
	}
	
	private CssLayout adicionarTemplate() {
		
		novoTemplate = new TextField();
		novoTemplate.setWidth(70.0f, Unit.PERCENTAGE);
		btnAdicionarTemplate = new Button(VaadinIcons.PLUS);
		btnAdicionarTemplate.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnAdicionarTemplate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Modelo novo = new Modelo();
				novo.setNome(novoTemplate.getValue());
				dao.salva(novo);
				lista.add(novo);
				grid.setItems(lista);
			}
		});
		
		layout = new CssLayout();
		layout.addComponent(novoTemplate);
		layout.addComponent(btnAdicionarTemplate);
		layout.setCaption("Nome do Modelo");
		layout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		return layout;
	}
	
	private void criarGrid() {
		
		grid = new Grid<>();
		grid.setItems(lista);
		grid.addColumn(Modelo::getNome).setCaption("Nome do Modelo");
		grid.addColumn(template -> "X",
			    new ButtonRenderer(clickEvent -> {
			    	 Modelo modelo = (Modelo) clickEvent.getItem();
			    	 dao.exclui(modelo.getId());
			    	 lista.remove(modelo);
			    	 grid.setItems(lista);
			    })).setWidth(65).setCaption("");
		grid.addItemClickListener(event ->
			UI.getCurrent().getNavigator().navigateTo("editarModelo" + "/" + event.getItem().getId()));
		grid.setSizeFull();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		lista = dao.buscaTodos(null);
		grid.setItems(lista);
	}
}
