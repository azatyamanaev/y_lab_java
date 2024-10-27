package ru.ylab.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class containing error data.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    /**
     * Error message.
     */
    private String message;

    /**
     * Error details.
     */
    @Builder.Default
    private List<ErrorDetail> details = new ArrayList<>();
}
