package com.kodeinc.authservice.models.dtos.requests;

import com.kodeinc.authservice.models.entities.entityenums.QueryLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-09
 * @Email moverr@gmail.com
 */
@Getter
@Setter
public class UsersSearchQuery extends SearchRequest{

    private QueryLevelEnum level;
    private long levelId;
    public UsersSearchQuery(String query, int offset, int limit, String sortBy, String sortType, QueryLevelEnum level,long levelId) {
        super(query, offset, limit, sortBy, sortType);
        this.level = level;
        this.levelId = levelId;
    }


}
