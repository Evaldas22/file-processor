package org.evaldas.file_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class StorageService {

	@Value("${tempDirName}")
	private String tempDirName;

	public String saveListIntoFile(List<String> content, String filename) {
		String path = System.getProperty("java.io.tmpdir") + "/" + tempDirName + "/" + filename;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
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
		}

		return filename; // Returning filename, because I don't want to expose server location where it's stored
	}
}
