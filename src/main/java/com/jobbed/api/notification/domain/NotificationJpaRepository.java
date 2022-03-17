package com.jobbed.api.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
}
