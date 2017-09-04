package br.com.innovaro.gd.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class IncluirRegistroView extends VerticalLayout implements View{
	public IncluirRegistroView() {
		
		Label title = new Label("Incluir Registro");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
        ComboBox documento = new ComboBox("Nome do Documento");
        List<String> listaDoc = new ArrayList<>();
        listaDoc.add("Documento 1");
        listaDoc.add("Documento 2");
        listaDoc.add("Documento 3");
        documento.setItems(listaDoc);
        documento.setWidth(500,Unit.PIXELS);
        
        ComboBox tipoRegistro = new ComboBox("Tipo de Registro");
        List<String> lista = new ArrayList<>();
        lista.add("Registro 1");
        lista.add("Registro 2");
        lista.add("Registro 3");
        tipoRegistro.setItems(lista);
        tipoRegistro.setWidth(500,Unit.PIXELS);
        
        VerticalLayout conteudo = new VerticalLayout();
        conteudo.setVisible(false);
        
        TextField txtCampo = new TextField("Campo Texto");
        txtCampo.setWidth(240,Unit.PIXELS);
        DateTimeField txtData = new DateTimeField("Campo Data"); 
        txtData.setDateFormat("dd-MM-yyyy");
        txtData.setValue(LocalDateTime.now());
        
        ComboBox cmbLista = new ComboBox("Campo Lista");
        List<String> listaCmb = new ArrayList<>();
        listaCmb.add("Opção 1");
        listaCmb.add("Opção 2");
        listaCmb.add("Opção 3");
        cmbLista.setItems(listaCmb);
        cmbLista.setWidth(240,Unit.PIXELS);
        
        TextField numCampo = new TextField("Campo Numérico");
        numCampo.setWidth(240,Unit.PIXELS);
        numCampo.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				String value = numCampo.getValue();
				value = value.replace(",", "");
				value = value.replace(".", "");
				try {
					Float.parseFloat(value);
				}
				catch(Exception e){
					numCampo.setValue("");
				}
			}
        	
        });
        
        Button btnIncluir = new Button("Incluir");
        btnIncluir.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        tipoRegistro.addValueChangeListener(event -> conteudo.setVisible(true));
        
        conteudo.addComponent(txtCampo);
        conteudo.addComponent(txtData);
        conteudo.addComponent(cmbLista);
        conteudo.addComponent(numCampo);
        conteudo.addComponent(btnIncluir);
        
        addComponents(title,documento,tipoRegistro,conteudo);
	}
}
