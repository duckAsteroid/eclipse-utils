package com.cs.eclipse.utils.test.generator.wizards;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class JUnitDependencyPage extends WizardPage {
	
	private ISelection selection;

	/**
	 * Create the wizard.
	 * @param selection 
	 */
	public JUnitDependencyPage(ISelection selection) {
		super("wizardPage");
		setTitle("Select JUnit dependency");
		setDescription("The wizard can automatically configure the new test fragment to inculde JUnit dependencies.");
		this.selection = selection;
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
		
		ComboViewer versionComboViewer = new ComboViewer(container, SWT.NONE);
		Combo versionCombo = versionComboViewer.getCombo();
		versionCombo.setToolTipText("The version of JUnit to include as a dependency. Typically the highest version number makes sense.");
		versionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		versionComboViewer.setContentProvider(new ArrayContentProvider());
		versionComboViewer.setInput(new Object[]{"4.8.2","4.8.1","3.8.2"});
		versionCombo.select(2);
		
		Label lblDependencyAs = new Label(container, SWT.NONE);
		lblDependencyAs.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDependencyAs.setText("Dependency as:");
		
		ComboViewer dependencyTypeComboViewer = new ComboViewer(container, SWT.NONE);
		Combo dependencyTypeCombo = dependencyTypeComboViewer.getCombo();
		dependencyTypeCombo.setToolTipText("How the JUnit dependency is added to your test fragment. Eclipse historically uses Require-Bundle; though Import-Package is the preferred option.");
		dependencyTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dependencyTypeComboViewer.setContentProvider(new ArrayContentProvider());
		dependencyTypeComboViewer.setInput(new Object[]{"Import-Package","Require-Bundle"});
		dependencyTypeCombo.select(0);
		
	}

}
