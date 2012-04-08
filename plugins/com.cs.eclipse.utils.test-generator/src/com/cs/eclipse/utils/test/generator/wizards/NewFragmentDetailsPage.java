package com.cs.eclipse.utils.test.generator.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.natures.PluginProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.cs.eclipse.utils.test.generator.TestGeneratorPlugin;

/**
 * This page provides controls to customise the fragment project basic details, such as:
 * <ul>
 * <li>Fragment Name
 * <li>Fragment Version
 * <li>Host plugin
 * </ul>
 */
public class NewFragmentDetailsPage extends WizardPage {
	private static final String PDE_NATURE = "org.eclipse.pde.PluginNature";

	private ISelection selection;
	
	private Text hostPluginText;
	private Text fragmentIdText;
	private Text fragmentNameText;
	private Text fragmentVendorText;
	private Text fragmentVersionText;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewFragmentDetailsPage(ISelection selection) {
		super("wizardPage");
		setTitle("Create New Test Fragment");
		setDescription("This wizard creates a new test fragment for the currently selected plugin project, copying settings from the host plugin.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		
		// Host
		
		Label lblhostPlugin = new Label(container, SWT.NULL);
		lblhostPlugin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblhostPlugin.setText("&Host plugin:");

		// FIXME Add auto complete to this field - all plugins in workspace
		hostPluginText = new Text(container, SWT.BORDER | SWT.SINGLE);
		hostPluginText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hostPluginText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		
		// Frag ID
		
		Label lblfragmentId = new Label(container, SWT.NULL);
		lblfragmentId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblfragmentId.setText("&Fragment ID:");

		fragmentIdText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fragmentIdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fragmentIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		// NAME
		
		Label lblfragmentName = new Label(container, SWT.NONE);
		lblfragmentName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblfragmentName.setText("Fragment &Name:");
		
		fragmentNameText = new Text(container, SWT.BORDER);
		fragmentNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fragmentNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		// VENDOR
		
		Label lblFragmentvendor = new Label(container, SWT.NONE);
		lblFragmentvendor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFragmentvendor.setText("Fragment &Vendor:");
		
		fragmentVendorText = new Text(container, SWT.BORDER);
		fragmentVendorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fragmentVendorText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		// VERSION
		
		Label lblFragmentVersion = new Label(container, SWT.NONE);
		lblFragmentVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFragmentVersion.setText("Fragment Version:");
		
		fragmentVersionText = new Text(container, SWT.BORDER);
		fragmentVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fragmentVersionText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		initialize();
		dialogChanged();
		setControl(container);
		
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IProject) {
				IProject prj = (IProject)obj;
				try {
					if (prj.hasNature(PDE_NATURE)){
						IPluginModelBase pluginModel = PluginRegistry.findModel(prj);
						if (pluginModel != null) {
							if (pluginModel.isFragmentModel()) {
								updateStatus("Selected project is a fragment project; please enter data manually");
							}
							else {
								initializeFromPlugin(pluginModel);
							}
						}
					}
				}
				catch (CoreException e) {
					TestGeneratorPlugin.getDefault().getLog().log(e.getStatus());
				}
				
			}
		}
	}

	/**
	 * Sets up the UI based on data from the selected (assumed host) plugin
	 * @param pluginModel The model to select
	 */
	private void initializeFromPlugin(IPluginModelBase pluginModel) throws CoreException {
		// FIXME populate UI
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		// FIXME Highlight any errors
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
}