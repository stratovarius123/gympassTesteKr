package br.com.kartracing.DTO;

import java.io.Serializable;
import java.time.LocalTime;

public class RaceResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer finalPosition;
    private Long pilotId;
    private String pilotName;
    private Integer lapsCompleted;
    private LocalTime totalRaceTime;

    public RaceResultDTO() {
    }

    public RaceResultDTO(Integer finalPosition, Long pilotId, String pilotName, Integer lapsCompleted,
                         LocalTime totalRaceTime) {
        super();
        this.finalPosition = finalPosition;
        this.pilotId = pilotId;
        this.pilotName = pilotName;
        this.lapsCompleted = lapsCompleted;
        this.totalRaceTime = totalRaceTime;
    }

    public Integer getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Integer finalPosition) {
        this.finalPosition = finalPosition;
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

    public Integer getLapsCompleted() {
        return lapsCompleted;
    }

    public void setLapsCompleted(Integer lapsCompleted) {
        this.lapsCompleted = lapsCompleted;
    }

    public LocalTime getTotalRaceTime() {
        return totalRaceTime;
    }

    public void setTotalRaceTime(LocalTime totalRaceTime) {
        this.totalRaceTime = totalRaceTime;
    }

    @Override
    public String toString() {
        return "RaceResultDTO [finalPosition=" + finalPosition + ", pilotId=" + pilotId + ", pilotName=" + pilotName
                + ", lapsCompleted=" + lapsCompleted + ", totalRaceTime=" + totalRaceTime + "]";
    }


}
