package org.evaldas.file_processor.service;

import lombok.AllArgsConstructor;
import org.evaldas.file_processor.runnable.FileProcessingRunnable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class FileProcessingService {

	public Map<String, Integer> process(List<MultipartFile> files) {
		Map<String, Integer> wordsFrequency = new ConcurrentHashMap<>();
		List<Thread> threads = new ArrayList<>();

		long start = System.currentTimeMillis();

		files.forEach(file -> {
			Thread t = new Thread(new FileProcessingRunnable(file, wordsFrequency));
			t.start();
			threads.add(t);
		});

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long finish = System.currentTimeMillis();
		System.out.println("File processing took " + (finish - start) + " ms");

		return wordsFrequency;
	}
}
