package br.com.kartracing.DTO;

import java.io.Serializable;
import java.time.LocalTime;

public class TimeAfterWinnerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pilotName;
    private LocalTime timeAfterWiner;

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public LocalTime getTimeAfterWiner() {
        return timeAfterWiner;
    }

    public void setTimeAfterWiner(LocalTime timeAfterWiner) {
        this.timeAfterWiner = timeAfterWiner;
    }

}
