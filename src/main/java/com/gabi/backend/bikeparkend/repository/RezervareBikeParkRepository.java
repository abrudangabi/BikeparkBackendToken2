package com.gabi.backend.bikeparkend.repository;


import com.gabi.backend.bikeparkend.model.BikePark;
import com.gabi.backend.bikeparkend.model.Biker;
import com.gabi.backend.bikeparkend.model.RezervareBikePark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezervareBikeParkRepository extends JpaRepository<RezervareBikePark,Long> {
    List<RezervareBikePark> findAllByBikeParkAndAndBiker(BikePark bikePark, Biker biker);
}
