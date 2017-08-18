package br.com.innovaro.gd.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    INICIO("Início", StartView.class, VaadinIcons.HOME,true), DOCUMENTO(
            "Novo Documento", DocumentoView.class, VaadinIcons.FILE_O, true),  REVISAR(
            "Revisão de Documento", RevisarDocumentoView.class, VaadinIcons.FILE_REFRESH, true), APROVAR(
            "Aprovação de Documento", AprovarDocumentoView.class, VaadinIcons.THUMBS_UP_O, false),MODELO(
            "Modelo de Documento", TemplateView.class, VaadinIcons.FILE_FONT, false), CABECALHO(
    		"Cabeçalho e Rodapé",null,VaadinIcons.BOOK,false), TIPOREG(
    		"Tipo de Registro",null,VaadinIcons.CLIPBOARD,false), REGISTRO(
    		"Inclusão de Registro",null,VaadinIcons.CLIPBOARD_TEXT,false);

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
