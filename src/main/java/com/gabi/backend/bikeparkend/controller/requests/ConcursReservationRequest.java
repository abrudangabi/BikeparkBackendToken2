package com.gabi.backend.bikeparkend.controller.requests;

import com.gabi.backend.bikeparkend.model.Biker;
import com.gabi.backend.bikeparkend.model.Concurs;
import com.gabi.backend.bikeparkend.model.RezervareBikePark;
import com.gabi.backend.bikeparkend.model.RezervareConcurs;

public class ConcursReservationRequest {
    private Long id;

    private Concurs concurs;

    private RezervareConcurs rezervareConcurs;

    public ConcursReservationRequest(Long id, Concurs concurs, RezervareConcurs rezervareConcurs) {
        this.id = id;
        this.concurs = concurs;
        this.rezervareConcurs = rezervareConcurs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RezervareConcurs getRezervareConcurs() {
        return rezervareConcurs;
    }

    public void setRezervareConcurs(RezervareConcurs rezervareConcurs) {
        this.rezervareConcurs = rezervareConcurs;
    }

    public Concurs getConcurs() {
        return concurs;
    }

    public void setConcurs(Concurs concurs) {
        this.concurs = concurs;
    }
}
