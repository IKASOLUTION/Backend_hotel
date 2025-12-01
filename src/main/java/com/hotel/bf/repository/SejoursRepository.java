package com.hotel.bf.repository;

import com.hotel.bf.domain.Sejours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SejoursRepository extends JpaRepository<Sejours, Long>, JpaSpecificationExecutor<Sejours> {

}
