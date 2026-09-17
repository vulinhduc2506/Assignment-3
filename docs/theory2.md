1\. Transaction và 4 tính chất ACID là gì?



Định nghĩa: Transaction gom nhiều thao tác thành một khối duy nhất với nguyên lý "Tất cả cùng thành công, hoặc rollback (hủy) toàn bộ". ACID là 4 tính chất: Nguyên tử (Atomicity), Nhất quán (Consistency), Cô lập (Isolation), Bền vững (Durability).



Ví dụ: Trong hàm changeStatus, lưu Ticket và TicketStatusHistory cùng lúc. Nhờ @Transactional, nếu lưu lịch sử bị lỗi rớt mạng, thao tác đổi trạng thái Ticket trước đó sẽ bị Rollback sạch sẽ, tránh dữ liệu bị lệch.



2\. Isolation Level giải quyết vấn đề gì?



Định nghĩa: Mức độ cô lập giúp ngăn chặn các lỗi xung đột tranh chấp (Race Condition) khi nhiều giao dịch chạy song song.



Ví dụ: Nếu 2 chuyên viên cùng bấm "Nhận việc" cho cùng 1 vé lúc 9:00:00, Isolation Level quyết định xem hệ thống sẽ xếp hàng cho 1 người đợi, hay đá văng người đến sau bằng lỗi.



3\. JPA Persistence Context và "phép màu" Dirty Checking?



Định nghĩa: Persistence Context là bộ nhớ đệm cấp 1 (RAM) của Hibernate. Dirty Checking là cơ chế so sánh dữ liệu trên RAM với gốc dưới DB.



Ví dụ: Trong hàm changeStatus, chỉ dùng ticket.setStatus(nextStatus) mà không hề gọi lệnh ticketRepository.save(). Nhờ Dirty Checking, khi hàm kết thúc, Hibernate tự phát hiện đối tượng bị "bẩn" và ngầm bắn lệnh UPDATE xuống DB.



4\. Phân biệt save(), saveAndFlush() và flush()?



Định nghĩa: save() chỉ đưa dữ liệu vào RAM xếp hàng. flush() ép Hibernate xả ngay các câu SQL đang xếp hàng xuống DB chạy tức thì.



Ví dụ: Các lỗi vi phạm Constraint (như thiếu reason sáng nay) thường chỉ lòi ra lúc hệ thống thực sự xả SQL (lúc flush hoặc khi kết thúc Transaction), chứ không văng lỗi ngay ở dòng code gọi save().



5\. Tại sao Pagination cần sắp xếp ổn định (Stable Sort)?



Định nghĩa: Phân trang giúp không tải hàng triệu bản ghi lên RAM cùng lúc gây sập server.



Ví dụ: Khi lấy danh sách vé, nếu chỉ sort theo createdAt, những vé sinh ra cùng 1 mili-giây sẽ khiến kết quả trang 1 và trang 2 bị nhảy lặp lại lung tung. đã khắc phục bằng cách truyền thêm mốc phụ: sort=createdAt,desc\&sort=id,desc.



Nhóm 2: Tối Ưu Hiệu Năng \& Chống Đụng Độ

6\. N+1 Query là gì? Tại sao chọn @EntityGraph thay vì JOIN FETCH?



Định nghĩa: N+1 Query là thảm họa khi lấy 100 vé (1 câu query) rồi gọi getAssignee() khiến Hibernate bắn thêm 100 câu query lẻ tẻ nữa.



Ví dụ: khắc phục bằng cách gộp vào 1 lệnh LEFT JOIN duy nhất. Với API searchTickets, bắt buộc dùng @EntityGraph thay vì JOIN FETCH vì JOIN FETCH khi kết hợp với Phân trang (Pageable) sẽ làm Hibernate không đếm được số lượng (COUNT) và gây lỗi tràn RAM.



7\. Khóa lạc quan (@Version) hoạt động thế nào?



Định nghĩa: Kỹ thuật đánh version cho dòng dữ liệu để chống đụng độ (Optimistic Locking).



Ví dụ: Khi vé vừa tạo, version = 0. Người A và B cùng lôi vé lên. Người A lưu trước, DB tăng version lên 1. Người B (đang cầm version 0) lưu sau, Hibernate đối chiếu thấy DB đã là 1 nên lập tức ném lỗi ObjectOptimisticLockingFailureException đá văng người B, bảo vệ dữ liệu tuyệt đối.



8\. Tại sao dùng DTO thay vì trả thẳng Entity ra API?



Định nghĩa: DTO là vỏ bọc luân chuyển dữ liệu với Client.



Ví dụ: Ticket có chứa Employee, và Employee lại chứa danh sách Ticket (quan hệ 2 chiều). Nếu trả thẳng Entity, JSON parser sẽ lặp đệ quy vô hạn (Infinite Recursion) làm sập hệ thống. Hơn nữa, DTO giúp giấu kín thông tin nhạy cảm của nhân viên.



Nhóm 3: Clean Code \& Kiến Trúc

9\. State Machine Pattern: Tại sao truyền Action thay vì Status?



Định nghĩa: Máy trạng thái khóa chặt các ngã rẽ nghiệp vụ, chỉ cho phép luân chuyển hợp lệ.



Ví dụ: Nếu cho phép Frontend truyền newStatus, hacker có thể lách luật đổi vé từ OPEN nhảy cóc sang CLOSED. Bằng cách bắt Client gửi Action (START, RESOLVE), Backend sẽ giành lại quyền kiểm soát, tự tính toán trạng thái đích dựa trên logic if/switch khắt khe.



10\. Vai trò của Global Exception Handler?



Định nghĩa: Dùng @RestControllerAdvice làm lưới hứng mọi lỗi văng ra và chuẩn hóa thành JSON.



Ví dụ: Khi hàm trạng thái ném lỗi IllegalArgumentException thuần Java, bọc lại thành AppException. Lưới lọc sẽ tự bóc mã HTTP 409 (Conflict) trả về Client. Tuyệt đối không trả Stack Trace (log đỏ) vì sẽ làm lộ cấu trúc Database nội bộ cho hacker.



11\. Tại sao phải cài Check Constraint dưới DB khi đã Validation ở Java?



Ví dụ: Sáng nay có lỗi gửi reason: "   ". Mặc dù Java có thể bắt lỗi, nhưng Constraint CHECK (TRIM(reason) <> '') dưới Database đóng vai trò là "chốt chặn sống còn". Bất chấp lập trình viên có viết code Java sai, hoặc ai đó thọc tay trực tiếp vào DB, dữ liệu rác vẫn bị Database từ chối ngay lập tức.



12\. BIGSERIAL và SERIAL trong PostgreSQL khác gì nhau?



Ví dụ: Java định nghĩa id của Entity bằng kiểu Long (tương đương 64-bit). Nếu tạo bảng bằng SERIAL (số nguyên 32-bit), Hibernate với chế độ ddl-auto=validate sẽ đình công ngay vì lệch pha kiểu dữ liệu. Việc đổi thành BIGSERIAL (hay BIGINT) giúp 2 bên khớp hoàn toàn cấu trúc, phòng chống lỗi tràn số ID khi hệ thống lớn lên.

