package com.kodeinc.authservice.models.entities.entityenums;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-17
 * @Email moverr@gmail.com
 */
public enum GeneralStatusEnum {
    PENDING,
    ACTIVE,
    DEACTIVE,
    ARCHIVED;

    public static GeneralStatusEnum findValue(String value){

        for (GeneralStatusEnum enumValue : GeneralStatusEnum.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }

        return GeneralStatusEnum.ACTIVE;
    }
}
