package com.palomo.wm.data.engineering.challenge.core;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.palomo.wm.data.engineering.challenge.EtlServiceConfiguration;

public class LabResultLoader {
	private static final Logger log = LoggerFactory.getLogger(LabResultLoader.class);

	private final EtlServiceConfiguration configuration;

	public LabResultLoader(EtlServiceConfiguration configuration) throws IOException, SQLException {
		this.configuration = configuration;
	}

	public void load() throws SQLException {
		List<String> filesToLoad = getBulkLoadFileNames(configuration.getOutputDir()); 
		bulkCopy(configuration, filesToLoad);
	}

	private List<String> getBulkLoadFileNames(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();

		List<String> fileNames = new ArrayList<>();
		for (int i = 0; i < listOfFiles.length; i++) {
			String fileName = listOfFiles[i].getPath();
			fileNames.add(fileName);
		}
		return fileNames;
	}

	private void bulkCopy(EtlServiceConfiguration config, List<String> filesToLoad) throws SQLException {
		long loadStart = System.currentTimeMillis();
		log.info(String.format("bulkd load started at [%d]", loadStart));

		try (Connection connection = DriverManager.getConnection(config.getJdbcConnectionUrl())) {
			int batch = 1;
			int batchSize = 0;
			long batchStart = System.currentTimeMillis();

			for (String filePath : filesToLoad) {
				if (filePath.contains(config.getOutputFilePrefix())) {
					try (Statement stmt = connection.createStatement();
							SQLServerBulkCopy bulkCopy = new SQLServerBulkCopy(connection);
							SQLServerBulkCSVFileRecord fileRecord = new SQLServerBulkCSVFileRecord(filePath, false);) {
						fileRecord.addColumnMetadata(1, "batch_id", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(2, "vendor_id", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(3, "product_id", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(4, "lab_id", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(5, "state", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(6, "tested_at", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(7, "expires_at", java.sql.Types.NVARCHAR, 255, 0);
						fileRecord.addColumnMetadata(8, "thc", java.sql.Types.DECIMAL, 18, 2);
						fileRecord.addColumnMetadata(9, "thca", java.sql.Types.DECIMAL, 18, 2);
						fileRecord.addColumnMetadata(10, "cbd", java.sql.Types.DECIMAL, 18, 2);
						fileRecord.addColumnMetadata(11, "cbda", java.sql.Types.DECIMAL, 18, 2);
						fileRecord.addColumnMetadata(12, "potency", java.sql.Types.DECIMAL, 18, 2);

						bulkCopy.setDestinationTableName(config.getDestinationTable());
						bulkCopy.writeToServer(fileRecord);

						++batchSize;
						if (batchSize % 10 == 0) {
							log.info(String.format("finished processing batch: [%d] with 10 files in [%d] ms", batch,
									System.currentTimeMillis() - batchStart));
							batch++;
							batchStart = System.currentTimeMillis();
							batchSize = 1;
						}
					}
				}
			}
		}

		log.info(String.format("bulk load finished with an elapsed time of [%d]", System.currentTimeMillis() - loadStart));
	}
}