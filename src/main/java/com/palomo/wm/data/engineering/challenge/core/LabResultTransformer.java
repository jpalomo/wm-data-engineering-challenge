package com.palomo.wm.data.engineering.challenge.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palomo.wm.data.engineering.challenge.EtlServiceConfiguration;
import com.palomo.wm.data.engineering.challenge.api.LabResult;

/**
 * This class is responsible for extracting the data from the input file,
 * transforming and normalizing data, and creating the file to be loaded to MS
 * SQL.
 * 
 */
public class LabResultTransformer {

	private static final Logger log = LoggerFactory.getLogger(LabResultTransformer.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final EtlServiceConfiguration configuration;

	public LabResultTransformer(EtlServiceConfiguration configuration) throws IOException {
		this.configuration = configuration;
	}

	public void transform() throws IOException {
		long start = System.currentTimeMillis();
		log.info(String.format("started at [%d]", start));

		// Create a print writer object to write to if any errors are encountered when
		// processing a line of the input
		try (PrintWriter errorWriter = new PrintWriter(new File(
				String.format("%s\\%s.txt", configuration.getOutputDir(), configuration.getErrorFilePrefix())))) {
			transformToCsv(errorWriter);
		}

		long stop = System.currentTimeMillis();
		log.info(String.format("ended at [%d] with a total elapsed time of [%d] ms", stop, start - stop));
	}

	private int transformToCsv(PrintWriter errorWriter) throws IOException {
		int batch = 1;
		int linesProcessed = 0;
		int processingErrors = 0;

		try (Scanner scanner = new Scanner(new FileReader(new File(configuration.getInputFile())))) {
			StringBuilder builder = new StringBuilder();
			while (scanner.hasNextLine()) {
				String line = null;
				int noOfBatchRecords = 0;
				while (scanner.hasNextLine() && (line = scanner.nextLine()) != null
						&& noOfBatchRecords < configuration.getBatchSize()) {
					linesProcessed++;
					noOfBatchRecords++;
					try {
						LabResult source = MAPPER.readValue(line, LabResult.class);
						builder.append(source.toCsvString());
					} catch (Exception e) {
						processingErrors++;
						if (processingErrors % 50 == 0) {
							log.error(String.format("encountered [%d] processing errors", processingErrors));
						}
						errorWriter.print(String.format("%s\n", line));
					}
				}

				try (PrintWriter printWriter = new PrintWriter(new File(String.format("%s\\%s_%d.txt",
						configuration.getOutputDir(), configuration.getOutputFilePrefix(), batch)))) {
					printWriter.write(builder.toString().trim());
					builder.setLength(0);
					if (batch % 25 == 0) {
						log.info(String.format("finished processing batch: [%d]", batch));
						log.info(String.format("lines processed thus far: [%d]", linesProcessed));
					}
					batch++;
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		if (processingErrors > 0) {
			log.info(String.format("encountered [%d] errors while processing input file.", processingErrors));
		}

		log.info(String.format("processed a total of [%d] lines", linesProcessed));

		return linesProcessed;
	}
}