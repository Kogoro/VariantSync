package de.tubs.variantsync.core.patch.interfaces;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;
import de.ovgu.featureide.fm.core.IExtension;
import de.tubs.variantsync.core.exceptions.PatchException;
import de.tubs.variantsync.core.patch.interfaces.IDelta.DELTATYPE;

/**
 * Factory to create patches from files
 *
 * @author Christopher Sontag
 * @version 1.0
 * @since 15.08.2017
 * @param <T> file element, e.g. line, ast element, ...
 */
public interface IPatchFactory<T> extends IExtension {

	public static String extensionPointID = "PatchFactory";

	public static String extensionID = "patchFactory";
	
	String getName();
	
	IPatch<IDelta<T>> createPatch(String context);
	
	/**
	 * Creates a empty delta object
	 * @param res
	 * @return
	 */
	IDelta<T> createDelta(IResource res);
	
	/**
	 * Creates a delta object for a resource
	 * 
	 * @param res - resource
	 * @param timestamp - timestamp
	 * @param kind - type of change
	 * @return patch object
	 */
	List<IDelta<T>> createDeltas(IResource res, long timestamp, DELTATYPE kind) throws PatchException;

	/**
	 * Creates patch object from a changed resource.
	 * 
	 * @param res - resource
	 * @param oldState - last history state
	 * @param kind - type of change
	 * @return patch object
	 */
	List<IDelta<T>> createDeltas(IResource res, IFileState oldState, long timestamp, DELTATYPE kind) throws PatchException;
	
	/**
	 * Patches a resource with a given patch.
	 * 
	 * @param res - resource
	 * @param patch - patch
	 * @return patched temp resource
	 */
	IFile applyDelta(IFile res, IDelta<T> patch);
	
	/**
	 * Unpatches a revised resource for a given patch.
	 * 
	 * @param res - the resource
	 * @param patch - patch
	 * @return original resource
	 */
	IFile reverseDelta(IFile res, IDelta<T> patch);
	
	/**
	 * Verifies that the given patch can be applied to the given resource
	 * 
	 * @param res - the resource
	 * @param patch - the patch to verify
	 * @return true if the patch can be applied
	 */
	boolean verifyDelta(IFile res, IDelta<T> patch);
	
	/**
	 * Checks whether the file is supported
	 * @param file
	 * @return true if the file is supported
	 */
	boolean isSupported(IFile file);
	
}
