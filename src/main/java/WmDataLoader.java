import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

//Line Count = 233,015,428

public class WmDataLoader {

	public static void main(String args[]) throws IOException, InterruptedException {
		String inputFile = "C:\\Users\\jpalomo\\Desktop\\2018-08-29T01_36_17-file.txt\\2018-08-29T01_36_17-file.txt";
		String outputFile = "C:\\Users\\jpalomo\\Desktop\\vendors.txt";

		long start = System.currentTimeMillis();
		System.out.println("started at: " + start);

		System.out.println(String.format("processed [%d] lines", writeVendorsToCsv(inputFile, outputFile)));

		long stop = System.currentTimeMillis();
		System.out.println("ended at: " + stop);

		System.err.println("elapsed at: " + (start - stop));
	}

	public static int writeVendorsToCsv(String fileInput, String fileOutput) throws IOException {
		int linesProcessed = 0;
		int processingErrors = 0;
		try (BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(new File(fileOutput)))) {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileInput)))) {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					linesProcessed++;
					try {
						Data source = new ObjectMapper().readValue(line, Data.class);
						bufferedWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
								source.getBatch_id().trim(), source.getVendor_id().trim(),
								source.getProduct_id().trim(), source.getLab_id().trim(), source.getState().trim(),
								source.getTested_at().trim(), source.getExpires_at().trim(), source.getThc().trim(),
								source.getThca().trim(), source.getCbd().trim(), source.getCbda().trim()));
					} catch (Exception e) {
						processingErrors++;
						System.err.println(String.format("error encountered processing: %s", line));
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println(String.format("encountered [%d] errors while processing input file.", processingErrors));

		return linesProcessed;
	}
}