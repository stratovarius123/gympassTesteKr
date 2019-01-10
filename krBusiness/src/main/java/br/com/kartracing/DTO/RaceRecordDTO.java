package br.com.kartracing.DTO;

import java.io.Serializable;
import java.time.LocalTime;

public class RaceRecordDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private LocalTime hour;
	private Long pilotId;
	private String pilotName;
	private Integer laps;
	private LocalTime lapTime;
	private Double averageLapSpeed;

	public RaceRecordDTO(LocalTime hour, Long pilotId, String pilotName, Integer laps, LocalTime lapTime,
			Double averageLapSpeed) {
		super();
		this.hour = hour;
		this.pilotId = pilotId;
		this.pilotName = pilotName;
		this.laps = laps;
		this.lapTime = lapTime;
		this.averageLapSpeed = averageLapSpeed;
	}
	
	public RaceRecordDTO() {}
	public LocalTime getHour() {
		return hour;
	}
	public void setHour(LocalTime hour) {
		this.hour = hour;
	}
	public Long getPilotId() {
		return pilotId;
	}
	public void setPilotId(Long pilotId) {
		this.pilotId = pilotId;
	}
	public String getPilotName() {
		return pilotName;
	}
	public void setPilotName(String pilotName) {
		this.pilotName = pilotName;
	}
	public Integer getLaps() {
		return laps;
	}
	public void setLaps(Integer laps) {
		this.laps = laps;
	}
	public LocalTime getLapTime() {
		return lapTime;
	}
	public void setLapTime(LocalTime lapTime) {
		this.lapTime = lapTime;
	}
	public Double getAverageLapSpeed() {
		return averageLapSpeed;
	}
	public void setAverageLapSpeed(Double averageLapSpeed) {
		this.averageLapSpeed = averageLapSpeed;
	}
	@Override
	public String toString() {
		return "RaceRecord [hour=" + hour + ", pilotId=" + pilotId + ", pilotName=" + pilotName + ", laps=" + laps
				+ ", lapTime=" + lapTime + ", averageLapSpeed=" + averageLapSpeed + "]";
	}
	
	

}
