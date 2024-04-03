package ru.coold.bank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private long depositId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; //Ссылка на клиента

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank; //Ссылка на банк

    @Column(name = "opening_date")
    private Date openingDate; // Дата открытия

    private double percent; // Процент

    @Column(name = "term_months")
    private int termMonths; //Срок в месяцах
}
