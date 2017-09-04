package br.com.innovaro.gd.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class RevisarDocumento extends VerticalLayout implements View{
	public RevisarDocumento() {
		Label title = new Label("Revisar Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		HorizontalLayout codigoLayout = new HorizontalLayout();
		
		TextField codigo = new TextField("Código");
		TextField titulo = new TextField("Título");
		titulo.setSizeFull();
		TextField autor = new TextField("Autor");
		autor.setSizeFull();
		
		codigo.setEnabled(false);
		titulo.setEnabled(false);
		autor.setEnabled(false);
		
		TextArea documento = new TextArea("Seção 1 - OBJETIVO");
		documento.setSizeFull();
		documento.setHeight("300px");
		documento.setEnabled(false);
		
		TextArea documento2 = new TextArea("Seção 2 - ABRANGÊNCIA");
		documento2.setSizeFull();
		documento2.setHeight("300px");
		documento2.setEnabled(false);
		
		
		Button revisao = new Button("Enviar para Aprovação");
		revisao.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		Button retornar = new Button("Retornar para Edição");
		retornar.setStyleName(ValoTheme.BUTTON_DANGER);
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.addComponents(retornar,revisao);
		
		codigoLayout.addComponents(codigo,buttonsLayout);
		codigoLayout.setWidth("100%");
		codigoLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
		
		RichTextArea nota = new RichTextArea("Notas de Revisão");
		nota.setSizeFull();
		nota.setHeight("300px");
		
		addComponents(title,codigoLayout,titulo,autor,documento,documento2,nota);
	}
}
