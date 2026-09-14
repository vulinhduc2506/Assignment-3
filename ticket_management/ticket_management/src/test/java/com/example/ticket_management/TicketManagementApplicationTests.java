package com.example.ticket_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TicketManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	// TODO [MENTOR REVIEW]: contextLoads không kiểm chứng business rule. Bổ sung test cho state transition,
	// mapping fullName, hai endpoint GET, rollback ticket/history và optimistic locking.

}
