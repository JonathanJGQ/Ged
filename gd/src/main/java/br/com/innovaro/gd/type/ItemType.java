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

public enum ItemType {
    TEXT("Empty Text", FontAwesome.FONT), EDIT("Edit Text",
            FontAwesome.TABLE), REVIEW("Review Text",
                    FontAwesome.BAR_CHART_O), TRANSACTIONS(
                            "Latest transactions", null);

    private final String title;
    private final FontAwesome icon;

    ItemType(final String title, final FontAwesome icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public FontAwesome getIcon() {
        return icon;
    }
}