package com.gabi.backend.bikeparkend.repository;


import com.gabi.backend.bikeparkend.model.Biker;
import com.gabi.backend.bikeparkend.model.Concurs;
import com.gabi.backend.bikeparkend.model.RezervareConcurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RezervareConcursRepository extends JpaRepository<RezervareConcurs,Long> {
    //findInternshipRequestByApplicantAndInternship
    Optional<RezervareConcurs> findRezervareConcursByBikerAndConcurs(Biker biker, Concurs concurs);
}
