package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Documento;

public class DocumentosAprovadosView extends GenericView{
	
	public DocumentosAprovadosView() {
		HorizontalLayout cabecalho = criarCabecalho("Documentos Aprovados", "documentos_aprovados_ajuda");
		
		Grid<Documento> grid = new Grid<>();
		List<Documento> lista = new ArrayList<>();
		Documento novo = new Documento();
        novo.setNome("Documento 4");
        novo.setVersao("1.0");
        novo.setStatus("Aprovado");
        
        Documento novo2 = new Documento();
        novo2.setNome("Documento 5");
        novo2.setVersao("1.0");
        novo2.setStatus("Aprovado");
        
        Documento novo3 = new Documento();
        novo3.setNome("Documento 6");
        novo3.setVersao("1.0");
        novo3.setStatus("Aprovado");
        
        lista.add(novo);
        lista.add(novo2);
        lista.add(novo3);
        
        grid.setItems(lista);
        grid.addColumn(Documento::getNome).setCaption("Nome");
        grid.addColumn(Documento::getVersao).setCaption("Versão").setWidth(100);
        grid.addColumn(Documento::getStatus).setCaption("Status").setWidth(150);
        //grid.addStyleName("testeGrid");
        grid.setSizeFull();
        
        grid.addItemClickListener(new ItemClickListener() {
			public void itemClick(ItemClick event) {
				Window window = new Window();
				FormLayout content = new FormLayout();
				
				Documento doc =(Documento) event.getItem();
				
				Label texto = new Label("Deseja criar uma nova versão para o documento ahsuahsuahus: " + doc.getNome() + " ?",ContentMode.HTML);
				texto.addStyleName("lineBreak");
				
				HorizontalLayout btnLayout = new HorizontalLayout();
				btnLayout.setMargin(false);
				Button sim = new Button("Sim");
				Button nao = new Button("Não");
				btnLayout.addComponents(sim,nao);
				btnLayout.setComponentAlignment(sim, Alignment.MIDDLE_CENTER);
				btnLayout.setComponentAlignment(nao, Alignment.MIDDLE_CENTER);
				
				content.addComponents(texto,btnLayout);
				content.addStyleName("lineBreak");
				
				window.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
				window.center();
				window.setWidth(500,Unit.PIXELS);
				window.setCaption("AVISO");
				window.setContent(content);	
				
				UI.getCurrent().addWindow(window);
			}
		});
        
        addComponents(cabecalho,grid);
	}
}
