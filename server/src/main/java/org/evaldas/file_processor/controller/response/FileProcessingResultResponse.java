package org.evaldas.file_processor.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class FileProcessingResultResponse {
	private final Map<String, Integer> wordFrequenciesFromAtoG;
	private final Map<String, Integer> wordFrequenciesFromHtoN;
	private final Map<String, Integer> wordFrequenciesFromOtoU;
	private final Map<String, Integer> wordFrequenciesFromVtoZ;
}
