package br.com.innovaro.gd.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UsuarioView extends VerticalLayout implements View{

	public UsuarioView() {
		
		Label novoUsuario = new Label("<b>NOVO USUÁRIO<b>",ContentMode.HTML);
		TextField nome = new TextField("Usuário");
		nome.setWidth("300px");
		TextField email = new TextField("Email");
		email.setWidth("300px");
		PasswordField senha = new PasswordField("Senha");
		senha.setWidth("300px");
		PasswordField confirmarSenha = new PasswordField("Confirmar Senha");
		confirmarSenha.setWidth("300px");
		
		List<String> tiposPermissao = new ArrayList<>();
		tiposPermissao.add("Básico");
		tiposPermissao.add("Gerente");
		tiposPermissao.add("Admin");
		ComboBox<String> permissao = new ComboBox<>("Permissão",tiposPermissao);
		permissao.setEmptySelectionAllowed(false);
		permissao.setValue("Básico");
		permissao.setWidth("300px");
		
		Button adicionarUsuario = new Button("Adicionar");
		
		
		addComponents(novoUsuario,nome,email,senha,confirmarSenha,permissao,adicionarUsuario);
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
	}
}
