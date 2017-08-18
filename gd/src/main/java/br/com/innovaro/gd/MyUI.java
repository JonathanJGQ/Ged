package br.com.innovaro.gd;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import br.com.innovaro.gd.view.AprovarDocumento;
import br.com.innovaro.gd.view.AprovarDocumentoView;
import br.com.innovaro.gd.view.DocumentoView;
import br.com.innovaro.gd.view.EditTemplateView;
import br.com.innovaro.gd.view.MenuView;
import br.com.innovaro.gd.view.RevisarDocumento;
import br.com.innovaro.gd.view.RevisarDocumentoView;
import br.com.innovaro.gd.view.StartView;
import br.com.innovaro.gd.view.TemplateView;
import br.com.innovaro.gd.view.UsuarioView;

@Theme("mytheme")
public class MyUI extends UI {
	
	Navigator navigator;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
		layout.setSpacing(false);
		
		HorizontalLayout head = new HorizontalLayout();
		HorizontalLayout headMenu = new HorizontalLayout();
		HorizontalLayout main = new HorizontalLayout();
		VerticalLayout mainHead = new VerticalLayout();
		CssLayout menu = new CssLayout();
		menu.addComponent(new MenuView());
		menu.setHeight(100,Unit.PERCENTAGE);
        
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath +"/WEB-INF/images/cegas.jpg"));
        Image image = new Image(null,resource);
        head.setWidth(100,Sizeable.Unit.PERCENTAGE);
        head.addComponents(image,headMenu);
        head.setHeight(99, Unit.PIXELS);
        head.addStyleName("backColorBlue");
        head.setComponentAlignment(headMenu, Alignment.MIDDLE_CENTER);
        
        VerticalLayout form = new VerticalLayout();
        form.setMargin(false);
        form.setSpacing(false);
        //form.addComponent(head);
        //form.setExpandRatio(head, 0);
        layout.setWidth(100, Unit.PERCENTAGE);
        form.addComponent(layout);
        form.setExpandRatio(layout, 10);
        
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setContent(layout);
        
        main.addComponents(menu,panel);
        main.setSizeFull();
        //main.setHeight(100,Unit.PERCENTAGE);
        main.setExpandRatio(panel, 1);
        main.setMargin(false);
        
        mainHead.addComponents(head,main);
        mainHead.setSizeFull();
        mainHead.setMargin(false);
        
        this.setContent(main);
		
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new StartView());
        navigator.addView("Início", new StartView());
        navigator.addView("Modelo de Documento", new TemplateView());
        navigator.addView("editTemplate",new EditTemplateView());
        navigator.addView("Novo Documento", new DocumentoView());
        navigator.addView("novoUsuario", new UsuarioView());
        navigator.addView("Revisão de Documento",new RevisarDocumentoView());
        navigator.addView("Aprovação de Documento",new AprovarDocumentoView());
        navigator.addView("Tela Revisar Documento",new RevisarDocumento());
        navigator.addView("Tela Aprovar Documento",new AprovarDocumento());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
