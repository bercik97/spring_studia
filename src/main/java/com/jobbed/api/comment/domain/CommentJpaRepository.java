package com.jobbed.api.comment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByRelationIdAndTypeAndDateAndCompanyName(long relationId, CommentType type, LocalDate date, String companyName, Pageable pageable);
}
