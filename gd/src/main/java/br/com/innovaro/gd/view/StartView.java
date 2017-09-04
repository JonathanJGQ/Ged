package br.com.innovaro.gd.view;

import java.util.Locale;
import java.util.ResourceBundle;

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
		Button btn3 = new Button("GQSMS - Qualidade, Segurança, Meio Ambiente ...");
		Button btn4 = new Button("GECOM - Comercial");
		Button btn5 = new Button("GERTI - Tecnologia da Informação");
		Button btn6 = new Button("GPLAN - Planejamento");
		Button btn7 = new Button("ASCOM - Comunicação");
		Button btn8 = new Button("GERAS - Suprimentos");
		Button btn9 = new Button("GEREH - Recursos Humanos");
		Button btn10 = new Button("GCONT - Contabilidade");
		Button btn11 = new Button("GEFIN - Financeiro");
		Button btn12 = new Button("ASJUR - Jurídico");
		Button btn13 = new Button("ASDIR - Assesoria");
		
		int btnSize = 400;
		
		btn1.setWidth(btnSize,Unit.PIXELS);
		btn2.setWidth(btnSize,Unit.PIXELS);
		btn3.setWidth(btnSize,Unit.PIXELS);
		btn4.setWidth(btnSize,Unit.PIXELS);
		btn5.setWidth(btnSize,Unit.PIXELS);
		btn6.setWidth(btnSize,Unit.PIXELS);
		btn7.setWidth(btnSize,Unit.PIXELS);
		btn8.setWidth(btnSize,Unit.PIXELS);
		btn9.setWidth(btnSize,Unit.PIXELS);
		btn10.setWidth(btnSize,Unit.PIXELS);
		btn11.setWidth(btnSize,Unit.PIXELS);
		btn12.setWidth(btnSize,Unit.PIXELS);
		btn13.setWidth(btnSize,Unit.PIXELS);
		
		HorizontalLayout todosBotoesLayout = new HorizontalLayout();
		todosBotoesLayout.setMargin(false);
		
		VerticalLayout botoesLayout = new VerticalLayout();
		botoesLayout.addComponents(btn1,btn2,btn3,btn4,btn5,btn6,btn7);
		botoesLayout.setMargin(false);
		
		VerticalLayout botoesLayout2 = new VerticalLayout();
		botoesLayout2.addComponents(btn8,btn9,btn10,btn11,btn12,btn13);
		botoesLayout2.setMargin(false);
		
		todosBotoesLayout.addComponents(botoesLayout,botoesLayout2);
		
		CssLayout pesquisarLayout = new CssLayout();
		TextField pesquisa = new TextField();
		pesquisa.setWidth(500,Unit.PIXELS);
		Button btnPesquisar = new Button(VaadinIcons.SEARCH);
		pesquisarLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		pesquisa.setPlaceholder("Pesquisar");
		pesquisa.setDescription("Pesquisar");
		
		pesquisarLayout.addComponents(pesquisa,btnPesquisar);
		
		layoutHorizontal.addComponents(documento,documentosRevisar,documentosAprovar,template);
		addComponents(pesquisarLayout,todosBotoesLayout);
		setComponentAlignment(todosBotoesLayout, Alignment.MIDDLE_CENTER);
		setComponentAlignment(pesquisarLayout, Alignment.MIDDLE_RIGHT);
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
        //Notification.show("Bem Vindo");
    }
			
}
