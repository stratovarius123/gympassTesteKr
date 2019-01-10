package br.com.kartracing.DTO;

import java.io.Serializable;

public class AverageSpeedPerPilotDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pilotName;
    private Double averageSpeed;

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }


}
