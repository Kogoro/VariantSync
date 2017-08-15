package de.tubs.variantsync.core.view.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

import de.tubs.variantsync.core.VariantSyncPlugin;
import de.tubs.variantsync.core.data.FeatureExpression;

public class DynamicContextDropDownItems extends CompoundContributionItem implements IWorkbenchContribution {

	private IServiceLocator mServiceLocator;

	@Override
	protected IContributionItem[] getContributionItems() {
		if (VariantSyncPlugin.getDefault() != null && VariantSyncPlugin.getContext() != null
				&& VariantSyncPlugin.getContext().getFeatureExpressions() != null) {
			List<FeatureExpression> expressions = VariantSyncPlugin.getContext().getFeatureExpressions();

			List<IContributionItem> items = new ArrayList<>();
			for (FeatureExpression expression : expressions) {
				
				// Root element of the feature model will not be added
				if (!VariantSyncPlugin.getContext().getConfigurationProject().getFeatureModel().getStructure().getRoot()
						.getFeature().getName().equals(expression.name)) {

					// Create a CommandContributionItem with a message which contains the feature
					// expression
					Map<String, String> params = new HashMap<String, String>();
					params.put(SelectContextHandler.PARM_MSG, expression.name);

					final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(
							mServiceLocator, SelectContextHandler.ID, SelectContextHandler.ID, CommandContributionItem.STYLE_RADIO);
					contributionParameter.visibleEnabled = true;
					contributionParameter.parameters = params;
					contributionParameter.label = expression.name;
					contributionParameter.icon = VariantSyncPlugin.imageDescriptorFromPlugin(VariantSyncPlugin.PLUGIN_ID,
							"icons/public_co.gif");

					// Composed expressions will have another indicator as pure features
					if (expression.isComposed())
						contributionParameter.icon = VariantSyncPlugin
								.imageDescriptorFromPlugin(VariantSyncPlugin.PLUGIN_ID, "icons/protected_co.gif");

					items.add(new CommandContributionItem(contributionParameter));
				}
			}

			return items.toArray(new IContributionItem[items.size()]);
		}
		return null;
	}

	@Override
	public void initialize(final IServiceLocator serviceLocator) {
		mServiceLocator = serviceLocator;
	}

	@Override
	public boolean isDirty() {
		return true;
	}

}