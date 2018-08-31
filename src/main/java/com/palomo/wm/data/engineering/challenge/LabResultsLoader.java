package com.palomo.wm.data.engineering.challenge;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;

public class LabResultsLoader {
	private static final Logger log = LoggerFactory.getLogger(LabResultsLoader.class);

	public static void main(String args[]) throws IOException, SQLException {
		BasicConfigurator.configure();
		String directory = "C:\\Users\\jpalomo\\Desktop\\wm_data";
		new LabResultsLoader(directory);
	}

	public LabResultsLoader(String directory) throws IOException, SQLException {
		bulkCopy(getBulkLoadFileNames(directory));
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

	private void bulkCopy(List<String> files) throws SQLException {
		String connectionUrl = "jdbc:sqlserver://localhost\\mssqlsites:1435;databaseName=wm;user=sa;password=firesocket2002";

		String destinationTable = "dbo.testresult";

		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			for (String filePath : files) {
				if (filePath.contains("errors")) {
					continue;
				}
				long start = System.currentTimeMillis();
				log.info(String.format("processing file %s", filePath));

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

					bulkCopy.setDestinationTableName(destinationTable);
					bulkCopy.writeToServer(fileRecord);

					log.info(String.format("processed file in [%d] ms", System.currentTimeMillis() - start));
				}
			}
		}
	}
}