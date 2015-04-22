package org.vaadin.exampleapp.views.desktop;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class DesktopPersonViewLayout extends VerticalSplitPanel {
	
	public DesktopPersonViewLayout() {
		Design.read(this);
	}

}
