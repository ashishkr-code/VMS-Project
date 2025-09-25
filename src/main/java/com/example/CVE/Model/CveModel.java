package com.example.CVE.Model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Cve record")
public class CveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cveId;
    private String version;
    private String packageName;
    private String description;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;
    private LocalDate createdAt;
}