package br.com.innovaro.gd.component;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ConteudoDao;
import br.com.innovaro.gd.model.Conteudo;
import br.com.innovaro.gd.type.ItemType;

@SuppressWarnings("serial")
public class InlineTextEditor extends CustomComponent {

    private final Component editor;
    private final ConteudoDao dao;
    private final Component readOnly;
    private final Long idConteudo; 
    private final Label text = new Label();
    private final RichTextArea rtaReadOnly = new RichTextArea();
    private final ItemType itemType;

    public InlineTextEditor(final String initialValue, String textContent, Long idConteudo, ItemType itemType) {
    	dao = new ConteudoDao();
        setWidth(100.0f, Unit.PERCENTAGE);
        addStyleName("inline-text-editor");
        this.idConteudo = idConteudo;
        this.itemType = itemType;

        if (initialValue != null) {
            editor = buildEditor(initialValue,textContent);
            readOnly = buildReadOnly(initialValue,textContent);
        } else {
            editor = buildEditor("SEÇÃO",textContent);
            readOnly = buildReadOnly("Enter text here...",textContent);
        }
        if(textContent.equals("")) {
        	setCompositionRoot(editor);
        }
        else {
        	setCompositionRoot(readOnly);
        }
    }

    private Component buildReadOnly(final String initialValue,String textContent) {
    	rtaReadOnly.addStyleName("lineBreak");
    	rtaReadOnly.setWidth(100.0f, Unit.PERCENTAGE);
    	rtaReadOnly.setReadOnly(true);
    	rtaReadOnly.addStyleName("noBorder");
    	rtaReadOnly.setValue(textContent);
    	
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
        
        if(itemType == ItemType.REVIEW) {
        	editButton.setVisible(false);
        }
        
        return result;
    }

    private Component buildEditor(final String initialValue, String textContent) {
        final RichTextArea rta = new RichTextArea(initialValue);
        rta.setValue(textContent);
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
				Conteudo conteudo = dao.findById(idConteudo);
				conteudo.setConteudo(rta.getValue());
				dao.update(conteudo);
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
}
