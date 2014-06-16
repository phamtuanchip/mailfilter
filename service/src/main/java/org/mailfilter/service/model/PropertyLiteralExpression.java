
package org.mailfilter.service.model;

public class PropertyLiteralExpression<T>  {

	private final Class<T> type;
	private final String name;

	public PropertyLiteralExpression(final Class<T> type, final String name) {

		if (type == null) {
			throw new NullPointerException();
		}

		if (name == null) {
			throw new NullPointerException();
		}

		this.type = type;
		this.name = name;
	}

	public Class<T> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
