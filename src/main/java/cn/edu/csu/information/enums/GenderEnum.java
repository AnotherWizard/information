package cn.edu.csu.information.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MAN("MAN"),
    WOMAN("WOMAN")
    ;

    private String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }



}
