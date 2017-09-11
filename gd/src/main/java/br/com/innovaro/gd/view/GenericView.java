package br.com.innovaro.gd.view;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.BundleStrings;

public class GenericView extends VerticalLayout implements View{
	
	
	public HorizontalLayout criarCabecalho(String titulo, String tipo_ajuda) {
		HorizontalLayout cabecalhoLayout = new HorizontalLayout();
		cabecalhoLayout.setMargin(false);
		cabecalhoLayout.setWidth(100,Unit.PERCENTAGE);
		
		Label title = new Label(titulo);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
        Button ajuda = new Button(VaadinIcons.QUESTION_CIRCLE_O);
        ajuda.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        ajuda.setDescription("Ajuda");
        
        ajuda.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				criarJanelaAjuda(tipo_ajuda);
			}
		});
        
        cabecalhoLayout.addComponents(title,ajuda);
        cabecalhoLayout.setComponentAlignment(ajuda, Alignment.MIDDLE_RIGHT);
        
        return cabecalhoLayout;
	}
	
	private void criarJanelaAjuda(String tipo_ajuda) {
		Window janelaAjuda = new Window("AJUDA");
		
		FormLayout content = new FormLayout();
        content.setMargin(true);
        
        Label texto = new Label("",ContentMode.HTML);
        BundleStrings bundle = new BundleStrings();
        texto.setValue(bundle.getString(tipo_ajuda));
        
        Button sair = new Button("Fechar");
        sair.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				janelaAjuda.close();	
			}
		});
        
        content.addComponent(texto);
        
        janelaAjuda.setContent(content);
        janelaAjuda.center();
        janelaAjuda.setModal(true);
        janelaAjuda.setWidth(700,Unit.PIXELS);
        janelaAjuda.setResizable(false);
        janelaAjuda.setDraggable(true);
        
        janelaAjuda.addBlurListener(new BlurListener() {
            @Override
            public void blur(BlurEvent event) {
            	janelaAjuda.close();

            }
        });
        
        UI.getCurrent().addWindow(janelaAjuda);
	}
}
