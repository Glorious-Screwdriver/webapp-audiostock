package com.audiostock.repos;

import com.audiostock.entities.PaymentInfo;
import org.springframework.data.repository.CrudRepository;

public interface PaymentInfoRepo extends CrudRepository<PaymentInfo, Long> {
}
