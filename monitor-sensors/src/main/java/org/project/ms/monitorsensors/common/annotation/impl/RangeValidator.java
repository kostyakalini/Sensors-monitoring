package org.project.ms.monitorsensors.common.annotation.impl;

import org.project.ms.monitorsensors.common.annotation.ValidRange;
import org.project.ms.monitorsensors.dto.request.RangeDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<ValidRange, RangeDto> {

    @Override
    public boolean isValid(RangeDto range, ConstraintValidatorContext context) {
        if (range == null) {
            return false;
        }
        return range.getFrom() < range.getTo() && range.getFrom() > 0;
    }
}