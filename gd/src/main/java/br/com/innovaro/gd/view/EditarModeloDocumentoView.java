package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.dao.SecaoDao;
import br.com.innovaro.gd.model.Modelo;

public class EditarModeloDocumentoView extends GenericView{
	
	HorizontalLayout layoutHorizontal;
	VerticalLayout layoutVertical;
	TextField idText = new TextField();
	TextField nomeText = new TextField("Nome do Modelo");
	Button alterar;
	Modelo template;
	SecaoDao dao;
	List<Secao> lista;
	ModeloDao mdao;
	Grid<Secao> grid;
	TextField novaSecao;
	String value;
	ComboBox totalAprovacoes;
	
	public EditarModeloDocumentoView() {
		
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
			    	 dao.delete(secao.getId());
			    	 lista.remove(secao);
			    	 grid.setItems(lista);
			    })).setWidth(65).setCaption("");
		
		ButtonRenderer btn = new ButtonRenderer();
		
		grid.setSizeFull();
		addComponent(grid);
	}
	
	public VerticalLayout criaCampos() {
		
		HorizontalLayout cabecalho = criarCabecalho("Editar Modelo de Documento", "editar_modelo_documento_ajuda");
		
		Label label = new Label("<b>MODELO<b>",ContentMode.HTML);
		HorizontalLayout layout = new HorizontalLayout();
		layoutVertical = new VerticalLayout();
		
		totalAprovacoes = new ComboBox("Total de Aprovações");
		List<String> lista_total_aprovacoes = new ArrayList<>();
		lista_total_aprovacoes.add("1");
		lista_total_aprovacoes.add("2");
		lista_total_aprovacoes.add("3");
		lista_total_aprovacoes.add("4");
		lista_total_aprovacoes.add("5");
		lista_total_aprovacoes.add("6");
		lista_total_aprovacoes.add("7");
		lista_total_aprovacoes.add("8");
		lista_total_aprovacoes.add("9");
		lista_total_aprovacoes.add("10");
		totalAprovacoes.setItems(lista_total_aprovacoes);
		totalAprovacoes.setValue("1");
		totalAprovacoes.setEmptySelectionAllowed(false);
		
		alterar = new Button("Alterar");
		alterar.addStyleName(ValoTheme.BUTTON_PRIMARY);
		alterar.addClickListener(new ClickListener() {	
			@Override
			public void buttonClick(ClickEvent event) {
				Modelo modelo = new Modelo();
				modelo.setNome(nomeText.getValue());
				modelo.setId(Long.parseLong(value));
				modelo.setTotalAprovacoes(Integer.parseInt(totalAprovacoes.getValue().toString()));
				mdao.update(modelo);
				Notification.show("Nome do modelo alterado com sucesso!!", Notification.TYPE_HUMANIZED_MESSAGE);
			}
		});
		
		Label separator = new Label("<hr />",ContentMode.HTML);
		separator.setSizeFull();
		
		layout.addComponents(nomeText,totalAprovacoes,alterar);
		layout.setComponentAlignment(alterar, Alignment.BOTTOM_LEFT);
		layout.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		layoutVertical.addComponents(cabecalho,label,layout, separator);
		
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
		btnAdicionarSecao.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnAdicionarSecao.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Secao secao = new Secao();
				secao.setIdTemplate(Long.parseLong(value));
				secao.setNome(novaSecao.getValue());
				novaSecao.clear();
				novaSecao.focus();
				dao.save(secao);
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
	    Modelo modelo = mdao.findById(Long.parseLong(value)); 
	    nomeText.setValue(modelo.getNome());
	    totalAprovacoes.setValue(modelo.getTotalAprovacoes());
	    lista = dao.buscaSecaoPorModelo(Long.parseLong(value));
	    grid.setItems(lista);
	    novaSecao.setValue("");
	}
}
