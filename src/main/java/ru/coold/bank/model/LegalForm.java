package ru.coold.bank.model;

public enum LegalForm {
    INDIVIDUAL_ENTREPRENEUR ("Individual entrepreneur"),
    LLC ("Limited Liability Company"),
    PJSC ("Public Joint Stock Company"),
    CJSC ("Closed Joint Stock Company");

    private String title;

    LegalForm(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
