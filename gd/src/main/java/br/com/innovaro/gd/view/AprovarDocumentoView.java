package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.Calendar;
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

public class AprovarDocumentoView extends GenericView{
	
	private List<Documento> lista;
	private DocumentoDao documentoDao;
	private Grid<Documento> grid;
	
	public AprovarDocumentoView(){
		HorizontalLayout cabecalho = criarCabecalho("Aprovar Documento", "aprovar_documento_ajuda");
        
		lista = new ArrayList<>();
		documentoDao = new DocumentoDao();
		grid = new Grid<>();
		
		grid.setItems(lista);
		grid.addColumn(Documento::getNome).setCaption("Título");
		Column<Documento, Date> inicioColumn = grid.addColumn(Documento::getVigencia_inicio,new DateRenderer("%1$te/%1$tm/%1$tY")).setCaption("Início").setWidth(140);
        Column<Documento, Date> fimColumn = grid.addColumn(Documento::getVigencia_fim,new DateRenderer("%1$te/%1$tm/%1$tY")).setCaption("Fim").setWidth(140);
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(90);
        
        grid.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClick event) {
				Documento documento = (Documento)event.getItem();
				UI.getCurrent().getNavigator().navigateTo("aprovarDocumento/" + documento.getId() + "/" + documento.getIdTemplate());
			}
		});
        
		grid.setSizeFull();
		 
		addComponents(cabecalho,grid);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		lista = documentoDao.buscaDocumentosPorStatus(DocumentoStatusType.APROVACAO.getTitle());
		grid.setItems(lista);
	}
}
