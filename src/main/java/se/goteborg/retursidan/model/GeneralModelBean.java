package se.goteborg.retursidan.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Base class for all model beans.
 *
 */
public abstract class GeneralModelBean {
	
	/**
	 * Returns a nice formatted string representation of the bean and its fields
	 */
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		return builder.toString().replace("<null>", "null");
	}
}
