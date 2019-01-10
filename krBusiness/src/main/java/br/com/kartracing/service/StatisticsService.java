package br.com.kartracing.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.kartracing.service.interfaces.Statistics;
import br.com.kartracing.DTO.AverageSpeedPerPilotDTO;
import br.com.kartracing.DTO.BestLapDTO;
import br.com.kartracing.DTO.RaceRecordDTO;
import br.com.kartracing.DTO.RaceResultDTO;
import br.com.kartracing.DTO.TimeAfterWinnerDTO;

@Service
public class StatisticsService implements Statistics {

	private static final Logger logger = Logger.getLogger(StatisticsService.class);

	@Autowired
	private br.com.kartracing.service.interfaces.LogFile raceRecord;

	@Autowired
	private br.com.kartracing.service.interfaces.Result result;

	@Override
	public BestLapDTO getBestLapOfTheRace(String path) {
		List<RaceRecordDTO> readLogFile = new ArrayList<>();
		try {
			readLogFile = raceRecord.readLogFile(path);
		}catch(Exception e) {
			logger.error("An error occurred while reading the file. Message: " + e.getMessage());
		}
		return readLogFile
				.stream()
				.map(r -> new BestLapDTO(r.getPilotName(), r.getLapTime()))
				.findFirst().get();
	}

	@Override
	public List<BestLapDTO> getBestLapByRider(String path) {

		List<RaceResultDTO> raceResult = result.getRaceResult(path);
		List<RaceRecordDTO> readLogFile = raceRecord.readLogFile(path);
		List<BestLapDTO> listBestLap = new ArrayList<>();

		try {
			raceResult
			.stream()
			.forEach(r -> {
				RaceRecordDTO race = getBestLap(readLogFile, r.getPilotName());
				BestLapDTO bestLap = new BestLapDTO(race.getPilotName(), race.getLapTime());
				listBestLap.add(bestLap);
			} );

		} catch(Exception e) {
			logger.error("An error occurred while reading the file. Message: " + e.getMessage());
		}

		return listBestLap;

	}

	private RaceRecordDTO getBestLap(List<RaceRecordDTO> readLogFile, String pilotName) {
		return readLogFile.stream().filter(p -> Objects.equals(p.getPilotName(), pilotName)).findFirst().get();
	}

	@Override
	public List<TimeAfterWinnerDTO> getTimeAfterWinner(String path) {

		List<TimeAfterWinnerDTO> listTimeAfterWinner = new ArrayList<>();
		List<RaceResultDTO> raceResult = new ArrayList<>();

		try {
			raceResult = result.getRaceResult(path);
			LocalTime championTime = raceResult.stream().map(RaceResultDTO::getTotalRaceTime).findFirst().get();
			raceResult
			.stream()
			.skip(NumberUtils.INTEGER_ONE)
			.forEach(rr -> {
				TimeAfterWinnerDTO time = new TimeAfterWinnerDTO();
				time.setTimeAfterWiner(getDifference(rr.getTotalRaceTime(), championTime));
				time.setPilotName(rr.getPilotName());
				listTimeAfterWinner.add(time);
			});

		} catch(Exception e) {
			logger.error("An error occurred while reading the file. Message: " + e.getMessage());
		}
		return listTimeAfterWinner;
	}

	@Override
	public List<AverageSpeedPerPilotDTO> getAverageSpeedPerPilot(String path) {

		List<AverageSpeedPerPilotDTO> avgSpeedList = new ArrayList<>();
		List<RaceResultDTO> raceResult = new ArrayList<>();

		try {

			raceResult = result.getRaceResult(path);
			raceResult.stream().forEach(rr -> {
				AverageSpeedPerPilotDTO avg = new AverageSpeedPerPilotDTO();
				avg.setAverageSpeed(getTotalSpeed(rr.getPilotName(), path)/rr.getLapsCompleted());
				avg.setPilotName(rr.getPilotName());
				avgSpeedList.add(avg);
			});

		} catch(Exception e) {
			logger.error("An error occurred while reading the file. Message: " + e.getMessage());
		}

		return avgSpeedList;
	}

	private static LocalTime getDifference(LocalTime pilotTime, LocalTime championTime) {
		LocalTime time;
		time = pilotTime.minusNanos(championTime.getNano());
		time = time.minusSeconds(championTime.getSecond());
		time = time.minusMinutes(championTime.getMinute());
		return time;
	}

	private Double getTotalSpeed(String pilotName, String path) {
		List<RaceRecordDTO> readLogFile = raceRecord.readLogFile(path);
		return readLogFile
				.stream()
				.filter(p -> Objects.equals(p.getPilotName(), pilotName))
				.map(RaceRecordDTO::getAverageLapSpeed)
				.reduce(NumberUtils.DOUBLE_ZERO, Double::sum);

	}

}
