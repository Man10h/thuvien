package com.web.thuvien.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {
    Trinh_tham("Trinh thám"),
    Kinh_di("Kinh dị"),
    Hai_huoc("Hài hước"),
    Doi_thuong("Đời thường"),
    Drama("Drama");

    private String typeName;
    private TypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public static Map<String, String> getTypeEnumMap(){
        Map<String, String> map = new HashMap<String, String>();
        for(TypeEnum typeEnum : TypeEnum.values()){
            map.put(typeEnum.toString(), typeEnum.name());
        }
        return map;
    }
}
