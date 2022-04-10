package com.vincent.api.trophy.dao;

import com.vincent.api.trophy.model.TrophySummaryDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrophySummaryDao extends MongoRepository<TrophySummaryDTO, String> {
}
