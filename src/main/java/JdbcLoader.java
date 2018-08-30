import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

//Line Count = 233,015,428

public class JdbcLoader {

	public static final int RECORDS_PER_BATCH = 250000;
	public static final int BATCHES_TO_PROCESS = Integer.MAX_VALUE;
	public static final String INPUT_FILE = "C:\\Users\\jpalomo\\Desktop\\2018-08-29T01_36_17-file.txt\\2018-08-29T01_36_17-file.txt";
	public static final String OUTPUT_DIR = "C:\\Users\\jpalomo\\Desktop\\wm_data";

	private static final Logger log = LoggerFactory.getLogger(JdbcLoader.class);

	public static void main(String args[]) throws IOException, InterruptedException {
		BasicConfigurator.configure();


		long start = System.currentTimeMillis();
		log.info("started at: " + start);

		try (PrintWriter errorWriter = new PrintWriter(new File(String.format("%s\\errors_output.txt", OUTPUT_DIR)))) {
			log.info(String.format("processed [%d] lines", writeVendorsToCsv(errorWriter)));
		}

		long stop = System.currentTimeMillis();
		log.info("ended at: " + stop);

		log.info("elapsed at: " + (stop - start));
	}

	public static int writeVendorsToCsv(PrintWriter errorWriter) throws IOException {
		int linesProcessed = 0;
		int batch = 1;
		int processingErrors = 0;
		try (Scanner scanner = new Scanner(new FileReader(new File(INPUT_FILE)))) {
			StringBuilder builder = new StringBuilder();
			while (scanner.hasNextLine() && batch < BATCHES_TO_PROCESS) {
				String line = null;
				int noOfBatchRecords = 0;
				while (scanner.hasNextLine() && (line = scanner.nextLine()) != null && noOfBatchRecords < RECORDS_PER_BATCH) {
					linesProcessed++;
					noOfBatchRecords++;
					try {
						Data source = new ObjectMapper().readValue(line, Data.class);
						builder.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", source.getBatch_id().trim(),
								source.getVendor_id().trim(), source.getProduct_id().trim(), source.getLab_id().trim(),
								source.getState().trim(), source.getTested_at().trim(), source.getExpires_at().trim(),
								source.getThc().trim(), source.getThca().trim(), source.getCbd().trim(),
								source.getCbda().trim()));
					} catch (Exception e) {
						processingErrors++;
						log.error(String.format("error encountered processing: %s", line));
						errorWriter.print(String.format("%s\n", line));
					}
				}

				try (PrintWriter printWriter = new PrintWriter(
						new File(String.format("%s\\output_%d.txt", OUTPUT_DIR, batch)))) {
					printWriter.write(builder.toString().trim());
					builder.setLength(0);
					log.info(String.format("finished processing batch [%d]", batch++));
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}

		}
		log.info(String.format("encountered [%d] errors while processing input file.", processingErrors));

		return linesProcessed;
	}
}