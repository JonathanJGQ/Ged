package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.model.Modelo;

public class ListarDocumentoView extends VerticalLayout implements View{
	
	private ModeloDao dao;
	private List<Modelo> listaModelo;
	private ComboBox modeloDocumento;
	private Map<String,String> map;
	private TextField idModelo;
	
	public ListarDocumentoView() {
		
		dao = new ModeloDao();
		listaModelo = new ArrayList<>();
		map = new HashMap<>();
		
		Label title = new Label("Editar Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
        HorizontalLayout novoDocumentoLayout = new HorizontalLayout();
        novoDocumentoLayout.setWidth(100,Unit.PERCENTAGE);
        novoDocumentoLayout.setMargin(false);
        
        Button novoDocumento = new Button(VaadinIcons.PLUS);
        novoDocumento.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        modeloDocumento = new ComboBox();
        modeloDocumento.setWidth(100,Unit.PERCENTAGE);
        modeloDocumento.setEmptySelectionAllowed(false);
        idModelo = new TextField();
        idModelo.setVisible(false);
        
        
        novoDocumentoLayout.addComponents(modeloDocumento,novoDocumento,idModelo);
        
        novoDocumento.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				String args[] = modeloDocumento.getValue().toString().split(" - ");
			    String id = args[0];
				modeloDocumento.getValue();
				UI.getCurrent().getNavigator().navigateTo("novoDocumento/" + id);
			}
		}); 
        
        
        List<Documento> lista = new ArrayList<>();
        Documento novo = new Documento();
        novo.setNome("Documento 1");
        novo.setStatus("Em Revisão");
        
        Documento novo2 = new Documento();
        novo2.setNome("Documento 2");
        novo2.setStatus("Em Aprovação");
        
        Documento novo3 = new Documento();
        novo3.setNome("Documento 3");
        novo3.setStatus("Em Edição");
        
        lista.add(novo);
        lista.add(novo2);
        lista.add(novo3);
        
        Grid<Documento> grid = new Grid<>();
        grid.setItems(lista);
        grid.addColumn(Documento::getNome).setCaption("Nome");
        grid.addColumn(Documento::getStatus).setCaption("Status").setWidth(150);
        //grid.addStyleName("testeGrid");
        grid.setSizeFull();
        
        addComponents(title,novoDocumentoLayout,grid);
        
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		listaModelo = dao.buscaTodos(null);
		List<String> lista = new ArrayList<>();
		for (Modelo modelo : listaModelo) {
			lista.add(modelo.getId() + " - " + modelo.getNome());
		}
		modeloDocumento.setItems(lista);
		if(lista == null) {
			modeloDocumento.setValue("");
		}
		else {
			modeloDocumento.setValue(lista.get(0));
		}
	}
}
