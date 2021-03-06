package com.ssafy.helpus.donation.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "donation_product")
public class DonationProduct {
    @Id @Column(name = "donation_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationProductId;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false, updatable = false)
    private Donation donation;

    @Column(name = "product_name", nullable = false, updatable = false)
    private String productName;

    @Column(name = "product_info", nullable = false, updatable = false)
    private String productInfo;

    @Column(name = "total_count", updatable = false)
    private int totalCount;

    @Column(name = "finish_count", insertable = false)
    private int finishCount;

    @Column(name = "delivery_count", insertable = false)
    private int deliveryCount;

    @Column(name = "waiting_count", insertable = false)
    private int waitingCount;

    @Column(nullable = false, insertable = false)
    private double percent;

    @Builder
    public DonationProduct(Donation donation, String productName, String productInfo, int totalCount) {
        this.donation = donation;
        this.productName = productName;
        this.productInfo = productInfo;
        this.totalCount = totalCount;
    }
}
