package br.com.innovaro.gd.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import br.com.innovaro.gd.BundleStrings;;

public enum DashboardViewType {
    INICIO(BundleStrings.getString("inicio"), StartView.class, VaadinIcons.HOME,true), DOCUMENTO(
    		BundleStrings.getString("editar_documento"), DocumentoView.class, VaadinIcons.FILE_O, true),  REVISAR(
    		BundleStrings.getString("revisar_documento"), RevisarDocumentoView.class, VaadinIcons.FILE_REFRESH, true), APROVAR(
    		BundleStrings.getString("aprovar_documento"), AprovarDocumentoView.class, VaadinIcons.THUMBS_UP_O, false), REGISTRO(
    		BundleStrings.getString("incluir_registro"),IncluirRegistroView.class,VaadinIcons.CLIPBOARD_TEXT,false),CONFIGURACAO(
    		BundleStrings.getString("configuracoes"),null,VaadinIcons.COG_O,false);
	/*,TESTE("Teste",null,VaadinIcons.HOME,false);*/

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
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

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
