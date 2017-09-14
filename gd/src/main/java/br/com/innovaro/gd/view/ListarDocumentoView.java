package br.com.innovaro.gd.view;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.MyUI;
import br.com.innovaro.gd.dao.DocumentoDao;
import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.model.Modelo;
import br.com.innovaro.gd.type.DocumentoStatusType;
	
public class ListarDocumentoView extends GenericView{
	
	private ModeloDao dao;
	private List<Modelo> listaModelo;
	private Map<String,String> map;
	private ComboBox comboModelo;
	private DocumentoDao docDao;
	private List<Documento> listaDocumentos;
	private Grid<Documento> grid;
	private TextField nomeDocumento;
	
	public ListarDocumentoView() {
		
		docDao = new DocumentoDao();
		dao = new ModeloDao();
		listaModelo = new ArrayList<>();
		listaDocumentos = new ArrayList<>();
		map = new HashMap<>();
		
		HorizontalLayout cabecalho = criarCabecalho("Editar Documento", "editar_documento_ajuda");
        
		nomeDocumento = new TextField("Nome do Documento");
		nomeDocumento.setWidth(100,Unit.PERCENTAGE);
		
        comboModelo = new ComboBox();
        HorizontalLayout novoDocumentoLayout = new HorizontalLayout();
        novoDocumentoLayout.setCaption("Tipo de Modelo");
        novoDocumentoLayout.setWidth(100,Unit.PERCENTAGE);
        novoDocumentoLayout.setMargin(false);
        
        List<String> listaModelo = new ArrayList<>();
        listaModelo.add("Modelo 1");
        listaModelo.add("Modelo 2");
        listaModelo.add("Modelo 3");
        
        comboModelo.setItems(listaModelo);
        comboModelo.setEmptySelectionAllowed(false);
        comboModelo.setWidth(100,Unit.PERCENTAGE);
        
        Button novoDocumento = new Button(VaadinIcons.PLUS);
        novoDocumento.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        Button documentosAprovados = new Button("Documentos Aprovados");
        documentosAprovados.addStyleName(ValoTheme.BUTTON_PRIMARY);
        
        documentosAprovados.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("documentosAprovados");
			}
		});
        
        novoDocumento.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(nomeDocumento.getValue() != "") {
					String args[] = comboModelo.getValue().toString().split(" - ");
				    String id = args[0];
				    comboModelo.getValue();
				    janelaConfirmação(Long.parseLong(id));
				}
				else {
					Notification notification = new Notification("Insira um nome para o Documento!");
					notification.setStyleName(ValoTheme.NOTIFICATION_BAR);
					notification.setPosition(Position.TOP_CENTER);
					notification.setDelayMsec(3000);
					notification.show(Page.getCurrent());
				}
			}
		}); 
        
        novoDocumentoLayout.addComponents(comboModelo,novoDocumento,documentosAprovados);
        novoDocumentoLayout.setComponentAlignment(documentosAprovados, Alignment.MIDDLE_RIGHT);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
        
        grid = new Grid<>(); 
        grid.setItems(listaDocumentos);
        grid.addColumn(Documento::getNome).setCaption("Nome");
        Column<Documento, Date> inicioColumn = grid.addColumn(Documento::getVigencia_inicio,new DateRenderer("%1$te/%1$tm/%1$tY")).setCaption("Início").setWidth(140);
        Column<Documento, Date> fimColumn = grid.addColumn(Documento::getVigencia_fim,new DateRenderer("%1$te/%1$tm/%1$tY")).setCaption("Fim").setWidth(140);
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(90);
        grid.addColumn(Documento::getStatus).setCaption("Status").setWidth(150);
        grid.addColumn(template -> "X",
			new ButtonRenderer(clickEvent -> {
				Notification not = new Notification("Só é permitido deletar documentos com status 'Em Edição'");
				not.setStyleName(ValoTheme.NOTIFICATION_BAR);
				not.setPosition(Position.TOP_CENTER);
				not.setDelayMsec(3000);
				Documento documento = (Documento) clickEvent.getItem();
				if(documento.getStatus().equals(DocumentoStatusType.EDICAO.getTitle())) {
					docDao.delete(documento.getId());
					listaDocumentos.remove(documento);
					grid.setItems(listaDocumentos);
					not.setCaption("Documento deletado!");
					not.show(Page.getCurrent());
				}
				else {
					not.show(Page.getCurrent());
				}
			})).setWidth(65).setCaption("");
        grid.setSizeFull();
        
        grid.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClick event) {
				Documento documento = (Documento)event.getItem();
				if(documento.getStatus().equals("Em Edição")) {
					UI.getCurrent().getNavigator().navigateTo("novoDocumento/" + documento.getId() + "/" + documento.getIdTemplate());
				}
			}
		});
        
        addComponents(cabecalho,nomeDocumento,novoDocumentoLayout,grid);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		listaModelo = dao.findAll(null);
		List<String> lista = new ArrayList<>();
		for (Modelo modelo : listaModelo) {
			lista.add(modelo.getId() + " - " + modelo.getNome());
		}
		comboModelo.setItems(lista);
		if(lista.size() == 0) {
			comboModelo.setValue("");
		}
		else {
			comboModelo.setValue(lista.get(0));
		}
		
		listaDocumentos = docDao.buscaDocumentosNaoAprovados("status");
		grid.setItems(listaDocumentos);
	}
	
	private void janelaConfirmação(Long id) {
		VerticalLayout content = new VerticalLayout();
		
		Window window = new Window();

		Label txtLabel = new Label("Deseja criar um novo documento?",ContentMode.HTML);
		
		HorizontalLayout btnLayout = new HorizontalLayout();
		Button sim = new Button("Sim");
		sim.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		Button nao = new Button("Não");
		nao.addStyleName(ValoTheme.BUTTON_DANGER);
		
		btnLayout.addComponents(sim, nao);
		
		sim.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				
				DocumentoDao dao = new DocumentoDao();
				Documento doc = new Documento();
				doc.setIdTemplate(id);
				doc.setStatus("Em Edição");
				doc.setVersao("1.0");
				doc.setNome(nomeDocumento.getValue());
				nomeDocumento.clear();
				
				dao.save(doc);
				((MyUI) UI.getCurrent()).updateNotifications();
				UI.getCurrent().getNavigator().navigateTo("novoDocumento/" + doc.getId() + "/" +id);
				window.close();
			}
		});
		
		nao.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				window.close();
			}
		});
		
		window.setModal(true);
		window.center();
		window.setClosable(true);
		window.setWidth(400, Unit.PIXELS);
		
		content.addComponents(txtLabel,btnLayout);
		window.setContent(content);
		
		UI.getCurrent().addWindow(window);
	}
}
