package edu.zut.awir.awir1.messaging;

public record UserEvent(
        Long id,
        String name,
        String email,
        String type // USER_CREATED / USER_UPDATED
) {}
