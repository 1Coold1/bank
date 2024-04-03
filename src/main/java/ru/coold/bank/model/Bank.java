package ru.coold.bank.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private long bankId;

    private String name; //Наименование банка

    private String BIC; //Банковский идентификационный код
}
