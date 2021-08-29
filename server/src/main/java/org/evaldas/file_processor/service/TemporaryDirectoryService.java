package org.evaldas.file_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

@Service
public class TemporaryDirectoryService {

	@Value("${tempDirName}")
	private String tempDirName;

	public String saveListIntoTempFile(List<String> content, String filename) {
		String path = System.getProperty("java.io.tmpdir") + "/" + tempDirName + "/" + filename;
		File file = new File(path);
		if (file.getParentFile().mkdirs()) { // create temporary directory with tempDirName if it doesn't exist
			System.out.println("Created " + tempDirName + " directorys");
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			content.forEach(string -> {
				try {
					writer.write(string + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			System.out.printf("Unable to save contents to a file %s: %s%n",
					path, e.getMessage());
			return ""; // empty string will indicate that there's no file to download
		}

		return filename; // Returning filename, because I don't want to expose server location where it's stored
	}

	public List<File> loadAllFromTempDirectory() {
		String path = System.getProperty("java.io.tmpdir") + "/" + tempDirName;
		File tempDirectory = new File(path);

		if (tempDirectory.isDirectory()) {
			File[] files = tempDirectory.listFiles();
			return nonNull(files) ? Arrays.asList(files) : emptyList();
		} else {
			return emptyList();
		}
	}

	public void deleteTempDirectory() {
		String path = System.getProperty("java.io.tmpdir") + "/" + tempDirName;
		File dirToDelete = new File(path);

		if (dirToDelete.isDirectory() && deleteDirectory(dirToDelete)) {
			System.out.println("Directory " + tempDirName + " was deleted");
		} else {
			System.out.println("Directory " + tempDirName + " was NOT deleted");
		}
	}

	boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (nonNull(allContents)) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}
}
