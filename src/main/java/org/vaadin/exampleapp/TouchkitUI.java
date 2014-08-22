package org.vaadin.exampleapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.exampleapp.views.mobile.MobilePersonView;
import org.vaadin.spring.VaadinUI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@VaadinUI(path="/mobile")
@Theme("touchkit")
@Widgetset("org.vaadin.exampleapp.ExampleAppWidgetset")
public class TouchkitUI extends UI {
	
	@Autowired
	private MobilePersonView personView;

	@Override
	protected void init(VaadinRequest request) {
		setContent(personView);		
	}

}
