package com.cs.eclipse.utils.test.generator.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
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

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (zzz).
 */

public class NewTestFragmentWizardPage extends WizardPage {
	private Text hostPluginText;

	private Text fragmentIdText;

	private ISelection selection;
	private Label lblhostPlugin;
	private GridData gd_hostPluginText;
	private Label lblfragmentName;
	private Text fragmentNameText;
	private Label lblFragmentvendor;
	private Text fragmentVendorText;
	private Label lblFragmentVersion;
	private Text fragmentVersionText;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewTestFragmentWizardPage(ISelection selection) {
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
		Label label;
		lblhostPlugin = new Label(container, SWT.NULL);
		lblhostPlugin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblhostPlugin.setText("&Host plugin:");

		hostPluginText = new Text(container, SWT.BORDER | SWT.SINGLE);
		hostPluginText.setEnabled(false);
		GridData gd_fragmentIdText;
		gd_hostPluginText = new GridData(GridData.FILL_HORIZONTAL);
		hostPluginText.setLayoutData(gd_hostPluginText);
		hostPluginText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("&Fragment ID:");

		fragmentIdText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd_fragmentIdText = new GridData(GridData.FILL_HORIZONTAL);
		fragmentIdText.setLayoutData(gd_fragmentIdText);
		fragmentIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
		
		lblfragmentName = new Label(container, SWT.NONE);
		lblfragmentName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblfragmentName.setText("Fragment &Name:");
		
		fragmentNameText = new Text(container, SWT.BORDER);
		fragmentNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblFragmentvendor = new Label(container, SWT.NONE);
		lblFragmentvendor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFragmentvendor.setText("Fragment &Vendor:");
		
		fragmentVendorText = new Text(container, SWT.BORDER);
		fragmentVendorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblFragmentVersion = new Label(container, SWT.NONE);
		lblFragmentVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFragmentVersion.setText("Fragment Version:");
		
		fragmentVersionText = new Text(container, SWT.BORDER);
		fragmentVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				hostPluginText.setText(container.getFullPath().toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("zzz") == false) {
				updateStatus("File extension must be \"zzz\"");
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return hostPluginText.getText();
	}

	public String getFileName() {
		return fragmentIdText.getText();
	}
}