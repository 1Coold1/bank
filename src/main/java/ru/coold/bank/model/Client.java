package ru.coold.bank.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private long clientId;

    private String name; //Наименование клиента

    @Column(name = "short_name")
    private  String shortName; //Краткое наименование

    private  String address; // Адрес

    @Enumerated(EnumType.STRING)
    @Column(name = "legal_form")
    private LegalForm legalForm; //Организационно-правовая форма
}
