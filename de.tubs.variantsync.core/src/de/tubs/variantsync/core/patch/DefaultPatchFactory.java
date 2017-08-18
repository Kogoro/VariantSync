package de.tubs.variantsync.core.patch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;

import de.tubs.variantsync.core.exceptions.PatchException;
import de.tubs.variantsync.core.patch.interfaces.IPatch;
import de.tubs.variantsync.core.patch.interfaces.IPatchFactory;
import difflib.Chunk;
import difflib.DiffUtils;


/**
 * DefaultPatchFactory
 * @author Christopher Sontag
 * @since 18.08.2017
 * @TODO Replace with line-wise diff
 */
public class DefaultPatchFactory<Chunk> implements IPatchFactory<Chunk> {

	private static IPatchFactory<?> instance = new DefaultPatchFactory();

	@Override
	public String getId() {
		return "de.tubs.variantsync.core.diff";
	}


	@Override
	public String getName() {
		return "Line-wise Diff";
	}

	public static IPatchFactory<?> getInstance() {
		return instance;
	}

	@Override
	public String toString() {
		return "DefaultPatchFactory [getId()=" + getId() + ", getName()=" + getName() + "]";
	}

	@Override
	public IFile applyPatch(IFile res, IPatch<Chunk> patch) {
		
		return null;
	}


	@Override
	public IFile reversePatch(IFile res, IPatch<Chunk> patch) {
		
		return null;
	}


	@Override
	public boolean verifyPatch(IFile res, IPatch<Chunk> patch) {
		
		return false;
	}


	@Override
	public IPatch<Chunk> createPatch(IResource res) throws PatchException {
		
		return null;
	}


	@Override
	public IPatch<Chunk> createPatch(IResource res, IFileState oldState) throws PatchException {
		// TODO Auto-generated method stub
		return null;
	}
}