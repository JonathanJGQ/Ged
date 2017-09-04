package br.com.innovaro.gd.view.editor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.CloseHandler;

import br.com.innovaro.gd.component.InlineTextEditor;
import br.com.innovaro.gd.dao.SecaoDao;
import br.com.innovaro.gd.event.DashboardEventBus;
import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.view.editor.ReportEditor.PaletteItemType;
import br.com.innovaro.gd.view.editor.ReportEditor.ReportEditorListener;

@SuppressWarnings("serial")
public final class ReportsView extends TabSheet implements View, CloseHandler,
        ReportEditorListener {

    public static final String CONFIRM_DIALOG_ID = "confirm-dialog";
    private String value;
    private SecaoDao dao;
    private List<Secao> listaSecao;
    ReportEditor reportEditor;

    public ReportsView() {
    	reportEditor = new ReportEditor(this);
        setSizeFull();
        addStyleName("reports");
        setCloseHandler(this);
        DashboardEventBus.register(this);
        
        dao = new SecaoDao();
        
    }

    public void addReport(final ReportType reportType, final Object prefillData) {
        reportEditor = new ReportEditor(this);
        addTab(reportEditor).setClosable(false);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        reportEditor.setTitle("Novo Documento " + (df.format(new Date()))
                + " (" + getComponentCount() + ")");
        //reportEditor.addWidget(PaletteItemType.TEXT, "SECAO 3");
        //reportEditor.addWidget(PaletteItemType.TEXT, "SECAO 2");
        //reportEditor.addWidget(PaletteItemType.TEXT, "SECAO 1");

        setSelectedTab(getComponentCount() - 1);
    }


    @Override
    public void enter(final ViewChangeEvent event) {
    	String args[] = event.getParameters().split("/");
	    value = args[0];
	    reportEditor.removeAllComponents();
	    addReport(ReportType.EMPTY, null);
	    listaSecao = dao.buscaSecaoPorModelo(Long.parseLong(value));
	    criarSecoes(listaSecao);
	    
    }

    private void criarSecoes(List<Secao> lista) {
		for (int i=lista.size();i>0;i--) {
			reportEditor.addWidget(PaletteItemType.TEXT, lista.get(i-1).getNome());
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
