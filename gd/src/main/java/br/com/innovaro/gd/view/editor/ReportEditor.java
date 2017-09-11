package br.com.innovaro.gd.view.editor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.component.InlineTextEditor;
import br.com.innovaro.gd.dao.ConteudoDao;
import br.com.innovaro.gd.model.Conteudo;

@SuppressWarnings({ "serial", "unchecked" })
public final class ReportEditor extends VerticalLayout {

    private final ReportEditorListener listener;
    private SortableLayout canvas;
    public List<Component> components;
    private ConteudoDao daoConteudo;

    public ReportEditor(final ReportEditorListener listener) {
    	components = new ArrayList<>();
    	daoConteudo = new ConteudoDao();
        this.listener = listener;
        setSizeFull();
        addStyleName("editor");
        setMargin(false);
        setSpacing(false);
        //addStyleName(ValoTheme.DRAG_AND_DROP_WRAPPER_NO_HORIZONTAL_DRAG_HINTS);

        //Component palette = buildPalette();
        //addComponent(palette);
        //setComponentAlignment(palette, Alignment.TOP_CENTER);

        canvas = new SortableLayout();
        canvas.setWidth(100.0f, Unit.PERCENTAGE);
        canvas.addStyleName("canvas");
        addComponent(criarCamposDatas());
        addComponent(canvas);
        setExpandRatio(canvas, 1);
    }

    public void setTitle(final String title) {
        canvas.setTitle(title);
    }
    
    private HorizontalLayout criarCamposDatas() {
    	
    	HorizontalLayout layout = new HorizontalLayout();
    	DateField inicio = new DateField("Início do Período de Vigência");
    	DateField fim = new DateField("Fim do Período de Vigência");
    	layout.setMargin(false);
    	layout.addStyleName("marginTop");
    	Button enviarRevisão = new Button("Enviar para Revisão");
    	enviarRevisão.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	layout.addComponents(inicio,fim,enviarRevisão);
    	
    	
    	layout.setWidth(94,Unit.PERCENTAGE);
    	layout.setExpandRatio(fim, 1);
    	layout.setComponentAlignment(enviarRevisão, Alignment.BOTTOM_RIGHT);
    	
    	return layout;
    }

    private Component buildPaletteItem(final PaletteItemType type) {
        Label caption = new Label(type.getIcon().getHtml() + type.getTitle(),
                ContentMode.HTML);
        caption.setSizeUndefined();

        DragAndDropWrapper ddWrap = new DragAndDropWrapper(caption);
        ddWrap.setSizeUndefined();
        ddWrap.setDragStartMode(DragStartMode.WRAPPER);
        ddWrap.setData(type);
        return ddWrap;
    }

    public void addWidget(final PaletteItemType paletteItemType,final Object prefillData, Long idConteudo) {
        canvas.addComponent(paletteItemType, prefillData,idConteudo);
    }

    public final class SortableLayout extends CustomComponent {

        private VerticalLayout layout;
        private TextField titleLabel;
        private DragAndDropWrapper placeholder;

        public SortableLayout() {
            layout = new VerticalLayout();
            setCompositionRoot(layout);
            layout.addStyleName("canvas-layout");
            layout.setMargin(true);
            layout.setSpacing(true);

            titleLabel = new TextField();
            titleLabel.addStyleName("title");
            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern("dd/MM/yyyy");

            titleLabel.addValueChangeListener(event -> {
                String t = titleLabel.getValue();
                if (t == null || t.equals("")) {
                    t = " ";
                }
                listener.titleChanged(t, ReportEditor.this);
            });
            layout.addComponent(titleLabel);
            
            Label l = new Label("Drag items here");
            l.setSizeUndefined();
        }

        public void setTitle(final String title) {
            titleLabel.setValue(title);
        }

        public void addComponent(final PaletteItemType paletteItemType,final Object prefillData, Long idConteudo) {
            //if (placeholder.getParent() != null) {
            //    layout.removeComponent(placeholder);
            //}
            layout.addComponent(createComponentFromPaletteItem(paletteItemType, prefillData,idConteudo),1);
        }

        private Component createComponentFromPaletteItem(final PaletteItemType type, final Object prefillData, Long idConteudo) {
            Component result = null;
            if(type == PaletteItemType.EDIT) {
            	Conteudo conteudo = daoConteudo.findById(idConteudo);
            	result = new InlineTextEditor(prefillData != null ? String.valueOf(prefillData) : null,conteudo.getConteudo(),idConteudo);
                components.add(result);
            }
            if (type == PaletteItemType.TEXT) {
                result = new InlineTextEditor(prefillData != null ? String.valueOf(prefillData) : null,"",idConteudo);
                components.add(result);
            }
            return result;
        } 
    }

    public interface ReportEditorListener {
        void titleChanged(String newTitle, ReportEditor editor);
    }

    public enum PaletteItemType {
        TEXT("Empty Text", FontAwesome.FONT), EDIT("Edit Text",
                FontAwesome.TABLE), CHART("Top 6 Revenue",
                        FontAwesome.BAR_CHART_O), TRANSACTIONS(
                                "Latest transactions", null);

        private final String title;
        private final FontAwesome icon;

        PaletteItemType(final String title, final FontAwesome icon) {
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
    
    public void removeComponentes() {
    	removeComponent(canvas);
    	canvas = new SortableLayout();
    	canvas.setWidth(100.0f, Unit.PERCENTAGE);
        canvas.addStyleName("canvas");
        addComponent(canvas);
    }
}