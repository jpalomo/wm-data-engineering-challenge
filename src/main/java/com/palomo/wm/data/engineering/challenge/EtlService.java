package com.palomo.wm.data.engineering.challenge;

import com.palomo.wm.data.engineering.challenge.core.LabResultTransformer;
import com.palomo.wm.data.engineering.challenge.core.LabResultLoader;

import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

public class EtlService extends Application<EtlServiceConfiguration> {

	public static void main(String args[]) throws Exception {
		(new EtlService()).run(args);
	}

	@Override
	public void run(EtlServiceConfiguration configuration, Environment environment) throws Exception {
		environment.lifecycle().manage(new EtlProcessManager(configuration));
	}
}

/**
 * Simple class that manages the extract, transform, and load process.
 * This was created to allow the HTTP server to be responsive to incoming requests
 * while running the process in the background.
 *
 */
class EtlProcessManager implements Managed {

	private final EtlServiceConfiguration configuration;

	public EtlProcessManager(EtlServiceConfiguration configurtion) {
		this.configuration = configurtion;
	}

	@Override
	public void start() throws Exception {
		new LabResultTransformer(configuration).transform();
		if (configuration.isDoLoad()) {
			new LabResultLoader(configuration).load();
		}
	}

	@Override
	public void stop() throws Exception {
		// Dont need to do anything right now. Will be useful if I can get this in
		// docker and deployed to AWS
	}
}
