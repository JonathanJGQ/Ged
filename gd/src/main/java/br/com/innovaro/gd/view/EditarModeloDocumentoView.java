package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.shared.ui.grid.DropLocation;
import com.vaadin.shared.ui.grid.DropMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.components.grid.GridDropTarget;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.dao.SecaoDao;
import br.com.innovaro.gd.model.Modelo;
import br.com.innovaro.gd.model.Secao;

public class EditarModeloDocumentoView extends GenericView{
	
	private HorizontalLayout layoutHorizontal;
	private VerticalLayout layoutVertical;
	private TextField idText = new TextField();
	private TextField nomeText = new TextField("Nome do Modelo");
	private Button alterar;
	private Modelo template;
	private SecaoDao dao;
	private List<Secao> lista;
	private ModeloDao mdao;
	private Grid<Secao> grid;
	private TextField novaSecao;
	private String value;
	private ComboBox totalAprovacoes;
	private Set<Secao> draggedItems;
	private ComboBox posicao;
	
	public EditarModeloDocumentoView() {
		
		dao = new SecaoDao();
		mdao = new ModeloDao();
		
		addComponent(criaCampos());
		addComponent(adicionarSecao());
		
		grid = new Grid();
		lista = new ArrayList<>();
		
		
		grid.setItems(lista);
		grid.addColumn(Secao::getPosicao).setCaption("Posição").setWidth(110);
		grid.addColumn(Secao::getNome).setCaption("Nome do Template");
		grid.addColumn(template -> "X",
			    new ButtonRenderer(clickEvent -> {
			    	 Secao secao = (Secao) clickEvent.getItem();
			    	 dao.delete(secao.getId());
			    	 lista.remove(secao);
			    	 for(Secao sec : lista) {
			    		 if(sec.getPosicao() >= secao.getPosicao()) {
			    			 sec.setPosicao(sec.getPosicao()-1);
			    			 dao.update(sec);
			    		 }
			    	 }
			    	 grid.setItems(lista);
			    	 populaPosicaoCombo();
			    })).setWidth(65).setCaption("");
		
		grid.addItemClickListener(new ItemClickListener() {

			public void itemClick(ItemClick event) {
				Secao secao = (Secao) event.getItem();
				criarJanelaAlteraSecao(secao);
			}

		});
		
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
		posicao = new ComboBox("Posição da Seção");
		posicao.setEmptySelectionAllowed(false);
		
		btnAdicionarSecao.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Secao secao = new Secao();
				secao.setIdTemplate(Long.parseLong(value));
				secao.setNome(novaSecao.getValue());
				secao.setPosicao(checarPosicao());
				novaSecao.clear();
				novaSecao.focus();
				dao.save(secao);
				lista.add(secao);
				grid.setItems(lista);
				populaPosicaoCombo();
			}
		});
		
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponents(novaSecao,posicao);
		layout.setMargin(false);
		layout.setSizeFull();
		VerticalLayout vertical = new VerticalLayout();
		vertical.addComponents(new Label("<b>SEÇÃO<b>",ContentMode.HTML),layout,btnAdicionarSecao);
		vertical.setMargin(false);
		
		return vertical;
	}
	
	private int checarPosicao() {
		System.out.println(posicao.getValue().toString());
		int pos = Integer.parseInt(posicao.getValue().toString());
		for(Secao secao : lista) {
			if(secao.getPosicao() == pos) {
				secao.setPosicao(lista.size()+1);
				dao.update(secao);
			}
		}
		return pos;
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		String args[] = event.getParameters().split("/");
	    value = args[0];
	    Modelo modelo = mdao.findById(Long.parseLong(value)); 
	    nomeText.setValue(modelo.getNome());
	    totalAprovacoes.setValue(modelo.getTotalAprovacoes());
	    lista = dao.buscaSecaoPorModelo(Long.parseLong(value));
	    populaPosicaoCombo();
	    grid.setItems(lista);
	    novaSecao.setValue("");
	}
	
	private void populaPosicaoCombo() {
		
		posicao.clear();
		
		List<Integer> listaPosicao = new ArrayList<>();
		for(int i=0;i<lista.size()+1;i++) {
			listaPosicao.add(i+1);
		}
		posicao.setItems(listaPosicao);
		posicao.setValue(listaPosicao.get(listaPosicao.size()-1));
		
	}

	private void criarJanelaAlteraSecao(Secao secao) {
		Window window = new Window("Alterar Seção");
		window.center();
		window.setWidth(50,Unit.PERCENTAGE);
		window.setModal(true);
		
		FormLayout content = new FormLayout();
		TextField txtNomeSecao = new TextField();
		txtNomeSecao.setValue(secao.getNome());
		txtNomeSecao.setWidth(100,Unit.PERCENTAGE);
		
		ComboBox posCombo = new ComboBox("Posição");
		List<Integer> listaPosicao = new ArrayList<>();
		for(int i=0;i<lista.size();i++) {
			listaPosicao.add(i+1);
		}
		posCombo.setItems(listaPosicao);
		posCombo.setValue(secao.getPosicao());
		
		HorizontalLayout btnLayout = new HorizontalLayout();
		Button alterar = new Button("Alterar");
		alterar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		Button cancelar = new Button("Cancelar");
		cancelar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		alterar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				for(Secao sec : lista) {
					if(secao != sec) {
						if(sec.getPosicao() == Integer.parseInt(posCombo.getValue().toString())) {
							sec.setPosicao(secao.getPosicao());
							dao.update(sec);
						}
					}
				}
				secao.setNome(txtNomeSecao.getValue());
				secao.setPosicao(Integer.parseInt(posCombo.getValue().toString()));
				dao.update(secao);
				window.close();
				Page.getCurrent().reload();
			}
		});
		
		cancelar.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				window.close();
			}
		});
		
		btnLayout.addComponents(alterar,cancelar);
		content.addComponents(txtNomeSecao,btnLayout,posCombo);
		window.setContent(content);
		
		UI.getCurrent().addWindow(window);
	}
}
