\*\*1. Authentication khác Authorization như thế nào?\*\*



\* \*\*Authentication (Xác thực):\*\* Kiểm tra "Bạn là ai?". \*Ví dụ:\* Bạn gọi API `/api/auth/login` với username/password đúng để chứng minh danh tính.

\* \*\*Authorization (Phân quyền):\*\* Kiểm tra "Bạn được phép làm gì?". \*Ví dụ:\* Có Token hợp lệ mới được gọi `/api/tickets/\*\*`, Token của USER thì không được phân công thay người khác.



\*\*2. JWT là gì? Các thành phần dùng làm gì?\*\*



\* JWT (JSON Web Token) là tiêu chuẩn truyền tải thông tin an toàn.

\* \*\*Header:\*\* Chứa loại token và thuật toán ký (VD: HS256).

\* \*\*Payload:\*\* Chứa thông tin nghiệp vụ (Claims) như username, employeeId.

\* \*\*Signature:\*\* Chữ ký bảo mật do Server dùng Secret Key tạo ra, dùng để chống giả mạo (nếu ai đó sửa Payload, Signature sẽ sai).



\*\*3. JWT có được mã hóa mặc định không?\*\*



\* \*\*Không.\*\* Mặc định Payload của JWT chỉ được mã hóa định dạng (Base64URL Encode) để truyền qua mạng, hoàn toàn không bị mã hóa nội dung (Encrypt). Bất kỳ ai có token cũng có thể dịch ngược Payload ra chữ thường. Do đó, tuyệt đối không bỏ password hay thông tin nhạy cảm vào đây.



\*\*4. Access token hết hạn thì client nhận gì và xử lý thế nào?\*\*



\* Client sẽ nhận mã lỗi \*\*HTTP 401\*\*. Client cần xử lý bằng cách điều hướng người dùng về trang Đăng nhập, hoặc tự động gọi một API khác bằng \*Refresh Token\* (nếu hệ thống có hỗ trợ) để xin Access Token mới ngầm.



\*\*5. Phân biệt HTTP 401 và 403. Nêu case thực tế.\*\*



\* \*\*401 Unauthorized (Vô danh):\*\* Lỗi chưa xác thực. \*Ví dụ:\* Gọi `/api/auth/me` nhưng không truyền Token, hoặc Token bị hết hạn.

\* \*\*403 Forbidden (Cấm cửa):\*\* Đã biết bạn là ai nhưng bạn không có quyền thao tác. \*Ví dụ:\* Dùng token của người A nhưng lại cố tình truyền `actorId` của người B để lách luật.



\*\*6. Vì sao không được tin actorId do client gửi?\*\*



\* Vì Client hoàn toàn có thể bị thao túng (người dùng dùng Postman tự sửa body). Backend bắt buộc phải tự bóc tách danh tính (Identity) trực tiếp từ ruột JWT do chính Server cấp phát để đối chiếu sự thật.



\*\*7. BCrypt dùng để làm gì?\*\*



\* BCrypt là thuật toán băm (Hash) một chiều dùng để giấu mật khẩu. Không lưu plain text (chữ thô) để phòng trường hợp Hacker trộm được Database, chúng cũng chỉ nhìn thấy chuỗi hash lộn xộn và không thể dịch ngược ra mật khẩu thật của nhân viên.



\*\*8. JWT và OAuth2 có phải cùng một khái niệm không?\*\*



\* \*\*Không.\*\* JWT là một \*định dạng chiếc thẻ\* (Token). Còn OAuth2 là một \*giao thức/quy trình\* (Protocol) quy định các bước để xin cấp phát cái thẻ đó.



\*\*9. Giải thích các vai trò trong OAuth2.\*\*



\* \*\*Resource Owner:\*\* Chủ tài nguyên (Chính là Người dùng).

\* \*\*Client:\*\* Ứng dụng trung gian đứng ra xin quyền (Frontend Web, Mobile App).

\* \*\*Authorization Server:\*\* Nơi kiểm tra thông tin đăng nhập và phát hành Token.

\* \*\*Resource Server:\*\* Nơi giữ tài nguyên thật sự (Chính là API Backend chứa dữ liệu Ticket của bạn).



\*\*10. Authorization Code Flow là gì? Vì sao dùng PKCE?\*\*



\* Là luồng bảo mật nhất của OAuth2: Client sẽ xin một cái Mã (Code) dùng một lần, rồi đem Mã đó đổi lấy Token.

\* Mobile/SPA bắt buộc dùng chuẩn PKCE vì chúng là các ứng dụng chạy trên máy khách, rất dễ bị dịch ngược code để ăn cắp mật khẩu ứng dụng (Client Secret). PKCE sinh ra một chuỗi băm ngẫu nhiên trong lúc chạy để thay thế cho Client Secret cố định.





