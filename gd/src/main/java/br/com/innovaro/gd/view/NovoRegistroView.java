package br.com.innovaro.gd.view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Anexo;
import br.com.innovaro.gd.model.Registro;
import br.com.innovaro.gd.model.RegistroCombo;

public class NovoRegistroView extends VerticalLayout implements View{
	
	private Grid<Registro> grid;
	private List<Registro> lista;
	private List<RegistroCombo> listaCombo;
	private Map<Integer,Anexo> listaAnexo;
	private HorizontalLayout anexoLayout;
	private Integer totalAnexos = 0;
	private List<Button> buttonList;
	
	public NovoRegistroView() {
		
		buttonList = new ArrayList<Button>();
		lista = new ArrayList<>();
		listaCombo = new ArrayList<>();
		listaAnexo = new HashMap<>();
		anexoLayout = new HorizontalLayout();
    	anexoLayout.setMargin(false);
		
		Label title = new Label("Novo Tipo de Registro");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
        HorizontalLayout layoutHorizontal = new HorizontalLayout();
        VerticalLayout listaLayout = new VerticalLayout();
        listaLayout.setMargin(false);
        listaLayout.setVisible(false);
       
        TextField nomeRegistro = new TextField("Nome do Registro");
        nomeRegistro.setSizeFull();
        
        TextField nomeCampo = new TextField("Nome do Campo");
        
        ComboBox tipoCombo = new ComboBox("Tipo do Campo");
        List<String> tipoLista = new ArrayList<>();
        tipoLista.add("Texto");
        tipoLista.add("Numérico");
        tipoLista.add("Data");
        tipoLista.add("Lista");
        tipoCombo.setItems(tipoLista);
        tipoCombo.setValue("Texto");
        tipoCombo.setEmptySelectionAllowed(false);
        
        tipoCombo.addValueChangeListener(new ValueChangeListener() {
        	public void valueChange(ValueChangeEvent event) {
                if (tipoCombo.getValue().toString().equals("Lista")) {
                    listaLayout.setVisible(true);
                }
                else {
                	listaLayout.setVisible(false);
                }
        	}
        });
        
        Button adicionarCampo = new Button(VaadinIcons.PLUS);
        adicionarCampo.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        layoutHorizontal.addComponents(nomeCampo,tipoCombo,adicionarCampo);
        layoutHorizontal.setComponentAlignment(adicionarCampo, Alignment.BOTTOM_CENTER);
        
        //GRID NOVA LISTA COMBO ------------------------------------------------------
        CssLayout adicionarListaLayout = new CssLayout();
        adicionarListaLayout.setCaption("Nome da Opção da Lista");
        
        TextField nomeLista = new TextField();
        Button adicionarLista = new Button(VaadinIcons.PLUS);
        adicionarLista.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        adicionarListaLayout.addComponents(nomeLista,adicionarLista);
        adicionarListaLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        Grid<RegistroCombo> listaGrid = new Grid<>("Opções da Lista");
        listaGrid.addColumn(RegistroCombo::getNome).setCaption("Nome");
        listaGrid.setHeight(230,Unit.PIXELS);
        
        adicionarLista.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				
				RegistroCombo novo = new RegistroCombo();
				novo.setNome(nomeLista.getValue());
				listaCombo.add(novo);
				listaGrid.setItems(listaCombo);
			}
		}); 
        
        listaLayout.addComponents(adicionarListaLayout,listaGrid);

        //-----------------------------------------------------------------------------
        
        grid = new Grid<>();
        grid.addColumn(Registro::getNome).setCaption("Nome");
        grid.addColumn(Registro::getTipo).setCaption("Tipo").setWidth(200);
        grid.setSizeFull();
        
        adicionarCampo.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				
				Registro novo = new Registro();
				novo.setNome(nomeCampo.getValue());
				novo.setTipo(tipoCombo.getValue().toString());
				lista.add(novo);
				grid.setItems(lista);
			}
		}); 
        
        //ANEXO -----------------------------------------------------
        
        Label anexo = new Label("Anexos");
        anexo.setSizeUndefined();
        anexo.addStyleName(ValoTheme.LABEL_H2);
        anexo.addStyleName(ValoTheme.LABEL_NO_MARGIN);
 
        CssLayout anexoProcurarLayout = new CssLayout();
        TextField url = new TextField();
        url.setWidth(300,Unit.PIXELS);
        Button buscarAnexo = new Button("Procurar");
        
        anexoProcurarLayout.addComponents(url,buscarAnexo);
        anexoProcurarLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        Grid<Anexo> anexoGrid = new Grid<>();
        anexoGrid.addColumn(Anexo::getUrl).setCaption("Url");
        anexoGrid.setSizeFull();
        
        buscarAnexo.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				
			}
		}); 
        
        //-----------------------------------------------------------
        
        
        
        
        
        addComponents(title,nomeRegistro,layoutHorizontal,listaLayout,grid,construirAnexo());
	}
	
	private Component construirAnexo() {

        VerticalLayout anexoLayout = new VerticalLayout();
        anexoLayout.setSizeUndefined();
        anexoLayout.setMargin(false);

   
        Label draftsTitle = new Label("Anexos");
        draftsTitle.addStyleName(ValoTheme.LABEL_H1);
        draftsTitle.setSizeUndefined();
        anexoLayout.addComponent(draftsTitle);

        anexoLayout.addComponent(buildDraftsList());

        return anexoLayout;
    }
	
	private Component buildDraftsList() {
        HorizontalLayout drafts = new HorizontalLayout();
        
        buildDraftThumb();
        drafts.addComponent(anexoLayout);
        drafts.addComponent(buildCreateBox());

        return drafts;
    }

    private void buildDraftThumb() {
    	
    	anexoLayout.removeAllComponents();
    	VerticalLayout draftThumb;
        
    	Button delete;
        for (Integer keyAnexo : listaAnexo.keySet()) {
        	
        	Anexo anexo = listaAnexo.get(keyAnexo);
        	
        	draftThumb = new VerticalLayout();
            draftThumb.setMargin(false);
        	
        	draftThumb.addStyleName("draft-thumb");
            String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
            FileResource resource = new FileResource(new File(basepath +"/WEB-INF/images/draft-report-thumb.png"));
            Image draft = new Image(null,resource);
            draft.setWidth(160.0f, Unit.PIXELS);
            draft.setHeight(200.0f, Unit.PIXELS);
            draftThumb.addComponent(draft);
            Label draftTitle = new Label(anexo.getUrl(),ContentMode.HTML);
            draftTitle.setSizeUndefined();
            draftThumb.addComponent(draftTitle);

            delete = new Button("×");
            delete.setDescription("Deletar");
            delete.setData(String.valueOf(keyAnexo));
            delete.setPrimaryStyleName("delete-button");
            delete.addClickListener(new ClickListener() {
                @Override
                public void buttonClick( ClickEvent event) {
                    listaAnexo.remove(Integer.parseInt(event.getButton().getData().toString()));
                    buildDraftThumb();
                }
            });
            draftThumb.addComponent(delete);

            draftThumb.addLayoutClickListener(new LayoutClickListener() {
                @Override
                public void layoutClick(LayoutClickEvent event) {
                    if (event.getButton() == MouseButton.LEFT
                            && event.getChildComponent() != delete) {
                        //addReport(ReportType.MONTHLY, null);
                    }
                }
            });
            anexoLayout.addComponent(draftThumb);
		}
    }

    private Component buildCreateBox() {
        VerticalLayout createBox = new VerticalLayout();
        createBox.setWidth(160.0f, Unit.PIXELS);
        createBox.setHeight(200.0f, Unit.PIXELS);
        createBox.addStyleName("create");
        createBox.setMargin(false);
        createBox.setSpacing(false);

        Button create = new Button("Novo Anexo");
        create.addStyleName(ValoTheme.BUTTON_PRIMARY);
        create.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                adicionarAnexoWindow();
            }
        });

        createBox.addComponent(create);
        createBox.setComponentAlignment(create, Alignment.MIDDLE_CENTER);
        return createBox;
    }
    
    private void adicionarAnexoWindow() {
    	
    	Window window = new Window();
    	VerticalLayout layout = new VerticalLayout();
    	TextField nomeAnexo = new TextField("Nome");
    	nomeAnexo.setWidth(100,Unit.PERCENTAGE);
    	
    	HorizontalLayout buttonsLayout = new HorizontalLayout();
    	Button procurar = new Button("Procurar");
    	Button adicionar = new Button("Adicionar");
    	adicionar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    	buttonsLayout.addComponents(procurar,adicionar);
    	
    	adicionar.addClickListener(new Button.ClickListener() {
    	    public void buttonClick(ClickEvent event) {
    	        Anexo anexo = new Anexo();
    	        anexo.setUrl(nomeAnexo.getValue());
    	        listaAnexo.put(totalAnexos, anexo);
    	        buildDraftThumb();
    	        totalAnexos++;
    	        window.close();
    	    }
    	});
    	
    	layout.addComponents(nomeAnexo,buttonsLayout);
    	
    	window.setHeight(150,Unit.PIXELS);
    	window.setWidth(500,Unit.PIXELS);
    	window.setContent(layout);
    	window.setResizable(false);
    	window.setModal(true);
    	window.center();
    	UI.getCurrent().addWindow(window);
    	
    }

}
