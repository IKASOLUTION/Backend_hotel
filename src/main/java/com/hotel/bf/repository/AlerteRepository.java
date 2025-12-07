package com.hotel.bf.repository;

import com.hotel.bf.domain.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long>, JpaSpecificationExecutor<Alerte> {

}
