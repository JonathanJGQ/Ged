package br.com.innovaro.gd.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.BundleStrings;

public class AjudaView extends VerticalLayout implements View{
	
	public AjudaView() {
		
		Label title = new Label("Ajuda");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
        Label introducao = criarTituloH2("<strong>Introdução</strong>");
        Label introducao_texto = new Label(BundleStrings.getString("ajuda_introducao"),ContentMode.HTML);
        introducao_texto.setWidth(100,Unit.PERCENTAGE);
        
        Label visao_geral = criarTituloH2("<strong>Visão Geral</strong>");
        Label visao_geral_texto = new Label(BundleStrings.getString("ajuda_visao_geral"),ContentMode.HTML);
        visao_geral_texto.setWidth(100,Unit.PERCENTAGE);
        
        Label telas = criarTituloH2("<strong>Telas</strong>");
        
        VerticalLayout layout_editar_documento = criarPainel("Editar Documento","editar_documento_ajuda");
        VerticalLayout layout_documentos_aprovados = criarPainel("Documentos Aprovados","documentos_aprovados_ajuda");
        VerticalLayout layout_revisar_documentos = criarPainel("Revisar Documentos","revisar_documento_ajuda");
        VerticalLayout layout_aprovar_documentos = criarPainel("Aprovar Documentos","aprovar_documento_ajuda");
        VerticalLayout layout_incluir_registro = criarPainel("Incluir Registro","incluir_registro_ajuda");
        VerticalLayout layout_modelo_documento = criarPainel("Modelo de Documento","modelo_documento_ajuda");
        VerticalLayout layout_editar_modelo_documento = criarPainel("Editar Modelo de Documento ","editar_modelo_documento_ajuda");
        VerticalLayout layout_modelo_registro = criarPainel("Modelo de Registro","modelo_registro_ajuda");
        VerticalLayout layout_novo_modelo_registro = criarPainel("Novo Modelo de Registro","novo_modelo_registro_ajuda");
        
        addComponents(title,introducao,introducao_texto,visao_geral,visao_geral_texto); 
        addComponents(telas,layout_editar_documento,layout_documentos_aprovados);
        addComponents(layout_revisar_documentos,layout_aprovar_documentos);
        addComponents(layout_incluir_registro,layout_modelo_documento,layout_editar_modelo_documento);
        addComponents(layout_modelo_registro,layout_novo_modelo_registro);
	}
	
	private Label criarTituloH2(String titulo) {
		Label label = new Label(titulo,ContentMode.HTML);
		label.setSizeUndefined();
		label.addStyleName(ValoTheme.LABEL_H2);
		label.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		return label;
	}
	
	private VerticalLayout criarPainel(String titulo, String conteudo_ajuda) {
		
		VerticalLayout layout = new VerticalLayout();
		Label titulo_label = criarTituloH2(titulo);
		Panel panel = new Panel();
		FormLayout layout_panel = new FormLayout();
		panel.addStyleName(ValoTheme.LAYOUT_CARD);
		Label content = new Label(BundleStrings.getString(conteudo_ajuda),ContentMode.HTML);
		
		layout_panel.addComponents(content);
		
		panel.setContent(layout_panel);
		
		layout.addComponents(titulo_label,panel, new Label("<br/>",ContentMode.HTML));
		layout.setMargin(false);
		
		return layout;
	}
}
