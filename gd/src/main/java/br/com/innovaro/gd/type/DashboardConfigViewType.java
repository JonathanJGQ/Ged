package br.com.innovaro.gd.type;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import br.com.innovaro.gd.BundleStrings;
import br.com.innovaro.gd.view.AjudaView;
import br.com.innovaro.gd.view.AmbienteView;
import br.com.innovaro.gd.view.ModeloDocumentoView;
import br.com.innovaro.gd.view.ModeloRegistroView;;

public enum DashboardConfigViewType {
	MODELO(BundleStrings.getString("modelo_de_documento"), ModeloDocumentoView.class, VaadinIcons.FILE_FONT, false),
	TIPOREG(BundleStrings.getString("modelo_de_registro"),ModeloRegistroView.class,VaadinIcons.CLIPBOARD,false),
	CABECALHO(BundleStrings.getString("cabecalho_e_rodape"),null,VaadinIcons.ACCORDION_MENU,false),
	CODIFICACAO(BundleStrings.getString("codigo_do_documento"),null,VaadinIcons.BARCODE,false),
	PROCESSOS(BundleStrings.getString("processos"),null,VaadinIcons.ARCHIVE,false),
	PAPEIS(BundleStrings.getString("papeis"),null,VaadinIcons.CLIPBOARD_USER,false),
	AMBIENTE(BundleStrings.getString("ambiente"),AmbienteView.class,VaadinIcons.GLOBE,false),
	AJUDA(BundleStrings.getString("ajuda"),AjudaView.class,VaadinIcons.QUESTION_CIRCLE_O,false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardConfigViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardConfigViewType getByViewName(final String viewName) {
        DashboardConfigViewType result = null;
        for (DashboardConfigViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
