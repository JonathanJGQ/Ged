package br.com.innovaro.gd.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class DocumentoView extends VerticalLayout implements View{
	public DocumentoView() {
		Label title = new Label("Novo Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		HorizontalLayout codigoLayout = new HorizontalLayout();
		
		TextField codigo = new TextField("Código");
		TextField titulo = new TextField("Título");
		titulo.setSizeFull();
		TextField autor = new TextField("Autor");
		autor.setSizeFull();
		
		RichTextArea documento = new RichTextArea("Seção 1 - OBJETIVO");
		documento.setSizeFull();
		documento.setHeight("300px");
		
		
		RichTextArea documento2 = new RichTextArea("Seção 2 - ABRANGÊNCIA");
		documento2.setSizeFull();
		documento2.setHeight("300px");
		
		Button revisao = new Button("Enviar para revisão");
		revisao.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		codigoLayout.addComponents(codigo,revisao);
		codigoLayout.setWidth("100%");
		codigoLayout.setComponentAlignment(revisao, Alignment.BOTTOM_RIGHT);
		
		addComponents(title,codigoLayout,titulo,autor,documento,documento2);
		
	}
}
