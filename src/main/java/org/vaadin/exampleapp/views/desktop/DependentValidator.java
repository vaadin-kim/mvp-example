package org.vaadin.exampleapp.views.desktop;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.ui.Field;

@SuppressWarnings("serial")
public class DependentValidator implements Validator {

	private List<Field<String>> fields = new ArrayList<Field<String>>();

	public void addDependentField(Field<String> field) {
		fields.add(field);
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		Boolean hasEmptyValue = null;
		for (Field<String> f : fields) {
			boolean isEmpty = f.getValue() == null || f.getValue().isEmpty();
			if (hasEmptyValue == null) {
				hasEmptyValue = isEmpty;
				continue;
			}

			if (hasEmptyValue != isEmpty) {
				throw new InvalidValueException(
						"All fields need to be either empty or have a value");
			}
		}
	}

}
