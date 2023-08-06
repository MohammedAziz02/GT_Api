package com.ensah.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.Instant;

public abstract class AbstractDto{
    @CreatedDate
    @JsonIgnore
    protected Instant createdDate=Instant.now();

    @LastModifiedDate
    @JsonIgnore
    protected Instant lastModifiedDate=Instant.now();

}
