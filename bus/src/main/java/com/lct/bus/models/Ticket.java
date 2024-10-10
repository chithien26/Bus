//package com.lct.bus.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "ticket")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Ticket {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    int id;
//    LocalDate dateOfPurchase;
//    int numberOfMonth;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    User user;
//
//    @OneToOne
//    @JoinColumn(name = "bill_id")
//    Bill bill;
//
//    LocalDateTime createdDate;
//    Boolean active;
//
//    public Ticket() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public LocalDate getDateOfPurchase() {
//        return dateOfPurchase;
//    }
//
//    public void setDateOfPurchase(LocalDate dateOfPurchase) {
//        this.dateOfPurchase = dateOfPurchase;
//    }
//
//    public int getNumberOfMonth() {
//        return numberOfMonth;
//    }
//
//    public void setNumberOfMonth(int numberOfMonth) {
//        this.numberOfMonth = numberOfMonth;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Bill getBill() {
//        return bill;
//    }
//
//    public void setBill(Bill bill) {
//        this.bill = bill;
//    }
//
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
//}
