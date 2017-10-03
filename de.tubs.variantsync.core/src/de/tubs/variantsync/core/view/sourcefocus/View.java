package de.tubs.variantsync.core.view.sourcefocus;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;

import de.tubs.variantsync.core.VariantSyncPlugin;
import de.tubs.variantsync.core.data.Context;
import de.tubs.variantsync.core.patch.interfaces.IDelta;
import de.tubs.variantsync.core.patch.interfaces.IPatch;
import de.tubs.variantsync.core.utilities.TreeNode;
import de.tubs.variantsync.core.view.resourcechanges.ResourceChangesColumnLabelProvider;

public class View extends ViewPart implements SelectionListener, ISelectionChangedListener {

	private Combo cbFeature;
	private TreeViewer tvChanges;
	private Text lbChange;
	private Button btnSyncAuto;
	private Button btnSyncManual;
	private org.eclipse.swt.widgets.List autoSyncTargets;
	private org.eclipse.swt.widgets.List manualSyncTargets;

	public View() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(4, false));

		Composite selectFeature = new Composite(parent, SWT.NONE);
		selectFeature.setLayout(new GridLayout(4, false));
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = false;
		selectFeature.setLayoutData(gridData);
		
		Label lblFeatureExpression = new Label(selectFeature, SWT.NONE);
		lblFeatureExpression.setText("Feature Expression");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.LEFT;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = false;
		lblFeatureExpression.setLayoutData(gridData);
		
		cbFeature = new Combo(selectFeature, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		cbFeature.setLayoutData(gridData);
		cbFeature.setItems(VariantSyncPlugin.getDefault().getActiveEditorContext().getFeatureExpressionsAsStrings().toArray(new String[] {}));
		cbFeature.addSelectionListener(this);
		
		tvChanges = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalSpan = 4;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		tvChanges.setContentProvider(new SourceFocusTreeContentProvider());
		tvChanges.addSelectionChangedListener(this);
		tvChanges.getTree().setLayoutData(gridData);
		setupTreeViewer(tvChanges.getTree());
		
//		Composite codeChange = new Composite(parent, SWT.BORDER);
//		codeChange.setLayout(new GridLayout(1, false));
//		gridData = new GridData();
//		gridData.verticalAlignment = SWT.FILL;
//		gridData.horizontalAlignment = SWT.FILL;
//		gridData.verticalSpan = 2;
//		gridData.horizontalSpan = 2;
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.grabExcessVerticalSpace = true;
//		codeChange.setLayoutData(gridData);
		
		lbChange = new Text(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalSpan = 2;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.heightHint = 300;
		lbChange.setLayoutData(gridData);
		lbChange.setEditable(false);
		
		Composite targets = new Composite(parent, SWT.NONE);
		targets.setLayout(new GridLayout(2, false));
		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalSpan = 2;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		targets.setLayoutData(gridData);
		
		autoSyncTargets = new org.eclipse.swt.widgets.List(targets, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		autoSyncTargets.setLayoutData(gridData);
		
		manualSyncTargets = new org.eclipse.swt.widgets.List(targets, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		manualSyncTargets.setLayoutData(gridData);
		
		btnSyncAuto = new Button(targets, SWT.NONE);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.BOTTOM;
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = false;
		gridData.grabExcessVerticalSpace = false;
		btnSyncAuto.setLayoutData(gridData);
		btnSyncAuto.setText("Auto Sync");
		btnSyncAuto.setEnabled(false);
		
		btnSyncManual = new Button(targets, SWT.NONE);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.BOTTOM;
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = false;
		gridData.grabExcessVerticalSpace = false;
		btnSyncManual.setLayoutData(gridData);
		btnSyncManual.setText("Manual Sync");
		btnSyncManual.setEnabled(false);
	}
	
	protected void setupTreeViewer(final Tree tree) {
		tree.setLinesVisible(true);
		tree.setHeaderVisible(false);

		TableLayout layout = new TableLayout();
		tree.setLayout(layout);
		tree.setHeaderVisible(true);

		TreeColumn col = new TreeColumn(tree, SWT.None, 0);
		col.setText("Resource");
		TreeViewerColumn tvCol = new TreeViewerColumn(tvChanges, col);
		tvCol.setLabelProvider(new ResourceChangesColumnLabelProvider(0));
		layout.addColumnData(new ColumnWeightData(1, 300, true));

		col = new TreeColumn(tree, SWT.None, 1);
		col.setText("Time");
		tvCol = new TreeViewerColumn(tvChanges, col);
		tvCol.setLabelProvider(new ResourceChangesColumnLabelProvider(4));
		layout.addColumnData(new ColumnWeightData(1, 250, true));
	}

	@Override
	public void setFocus() {
		cbFeature.setFocus();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String feature = cbFeature.getItem(cbFeature.getSelectionIndex());
		updateTreeViewer(feature);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		//CLEAR ALL
	}

	protected void updateTreeViewer(String feature) {
		Context context = VariantSyncPlugin.getDefault().getActiveEditorContext();
		if (context != null) {
			List<IPatch<?>> patches = context.getPatches();
			IPatch<?> actualPatch = context.getActualContextPatch();
			if (actualPatch != null && !patches.contains(actualPatch)) patches.add(actualPatch);

			if (patches != null && !patches.isEmpty()) tvChanges.setInput(FeatureTree.construct(feature, patches));
			tvChanges.expandToLevel(3);
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ITreeSelection selection = tvChanges.getStructuredSelection();
		if (selection.size()==1) {
			Object o = selection.getFirstElement();
			if (o instanceof TreeNode) 
				o = ((TreeNode) o).getData();
			if (o instanceof IDelta) {
				lbChange.setText(((IDelta<?>) o).getRepresentation());
			} else {
				lbChange.setText("");
			}
		} else {
			IResource res = null;
			String ret = "";
			for (Object o : selection.toList()) {
				if (o instanceof TreeNode) 
					o = ((TreeNode) o).getData();
				if (o instanceof IDelta) {
					if (res == null)
						res = ((IDelta<?>) o).getResource();
					if (!res.equals(((IDelta<?>) o).getResource())) { 
						lbChange.setText("No multiple resources supported");
						return;
					}
					ret += ret.isEmpty()?((IDelta<?>) o).getRepresentation():"\n\n" + ((IDelta<?>) o).getRepresentation();
				}
			}
			lbChange.setText(ret);
		}
	}
	
}
