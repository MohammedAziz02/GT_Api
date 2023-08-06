package com.ensah.commons;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author med_Aziz
 * @version 1.0
 */

@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "createddate", updatable = false)
    @JsonIgnore
    protected Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "lastmodifieddate")
    @JsonIgnore
    protected Instant lastModifiedDate = Instant.now();


}
