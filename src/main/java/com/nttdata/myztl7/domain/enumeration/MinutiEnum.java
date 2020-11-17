package com.nttdata.myztl7.domain.enumeration;

/**
 * The MinutiEnum enumeration.
 */
public enum MinutiEnum {
    QUINDICI("15"),
    TRENTA("30"),
    QUARANTACINQUE("45"),
    SESSANTA("60");

    private final String value;

    MinutiEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
