package com.devtomato.loan.repository;

import com.devtomato.loan.domain.AcceptTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptTermsRepository extends JpaRepository<AcceptTerms, Long> {
}
