package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.model.Template;

public class EditTemplateView extends VerticalLayout implements View{
	
	HorizontalLayout layoutHorizontal;
	VerticalLayout layoutVertical;
	TextField idText = new TextField();
	TextField nomeText = new TextField();
	Button alterar;
	Template template;
	
	public EditTemplateView() {
		addComponent(criaCampos());
		addComponent(adicionarSecao());
		
		Grid<Secao> grid = new Grid();
		List<Secao> lista = new ArrayList<>();
		Secao novo = new Secao();
		novo.setNome("Seção 1 - OBJETIVO");
		Secao novo2 = new Secao();
		novo2.setNome("Seção 2 - ABRANGÊNCIA");
		lista.add(novo);
		lista.add(novo2);
		
		grid.setItems(lista);
		grid.addColumn(Secao::getNome).setCaption("Nome do Template");
		grid.addColumn(template -> "Editar",
			      new ButtonRenderer(clickEvent -> {
			    	  
			    })).setWidth(getWidth()).setCaption("");
		
		grid.setSizeFull();
		addComponent(grid);
	}
	
	public VerticalLayout criaCampos() {
		
		Label title = new Label("Editar Modelo de Documento");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		Label label = new Label("<b>MODELO<b>",ContentMode.HTML);
		CssLayout layoutCss = new CssLayout();
		layoutVertical = new VerticalLayout();
		
		
		idText.setCaption("Código");
		idText.setEnabled(false);
		
		alterar = new Button("Alterar");
		
		Label separator = new Label("<hr />",ContentMode.HTML);
		separator.setSizeFull();
		
		layoutCss.addComponents(nomeText,alterar);
		layoutCss.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		layoutCss.setCaption("Nome");
		layoutVertical.addComponents(title,label,layoutCss, separator);
		
		layoutVertical.setMargin(false);
		
		return layoutVertical;
	}
	
private VerticalLayout adicionarSecao() {
		
		TextField novaSecao = new TextField("Nome da Seção");
		novaSecao.setWidth(100, Unit.PERCENTAGE);
		
		List<String> dadosTipoSecao = new ArrayList<>();
		dadosTipoSecao.add("Campo");
		dadosTipoSecao.add("Texto");
		
		
		Button btnAdicionarSecao = new Button("Adicionar");
		
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponents(novaSecao);
		layout.setMargin(false);
		layout.setSizeFull();
		VerticalLayout vertical = new VerticalLayout();
		vertical.addComponents(new Label("<b>SEÇÃO<b>",ContentMode.HTML),layout,btnAdicionarSecao);
		vertical.setMargin(false);
		
		return vertical;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
}
