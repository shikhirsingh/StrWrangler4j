package com.shikhir.StrWrangler4j.fileops;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class FileOpsUtil {
	private static final Logger log = Logger.getLogger(FileOpsUtil.class.getName());

	/**
	 * Parses a file into a string array where each item in array is a new line
	 * 
	 * @param fileName A string containing the name of the file to be loaded
	 * @param encoding The type of encoding of the file (eg. UTF-8). null means
	 *                 platform defaults. UTF-8 is recommended.
	 * @return An array containing all the lines of the file
	 * @throws java.io.IOException
	 */
	public static String[] loadFile(String fileName, String encoding) throws IOException {
		File file = FileUtils.getFile(fileName);
		return loadFile(file, encoding);
	}

	/**
	 * Loads file as a string array where each item is a new line
	 * 
	 * @param file     A File which needs to be parsed
	 * @param encoding The type of encoding of the file (eg. UTF-8). null means
	 *                 platform defaults. UTF-8 is recommended.
	 * @return An array containing all the lines of the file
	 * @throws java.io.IOException
	 */

	public static String[] loadFile(File file, String encoding) throws IOException {
		ArrayList<String> record = new ArrayList<String>();
		String[] list = null;

		try {
			LineIterator it = FileUtils.lineIterator(file, encoding);

			while (it.hasNext()) {
				record.add(it.nextLine());
			}
			it.close();
		} finally {
			list = new String[record.size()];
			list = record.toArray(list);
		}
		return list;
	}

	public static void shuffleFile(File fileInput, File fileOutput, String encoding) throws FileNotFoundException {
		ArrayList<String> records = new ArrayList<String>();
		try {
			LineIterator it = FileUtils.lineIterator(fileInput, encoding);

			while (it.hasNext()) {
				records.add(it.nextLine());
			}
			it.close();
			Collections.shuffle(records);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(fileOutput));
		for (String arecord : records) pw.println(arecord);
		pw.close();

	}

}
