package br.com.kartracing.service.interfaces;

import java.util.List;

import br.com.kartracing.DTO.AverageSpeedPerPilotDTO;
import br.com.kartracing.DTO.BestLapDTO;
import br.com.kartracing.DTO.TimeAfterWinnerDTO;

public interface Statistics {
	
	BestLapDTO getBestLapOfTheRace(String path);
	List<BestLapDTO> getBestLapByRider(String path);
	List<TimeAfterWinnerDTO> getTimeAfterWinner(String path);
	List<AverageSpeedPerPilotDTO> getAverageSpeedPerPilot(String path);

}
