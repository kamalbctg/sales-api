package com.sales.ws.util;

import com.sales.ws.exception.ResourceNotFoundException;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public class RestPreconditions {
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        return resource;
    }
}
