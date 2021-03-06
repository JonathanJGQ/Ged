package br.com.innovaro.gd.editor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.dao.ConteudoDao;
import br.com.innovaro.gd.dao.DocumentoDao;
import br.com.innovaro.gd.dao.SecaoDao;
import br.com.innovaro.gd.editor.EditorUtil.ReportEditorListener;
import br.com.innovaro.gd.event.DashboardEventBus;
import br.com.innovaro.gd.model.Conteudo;
import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.type.ItemType;

@SuppressWarnings("serial")
public final class EditorView extends TabSheet implements View, CloseHandler,ReportEditorListener {

    public static final String CONFIRM_DIALOG_ID = "confirm-dialog";
    private String valueId;
    private ConteudoDao daoConteudo;
    private String valueIdTemplate;
    private SecaoDao dao;
    private DocumentoDao documentoDao;
    private List<Secao> listaSecao;
    private EditorUtil editorUtil;
    private Documento documento;
    
    public EditorView() {
    	dao = new SecaoDao(); 
    	daoConteudo = new ConteudoDao();
    	documentoDao = new DocumentoDao();
        setSizeFull();
        addStyleName("reports");
        setCloseHandler(this);
        editorUtil = new EditorUtil(this);
        DashboardEventBus.register(this);
        addReport(ReportType.EMPTY, null);
        
    }

    public void addReport(final ReportType reportType, final Object prefillData) {
        addTab(editorUtil).setClosable(false);

        //reportEditor.addWidget(PaletteItemType.TEXT, "SECAO 3");
        //reportEditor.addWidget(PaletteItemType.TEXT, "SECAO 2");
        //reportEditor.addWidget(PaletteItemType.TEXT, "SECAO 1");

        setSelectedTab(getComponentCount() - 1);
    }


    @Override
    public void enter(final ViewChangeEvent event) {
    	String args[] = event.getParameters().split("/");
	    valueId = args[0];
	    valueIdTemplate = args[1];
	    editorUtil.setIdDocumento(Long.parseLong(valueId));
	    editorUtil.removeComponentes();
	    editorUtil.atualizaData();
	    listaSecao = dao.buscaSecaoPorModelo(Long.parseLong(valueIdTemplate));
	    documento = documentoDao.findById(Long.parseLong(valueId));
	    if(documento.getObservacao() != null) {
	    	criarJanelaObservacao(documento.getObservacao());
	    }
	    criarSecoes(listaSecao);
    }
    
    private void criarSecoes(List<Secao> lista) {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	List<Conteudo> listaConteudo = daoConteudo.buscaConteudosPorDocumento(Long.parseLong(valueId));
    	editorUtil.setTitle(documento.getNome());
		for (int i=lista.size();i>0;i--) {
			if(listaConteudo.size() == 0) {
				Conteudo conteudo = new Conteudo();
				conteudo.setConteudo("");
				conteudo.setIdSecao(lista.get(i-1).getId());
				conteudo.setIdDocumento(Long.parseLong(valueId));
				daoConteudo.save(conteudo);
				editorUtil.addWidget(ItemType.TEXT, lista.get(i-1).getNome(),conteudo.getId());
			}
			else {
				for(int j=0;j<listaConteudo.size();j++) {
					if(listaConteudo.get(j).getIdSecao() == lista.get(i-1).getId())
						editorUtil.addWidget(ItemType.EDIT, lista.get(i-1).getNome(),listaConteudo.get(j).getId());
				}
			}
		}
	}

    @Override
    public void titleChanged(final String newTitle, final EditorUtil editor) {
        getTab(editor).setCaption(newTitle);
    }

    public enum ReportType {
        MONTHLY, EMPTY, TRANSACTIONS
    }

	@Override
	public void onTabClose(TabSheet tabsheet, Component tabContent) {
		
	}
	
	private void criarJanelaObservacao(String texto) {
		Window window = new Window("Observação");
		VerticalLayout content = new VerticalLayout();
		Label label = new Label(texto,ContentMode.HTML);
		RichTextArea txtObservacao = new RichTextArea();
		txtObservacao.setValue(label.getValue());
		txtObservacao.setReadOnly(true);
		txtObservacao.setSizeFull();
		Button ok = new Button("Ok");
		ok.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				window.close();
			}
		});
		
		window.center();
		window.setResizable(false);
		window.setModal(true);
		window.setWidth(50,Unit.PERCENTAGE);
		
		content.addComponents(txtObservacao,ok);
		window.setContent(content);
		UI.getCurrent().addWindow(window);
	}
}
