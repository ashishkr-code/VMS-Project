package com.example.vms.Model;

import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Version.Version;
import com.example.vms.Version.VersionJsonConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "cve_record")
@EntityListeners(AuditingEntityListener.class)
public class CveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cveId;

    private String packageName;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Convert(converter = VersionJsonConverter.class)
    private Version version;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Product product;
    @Enumerated(EnumType.STRING)
    private CveStatus status;
    @Enumerated(EnumType.STRING)
    private Severity severity;





    @PrePersist
    public void generateCveId() {
        if (this.cveId == null || this.cveId.isEmpty()) {
            this.cveId = "cve-" + UUID.randomUUID().toString().substring(20);
        }
    }
}
