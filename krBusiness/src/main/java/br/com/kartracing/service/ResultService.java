package br.com.kartracing.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.kartracing.service.interfaces.Result;
import br.com.kartracing.DTO.RaceRecordDTO;
import br.com.kartracing.DTO.RaceResultDTO;

@Service
public class ResultService implements Result {

	private static final Logger logger = Logger.getLogger(ResultService.class);
	private static final Integer FOURTH_TURN = Integer.valueOf(4);

	@Autowired
	private br.com.kartracing.service.interfaces.LogFile raceRecord;

	@Override
	public List<RaceResultDTO> getRaceResult(String path) {

		List<RaceResultDTO> raceList = new ArrayList<>();
		List<RaceRecordDTO> readLogFile = raceRecord.readLogFile(path);

		try {

			readLogFile
			.stream()
			.map(RaceRecordDTO::getPilotId)
			.distinct()
			.forEach(t -> {
				RaceResultDTO race = new RaceResultDTO();
				race.setPilotId(t);
				race.setTotalRaceTime(getTotalRaceTime(t, readLogFile));
				race.setPilotName(getPilotName(readLogFile, t));
				race.setLapsCompleted(getLapsCompleted(readLogFile, t));
				raceList.add(race);
			});

			configureFinalPosition(raceList);

		} catch(Exception e) {
			logger.error("An error occurred while reading the file. Message: " + e.getMessage());
		}

		return raceList;
	}

	private static LocalTime getTotalRaceTime(Long pilotId, List<RaceRecordDTO> raceRecord) {

		RaceResultDTO race = new RaceResultDTO();
		race.setTotalRaceTime(LocalTime.MIN);
		raceRecord
		.stream()
		.filter(pilot -> Objects.equals(pilot.getPilotId(), pilotId))
		.filter(hour -> {
			return Objects.equals(hour.getPilotId(), getChampionId(raceRecord)) ? Boolean.TRUE : getHourStartLastLap(hour).isBefore(getTimeChampionFinished(raceRecord));
		})
		.map(RaceRecordDTO::getLapTime)
		.forEach(tr -> {
			race.setTotalRaceTime(race.getTotalRaceTime().plusHours(tr.getHour()));
			race.setTotalRaceTime(race.getTotalRaceTime().plusMinutes(tr.getMinute()));
			race.setTotalRaceTime(race.getTotalRaceTime().plusSeconds(tr.getSecond()));
			race.setTotalRaceTime(race.getTotalRaceTime().plusNanos(tr.getNano()));
		});

		return race.getTotalRaceTime();
	}

	private static String getPilotName(List<RaceRecordDTO> readLogFile, Long pilotId) {
		return readLogFile
				.stream()
				.filter(pilot -> Objects.equals(pilot.getPilotId(), pilotId))
				.map(RaceRecordDTO::getPilotName)
				.distinct()
				.findFirst()
				.get();
	}

	private static Integer getLapsCompleted(List<RaceRecordDTO> readLogFile, Long pilotId) {
		return readLogFile
				.stream()
				.filter(pilot -> Objects.equals(pilot.getPilotId(), pilotId))
				.filter(hour -> {
					return Objects.equals(hour.getPilotId(), getChampionId(readLogFile)) ? Boolean.TRUE : getHourStartLastLap(hour).isBefore(getTimeChampionFinished(readLogFile));
				})
				.max(Comparator.comparing(RaceRecordDTO::getLaps))
				.get().getLaps();
	}

	private static LocalTime getHourStartLastLap(RaceRecordDTO hour) {
		LocalTime time;
		time = hour.getHour().minusNanos(hour.getLapTime().getNano());
		time = time.minusSeconds(hour.getLapTime().getSecond());
		time = time.minusMinutes(hour.getLapTime().getMinute());
		time = time.minusHours(hour.getLapTime().getHour());
		return time;
	}

	private static void configureFinalPosition(List<RaceResultDTO> raceList) {
		Integer position = NumberUtils.INTEGER_ONE;
		for(RaceResultDTO result : raceList) {
			result.setFinalPosition(position);
			position++;
		}
	}

	private static LocalTime getTimeChampionFinished(List<RaceRecordDTO> readLogFile) {
		return readLogFile.stream()
				.filter(lap -> Objects.equals(lap.getLaps(), FOURTH_TURN))
				.sorted((a,b) -> Integer.compare(a.getLaps(), b.getLaps()))
				.map(RaceRecordDTO::getHour)
				.findFirst()
				.get();
	}

	private static Long getChampionId(List<RaceRecordDTO> readLogFile) {
		return readLogFile.stream()
				.filter(lap -> Objects.equals(lap.getLaps(), FOURTH_TURN))
				.sorted((a,b) -> Integer.compare(a.getLaps(), b.getLaps()))
				.map(RaceRecordDTO::getPilotId)
				.findFirst()
				.get();
	}

}
