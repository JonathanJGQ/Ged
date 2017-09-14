package br.com.innovaro.gd.editor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.MyUI;
import br.com.innovaro.gd.component.InlineTextEditor;
import br.com.innovaro.gd.dao.ConteudoDao;
import br.com.innovaro.gd.dao.DocumentoDao;
import br.com.innovaro.gd.model.Conteudo;
import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.type.DocumentoStatusType;
import br.com.innovaro.gd.type.ItemType;

@SuppressWarnings({ "serial", "unchecked" })
public final class EditorAprovarUtil extends VerticalLayout {

    private final ReportEditorAprovarListener listener;
    private SortableLayout canvas;
    public List<Component> components;
    private ConteudoDao daoConteudo;
    private DocumentoDao daoDocumento;
    private Long idDocumento;
    private DateField inicio;
    private DateField fim;

    public EditorAprovarUtil(final ReportEditorAprovarListener listener) {
    	components = new ArrayList<>();
    	daoConteudo = new ConteudoDao();
    	daoDocumento = new DocumentoDao();
        this.listener = listener;
        setSizeFull();
        addStyleName("editor");
        setMargin(false);
        setSpacing(false);

        canvas = new SortableLayout();
        canvas.setWidth(100.0f, Unit.PERCENTAGE);
        canvas.addStyleName("canvas");
        addComponent(criarCamposDatas());
        addComponent(canvas);
        setExpandRatio(canvas, 1);
    }

    private VerticalLayout criarCamposObservação() {
		VerticalLayout layout = new VerticalLayout();
		Label lblObservacao = new Label("Observações");
		RichTextArea txtObservacao = new RichTextArea();
		Button salvarObservacao = new Button("Salvar");
		layout.setMargin(false);
		txtObservacao.setWidth(100,Unit.PERCENTAGE);
		
		layout.addComponents(lblObservacao,txtObservacao,salvarObservacao);
		
		return layout;
	}

	public void setTitle(final String title) {
        canvas.setTitle(title);
    }
    
    private HorizontalLayout criarCamposDatas() {
    	
    	HorizontalLayout layout = new HorizontalLayout();
    	inicio = new DateField("Início do Período de Vigência");
    	fim = new DateField("Fim do Período de Vigência");
    	layout.setMargin(false);
    	layout.addStyleName("marginTop");
    	Button enviarAprovacao = new Button("Aprovar");
    	Button retornarEdicao = new Button("Retornar para Edição");
    	enviarAprovacao.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	retornarEdicao.addStyleName(ValoTheme.BUTTON_DANGER);
    	layout.addComponents(inicio,fim,retornarEdicao,enviarAprovacao);
    	
    	retornarEdicao.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Window window = new Window();
				VerticalLayout content = new VerticalLayout();
				Label lblObservacao = new Label("Observações");
				RichTextArea txtObservacao = new RichTextArea();
				HorizontalLayout layoutButtons = new HorizontalLayout();
				Button btnRetornar = new Button("Salvar");
				btnRetornar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
				Button btnCancelar = new Button("Cancelar");
				
				btnRetornar.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Documento doc = daoDocumento.findById(idDocumento);
						doc.setStatus(DocumentoStatusType.EDICAO.getTitle());
						daoDocumento.update(doc);
						window.close();
						((MyUI) UI.getCurrent()).updateNotifications();
						UI.getCurrent().getNavigator().navigateTo("Aprovar Documento");
					}
				});
				
				btnCancelar.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						window.close();
					}
				});
				
				layoutButtons.addComponents(btnRetornar,btnCancelar);
				
				window.center();
				window.setModal(true);
				window.setResizable(false);
				window.setWidthUndefined();
				window.setContent(content);
				
				content.addComponents(lblObservacao,txtObservacao,layoutButtons);
				UI.getCurrent().addWindow(window);
			
			}
		});
    	
    	enviarAprovacao.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				janelaConfirmacao();
			}
		});
    	
    	layout.setWidth(94,Unit.PERCENTAGE);
    	layout.setExpandRatio(fim, 1);
    	layout.setComponentAlignment(retornarEdicao
    			, Alignment.BOTTOM_RIGHT);
    	layout.setComponentAlignment(enviarAprovacao, Alignment.BOTTOM_RIGHT);
    	
    	return layout;
    }

    private Component buildPaletteItem(final ItemType type) {
        Label caption = new Label(type.getIcon().getHtml() + type.getTitle(),
                ContentMode.HTML);
        caption.setSizeUndefined();

        DragAndDropWrapper ddWrap = new DragAndDropWrapper(caption);
        ddWrap.setSizeUndefined();
        ddWrap.setDragStartMode(DragStartMode.WRAPPER);
        ddWrap.setData(type);
        return ddWrap;
    }

    public void addWidget(final ItemType itemType,final Object prefillData, Long idConteudo) {
        canvas.addComponent(itemType, prefillData,idConteudo);
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
                listener.titleChanged(t, EditorAprovarUtil.this);
            });
            layout.addComponent(titleLabel);
            
            Label l = new Label("Drag items here");
            l.setSizeUndefined();
        }

        public void setTitle(final String title) {
            titleLabel.setValue(title);
        }

        public void addComponent(final ItemType itemType,final Object prefillData, Long idConteudo) {
            //if (placeholder.getParent() != null) {
            //    layout.removeComponent(placeholder);
            //}
            layout.addComponent(createComponentFromPaletteItem(itemType, prefillData,idConteudo),1);
        }

        private Component createComponentFromPaletteItem(final ItemType type, final Object prefillData, Long idConteudo) {
            Component result = null;
            if(type == ItemType.EDIT || type == ItemType.REVIEW) {
            	Conteudo conteudo = daoConteudo.findById(idConteudo);
            	result = new InlineTextEditor(prefillData != null ? String.valueOf(prefillData) : null,conteudo.getConteudo(),idConteudo,type);
                components.add(result);
            }
            if (type == ItemType.TEXT) {
                result = new InlineTextEditor(prefillData != null ? String.valueOf(prefillData) : null,"",idConteudo,type);
                components.add(result);
            }
            return result;
        } 
    }

    public interface ReportEditorAprovarListener {
        void titleChanged(String newTitle, EditorAprovarUtil editor);
    }
    
    public void removeComponentes() {
    	removeComponent(canvas);
    	canvas = new SortableLayout();
    	canvas.setWidth(100.0f, Unit.PERCENTAGE);
        canvas.addStyleName("canvas");
        addComponent(canvas,1);
    }

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	public void atualizaData() {
		Documento doc = daoDocumento.findById(idDocumento);
		if(doc.getVigencia_inicio() != null) {
			Instant instant = Instant.ofEpochMilli(doc.getVigencia_inicio().getTime());
	        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			inicio.setValue(localDate);
		}
		if(doc.getVigencia_fim() != null) {
			Instant instant = Instant.ofEpochMilli(doc.getVigencia_fim().getTime());
        	LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			fim.setValue(localDate);
		}
	}
	
	public void janelaConfirmacao() {
    	Window window = new Window();
    	
    	VerticalLayout content = new VerticalLayout();
    	HorizontalLayout btnLayout = new HorizontalLayout();
    	Label text = new Label("Deseja aprovar este documento?");
    	
    	Button sim = new Button("Sim");
    	sim.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    	Button nao = new Button("Não");
    	nao.setStyleName(ValoTheme.BUTTON_DANGER);
    	
    	sim.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Documento documento = daoDocumento.findById(idDocumento);
				
				if(inicio.getValue() != null) {
					Date date = Date.from(inicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					documento.setVigencia_inicio(date);
				}
				
				if(fim.getValue() != null) {
					Date date = Date.from(fim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					documento.setVigencia_fim(date);
				}
				documento.setStatus(DocumentoStatusType.APROVADO.getTitle());
				
				window.close();
				daoDocumento.update(documento);
				((MyUI) UI.getCurrent()).updateNotifications();
				UI.getCurrent().getNavigator().navigateTo("Aprovar Documento");
			}
		});
    	
    	nao.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				window.close();
			}
		});
    	
    	btnLayout.addComponents(sim, nao);
    	content.addComponents(text,btnLayout);
    	
    	window.setContent(content);
    	window.center();
    	window.setResizable(false);
    	window.setModal(true);
    	window.setWidth(500,Unit.PIXELS);
    	
    	UI.getCurrent().addWindow(window);
    }
}