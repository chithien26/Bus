//package com.lct.bus.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "bill")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Bill {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    int id;
//    Boolean paid;
//    Double totalAmount;
//    LocalDateTime createdDate;
//    Boolean active;
//
//    @OneToOne(mappedBy = "bill")
//    Ticket ticket;
//
//    public Bill() {
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
//    public Boolean getPaid() {
//        return paid;
//    }
//
//    public void setPaid(Boolean paid) {
//        this.paid = paid;
//    }
//
//    public Double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(Double totalAmount) {
//        this.totalAmount = totalAmount;
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
//
//    public Ticket getTicket() {
//        return ticket;
//    }
//
//    public void setTicket(Ticket ticket) {
//        this.ticket = ticket;
//    }
//}
