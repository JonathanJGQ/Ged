package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.dao.SecaoDao;
import br.com.innovaro.gd.model.Modelo;
import br.com.innovaro.gd.model.Secao;

public class EditModeloView extends VerticalLayout implements View{
	
	HorizontalLayout layoutHorizontal;
	VerticalLayout layoutVertical;
	TextField idText = new TextField();
	TextField nomeText = new TextField();
	Button alterar;
	Modelo template;
	List<Secao> lista;
	SecaoDao dao;
	ModeloDao mdao;
	String value;
	Grid<Secao> grid;
	TextField novaSecao;
	
	public EditModeloView() {
		
		dao = new SecaoDao();
		mdao = new ModeloDao();
		
		addComponent(criaCampos());
		addComponent(adicionarSecao());
		
		grid = new Grid();
		lista = new ArrayList<>();
		
		grid.setItems(lista);
		grid.addColumn(Secao::getNome).setCaption("Nome do Template");
		grid.addColumn(template -> "X",
			    new ButtonRenderer(clickEvent -> {
			    	 Secao secao = (Secao) clickEvent.getItem();
			    	 dao.exclui(secao.getId());
			    	 lista.remove(secao);
			    	 grid.setItems(lista);
			    })).setWidth(65).setCaption("");
		
		ButtonRenderer btn = new ButtonRenderer();
		
		grid.setSizeFull();
		addComponent(grid);
	}
	
	public VerticalLayout criaCampos() {
		
		Label title = new Label("Editar Modelo de Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		Label label = new Label("<b>MODELO<b>",ContentMode.HTML);
		CssLayout layoutCss = new CssLayout();
		layoutVertical = new VerticalLayout();
		
		
		idText.setCaption("Código");
		idText.setEnabled(false);
		
		alterar = new Button("Alterar");
		alterar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Modelo modelo = new Modelo();
				modelo.setNome(nomeText.getValue());
				modelo.setId(Long.parseLong(value));
				mdao.atualiza(modelo);
				Notification.show("Nome do modelo alterado com sucesso!!", Notification.TYPE_HUMANIZED_MESSAGE);
			}
		});
		
		Label separator = new Label("<hr />",ContentMode.HTML);
		separator.setSizeFull();
		
		layoutCss.addComponents(nomeText,alterar);
		layoutCss.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		layoutCss.setCaption("Nome");
		layoutVertical.addComponents(title,label,layoutCss, separator);
		
		layoutVertical.setMargin(false);
		
		return layoutVertical;
	}
	
private VerticalLayout adicionarSecao() {
		
		novaSecao = new TextField("Nome da Seção");
		novaSecao.setWidth(100, Unit.PERCENTAGE);
		
		List<String> dadosTipoSecao = new ArrayList<>();
		dadosTipoSecao.add("Campo");
		dadosTipoSecao.add("Texto");
		
		
		Button btnAdicionarSecao = new Button("Adicionar");
		btnAdicionarSecao.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Secao secao = new Secao();
				secao.setIdTemplate(Long.parseLong(value));
				secao.setNome(novaSecao.getValue());
				dao.salva(secao);
				lista.add(secao);
				grid.setItems(lista);
			}
		});
		
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponents(novaSecao);
		layout.setMargin(false);
		layout.setSizeFull();
		VerticalLayout vertical = new VerticalLayout();
		vertical.addComponents(new Label("<b>SEÇÃO<b>",ContentMode.HTML),layout,btnAdicionarSecao);
		vertical.setMargin(false);
		
		return vertical;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		String args[] = event.getParameters().split("/");
	    value = args[0];
	    Modelo modelo = mdao.buscaPorId(Long.parseLong(value)); 
	    nomeText.setValue(modelo.getNome());
	    lista = dao.buscaSecaoPorModelo(Long.parseLong(value));
	    grid.setItems(lista);
	    novaSecao.setValue("");
		
	}
	
}
