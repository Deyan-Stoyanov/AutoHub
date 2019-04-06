package com.autohub.domain.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = DateBeforeTodayValidator.class)
public @interface BeforeToday {

    String message() default "Invalid Date. Date should be before today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
