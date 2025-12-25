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
@Table(name = "action_audit")
public class ActionAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_code")
    private String actionCode;

    private String description;

    @Column(name = "issue_datetime")
    private LocalDateTime issueDateTime;

    @Column(name = "pk_id")
    private Long pkId;

    @Column(name = "pk_type")
    private String pkType;

    private String step;

    private String username;
}
