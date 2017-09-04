package br.com.innovaro.gd;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.event.DashboardEventBus;
import br.com.innovaro.gd.view.AprovarDocumento;
import br.com.innovaro.gd.view.AprovarDocumentoView;
import br.com.innovaro.gd.view.DocumentoView;
import br.com.innovaro.gd.view.EditModeloView;
import br.com.innovaro.gd.view.IncluirRegistroView;
import br.com.innovaro.gd.view.ListarDocumentoView;
import br.com.innovaro.gd.view.MenuView;
import br.com.innovaro.gd.view.NovoRegistroView;
import br.com.innovaro.gd.view.RevisarDocumento;
import br.com.innovaro.gd.view.RevisarDocumentoView;
import br.com.innovaro.gd.view.StartView;
import br.com.innovaro.gd.view.ModeloView;
import br.com.innovaro.gd.view.TipoRegistroView;
import br.com.innovaro.gd.view.editor.ReportsView;


@Theme("mytheme")
public class MyUI extends UI {
	
	Navigator navigator;
	private final DashboardEventBus dashboardEventbus = new DashboardEventBus();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	Responsive.makeResponsive(this);
    	//addStyleName(ValoTheme.UI_WITH_MENU);
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
		layout.setSpacing(false);
		layout.setWidth(100, Unit.PERCENTAGE);
		
		HorizontalLayout main = new HorizontalLayout();
		VerticalLayout lateralLayout = new VerticalLayout();
		lateralLayout.setMargin(false);
		lateralLayout.setSpacing(false);
		lateralLayout.setHeight(100,Unit.PERCENTAGE);
		lateralLayout.setWidth(250,Unit.PIXELS);
		
		CssLayout menu = new CssLayout();
		menu.addComponent(new MenuView());
		menu.setHeight(100,Unit.PIXELS);
		menu.setHeight(100,Unit.PERCENTAGE);
		menu.addStyleName("backColorBlueLight");
		
		HorizontalLayout footer = new HorizontalLayout();
		Label lblFooter = new Label("powered by",ContentMode.HTML);
		lblFooter.addStyleName("footerStyle");
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath +"/WEB-INF/images/innovaro.png"));
        Image image = new Image(null,resource);
        image.setHeight(25,Unit.PIXELS);
        image.setWidth(100,Unit.PIXELS);
		
		footer.setMargin(false);
		footer.setSpacing(false);
		footer.addComponents(lblFooter,image);
		footer.setHeight(25,Unit.PIXELS);
		footer.setWidth(100,Unit.PERCENTAGE);
		footer.setStyleName("backColorWhite");
		footer.setComponentAlignment(lblFooter, Alignment.MIDDLE_RIGHT);
		
		lateralLayout.addComponents(menu,footer);
		lateralLayout.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
		lateralLayout.setExpandRatio(menu,1);
		
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
        panel.setContent(layout);
        
        main.addComponents(lateralLayout,panel);
        main.setSizeFull();
        //main.setHeight(100,Unit.PERCENTAGE);
        main.setExpandRatio(panel, 1);
        main.setMargin(false);
        
        this.setContent(main);
		
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new StartView());
        navigator.addView("Início", new StartView());
        navigator.addView("Modelo de Documento", new ModeloView());
        navigator.addView("editarModelo",new EditModeloView());
        navigator.addView("Editar Documento", new ListarDocumentoView());
        navigator.addView("novoDocumento", new ReportsView());
        navigator.addView("Revisar Documento",new RevisarDocumentoView());
        navigator.addView("Aprovar Documento",new AprovarDocumentoView());
        navigator.addView("Tipo de Registro",new TipoRegistroView());
        navigator.addView("novoRegistro",new NovoRegistroView());
        navigator.addView("Tela Revisar Documento",new RevisarDocumento());
        navigator.addView("Tela Aprovar Documento",new AprovarDocumento());
        navigator.addView("Incluir Registro",new IncluirRegistroView());
        navigator.addView("Teste",new ReportsView());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    public static DashboardEventBus getDashboardEventbus() {
        return ((MyUI) getCurrent()).dashboardEventbus;
    }
}