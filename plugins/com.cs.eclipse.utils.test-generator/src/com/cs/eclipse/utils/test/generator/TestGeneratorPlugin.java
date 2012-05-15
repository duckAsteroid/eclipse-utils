package com.cs.eclipse.utils.test.generator;

import java.net.URL;

import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class TestGeneratorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.cs.eclipse.utils.test-generator"; //$NON-NLS-1$

	// The shared instance
	private static TestGeneratorPlugin plugin;
	
	private IBundleProjectService projectService;
	
	private ServiceTracker<IBundleProjectService, URL> tracker;
	
	/**
	 * The constructor
	 */
	public TestGeneratorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		tracker = new ServiceTracker<IBundleProjectService, URL>(context, IBundleProjectService.class, null);
		
		tracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		projectService = null;
		tracker.close();
		tracker = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TestGeneratorPlugin getDefault() {
		return plugin;
	}

}
