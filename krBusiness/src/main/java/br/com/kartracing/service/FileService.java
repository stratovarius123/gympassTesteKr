package br.com.kartracing.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import br.com.kartracing.service.interfaces.LogFile;
import br.com.kartracing.DTO.RaceRecordDTO;

@Service
public class FileService implements LogFile {

	private static final Integer FIRST = NumberUtils.INTEGER_ONE;
	private static final Logger logger = Logger.getLogger(FileService.class);

	public List<RaceRecordDTO> readLogFile(String path) {

		List<RaceRecordDTO> raceRecord = new ArrayList<>();

		try {

			raceRecord = Files.lines(Paths.get(path))
					.skip(FIRST)
					.map(line -> line.replaceAll("–", ""))
					.map(line -> line.split("\\s+"))
					.map(value -> new RaceRecordDTO(LocalTime.parse(value[0]), Long.parseLong(value[1]), 
							value[2], Integer.parseInt(value[3]),
							getLocalTime(value[4]),
							Double.parseDouble(value[5].replaceAll(",", "."))))
					.sorted((a,b) -> Integer.compare(a.getLapTime().getNano(), b.getLapTime().getNano()))
					.sorted((a,b) -> Integer.compare(a.getLapTime().getSecond(), b.getLapTime().getSecond()))
					.sorted((a,b) -> Integer.compare(a.getLapTime().getMinute(), b.getLapTime().getMinute()))
					.collect(Collectors.toList());

		} catch (IOException e) {
			logger.error("An error occurred while reading the file. Message: " + e.getMessage());
		}

		return raceRecord;
	}

	private static LocalTime getLocalTime(String string) {
		LocalTime time = LocalTime.MIN;

		String[] split = string.split("[:.]");

		if(split.length < 4) {
			time = LocalTime.of(NumberUtils.INTEGER_ZERO, Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
		} else {
			time = LocalTime.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3]));
		}

		return time;
	}

}
