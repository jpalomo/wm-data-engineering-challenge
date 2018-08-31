package com.palomo.wm.data.engineering.challenge;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

public class EtlServiceConfiguration extends Configuration {

	@NotEmpty
	private String inputFile;

	@NotEmpty
	private String outputDir;

	@NotEmpty
	private String jdbcConnectionUrl;

	@NotEmpty
	private String destinationTable;

	@NotEmpty
	private String outputFilePrefix;

	@NotEmpty
	private String errorFilePrefix;

	@Min(1)
	@Max(Integer.MAX_VALUE)
	private int batchSize;

    private boolean doLoad; 

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getJdbcConnectionUrl() {
		return jdbcConnectionUrl;
	}

	public void setJdbcConnectionUrl(String jdbcConnectionUrl) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
	}

	public String getDestinationTable() {
		return destinationTable;
	}

	public void setDestinationTable(String destinationTable) {
		this.destinationTable = destinationTable;
	}

	public String getOutputFilePrefix() {
		return outputFilePrefix;
	}

	public void setOutputFilePrefix(String outputFilePrefix) {
		this.outputFilePrefix = outputFilePrefix;
	}

	public String getErrorFilePrefix() {
		return errorFilePrefix;
	}

	public void setErrorFilePrefix(String errorFilePrefix) {
		this.errorFilePrefix = errorFilePrefix;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public boolean isDoLoad() {
		return doLoad;
	}

	public void setDoLoad(boolean doLoad) {
		this.doLoad = doLoad;
	} 
}
