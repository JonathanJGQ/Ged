package br.com.innovaro.gd.view.editor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;

import br.com.innovaro.gd.dao.ConteudoDao;
import br.com.innovaro.gd.dao.SecaoDao;
import br.com.innovaro.gd.event.DashboardEventBus;
import br.com.innovaro.gd.model.Conteudo;
import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.view.editor.ReportEditor.PaletteItemType;
import br.com.innovaro.gd.view.editor.ReportEditor.ReportEditorListener;

@SuppressWarnings("serial")
public final class ReportsView extends TabSheet implements View, CloseHandler,
        ReportEditorListener {

    public static final String CONFIRM_DIALOG_ID = "confirm-dialog";
    private String valueId;
    private ConteudoDao daoConteudo;
    private String valueIdTemplate;
    private String estadoDocumento;
    private SecaoDao dao;
    private List<Secao> listaSecao;
    ReportEditor reportEditor;
    
    public ReportsView() {
    	dao = new SecaoDao(); 
    	daoConteudo = new ConteudoDao();
    	reportEditor = new ReportEditor(this);
        setSizeFull();
        addStyleName("reports");
        setCloseHandler(this);
        DashboardEventBus.register(this);
        addReport(ReportType.EMPTY, null);
        
    }

    public void addReport(final ReportType reportType, final Object prefillData) {
        addTab(reportEditor).setClosable(false);

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
	    estadoDocumento = args[2];
	    reportEditor.removeComponentes();
	    listaSecao = dao.buscaSecaoPorModelo(Long.parseLong(valueIdTemplate));
	    criarSecoes(listaSecao);
    }
    
    private void criarSecoes(List<Secao> lista) {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	List<Conteudo> listaConteudo = daoConteudo.buscaConteudosPorDocumento(Long.parseLong(valueId));
    	reportEditor.setTitle("Novo Documento " + (df.format(new Date())) + " (" + getComponentCount() + ")");
		for (int i=lista.size();i>0;i--) {
			if(listaConteudo.size() == 0) {
				Conteudo conteudo = new Conteudo();
				conteudo.setConteudo("");
				conteudo.setIdSecao(lista.get(i-1).getId());
				daoConteudo.save(conteudo);
				reportEditor.addWidget(PaletteItemType.TEXT, lista.get(i-1).getNome(),conteudo.getId());
			}
			else {
				for(int j=0;j<listaConteudo.size();j++) {
					if(listaConteudo.get(j).getIdSecao() == lista.get(i-1).getId())
						reportEditor.addWidget(PaletteItemType.EDIT, lista.get(i-1).getNome(),listaConteudo.get(j).getId());
				}
			}
		}
	}

    @Override
    public void titleChanged(final String newTitle, final ReportEditor editor) {
        getTab(editor).setCaption(newTitle);
    }

    public enum ReportType {
        MONTHLY, EMPTY, TRANSACTIONS
    }

	@Override
	public void onTabClose(TabSheet tabsheet, Component tabContent) {
		// TODO Auto-generated method stub
		
	}

}
