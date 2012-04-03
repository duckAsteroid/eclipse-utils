package com.cs.eclipse.utils.test.generator.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class JUnitDependencyPage extends WizardPage {
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * Create the wizard.
	 */
	public JUnitDependencyPage() {
		super("wizardPage");
		setTitle("Select JUnit dependency");
		setDescription("The wizard can automatically configure the new test fragment to inculde JUnit dependencies.");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Button btnCheckButton = new Button(container, SWT.CHECK);
		btnCheckButton.setSelection(true);
		btnCheckButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnCheckButton.setText("Create JUnit dependency");
		
		Label lblJunitVersion = new Label(container, SWT.NONE);
		lblJunitVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblJunitVersion.setText("JUnit Version:");
		
		ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		Combo versionCombo = comboViewer.getCombo();
		versionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDependencyAs = new Label(container, SWT.NONE);
		lblDependencyAs.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDependencyAs.setText("Dependency as:");
		
		ComboViewer comboViewer_1 = new ComboViewer(container, SWT.NONE);
		Combo dependencyTypeCombo = comboViewer_1.getCombo();
		dependencyTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboViewer_1.setContentProvider(new ArrayContentProvider());
		comboViewer_1.setInput(new Object[]{});
		
	}

}
