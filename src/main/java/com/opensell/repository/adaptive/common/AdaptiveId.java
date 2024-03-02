package com.opensell.repository.adaptive.common;

public class AdaptiveId {
    private int idValue;
    private String idColumnName;

    public AdaptiveId() {


    }

    public AdaptiveId(int idValue, String idColumnName) {
        if(idValue >= 1) this.idValue = idValue;
        this.idColumnName = idColumnName;
    }
}