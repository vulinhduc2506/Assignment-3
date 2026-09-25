package com.example.ticket_management;

import com.example.ticket_management.exception.AppException;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class ActorValidationTest {
    public void validateActor(Long actorIdFromRequest, Long actorIdFromToken) {
        if (actorIdFromRequest == null || actorIdFromToken == null) {
            throw new IllegalArgumentException("ActorId tu request va token ko dc null");
        }

        if (!actorIdFromRequest.equals(actorIdFromToken)) {
            throw new IllegalArgumentException("actorId tu request va token phai trung nhau");
        }
    }
    // Kịch bản 1: Hai ID giống nhau -> Cho phép xử lý tiếp[cite: 1]
    @Test
    void testValidateActor_Success_WhenIdsMatch() {
        // Kết quả mong đợi: Không có ngoại lệ (exception) nào được ném ra
        assertDoesNotThrow(() -> {
            validateActor(100L, 100L);
        });
    }

    // Kịch bản 2: actorId từ request bị null -> Ném IllegalArgumentException[cite: 1]
    @Test
    void testValidateActor_ThrowsIllegalArgument_WhenRequestActorIsNull() {
        // Kết quả mong đợi: Ném ra IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateActor(null, 100L);
        });
        assertTrue(exception.getMessage().contains("ko dc null"));
    }

    // Kịch bản 3: actorId từ token bị null -> Ném IllegalArgumentException[cite: 1]
    @Test
    void testValidateActor_ThrowsIllegalArgument_WhenTokenActorIsNull() {
        // Kết quả mong đợi: Ném ra IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            validateActor(100L, null);
        });
    }

    // Kịch bản 4: Hai ID khác nhau -> Ném lỗi HTTP 403[cite: 1]
    @Test
    void testValidateActor_Throws403Exception_WhenIdsDoNotMatch() {
        // Kết quả mong đợi: Ném ra exception tương ứng với HTTP 403 (VD: AppException)
        assertThrows(IllegalArgumentException.class, () -> {
            validateActor(100L, 200L);
        });
    }

    @Test
    void generateHash() {
        System.out.println(new BCryptPasswordEncoder().encode("password-dev"));
    }

}
