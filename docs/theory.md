##### **Phần 2.1: Java Core và OOP (Hiệp 1)**

###### **Câu 1. Class và object khác nhau thế nào?**



* Định nghĩa: Class là một bản vẽ thiết kế (blueprint), định nghĩa cấu trúc và hành vi. Object (đối tượng) là một thực thể cụ thể được khởi tạo ra từ bản vẽ đó trên bộ nhớ RAM.



* Trả lời hỏi sâu: Ticket trong mã nguồn Java là một Class. Khi một nhân viên bấm "Tạo phiếu mới", hệ thống sẽ new Ticket() sinh ra nhiều Object khác nhau thuộc cùng Class này để chứa dữ liệu của từng người.



* Minh chứng bắt buộc: Dưới cơ sở dữ liệu PostgreSQL, bảng tickets đóng vai trò là Class. Hai dòng dữ liệu giả định (dòng có ticket\_code = 'TCK-001' và TCK-002) chính là 2 Object khác nhau.



###### **Câu 2. Encapsulation là gì và tại sao không nên cho sửa trực tiếp mọi field?**



* Định nghĩa: Encapsulation (Đóng gói) là việc che giấu dữ liệu bằng từ khóa private và chỉ cho phép thao tác thông qua các phương thức được kiểm soát (getter/setter/method).



* Trả lời hỏi sâu: Nếu mở public hoặc có public setStatus() tùy ý, ai cũng có thể gọi ticket.setStatus(CLOSED) bất chấp vé đó chưa qua bước RESOLVED. Business Rule (quy tắc nghiệp vụ) sẽ bị phá nát.



* Minh chứng bắt buộc: Trong project, ở hàm changeStatus của TicketService, thay vì gán trực tiếp, ta phải check điều kiện if (oldStatus == TicketStatus.CLOSED) ném ra lỗi INVALID\_STATUS\_TRANSITION rồi mới cho phép cập nhật.



###### **Câu 3. Overloading và overriding khác nhau thế nào?**



* Định nghĩa: Overloading (Nạp chồng) là viết nhiều hàm cùng tên nhưng khác tham số trong cùng 1 class. Overriding (Ghi đè) là class con viết lại ruột hàm của class cha (giữ nguyên tên và tham số).



* Trả lời hỏi sâu: Compiler (Trình biên dịch) quyết định hàm Overload lúc viết code dựa vào kiểu dữ liệu truyền vào. Runtime (Lúc chạy) quyết định hàm Override dựa vào class thực tế đang trỏ tới. Trường hợp null gây mơ hồ ở Overload: Nếu có hàm print(String) và print(Integer), gọi print(null) sẽ làm Compiler báo lỗi vì không biết null thuộc kiểu nào.



* Minh chứng bắt buộc:



Overriding: Hàm handleAppException() trong @RestControllerAdvice đang ghi đè lại cách xử lý lỗi mặc định.



Overloading: Phương thức findById(Long) và findById(Long, String) (nếu tự viết) trong Repository.



###### **Câu 4. Interface và abstract class khác nhau thế nào?**



* Định nghĩa: Interface chỉ chứa hợp đồng (chữ ký hàm), không có trạng thái. Abstract class có thể chứa cả ruột hàm đã triển khai và biến trạng thái, nhưng không thể khởi tạo trực tiếp.



* Trả lời hỏi sâu: Service thường dùng Interface để tạo sự lỏng lẻo (Loose Coupling), giúp dễ dàng thay thế class triển khai (Impl) hoặc tạo dữ liệu giả (Mock) khi Unit Test.



* Minh chứng bắt buộc: Trong dự án này, TicketService được viết dưới dạng Class trực tiếp mà không dùng Interface. Lý do: Để giữ kiến trúc đơn giản (KISS) cho một nghiệp vụ CRUD cơ bản chưa có nhu cầu đa hình. Nếu sau này có 2 kiểu xử lý vé (Vé IT và Vé HR), em sẽ tái cấu trúc (Refactor) tách thành Interface.



###### **Câu 5. Enum giải quyết vấn đề gì?**



* Định nghĩa: Enum giải quyết bài toán "Magic Strings", giới hạn giá trị của một biến vào một tập hợp cố định, giúp an toàn kiểu dữ liệu (Type-safety) ngay lúc biên dịch.



* Trả lời hỏi sâu: Lưu Enum bằng Ordinal sẽ lưu số (0, 1, 2). Nếu chèn thêm 1 giá trị mới vào giữa Enum, toàn bộ số thứ tự sẽ xô lệch, làm hỏng dữ liệu cũ. Lưu bằng String giữ nguyên được chữ, an toàn tuyệt đối.



* Minh chứng bắt buộc: Trong Java, trường Priority dùng Enum. Xuống PostgreSQL, nó được map thẳng thành chuỗi kết hợp ràng buộc CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT')).



###### **Câu 6. equals và hashCode có contract gì?**



* Định nghĩa: Contract (Giao kèo) quy định: Nếu hai đối tượng equals() nhau trả về true, thì hashCode() của chúng bắt buộc phải giống hệt nhau.



* Trả lời hỏi sâu: Nếu chỉ override equals mà quên hashCode, khi bỏ đối tượng vào HashSet, hệ thống sẽ hiểu nhầm đó là 2 vật khác nhau. Không nên dùng ID trong equals nếu Entity chưa được lưu (chưa có ID, ID=null), vì sau khi lưu ID nhảy số sẽ làm thay đổi mã băm, làm đối tượng "thất lạc" trong Set.



* Minh chứng bắt buộc (Ví dụ): Set<Ticket> set = new HashSet<>(); Nhét 2 vé có cùng ID vào, Set sẽ tự động gộp làm 1 nhờ Contract này. Đó là lý do ta không tùy tiện dùng @Data cho Entity.



###### **Câu 7. List, Set và Map khác nhau thế nào?**



* Định nghĩa: List có thứ tự, cho phép trùng. Set không quan tâm thứ tự, từ chối trùng lặp. Map lưu trữ theo cặp Key-Value.



* Trả lời hỏi sâu: Chọn Set để loại trùng vì nó tự động từ chối phần tử đã tồn tại (dựa vào equals). LinkedHashSet khắc phục nhược điểm của HashSet bằng cách duy trì đúng thứ tự lúc thêm dữ liệu vào.



* Minh chứng bắt buộc: Lấy danh sách assignee không trùng:

Set<String> uniqueAssignees = tickets.stream().map(t -> t.getAssignee().getUsername()).collect(Collectors.toSet());



###### **Câu 8. Checked exception và unchecked exception khác nhau thế nào?**



* Định nghĩa: Checked exception bắt buộc coder phải dùng try-catch hoặc throws lúc gõ code (vd: IOException). Unchecked exception (kế thừa RuntimeException) không bắt buộc, thường do lỗi logic (vd: NullPointerException).



* Trả lời hỏi sâu: Spring @Transactional mặc định CHỈ rollback khi gặp Unchecked Exception. Tuyệt đối không dùng Exception để điều khiển luồng (như thay cho if-else) vì chi phí tạo ra Stack Trace trong Java tốn rất nhiều CPU/RAM.



* Minh chứng bắt buộc: AppException trong project kế thừa RuntimeException (Unchecked). Khi văng lỗi TICKET\_NOT\_FOUND, nó map với mã HTTP 404. Lỗi INVALID\_STATUS\_TRANSITION map với HTTP 409 (Conflict).



###### **Câu 9. Optional nên và không nên dùng ở đâu?**



* Định nghĩa: Optional là một chiếc hộp bọc lấy dữ liệu, dùng để thể hiện rõ ý nghĩa "có thể trả về null", giúp lập trình viên không quên check null.



* Trả lời hỏi sâu: Tuyệt đối không dùng làm Field của Entity vì nó không thể Serialize (lưu trữ mảng byte), gây lãng phí bộ nhớ.



* Minh chứng bắt buộc: ticketRepository.findById() trả về Optional<Ticket>. Thay vì check if (ticket == null), em dùng cú pháp nối chuỗi: .orElseThrow(() -> new AppException(ErrorCode.TICKET\_NOT\_FOUND)); để xử lý thanh lịch ném lỗi.



###### **Câu 10. Stream khác vòng lặp thông thường thế nào?**



* Định nghĩa: Stream API thao tác dữ liệu theo kiểu khai báo (Declarative) dưới dạng đường ống (Pipeline), trong khi vòng lặp for/while thao tác kiểu mệnh lệnh (Imperative).



* Trả lời hỏi sâu: Stream chạy theo cơ chế "Lazy" (Lười biếng), chỉ thực thi khi gọi các hàm terminal (như .toList()). Stream khó debug vì không thể đặt breakpoint xem từng dòng. Dùng Stream gọi hàm Lazy Loading (getAssignee()) rất dễ sinh ra lỗi N+1 Query ngầm.



* Minh chứng bắt buộc: (2 cách tính số vé High)



Cách 1 (Vòng lặp): int count = 0; for(Ticket t : list) { if(t.getPriority() == Priority.HIGH) count++; }



Cách 2 (Stream): long count = list.stream().filter(t -> t.getPriority() == Priority.HIGH).count();





##### **Phần 2.2: Spring Boot và REST API**



###### **Câu 11. Spring Bean là gì?**



* Định nghĩa: Bean là một đối tượng Java được quản lý, khởi tạo và duy trì toàn bộ vòng đời bởi Spring IoC Container.



* Hỏi sâu: Bean được tạo bởi Spring, cất trong ApplicationContext (kho RAM của Spring). Khác với `new` (bạn tự tạo, tự hủy, tốn RAM), Bean thường là Singleton (có duy nhất 1 bản sao dùng chung cho vạn người).



* Minh chứng: `TicketService`, `EmployeeRepository`, `GlobalExceptionHandler` đều là các Bean được Spring tự động tạo lúc chạy dự án.



###### **Câu 12. IoC và DI là gì?**



* Định nghĩa: IoC (Đảo ngược điều khiển) là việc giao phó quyền quản lý object cho Framework. DI (Tiêm phụ thuộc) là hành động Spring tự động mang các Bean đã tạo đi "giao/tiêm" vào các class đang cần dùng chúng.



* Hỏi sâu: Constructor Injection (dùng `@RequiredArgsConstructor` + `final`) ưu việt hơn Field Injection (`@Autowired`) vì nó đảm bảo không bị NullPointerException, bảo vệ dữ liệu bất biến và dễ dàng truyền đối tượng giả (Mock) khi Unit Test. Nếu có 2 Bean cùng interface, dùng `@Qualifier` để chỉ định đích danh.



* Minh chứng: Trong `TicketService`, em tiêm `TicketRepository` thông qua Constructor (Lombok) để thực hiện DI.



###### **Câu 13. Vòng đời cơ bản của một Bean?**



* Định nghĩa: Khởi tạo (Instantiate) -> Bơm thuộc tính (Populate Properties) -> Khởi tạo tiền xử lý (`@PostConstruct`) -> Sẵn sàng sử dụng -> Hủy (`@PreDestroy`).



* Hỏi sâu: `@PostConstruct` chạy ngay sau khi Spring tiêm xong các DI. Không nên gọi Database hoặc API chậm ở đây vì nó sẽ làm "treo" quá trình khởi động của toàn bộ server.



* Minh chứng: Em có thể dùng `@PostConstruct` trong một class Cấu hình để nạp sẵn danh sách các `ErrorCode` hoặc nạp Cache cơ bản lên RAM trước khi đón Client.



###### **Câu 14. @Component, @Service, @Repository và @RestController khác nhau gì?**



* Định nghĩa: `@Component` là mác chung nhất. `@Service` chứa logic nghiệp vụ. `@Repository` chuyên giao tiếp Database. `@RestController` bọc `@Controller` và `@ResponseBody` để trả về JSON.



* Hỏi sâu: `@Repository` còn có siêu năng lực tự động "dịch" các lỗi SQL/JDBC checked exception rườm rà thành các lỗi unchecked exception (DataAccessException) của Spring.



* Minh chứng: Khi tạo Ticket: `TicketController` (đón Request) gọi `TicketService` (chứa rule bắt lỗi 404, check active) gọi `TicketRepository` (lệnh save xuống Postgres).



###### **Câu 15. @PathVariable, @RequestParam và @RequestBody dùng khi nào?**



* Định nghĩa: `@PathVariable` móc dữ liệu từ URL (vd: `/tickets/1`). `@RequestParam` móc từ dấu `?` (vd: `?status=OPEN`). `@RequestBody` móc từ cục JSON trong thân Request.



* Hỏi sâu: Chuyển trạng thái dùng Path/PATCH vì nó tuân thủ chuẩn REST: nhắm đích danh 1 tài nguyên cụ thể (qua Path) và chỉ cập nhật 1 phần dữ liệu. Truyền tùy ý vào Create Body sẽ làm rối loạn logic tạo mới (POST).
* Minh chứng: API xem chi tiết dùng `@PathVariable Long id`. API tạo Ticket dùng `@RequestBody TicketCreateRequest`.



###### **Câu 16. Bean Validation hoạt động ở đâu?**



* Định nghĩa: Hoạt động ngay tại tầng Controller khi có request bay vào, đóng vai trò như "bảo vệ cổng".



* Hỏi sâu: `@NotNull` bắt lỗi null (dùng cho số/object). `@NotBlank` mạnh hơn, cấm null, cấm rỗng `""` và cấm toàn dấu cách (dùng cho String). Validation DTO chỉ chặn dữ liệu rác, KHÔNG thay thế được validation nghiệp vụ ở Service (kiểm tra tồn tại DB).



* Minh chứng: `@NotNull` cho `assigneeId` ở Controller. Việc check assignee có `active` không phải nằm ở Service.



###### **Câu 17. @Transactional hoạt động như thế nào?**



* Định nghĩa: Gói gọn nhiều lệnh SQL thành một Giao dịch (Tất cả thành công, hoặc rollback toàn bộ). Nó hoạt động dựa trên Proxy Pattern (Kẻ đóng thế).





* Hỏi sâu: Self-invocation (gọi hàm nội bộ trong cùng 1 class) làm mất tác dụng vì luồng đi không đi xuyên qua lớp vỏ bọc Proxy. Nên đặt ở Service để bao quát toàn bộ logic nghiệp vụ.





* Minh chứng: Hàm `changeStatus()`. Nếu cập nhật bảng `tickets` thành công nhưng ghi `ticket\_status\_history` thất bại, `@Transactional` sẽ rollback ngay, trả sách về nguyên vẹn.



###### **Câu 18. Global exception handler có vai trò gì?**



* Định nghĩa: Dùng `@RestControllerAdvice` làm lưới gom mọi lỗi văng ra từ hệ thống, định dạng lại thành cấu trúc JSON chuẩn trước khi trả cho Client.



* Hỏi sâu: Phân biệt qua `ErrorCode` Enum mapping với HttpStatus. Trả stack trace cho client cực kỳ nguy hiểm vì làm lộ cấu trúc mã nguồn, database nội bộ cho hacker.



* Minh chứng: Lỗi `EMPLOYEE\_NOT\_FOUND` ném ra mã HTTP 404. Lỗi `INVALID\_STATUS\_TRANSITION` ném ra mã HTTP 409 (Conflict). Cấu trúc JSON chỉ có `timestamp`, `code`, `message`.



###### **Câu 19. Entity và DTO khác nhau thế nào?**



* Định nghĩa: Entity ánh xạ trực tiếp thành bảng dưới Database. DTO (Data Transfer Object) là vỏ bọc để luân chuyển dữ liệu với Client.



* Hỏi sâu: Trả Entity trực tiếp sẽ gây thảm họa Vòng lặp đệ quy vô hạn (Infinite Recursion) do quan hệ 2 chiều, làm sập RAM. Cần DTO riêng để che giấu mật khẩu, che giấu dữ liệu thừa và kiểm soát 100% dữ liệu đầu ra.



* Minh chứng: Nhận vào `TicketCreateRequest` (ít trường). Trả ra `TicketResponse` (nhiều trường). Entity `Ticket` nằm ẩn bên dưới.



###### **Câu 20. Lazy loading và eager loading là gì?**



* Định nghĩa: Eager là lấy sẵn toàn bộ dữ liệu liên quan ngay lập tức. Lazy là chỉ tải dữ liệu lên RAM khi nào có lệnh gọi `.get()`.



* Hỏi sâu: Lỗi N+1 query xảy ra khi Lazy Loading nằm trong vòng lặp. 1 câu SQL lấy 100 vé, sau đó gọi `.getAssignee()` khiến Hibernate bắn thêm 100 câu SQL lẻ tẻ để lấy nhân viên.



* Minh chứng: Khắc phục N+1 ở API Xem chi tiết Ticket bằng cách dùng `@EntityGraph(attributePaths = {"reporter", "assignee"})` để gộp chung vào 1 câu lệnh JOIN duy nhất.





##### **Phần 2.3: SQL PostgreSQL và JPA**



###### **Câu 21. Primary key, foreign key, unique và check constraint khác nhau thế nào?**



* Định nghĩa:

Primary Key: Khóa chính, định danh duy nhất và không được phép Null.

Foreign Key: Khóa ngoại, bảo vệ tính toàn vẹn tham chiếu (dữ liệu phải tồn tại ở bảng cha mới được nhập vào bảng con).



Unique: Chống trùng lặp dữ liệu trong một cột.

Check: Đặt ra các giới hạn vật lý/logic cho giá trị của cột.



* Trả lời hỏi sâu: Dù ở tầng Java đã có Bean Validation (như `@NotBlank`, `@NotNull`), database vẫn BẮT BUỘC phải có Constraint làm "chốt chặn cuối cùng" bảo vệ dữ liệu phòng trường hợp có người dùng Navicat insert thẳng vào DB, hoặc code Java có bug lọt qua lớp Validation.



* Minh chứng bắt buộc: Trong `01\_create\_schema.sql`:

1\. `id BIGSERIAL PRIMARY KEY` (Khóa chính).

2\. `ticket\_code VARCHAR(20) UNIQUE` (Unique).

3\. `status VARCHAR(50) CHECK (status IN (...))` (Check).

4\. `reporter\_id BIGINT REFERENCES employees(id)` (Khóa ngoại).





###### **Câu 22. INNER JOIN và LEFT JOIN khác nhau thế nào?**



* Định nghĩa: `INNER JOIN` chỉ lấy bản ghi khi cả 2 bảng đều có dữ liệu khớp nhau hoàn toàn. `LEFT JOIN` lấy toàn bộ bản ghi ở bảng bên trái, bất chấp bảng bên phải có khớp hay không (chỗ nào không khớp thì trả về `NULL`).



* Trả lời hỏi sâu: Muốn lấy cả nhân viên chưa được giao ticket, bắt buộc dùng `LEFT JOIN` (nhân viên nằm bảng trái). Để lọc ra người \*chưa có vé\*, điều kiện lọc "ghép cặp bị trượt" phải đặt ở mệnh đề `WHERE` (`WHERE tickets.id IS NULL`), tuyệt đối không đặt ở mệnh đề `ON`.



* Minh chứng bắt buộc: Câu SQL tìm người nhàn rỗi:

`SELECT e.full\_name FROM employees e LEFT JOIN tickets t ON e.id = t.assignee\_id WHERE t.id IS NULL;`



###### **Câu 23. Index hoạt động nhằm mục đích gì?**



* Định nghĩa: Index (chỉ mục) hoạt động như mục lục của một cuốn sách. Nó giúp Database truy xuất dữ liệu cực nhanh thông qua cấu trúc cây (B-Tree) thay vì phải quét qua từng dòng một (Full Table Scan).



* Trả lời hỏi sâu: Index không phải càng nhiều càng tốt vì mỗi khi thực hiện lệnh `INSERT/UPDATE/DELETE`, DB phải tốn thêm tài nguyên để sắp xếp, xây lại cây Index, làm thao tác ghi bị chậm đi. Trong Composite Index (Chỉ mục đa cột), thứ tự rất quan trọng (Quy tắc Leftmost Prefix): Cột nào hay được tìm kiếm hoặc có tính phân loại cao nhất phải đứng trước.



* Minh chứng bắt buộc: API `GET /api/tickets` thường xuyên tìm kiếm theo `status` và `priority`. Em sẽ tạo index: `CREATE INDEX idx\_ticket\_search ON tickets (status, priority);` để tăng tốc độ lọc.



###### **Câu 24. Transaction và bốn tính chất ACID là gì?**



* Định nghĩa: Transaction gom nhiều lệnh SQL thao tác trên nhiều bảng thành một Giao dịch duy nhất: "Tất cả cùng thành công, hoặc hủy bỏ toàn bộ". Bốn tính chất ACID là: Atomicity (Nguyên tử), Consistency (Nhất quán), Isolation (Độc lập), Durability (Bền vững).



* Trả lời hỏi sâu: Nếu cập nhật Ticket thành công nhưng ghi History bị lỗi, Transaction sẽ lập tức `ROLLBACK` (quay xe). Kết quả đúng là: Ticket phải giữ nguyên trạng thái cũ, và bảng History không có dữ liệu rác được ghi vào, bảo vệ tính nhất quán tuyệt đối.



* Minh chứng bắt buộc (Ví dụ): Hàm `changeStatus` được bọc bởi `@Transactional`. Khi chạy Test, em giả lập lỗi văng Exception ở dòng lưu History, kết quả truy vấn DB cho thấy Ticket cũ không hề bị thay đổi.





###### **Câu 25. Isolation level giải quyết vấn đề gì?**



* Định nghĩa: Xác định mức độ "cô lập" giữa các Transaction đang chạy song song, nhằm ngăn chặn các lỗi xung đột tranh chấp (Race Condition).



* Trả lời hỏi sâu:

Dirty read: Transaction A đọc nhầm dữ liệu mà Transaction B đang sửa nhưng chưa Commit.

Non-repeatable read: Đọc 1 dòng 2 lần ra 2 kết quả khác nhau (do ai đó Update chen ngang).

Phantom read: Đếm số lượng 2 lần ra 2 số lượng khác nhau (do ai đó Insert/Delete chen ngang).

PostgreSQL mặc định mức: `Read Committed`.



* Minh chứng bắt buộc: Hai người cùng tranh bấm "Nhận" một Ticket. Để chống Race Condition, em dùng Khóa lạc quan (Optimistic Locking) bằng trường `@Version` ở tầng Java. Nếu người B lưu sau, version database đã tăng, Hibernate sẽ văng lỗi `ObjectOptimisticLockingFailureException`, Transaction của B bị hủy.



###### **Câu 26. JPA persistence context là gì?**



* Định nghĩa: Persistence Context là "Bộ nhớ đệm cấp 1" (First-Level Cache) nằm trên RAM. Đây là khu vực EntityManager quản lý và theo dõi sự thay đổi của các Entity trong một Transaction.
* Trả lời hỏi sâu: Dirty Checking là cơ chế "kiểm tra vết bẩn". EntityManager so sánh trạng thái hiện tại của Object trên RAM với trạng thái gốc lúc mới lấy từ DB lên. Nếu thấy khác biệt (Dirty), nó tự động sinh lệnh SQL `UPDATE` lúc commit mà không cần ta gọi hàm `save()`. Chỉ dùng `save()` khi muốn tạo bản ghi mới hoàn toàn (`INSERT`).
* Minh chứng bắt buộc: Trong `TicketService.assignTicket()`, sau khi gọi `ticket.setAssignee(employee)`, hàm kết thúc mà không hề có lệnh `ticketRepository.save(ticket)`, nhưng dữ liệu vẫn được lưu thành công nhờ Dirty Checking.





###### **Câu 27. save, saveAndFlush và flush khác nhau thế nào?**



* Định nghĩa: `save()` chỉ đưa Entity vào bộ nhớ tạm (Persistence Context) để xếp hàng. `flush()` là lệnh ép Hibernate đẩy ngay các câu SQL đang xếp hàng xuống Database chạy tức thì. `saveAndFlush()` là gom 2 bước trên làm 1.
* Trả lời hỏi sâu: `Flush` đẩy SQL xuống DB nhưng KHÔNG đồng nghĩa với Transaction đã commit. Nếu sau đó bị lỗi, DB vẫn có thể Rollback những gì vừa flush. Lỗi vi phạm Constraint dưới DB (như trùng Username) thường chỉ lòi ra lúc hệ thống thực sự xả SQL (lúc gọi `flush` hoặc lúc kết thúc hàm để tự động commit), chứ không hiện ra ngay ở dòng gọi `save()`.
* Minh chứng bắt buộc: Viết Test tạo 2 nhân viên trùng `username`. Lệnh `employeeRepository.save(nv2)` chạy bình thường không văng lỗi, nhưng vừa gọi `employeeRepository.flush()` thì bị văng `DataIntegrityViolationException`.



###### **Câu 28. Pagination cần xử lý thế nào?**



* &#x20;Định nghĩa: Kỹ thuật chia nhỏ một lượng lớn dữ liệu thành các "Trang" (Page), tránh việc tải hàng triệu bản ghi lên RAM cùng lúc gây sập server. Spring cung cấp interface `Pageable`.
* &#x20;Trả lời hỏi sâu: Dùng Offset Pagination với Page number quá lớn (ví dụ: `OFFSET 1000000 LIMIT 10`) sẽ làm DB chạy rất chậm vì phải duyệt và ném bỏ 1 triệu dòng. Keyset Pagination (Cursor) dùng mốc giá trị của dòng cuối cùng trang trước (VD: `WHERE id > 1000000 LIMIT 10`) nên nhanh hơn. Sort không ổn định (sort theo ngày tạo giống nhau) sẽ làm bản ghi bị nhảy lung tung giữa trang 1 và trang 2.
* Minh chứng bắt buộc: API tìm kiếm Ticket dùng `PageRequest.of(page, size, Sort.by("createdAt").descending().and(Sort.by("id").descending()))` (Thêm sort theo ID để đảm bảo tính ổn định), trả về DTO phân trang chứa `totalPages`, `totalElements`.



###### **Câu 29. Native query và JPQL khác nhau thế nào?**



* Định nghĩa: JPQL (`@Query`) thao tác trên tên Class và Thuộc tính Java, tự động dịch ra mã tương ứng nên có tính Độc lập Database. Native Query (`nativeQuery = true`) là SQL thuần túy thao tác trực tiếp lên tên Bảng và Cột, bị khóa cứng vào loại Database đang dùng (PostgreSQL).
* Trả lời hỏi sâu: Khi Derived Query Methods (đặt tên hàm) quá dài và lố bịch (Ví dụ: `findByStatusAndPriorityAndReporterIdOrderByCreatedAtDesc`), ta nên dùng `@Query` JPQL để code ngắn và dễ đọc hơn. Nếu filter nhiều tham số động (có thể truyền hoặc không), nên dùng `Specification` (Criteria API).
* Minh chứng bắt buộc: API Lọc Ticket, em không dùng tên hàm mà dùng `@Query("SELECT t FROM Ticket t WHERE t.status = :status AND t.priority = :priority")` hoặc `Specification` để linh hoạt hơn trong việc thêm bớt các tham số truy vấn.



