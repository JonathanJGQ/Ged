package br.com.innovaro.gd.view;

import java.io.File;
import java.util.Collection;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.event.DashboardEvent.PostViewChangeEvent;
import br.com.innovaro.gd.type.DashboardConfigViewType;
import br.com.innovaro.gd.type.DashboardViewType;
import br.com.innovaro.gd.event.DashboardEventBus;


public class MenuView extends CustomComponent{
	public static final String ID = "cegas-menu";
    public static final String REPORTS_BADGE_ID = "cegas-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "cegas-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private Label notificationsBadge;
    private Label reportsBadge;
    private MenuItem settingsItem;
    private CssLayout configItemsLayout;
    
    public MenuView() {
        setStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }
    
    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight(100,Unit.PERCENTAGE);
        menuContent.addStyleName("backColorBlueLight");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
        //menuContent.addComponent(buildFooter());
     
        return menuContent;
    }
    
    private Component buildFooter() {
		
    	HorizontalLayout footer = new HorizontalLayout();
    	Label innovaro = new Label("Powered by Innovaro&copy;",ContentMode.HTML);
    	footer.addComponent(innovaro);
    	footer.setComponentAlignment(innovaro, Alignment.TOP_LEFT);
    	footer.setStyleName("backColorWhite");
    	footer.setWidth(100,Unit.PERCENTAGE);
    	footer.setHeight(25,Unit.PIXELS);
    	
		return footer;
	}

	private Component buildTitle() {
    	
    	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath +"/WEB-INF/images/cegas.png"));
        Image image = new Image(null,resource);
        image.setWidth(100,Unit.PERCENTAGE);
        image.setHeight(67,Unit.PIXELS);
        HorizontalLayout logoWrapper = new HorizontalLayout(image);
        logoWrapper.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        //logoWrapper.setStyleName(ValoTheme.LAYOUT_CARD);
        logoWrapper.addStyleName("menuTitle");
        logoWrapper.setMargin(false);
        logoWrapper.setWidth(100,Unit.PERCENTAGE);
        return logoWrapper;
    }



    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName()
                        .contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.addStyleName("whiteIcon");
        
        configItemsLayout = new CssLayout();
        configItemsLayout.addStyleName("valo-menuitems");
        configItemsLayout.setVisible(false);
        

        for (final DashboardViewType view : DashboardViewType.values()) {
        	Component menuItemComponent = botaoItemMenu(view);
            
            if(view == DashboardViewType.REVISAR) {
            	notificationsBadge = new Label();
                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
                menuItemComponent = buildBadgeWrapper(menuItemComponent,notificationsBadge);
                notificationsBadge.setValue("4");
            }
            
            if(view == DashboardViewType.APROVAR) {
            	notificationsBadge = new Label();
                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
                menuItemComponent = buildBadgeWrapper(menuItemComponent,notificationsBadge);
                notificationsBadge.setValue("5");
            }
            
            menuItemsLayout.addComponent(menuItemComponent);
            
            if(view.getViewName().equals(view.CONFIGURACAO.getViewName())) {
            	for (final DashboardConfigViewType viewConf : DashboardConfigViewType.values()) {
                    configItemsLayout.addComponent(botaoConfigItemMenu(viewConf));
                }
                menuItemsLayout.addComponent(configItemsLayout);
            }
        }
        
        return menuItemsLayout;
    }
    
    private Component build() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final DashboardViewType view : DashboardViewType.values()) {
           
            menuItemsLayout.addComponent(botaoItemMenu(view));
        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
            final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(true);
        badgeLabel.addStyleName("backColorWhite");
        dashboardWrapper.addComponent(badgeLabel);
        
        return dashboardWrapper;
    }


    private Button botaoItemMenu(DashboardViewType view) {
    	
    	Button botao = new Button();
    	
    	botao.setPrimaryStyleName("valo-menu-item");
    	botao.setIcon(view.getIcon());
    	botao.addStyleName("menuItemHover");
    	botao.setCaption(view.getViewName().substring(0, 1).toUpperCase() + view.getViewName().substring(1));
        if(view.getViewName().equals(view.CONFIGURACAO.getViewName())) {
        	botao.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                	if(configItemsLayout.isVisible()) {
                		configItemsLayout.setVisible(false);
                	}
                	else {
                		configItemsLayout.setVisible(true);
                	}
                }
            });
        }
        else {
        	botao.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo(view.getViewName());
                }
            });
        }
    	
    	return botao;
    }
    
    private Button botaoConfigItemMenu(DashboardConfigViewType view) {
    	
    	Button botao = new Button();
    	botao.setPrimaryStyleName("valo-menu-item");
    	botao.addStyleName("menuSubItens");
    	botao.addStyleName("menuItemHover");
    	botao.setIcon(view.getIcon());
    	botao.setWidth(280,Unit.PIXELS);
    	botao.setCaption(view.getViewName().substring(0, 1).toUpperCase() + view.getViewName().substring(1));
    	botao.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(view.getViewName());
            }
        });
    	
    	return botao;
    }
    
    public final class ValoMenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected";

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
            DashboardEventBus.register(this);
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo(view.getViewName());
                }
            });

        }

        @Subscribe
        public void postViewChange(final PostViewChangeEvent event) {
            removeStyleName(STYLE_SELECTED);
            if (event.getView() == view) {
                addStyleName(STYLE_SELECTED);
            }
        }
    }
    
}
