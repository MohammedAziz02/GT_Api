package com.ensah.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author med
 * @utility ;j'ai un problème lorsque je passe des date depuis angular ils arrivent au backend -1 hour
 */
@Component
public class DateConverter {
    public LocalDateTime convertToGmtPlusOne(LocalDateTime time){
         return time.plusHours(1);
     }
}
