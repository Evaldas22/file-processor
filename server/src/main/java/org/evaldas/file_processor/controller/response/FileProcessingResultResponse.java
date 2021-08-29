package org.evaldas.file_processor.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class FileProcessingResultResponse {
	private final FileResponse wordFrequenciesFromAtoG;
	private final FileResponse wordFrequenciesFromHtoN;
	private final FileResponse wordFrequenciesFromOtoU;
	private final FileResponse wordFrequenciesFromVtoZ;

	@Getter
	@AllArgsConstructor
	public static class FileResponse {
		private final String filename;
		private final Map<String, Integer> words;
	}
}
