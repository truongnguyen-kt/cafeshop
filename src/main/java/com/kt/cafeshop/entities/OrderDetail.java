package com.kt.cafeshop.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "`option`", columnDefinition = "TEXT")
    private String optionNote;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
