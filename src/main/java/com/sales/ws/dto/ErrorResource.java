package com.sales.ws.dto;

import lombok.*;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"status", "message"})
@Builder(builderClassName = "Builder", toBuilder = true)
public class ErrorResource {
    private int status;
    private String message;
}
