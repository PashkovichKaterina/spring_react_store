package com.epam.esm.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(name = "purchase_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "lnk_orders_certificates",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "certificate_id")}
    )
    private List<Certificate> certificates = Collections.emptyList();

    public Order() {
    }

    public Order(Long id, User user, BigDecimal cost, LocalDateTime purchaseDate, List<Certificate> certificates) {
        this.id = id;
        this.user = user;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.certificates = certificates == null ? Collections.emptyList() : new ArrayList<>(certificates);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public List<Certificate> getCertificates() {
        return certificates == null ? Collections.emptyList() : new ArrayList<>(certificates);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates == null ? Collections.emptyList() : new ArrayList<>(certificates);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Order order = (Order) obj;
        return (id == null ? id == order.id : id.equals(order.id))
                && (cost == null ? cost == order.cost : cost.equals(order.cost))
                && (purchaseDate == null ? purchaseDate == order.purchaseDate : purchaseDate.equals(order.purchaseDate))
                && (user == null ? user == order.user : user.equals(order.user));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (cost == null ? 0 : cost.hashCode())
                + (purchaseDate == null ? 0 : purchaseDate.hashCode()) + (user == null ? 0 : user.hashCode())
                + (certificates == null ? 0 : certificates.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; COST=" + cost + "; PURCHASE_DATE=" + purchaseDate
                + "; CERTIFICATES=" + certificates;
    }
}
