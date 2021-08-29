package org.evaldas.file_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class StorageService {

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
}
