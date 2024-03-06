package com.devtomato.loan.repository;

import com.devtomato.loan.domain.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {
}
