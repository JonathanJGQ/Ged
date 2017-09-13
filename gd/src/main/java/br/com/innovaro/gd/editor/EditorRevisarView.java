package br.com.innovaro.gd.editor;

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
import br.com.innovaro.gd.editor.EditorRevisarUtil.ReportEditorRevisarListener;
import br.com.innovaro.gd.event.DashboardEventBus;
import br.com.innovaro.gd.model.Conteudo;
import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.type.ItemType;

@SuppressWarnings("serial")
public final class EditorRevisarView extends TabSheet implements View, CloseHandler,ReportEditorRevisarListener {

    public static final String CONFIRM_DIALOG_ID = "confirm-dialog";
    private String valueId;
    private ConteudoDao daoConteudo;
    private String valueIdTemplate;
    private SecaoDao dao;
    private List<Secao> listaSecao;
    private EditorRevisarUtil editorRevisarUtil;
    
    public EditorRevisarView() {
    	dao = new SecaoDao(); 
    	daoConteudo = new ConteudoDao();
        setSizeFull();
        addStyleName("reports");
        setCloseHandler(this);
        editorRevisarUtil = new EditorRevisarUtil(this);
        DashboardEventBus.register(this);
        addReport(ReportType.EMPTY, null);
        
    }

    public void addReport(final ReportType reportType, final Object prefillData) {
        addTab(editorRevisarUtil).setClosable(false);
        setSelectedTab(getComponentCount() - 1);
    }


    @Override
    public void enter(final ViewChangeEvent event) {
    	String args[] = event.getParameters().split("/");
	    valueId = args[0];
	    valueIdTemplate = args[1];
	    editorRevisarUtil.setIdDocumento(Long.parseLong(valueId));
	    editorRevisarUtil.removeComponentes();
	    editorRevisarUtil.atualizaData();
	    listaSecao = dao.buscaSecaoPorModelo(Long.parseLong(valueIdTemplate));
	    criarSecoes(listaSecao);
    }
    
    private void criarSecoes(List<Secao> lista) {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	List<Conteudo> listaConteudo = daoConteudo.buscaConteudosPorDocumento(Long.parseLong(valueId));
    	editorRevisarUtil.setTitle("Novo Documento " + (df.format(new Date())) + " (" + getComponentCount() + ")");
		for (int i=lista.size();i>0;i--) {
			for(int j=0;j<listaConteudo.size();j++) {
				if(listaConteudo.get(j).getIdSecao() == lista.get(i-1).getId())
					editorRevisarUtil.addWidget(ItemType.REVIEW, lista.get(i-1).getNome(),listaConteudo.get(j).getId());
			}
		}
	}

    @Override
    public void titleChanged(final String newTitle, final EditorRevisarUtil editor) {
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
