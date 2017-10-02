package de.tubs.variantsync.core.patch;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;

import difflib.Chunk;

public class DefaultDelta extends ADelta<Chunk> {

	public DefaultDelta(IResource res) {
		super(res);
	}

	@Override
	public String getOriginalAsString() {
		String ret = String.valueOf(original.getPosition());
		for (String line : (List<String>)original.getLines()) {
			ret = ret + ";" + line;
		}
		return ret;
	}

	@Override
	public void setOriginalFromString(String original) {
		List<String> elements = Arrays.asList(original.split(";"));
		int pos = Integer.valueOf(elements.get(0));
		elements = elements.subList(1, elements.size());
		this.original = new Chunk<String>(pos, elements);
	}

	@Override
	public String getRevisedAsString() {
		String ret = String.valueOf(revised.getPosition());
		for (String line : (List<String>)revised.getLines()) {
			ret = ret + ";" + line;
		}
		return ret;
	}

	@Override
	public void setRevisedFromString(String revised) {
		List<String> elements = Arrays.asList(revised.split(";"));
		int pos = Integer.valueOf(elements.get(0));
		elements = elements.subList(1, elements.size());
		this.revised = new Chunk<String>(pos, elements);
	}

}