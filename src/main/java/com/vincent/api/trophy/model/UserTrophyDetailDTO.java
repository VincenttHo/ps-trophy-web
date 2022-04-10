package com.vincent.api.trophy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("userTrophy")
public class UserTrophyDetailDTO {

    @Id
    private String id;

    private String psnId;

    private String accountId;

    private String gameId;

    private Date lastUpdatedDateTime;

    private List<Trophy> trophies;

}
