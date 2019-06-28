package com.gabi.backend.bikeparkend.controller.requests;

import com.gabi.backend.bikeparkend.model.BikePark;
import com.gabi.backend.bikeparkend.model.Biker;
import com.gabi.backend.bikeparkend.model.RezervareBikePark;

public class BikeparkReservationRequest {
    private Long id;

    private BikePark bikePark;

    private String numeBikepark;

    private RezervareBikePark rezervareBikePark;

    public BikeparkReservationRequest(Long id, BikePark bikePark, RezervareBikePark rezervareBikePark) {
        this.id = id;
        this.bikePark = bikePark;
        this.rezervareBikePark = rezervareBikePark;
    }

    public BikeparkReservationRequest(Long id, BikePark bikePark, String numeBikepark, RezervareBikePark rezervareBikePark) {
        this.id = id;
        this.bikePark = bikePark;
        this.numeBikepark = numeBikepark;
        this.rezervareBikePark = rezervareBikePark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RezervareBikePark getRezervareBikePark() {
        return rezervareBikePark;
    }

    public void setRezervareBikePark(RezervareBikePark rezervareBikePark) {
        this.rezervareBikePark = rezervareBikePark;
    }

    public BikePark getBikePark() {
        return bikePark;
    }

    public void setBikePark(BikePark bikePark) {
        this.bikePark = bikePark;
    }

    public String getNumeBikepark() {
        return numeBikepark;
    }

    public void setNumeBikepark(String numeBikepark) {
        this.numeBikepark = numeBikepark;
    }
}
