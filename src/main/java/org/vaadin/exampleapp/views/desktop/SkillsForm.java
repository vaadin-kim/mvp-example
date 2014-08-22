package org.vaadin.exampleapp.views.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.vaadin.exampleapp.data.Skill;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SkillsForm extends VerticalLayout {

	@PropertyId("skills")
	private TwinColSelect skills = new TwinColSelect();

	public SkillsForm() {
		setSizeFull();
		setMargin(true);

		skills.setHeight("100%");
		skills.setWidth("450px");
		skills.setCaption("Skills");
		skills.setConverter(new Converter<Object, List>() {

			@Override
			public List<?> convertToModel(Object value,
					Class<? extends List> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				if (value != null) {
					return new ArrayList<Object>((Collection) value);
				}
				return new ArrayList<Object>();
			}

			@Override
			public Object convertToPresentation(List value,
					Class<? extends Object> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				if (value != null) {
					return new HashSet<Object>(value);
				}

				return new HashSet<Object>();

			}

			@Override
			public Class<List> getModelType() {
				return List.class;
			}

			@Override
			public Class<Object> getPresentationType() {
				return Object.class;
			}

		});

		addComponent(skills);
	}

	public void setAvailableSkills(List<Skill> availableSkills) {
		BeanItemContainer<Skill> skillsContainer = new BeanItemContainer<Skill>(
				Skill.class, availableSkills);
		skills.setContainerDataSource(skillsContainer);
		skills.setItemCaptionPropertyId("skillName");
	}

}
