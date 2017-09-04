package br.com.innovaro.gd.component;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ModeloDao;
import br.com.innovaro.gd.model.Modelo;

@SuppressWarnings("serial")
public class InlineTextEditor extends CustomComponent {

    private final Component editor;
    private final Component readOnly;
    private final Label text = new Label();
    private final RichTextArea rtaReadOnly = new RichTextArea();

    public InlineTextEditor(final String initialValue) {
        setWidth(100.0f, Unit.PERCENTAGE);
        addStyleName("inline-text-editor");
        
        if (initialValue != null) {
            editor = buildEditor(initialValue);
            readOnly = buildReadOnly(initialValue);
        } else {
            editor = buildEditor("SEÇÃO");
            readOnly = buildReadOnly("Enter text here...");
        }

        setCompositionRoot(editor);
    }

    private Component buildReadOnly(final String initialValue) {
    	rtaReadOnly.addStyleName("lineBreak");
    	rtaReadOnly.setWidth(100.0f, Unit.PERCENTAGE);
    	rtaReadOnly.setReadOnly(true);
    	rtaReadOnly.addStyleName("noBorder");
    	
        text.setValue(initialValue);
        text.setContentMode(ContentMode.HTML);
        
        Button editButton = new Button(FontAwesome.EDIT);
        editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.addClickListener(event -> setCompositionRoot(editor));
        
        CssLayout result = new CssLayout(rtaReadOnly,editButton);
        result.addStyleName("text-editor");
        result.setSizeFull();
        result.addLayoutClickListener(event -> {
            if (event.getChildComponent() == text && event.isDoubleClick()) {
                setCompositionRoot(editor);
            }
        });
        
        return result;
    }

    private Component buildEditor(final String initialValue) {
        final RichTextArea rta = new RichTextArea(initialValue);
        rta.addStyleName("lineBreak");
        
        rta.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				text.setValue(event.getValue());
				rtaReadOnly.setValue(text.getValue());
				
			}
		});
        rta.setWidth(100, Unit.PERCENTAGE);
        
        rta.addAttachListener(event -> {
            rta.focus();
            rta.selectAll();
        });

        Button save = new Button("Salvar");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				//rta.addStyleName("noBorder");
				//rta.setReadOnly(true);
				setCompositionRoot(readOnly);
			}
		});
        CssLayout result = new CssLayout(rta, save);
        result.addStyleName("edit");
        result.setSizeFull();
        return result;
    }
    
    public void teste() {
    	int count = 0;
    	text.setValue(text.getValue().replace("<br/>", ""));
    	String str = text.getValue();
    	String saida = "";
    	for(int i=0;i<str.length();i++) {
    		saida = saida + str.charAt(i);
    		if(count > 70) {
    			saida = saida + "<br/>";
    			count = 0;
    		}
    		count++;
    	}
    	text.setValue(saida);
    }
    
    public Label getText() {
    	return text;
    }
}
