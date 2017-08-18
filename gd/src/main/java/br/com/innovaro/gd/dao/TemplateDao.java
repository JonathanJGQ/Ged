package br.com.innovaro.gd.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.innovaro.gd.model.Template;

public class TemplateDao {
	public static int id = 1;
	public static List<Template> templates = new ArrayList<>();
	
	public void add(Template template) {
		id++;
		templates.add(template);
	}
	
	public void remove(Template template) {
		templates.remove(template);
	}
	
	public void update(Template template) {
		
	}
}
