package de.tubs.variantsync.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.tubs.variantsync.core.VariantSyncPlugin;
import de.tubs.variantsync.core.patch.PatchFactoryManager;
import de.tubs.variantsync.core.patch.interfaces.IPatchFactory;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = VariantSyncPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_PATCHFACTORY, PatchFactoryManager.getDefaultFactory().getId());
	}

}