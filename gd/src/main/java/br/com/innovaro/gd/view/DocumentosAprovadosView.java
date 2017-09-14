package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.ItemClickListener;

import br.com.innovaro.gd.dao.ConteudoDao;
import br.com.innovaro.gd.dao.DocumentoDao;
import br.com.innovaro.gd.model.Conteudo;
import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.type.DocumentoStatusType;

public class DocumentosAprovadosView extends GenericView{
	
	private Grid<Documento> grid;
	private List<Documento> lista;
	private DocumentoDao documentoDao;
	private ConteudoDao conteudoDao;
	
	public DocumentosAprovadosView() {
		HorizontalLayout cabecalho = criarCabecalho("Documentos Aprovados", "documentos_aprovados_ajuda");
		
		grid = new Grid<>();
		lista = new ArrayList<>();
		documentoDao = new DocumentoDao();
		conteudoDao = new ConteudoDao();

        grid.setItems(lista);
        grid.addColumn(Documento::getNome).setCaption("Nome");
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(100);
        grid.addColumn(Documento::getStatus).setCaption("Status").setWidth(150);
        grid.setSizeFull();
        
        grid.addItemClickListener(new ItemClickListener() {
			public void itemClick(ItemClick event) {
				Window window = new Window();
				FormLayout content = new FormLayout();
				
				Documento doc = (Documento) event.getItem();
				
				Label texto = new Label("Deseja criar uma nova versão para o documento?",ContentMode.HTML);
				
				HorizontalLayout btnLayout = new HorizontalLayout();
				btnLayout.setMargin(false);
				Button sim = new Button("Sim");
				Button nao = new Button("Não");
				btnLayout.addComponents(sim,nao);
				btnLayout.setComponentAlignment(sim, Alignment.MIDDLE_CENTER);
				btnLayout.setComponentAlignment(nao, Alignment.MIDDLE_CENTER);
				
				sim.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Documento novoDocumento = new Documento();
						novoDocumento.setIdTemplate(doc.getIdTemplate());
						novoDocumento.setAutor(doc.getAutor());
						novoDocumento.setNome(doc.getNome());
						novoDocumento.setStatus(DocumentoStatusType.EDICAO.getTitle());
						String versao = doc.getVersao();
						versao = String.valueOf(Float.parseFloat(versao) + 1.0);
						novoDocumento.setVersao(versao);
						documentoDao.save(novoDocumento);
						List<Conteudo> listaConteudo = conteudoDao.buscaConteudosPorDocumento(doc.getId());
						for(Conteudo conteudo : listaConteudo) {
							Conteudo novoConteudo = new Conteudo();
							novoConteudo.setConteudo(conteudo.getConteudo());
							novoConteudo.setIdDocumento(novoDocumento.getId());
							novoConteudo.setIdSecao(conteudo.getIdSecao());
							conteudoDao.save(novoConteudo);
						}
						window.close();
						UI.getCurrent().getNavigator().navigateTo("novoDocumento/" + novoDocumento.getId() + "/" + novoDocumento.getIdTemplate());
					}
				});
				
				nao.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						window.close();
						
					}
				});
				
				content.addComponents(texto,btnLayout);
				
				window.center();
				window.setModal(true);
				window.setWidth(500,Unit.PIXELS);
				window.setCaption("AVISO");
				window.setContent(content);	
				
				UI.getCurrent().addWindow(window);
			}
		});
        
        addComponents(cabecalho,grid);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		lista = documentoDao.buscaDocumentosPorStatus(DocumentoStatusType.APROVADO.getTitle());
		grid.setItems(lista);
	}
}
