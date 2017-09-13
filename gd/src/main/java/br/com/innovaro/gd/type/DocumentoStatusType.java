package br.com.innovaro.gd.type;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import br.com.innovaro.gd.BundleStrings;
import br.com.innovaro.gd.view.AprovarDocumentoView;
import br.com.innovaro.gd.view.DocumentoView;
import br.com.innovaro.gd.view.IncluirRegistroView;
import br.com.innovaro.gd.view.RevisarDocumentoView;
import br.com.innovaro.gd.view.StartView;;

public enum DocumentoStatusType {
    EDICAO("Em Edição"), 
    REVISAO("Em Revisão"),
    APROVACAO("Em Aprovação"), 
    APROVADO("Aprovado");

    private final String title;

    DocumentoStatusType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}