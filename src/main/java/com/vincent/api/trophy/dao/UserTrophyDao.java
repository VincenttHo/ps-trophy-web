package com.vincent.api.trophy.dao;

import com.vincent.api.trophy.model.TrophyTitleDTO;
import com.vincent.api.trophy.model.UserTrophyDetailDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTrophyDao extends MongoRepository<UserTrophyDetailDTO, String> {

    UserTrophyDetailDTO findByPsnIdAndGameId(String psnId, String gameId);

    void deleteByPsnId(String psnId);

}
