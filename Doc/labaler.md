# CareSoft Audio Labeler

## Hướng dẫn sử dụng cho Worker và Reviewer

**Mã tài liệu:** UM-PRJ-WR-004  
**Phiên bản:** 1.1  
**Phạm vi áp dụng:** Worker, Reviewer

---

## Giới thiệu

Tài liệu này hướng dẫn sử dụng hệ thống **CareSoft Audio Labeler** cho 2 vai trò chính:

- **Worker**: xử lý transcript của task
- **Reviewer**: kiểm tra kết quả xử lý và thực hiện duyệt hoặc yêu cầu sửa

Tài liệu được biên soạn theo giao diện thực tế của hệ thống, bao gồm các màn hình:

- Projects
- Work Queue
- Review Queue
- Báo cáo

> Lưu ý: Hệ thống **không hiển thị popup “Task đã được lưu”**. Người dùng nhận biết thao tác thành công qua việc trạng thái task thay đổi trên giao diện và task tiếp theo được mở tự động nếu còn dữ liệu.

---

## Luồng sử dụng tổng quan

Luồng thao tác cơ bản của hệ thống như sau:

1. Vào màn hình **Projects**
2. Chọn project cần thao tác
3. Vào đúng queue theo vai trò:
    - Worker vào **Work Queue**
    - Reviewer vào **Review Queue**
4. Thực hiện xử lý hoặc review task
5. Theo dõi tiến độ tại màn hình **Báo cáo** khi cần

---

## Phân quyền

### Worker

Worker có quyền:

- xem danh sách dự án được phân công xử lý
- bấm nút **Work** tại màn hình Projects
- truy cập **Work Queue**
- cập nhật transcript và trạng thái task

### Reviewer

Reviewer có quyền:

- xem danh sách dự án được phân công review
- bấm nút **Review** tại màn hình Projects
- truy cập **Review Queue**
- duyệt hoặc yêu cầu sửa task

### Trường hợp không có quyền

Nếu người dùng không có quyền phù hợp thì hệ thống sẽ:

- không hiển thị nút thao tác tương ứng
- hoặc chặn truy cập trực tiếp bằng URL

---

# 1. Projects

Màn hình **Projects** hiển thị danh sách dự án mà người dùng được phép thao tác.

Do giao diện giữa Worker và Reviewer khác nhau ở cột **Actions**, phần này được tách thành 2 mục riêng.

---

## 1.1. Projects dành cho Worker

### Mục đích

Worker sử dụng màn hình này để:

- xem project được gán
- kiểm tra số lượng task còn chờ xử lý
- đi vào **Work Queue**

### Đặc điểm giao diện

Ở tài khoản Worker, cột **Actions** hiển thị nút **Work**.

### Thành phần chính

| Thành phần | Mô tả |
|---|---|
| Project | Tên project |
| Created | Ngày tạo project |
| Total | Tổng số task |
| Pending | Số task còn chờ Worker xử lý |
| Review Pending | Số task đang chờ Reviewer xử lý |
| Assigned | Worker và Reviewer được gán |
| Actions | Nút **Work** để vào Work Queue |

### Cách sử dụng

1. Mở màn hình **Projects**
2. Kiểm tra cột **Pending**
3. Chọn project cần thao tác
4. Bấm **Work** để vào hàng đợi xử lý

### Hình minh họa

> Chèn ảnh màn hình Projects của vai trò Worker tại đây.

---

## 1.2. Projects dành cho Reviewer

### Mục đích

Reviewer sử dụng màn hình này để:

- xem project cần review
- kiểm tra số lượng task đang chờ duyệt
- đi vào **Review Queue**

### Đặc điểm giao diện

Ở tài khoản Reviewer, cột **Actions** hiển thị nút **Review**.

### Thành phần chính

| Thành phần | Mô tả |
|---|---|
| Project | Tên project |
| Created | Ngày tạo project |
| Total | Tổng số task |
| Pending | Số task còn ở trạng thái work pending |
| Review Pending | Số task chờ reviewer xử lý |
| Assigned | Worker và Reviewer được gán |
| Actions | Nút **Review** để vào Review Queue |

### Cách sử dụng

1. Mở màn hình **Projects**
2. Kiểm tra cột **Review Pending**
3. Chọn project cần thao tác
4. Bấm **Review** để vào hàng đợi review

### Hình minh họa

> Chèn ảnh màn hình Projects của vai trò Reviewer tại đây.

---

## 1.3. So sánh nhanh giữa Worker và Reviewer tại màn hình Projects

| Nội dung | Worker | Reviewer |
|---|---|---|
| Nút ở cột Actions | Work | Review |
| Mục đích chính | Xử lý transcript | Kiểm tra kết quả |
| Chỉ số cần quan tâm | Pending | Review Pending |
| Màn hình tiếp theo | Work Queue | Review Queue |

---

# 2. Work Queue

Màn hình **Work Queue** dành cho Worker để nghe audio, kiểm tra transcript và lưu kết quả xử lý.

---

## 2.1. Mục đích

Worker sử dụng màn hình này để:

- chọn task cần xử lý
- nghe audio
- nhập hoặc chỉnh sửa transcript
- cập nhật trạng thái task

---

## 2.2. Cấu trúc màn hình

| Khu vực | Ý nghĩa |
|---|---|
| Danh sách task | Hiển thị toàn bộ task và trạng thái hiện tại |
| Thống kê tổng quan | Hiển thị tổng task và số lượng theo từng trạng thái |
| Audio player | Phát audio để đối chiếu nội dung |
| Transcript | Ô nhập/chỉnh sửa transcript |
| Đúng & Lưu | Xác nhận transcript đúng |
| Sửa & Lưu | Lưu transcript sau khi chỉnh sửa |
| Đánh dấu hỏng | Đánh dấu task không thể xử lý |
| Quay lại tổng quan | Quay về màn hình trước |

---

## 2.3. Các trạng thái tại Work Queue

| Trạng thái | Ý nghĩa |
|---|---|
| pending | Task chưa được xử lý |
| confirmed | Transcript đúng và được xác nhận |
| corrected | Transcript đã được chỉnh sửa |
| broken | Task không thể xử lý |

---

## 2.4. Quy trình thao tác

### Bước 1: Chọn task

Chọn task từ danh sách bên trái.

### Bước 2: Nghe audio

Bấm phát audio để nghe nội dung cần xử lý.

### Bước 3: Kiểm tra transcript

Đọc transcript hiện tại và chỉnh sửa nếu cần.

### Bước 4: Chọn thao tác phù hợp

#### Đúng & Lưu

Sử dụng khi transcript hiện tại đã chính xác.

**Kết quả:**

- trạng thái task chuyển sang `confirmed`
- hệ thống mở task tiếp theo nếu còn dữ liệu

#### Sửa & Lưu

Sử dụng khi transcript cần chỉnh sửa trước khi lưu.

**Kết quả:**

- trạng thái task chuyển sang `corrected`
- hệ thống mở task tiếp theo nếu còn dữ liệu

#### Đánh dấu hỏng

Sử dụng khi audio hoặc dữ liệu task không thể xử lý.

**Kết quả:**

- trạng thái task chuyển sang `broken`
- hệ thống mở task tiếp theo nếu còn dữ liệu

### Bước 5: Kiểm tra kết quả

Sau khi thao tác:

- trạng thái task trong danh sách bên trái thay đổi
- tiến độ tổng quan được cập nhật
- task tiếp theo được mở tự động nếu còn

---

## 2.5. Lưu ý khi sử dụng Work Queue

- Worker có thể chỉnh sửa nội dung transcript trực tiếp
- Không có popup xác nhận sau khi lưu
- Cần quan sát trạng thái task để xác nhận thao tác thành công

### Hình minh họa

> Chèn ảnh màn hình Work Queue tại đây.

---

# 3. Review Queue

Màn hình **Review Queue** dành cho Reviewer để kiểm tra kết quả mà Worker đã xử lý.

---

## 3.1. Mục đích

Reviewer sử dụng màn hình này để:

- chọn task cần review
- nghe lại audio
- đối chiếu transcript
- duyệt hoặc yêu cầu sửa

---

## 3.2. Cấu trúc màn hình

| Khu vực | Ý nghĩa |
|---|---|
| Danh sách task | Hiển thị các task của project |
| Thống kê tổng quan | Tổng số task và trạng thái review |
| Audio player | Nghe lại audio để kiểm tra |
| Transcript | Chỉ đọc, không chỉnh sửa trực tiếp |
| Duyệt | Xác nhận kết quả đạt yêu cầu |
| Yêu cầu sửa | Trả task về để xử lý lại |
| Quay lại tổng quan | Quay về màn hình trước |

---

## 3.3. Các trạng thái tại Review Queue

| Trạng thái | Ý nghĩa |
|---|---|
| review pending | Task đang chờ review |
| approved | Task đã được duyệt |
| rejected | Task bị yêu cầu sửa lại |

---

## 3.4. Quy trình thao tác

### Bước 1: Chọn task

Chọn task cần review ở danh sách bên trái.

### Bước 2: Nghe audio

Bấm phát audio để nghe lại nội dung.

### Bước 3: Đối chiếu transcript

So sánh transcript hiện tại với audio.

> Tại màn hình Review Queue, transcript chỉ đọc và không được chỉnh sửa trực tiếp.

### Bước 4: Chọn thao tác phù hợp

#### Duyệt

Sử dụng khi transcript đạt yêu cầu.

**Kết quả:**

- trạng thái review chuyển sang `approved`
- hệ thống mở task tiếp theo nếu còn dữ liệu

#### Yêu cầu sửa

Sử dụng khi transcript chưa đạt yêu cầu và cần trả lại cho Worker xử lý lại.

**Kết quả:**

- trạng thái review chuyển sang `rejected`
- hệ thống mở task tiếp theo nếu còn dữ liệu

### Bước 5: Kiểm tra kết quả

Sau khi thao tác:

- trạng thái review được cập nhật trong danh sách
- tiến độ review được cập nhật
- task tiếp theo được mở tự động nếu còn

---

## 3.5. Lưu ý khi sử dụng Review Queue

- Reviewer không chỉnh sửa transcript trực tiếp
- Reviewer chỉ chọn **Duyệt** hoặc **Yêu cầu sửa**
- Không có popup xác nhận sau thao tác

### Hình minh họa

> Chèn ảnh màn hình Review Queue tại đây.

---

# 4. Trạng thái và hành vi hệ thống

## 4.1. Bảng thao tác và kết quả

| Vai trò | Nút thao tác | Kết quả |
|---|---|---|
| Worker | Đúng & Lưu | Task chuyển sang `confirmed` |
| Worker | Sửa & Lưu | Task chuyển sang `corrected` |
| Worker | Đánh dấu hỏng | Task chuyển sang `broken` |
| Reviewer | Duyệt | Task được review thành `approved` |
| Reviewer | Yêu cầu sửa | Task được review thành `rejected` |

---

## 4.2. Cách nhận biết thao tác thành công

Hệ thống không hiển thị popup sau khi thao tác. Người dùng xác nhận thao tác thành công thông qua:

- trạng thái task thay đổi
- tiến độ tổng quan được cập nhật
- task tiếp theo được mở tự động nếu còn

---

# 5. Checklist sử dụng nhanh

## 5.1. Checklist cho Worker

- Vào **Projects**
- Chọn đúng project
- Bấm **Work**
- Nghe audio
- Kiểm tra hoặc chỉnh transcript
- Chọn đúng nút thao tác
- Kiểm tra trạng thái task thay đổi

## 5.2. Checklist cho Reviewer

- Vào **Projects**
- Chọn đúng project
- Bấm **Review**
- Nghe audio
- Đối chiếu transcript
- Chọn **Duyệt** hoặc **Yêu cầu sửa**
- Kiểm tra trạng thái review thay đổi

---

# 6. Báo cáo

Màn hình **Báo cáo** dùng để theo dõi tiến độ xử lý task theo nhiều góc nhìn như:

- theo ngày
- theo user trong ngày
- theo tháng
- theo user trong tháng

---

## 6.1. Cách truy cập

Từ thanh menu trên cùng, bấm vào mục **Báo cáo** để mở màn hình báo cáo.

---

## 6.2. Cấu trúc màn hình

Màn hình Báo cáo gồm 2 phần chính:

- **Bộ lọc báo cáo**
- **Các khối kết quả báo cáo**

### Hình minh họa

> Chèn ảnh màn hình Báo cáo tại đây.

---

## 6.3. Bộ lọc báo cáo

### Các trường hiển thị

| Trường | Ý nghĩa |
|---|---|
| Project | Chọn project cụ thể hoặc tất cả project |
| Từ ngày | Ngày bắt đầu thống kê |
| Đến ngày | Ngày kết thúc thống kê |
| Chọn tháng | Tháng cần xem số liệu tổng hợp |
| Đã áp dụng | Nút áp dụng bộ lọc và tải dữ liệu |

---

## 6.4. Cách sử dụng bộ lọc

1. Chọn **Project**
2. Chọn **Từ ngày**
3. Chọn **Đến ngày**
4. Chọn **tháng** nếu cần xem theo tháng
5. Bấm **Đã áp dụng** để tải dữ liệu báo cáo

---

## 6.5. Các khối báo cáo

### Hoàn thành theo ngày

Hiển thị tổng số task đã hoàn thành theo từng ngày trong khoảng thời gian được chọn.

### Hoàn thành theo user (theo ngày)

Hiển thị số lượng task hoàn thành theo từng user trong từng ngày.

### Hoàn thành theo tháng

Hiển thị tổng số task hoàn thành của project trong tháng đã chọn.

### Hoàn thành theo user (theo tháng)

Hiển thị tổng số task hoàn thành của từng user trong tháng đã chọn.

---

## 6.6. Ý nghĩa số liệu chi tiết

Ngoài tổng số task hoàn thành, báo cáo còn thể hiện các chỉ số chi tiết như:

- **Confirmed**
- **Corrected**
- **Broken**
- **Review Approve**
- **Review Reject**

Những số liệu này giúp đánh giá cả sản lượng và chất lượng xử lý.

---

## 6.7. Lưu ý khi sử dụng

- Màn hình Báo cáo chỉ dùng để theo dõi và thống kê
- Không thao tác xử lý task trực tiếp tại đây
- Khi cần xử lý task, người dùng quay lại menu **Projects**
- Cần kiểm tra kỹ project, khoảng ngày và tháng trước khi đối chiếu số liệu

---

# 7. Câu hỏi thường gặp

## Worker có thấy nút Review không?

Không. Worker chỉ thấy nút **Work** nếu được cấp quyền xử lý.

## Reviewer có chỉnh sửa transcript được không?

Không. Reviewer chỉ có thể nghe audio, kiểm tra transcript và chọn **Duyệt** hoặc **Yêu cầu sửa**.

## Vì sao sau khi bấm nút không có popup thông báo?

Vì hệ thống không sử dụng popup xác nhận. Trạng thái task thay đổi trực tiếp trên giao diện.

## Khi nào cần dùng màn hình Báo cáo?

Khi cần theo dõi tiến độ xử lý theo ngày, theo tháng, theo project hoặc theo user.

---

# 8. Gợi ý cấu trúc GitBook

Bạn có thể tổ chức nội dung trên GitBook theo cây trang sau:

- Giới thiệu
- Phân quyền
- Projects
    - Projects cho Worker
    - Projects cho Reviewer
    - So sánh nhanh
- Work Queue
- Review Queue
- Trạng thái và hành vi hệ thống
- Checklist sử dụng nhanh
- Báo cáo
- Câu hỏi thường gặp