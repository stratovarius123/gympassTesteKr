package br.com.kartracing.DTO;

import java.io.Serializable;
import java.time.LocalTime;

public class BestLapDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pilotName;
    private LocalTime bestLap;

    public BestLapDTO() {
    }

    public BestLapDTO(String pilotName, LocalTime bestLap) {
        super();
        this.pilotName = pilotName;
        this.bestLap = bestLap;
    }

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public LocalTime getBestLap() {
        return bestLap;
    }

    public void setBestLap(LocalTime bestLap) {
        this.bestLap = bestLap;
    }

}
