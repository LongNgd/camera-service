package vn.atdigital.cameraservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    private Long status;
}
