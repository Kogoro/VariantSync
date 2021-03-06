package de.tubs.variantsync.core.data;

import de.ovgu.featureide.fm.core.color.FeatureColor;

public class FeatureExpression {
	
	public String name;
	public FeatureColor highlighter;
	
	public FeatureExpression() {
		this.name = "";
		this.highlighter = FeatureColor.NO_COLOR;
	}
	
	public FeatureExpression(String name) {
		super();
		this.name = name;
		this.highlighter = FeatureColor.Yellow;
	}

	public FeatureExpression(String name, FeatureColor highlighter) {
		super();
		this.name = name;
		this.highlighter = highlighter;
	}

	public boolean isComposed() {
		return name.matches(".*(or|and|not).*");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((highlighter == null) ? 0 : highlighter.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeatureExpression other = (FeatureExpression) obj;
		if (highlighter != other.highlighter)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
