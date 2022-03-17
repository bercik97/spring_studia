package com.jobbed.api.role;

import com.jobbed.api.shared.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "role", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {

    private String role;

    public RoleEntity(long id, String role) {
        super(id, ZonedDateTime.now());
        this.role = role;
    }

    public RoleAggregate toAggregate() {
        return new RoleAggregate(getId(), role);
    }
}
