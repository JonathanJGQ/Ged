package br.com.innovaro.gd.view;

import java.awt.Checkbox;
import java.util.Arrays;
import java.util.List;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class AmbienteView extends GenericView{
	public AmbienteView() {
		HorizontalLayout cabecalho = criarCabecalho("Geral", "ambiente_ajuda");
		Label separador = new Label("<hr/>",ContentMode.HTML);
		separador.setWidth(100,Unit.PERCENTAGE);
		Label fonte = new Label("<strong>Fonte</strong>",ContentMode.HTML);
		
		CheckBox editarFonte = new CheckBox("Habilitar edição de fonte");
		
		addComponents(cabecalho,separador, fonte,editarFonte);
	}
}
