package br.com.innovaro.gd.view;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinServletService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.TemplateDao;
import br.com.innovaro.gd.model.Template;

public class TemplateView extends VerticalLayout implements View{
	
	private Grid<Template> grid;
	List<Template> lista;
	TemplateDao dao;
	TextField novoTemplate;
	Button btnAdicionarTemplate;
	CssLayout layout;
	
	public TemplateView() {
		
		dao = new TemplateDao();
		lista = TemplateDao.templates;
		
		Label title = new Label("Novo Modelo de Documento");
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
		btnAdicionarTemplate = new Button("Adicionar");
		btnAdicionarTemplate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Template novo = new Template();
				novo.setId(TemplateDao.id);
				novo.setNome(novoTemplate.getValue());
				dao.add(novo);
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
		grid.addColumn(Template::getId).setCaption("Código").setWidth(getWidth());
		grid.addColumn(Template::getNome).setCaption("Nome do Template");
		grid.addColumn(template -> "Editar",
			      new ButtonRenderer(clickEvent -> {
			    	  Template tmpl = (Template)clickEvent.getItem();
			          VaadinServletService.getCurrentRequest().getWrappedSession().setAttribute("template", tmpl);
			          UI.getCurrent().getNavigator().navigateTo("editTemplate");
			    })).setWidth(getWidth()).setCaption("");
		grid.addColumn(template -> "Deletar",
			      new ButtonRenderer(clickEvent -> {
			          //lista.remove(clickEvent.getItem());
			    	  Template tmpl = (Template)clickEvent.getItem();
			    	  dao.remove(tmpl);
			    	  grid.setItems(lista);
			    })).setWidth(getWidth()).setCaption("");
		grid.setSizeFull();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
}
