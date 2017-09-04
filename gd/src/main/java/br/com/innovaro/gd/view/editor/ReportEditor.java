package br.com.innovaro.gd.view.editor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import br.com.innovaro.gd.component.InlineTextEditor;

@SuppressWarnings({ "serial", "unchecked" })
public final class ReportEditor extends VerticalLayout {

    private final ReportEditorListener listener;
    private final SortableLayout canvas;
    public List<Component> components;

    public ReportEditor(final ReportEditorListener listener) {
    	components = new ArrayList<>();
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
        addComponent(canvas);
        setExpandRatio(canvas, 1);
    }

    public void setTitle(final String title) {
        canvas.setTitle(title);
    }

    private Component buildPalette() {
        HorizontalLayout paletteLayout = new HorizontalLayout();
        paletteLayout.setWidthUndefined();
        paletteLayout.addStyleName("palette");

        paletteLayout.addComponent(buildPaletteItem(PaletteItemType.TEXT));
        paletteLayout.addComponent(buildPaletteItem(PaletteItemType.TABLE));
        paletteLayout.addComponent(buildPaletteItem(PaletteItemType.CHART));

        paletteLayout.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                if (event.getChildComponent() != null) {
                    PaletteItemType data = (PaletteItemType) ((DragAndDropWrapper) event
                            .getChildComponent()).getData();
                    addWidget(data, null);
                }
            }
        });

        return paletteLayout;
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

    public void addWidget(final PaletteItemType paletteItemType,
            final Object prefillData) {
        canvas.addComponent(paletteItemType, prefillData);
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

        public void addComponent(final PaletteItemType paletteItemType,
                final Object prefillData) {
            //if (placeholder.getParent() != null) {
            //    layout.removeComponent(placeholder);
            //}
            layout.addComponent(createComponentFromPaletteItem(paletteItemType, prefillData),1);
            
        }

        private Component createComponentFromPaletteItem(
                final PaletteItemType type, final Object prefillData) {
            Component result = null;
            if (type == PaletteItemType.TEXT) {
                result = new InlineTextEditor(prefillData != null
                        ? String.valueOf(prefillData) : null);
                components.add(result);
            }
            return result;
        } 
    }

    public interface ReportEditorListener {
        void titleChanged(String newTitle, ReportEditor editor);
    }

    public enum PaletteItemType {
        TEXT("Text Block", FontAwesome.FONT), TABLE("Top 10 Movies",
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
}