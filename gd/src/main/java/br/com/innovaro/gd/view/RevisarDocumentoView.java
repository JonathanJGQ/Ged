package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.DateRenderer;

import br.com.innovaro.gd.dao.DocumentoDao;
import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.type.DocumentoStatusType;

public class RevisarDocumentoView extends GenericView{
	
	private List<Documento> lista;
	private DocumentoDao documentoDao;
	private Grid<Documento> grid;
	
	public RevisarDocumentoView(){
		HorizontalLayout cabecalho = criarCabecalho("Revisar Documento", "revisar_documento_ajuda");
        
		grid = new Grid<>();
		
		lista = new ArrayList<>();
		documentoDao = new DocumentoDao();
		
		grid.setItems(lista);
		grid.addColumn(Documento::getNome).setCaption("Título");
		Column<Documento, Date> inicioColumn = grid.addColumn(Documento::getVigencia_inicio,new DateRenderer("%1$te/%1$tm/%1$tY")).setCaption("Início").setWidth(140);
        Column<Documento, Date> fimColumn = grid.addColumn(Documento::getVigencia_fim,new DateRenderer("%1$te/%1$tm/%1$tY")).setCaption("Fim").setWidth(140);
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(90);
		grid.addItemClickListener(event ->
	    	UI.getCurrent().getNavigator().navigateTo("Tela Revisar Documento"));
		grid.setSizeFull();
		
		grid.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClick event) {
				Documento documento = (Documento)event.getItem();
				UI.getCurrent().getNavigator().navigateTo("revisarDocumento/" + documento.getId() + "/" + documento.getIdTemplate());
			}
		});
		
		addComponents(cabecalho,grid);	      
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		lista = documentoDao.buscaDocumentosPorStatus(DocumentoStatusType.REVISAO.getTitle());
		grid.setItems(lista);
	}
}
