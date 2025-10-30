package com.example.vms.Model;

import com.example.vms.Enum.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private String name;
    private String modelno;
    @CreatedDate
    private LocalDateTime publishAt;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    // One Product has many CVEs
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CveModel> cveList = new ArrayList<>();
}
