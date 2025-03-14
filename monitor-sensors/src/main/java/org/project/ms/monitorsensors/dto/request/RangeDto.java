package org.project.ms.monitorsensors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.ms.monitorsensors.common.annotation.ValidRange;

import javax.validation.constraints.Min;

@Data
@Builder
@ValidRange
@NoArgsConstructor
@AllArgsConstructor
public class RangeDto {
    @Min(value = 1, message = "Range 'from' must be greater than 0")
    private int from;

    @Min(value = 1, message = "Range 'to' must be greater than 0")
    private int to;
}