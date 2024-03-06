package com.devtomato.loan.repository;

import com.devtomato.loan.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRespository extends JpaRepository<Application, Long> {
}
