package org.project.ms.monitorsensors.common.annotation;

import org.project.ms.monitorsensors.common.annotation.impl.RangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRange {
    String message() default "Invalid range: 'from' must be less than 'to', and both must be positive integers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}