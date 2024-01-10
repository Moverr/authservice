package com.kodeinc.authservice.models.entities.entityenums;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-09
 * @Email moverr@gmail.com
 */
public enum QueryLevelEnum {
    ALL,ROLE,PROJECT,RESOURCE;

    public static QueryLevelEnum findValue(String value){

        for (QueryLevelEnum enumValue : QueryLevelEnum.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }

        return QueryLevelEnum.ALL;
    }


}
