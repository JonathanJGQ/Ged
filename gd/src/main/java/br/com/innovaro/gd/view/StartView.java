package br.com.innovaro.gd.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class StartView extends VerticalLayout implements View{
	
	public StartView() {
		setSizeFull();
		
		HorizontalLayout layoutHorizontal = new HorizontalLayout(); 
		
		Button template = new Button("Modelo de Documento",
				new Button.ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo("Modelo de Documento");
				}
		});
		template.setStyleName("styleButton");
		template.setHeight("150px");
		template.setWidth("220px");
		template.setIcon(VaadinIcons.FILE_FONT);
		template.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
		
		Button documento = new Button("Novo Documento",
				new Button.ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo("Novo Documento");
				}
		});
		documento.setStyleName("styleButton");
		documento.setHeight("150px");
		documento.setWidth("220px");
		documento.setIcon(VaadinIcons.FILE_O);
		documento.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
		
		Button documentosRevisar = new Button(null,
				new Button.ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo("Revisar Documento");
				}
		});
		
		documentosRevisar.setHeight("150px");
		documentosRevisar.setWidth("220px");
		Label numRevisar = new Label("21");
		numRevisar.setStyleName("iconNumber");	
		documentosRevisar.setCaption("Documentos a Revisar");
		
		Button documentosAprovar = new Button(null,
				new Button.ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo("Aprovar Documento");
				}
		});
		documentosAprovar.setHeight("150px");
		documentosAprovar.setWidth("220px");
		documentosAprovar.setCaption("Documentos a Aprovar");
		
		Button btn1 = new Button("GEOPM - Operação e Manutenção");
		Button btn2 = new Button("GENGE - Engenharia");
		Button btn3 = new Button("GQSMS - Qualidade, Segurança, Meio Ambiente e Saúde");
		Button btn4 = new Button("GECOM - Comercial");
		Button btn5 = new Button("GERTI - Tecnologia da Informação");
		Button btn6 = new Button("GPLAN - Planejamento");
		
		btn1.setWidth(500,Unit.PIXELS);
		btn2.setWidth(500,Unit.PIXELS);
		btn3.setWidth(500,Unit.PIXELS);
		btn4.setWidth(500,Unit.PIXELS);
		btn5.setWidth(500,Unit.PIXELS);
		btn6.setWidth(500,Unit.PIXELS);
		
		VerticalLayout botoesLayout = new VerticalLayout();
		botoesLayout.addComponents(btn1,btn2,btn3,btn4,btn5,btn6);
		
		CssLayout pesquisarLayout = new CssLayout();
		TextField pesquisa = new TextField();
		Button btnPesquisar = new Button(VaadinIcons.SEARCH);
		pesquisarLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		pesquisa.setDescription("Pesquisar");
		
		pesquisarLayout.addComponents(pesquisa,btnPesquisar);
		
		layoutHorizontal.addComponents(documento,documentosRevisar,documentosAprovar,template);
		addComponents(pesquisarLayout,botoesLayout);
		setComponentAlignment(botoesLayout, Alignment.MIDDLE_CENTER);
		setComponentAlignment(pesquisarLayout, Alignment.MIDDLE_RIGHT);
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Bem Vindo");
    }
			
}
