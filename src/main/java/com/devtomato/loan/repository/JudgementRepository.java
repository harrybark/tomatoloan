package com.devtomato.loan.repository;

import com.devtomato.loan.domain.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
}
