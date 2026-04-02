# 01 - Tổng Quan Bàn Giao Test ChatModule
Cập nhật: 2026-04-02

## 1) Mục tiêu tài liệu
Tài liệu này giúp tester nắm nhanh:
- Phạm vi cần test của ChatModule mới.
- Luồng nghiệp vụ chính theo góc nhìn người dùng.
- Tiêu chí pass/fail trước khi bàn giao release.

## 2) Phạm vi test (In Scope)
Phần bắt buộc kiểm thử:
- Panel `Processing`: danh sách hội thoại đang xử lý, chọn hội thoại, gửi tin, đóng chat, seen/unseen.
- Panel `History`: danh sách lịch sử theo kênh, filter/search/sort, phân trang cursor, refresh cứng.
- Panel `Segment`: danh sách hội thoại theo segment, filter/search/sort, phân trang cursor, refresh cứng.
- Detail pane: tải lịch sử session/message, load thêm dữ liệu cũ, hiển thị đúng thứ tự.
- Realtime/WebSocket: nhận tin nhắn mới, typing, trạng thái hội thoại, đồng bộ unread.
- Segment CRUD: tạo/sửa/xóa phân loại và áp dụng vào panel segment.
- Điều hướng URL và khôi phục state khi reload trang `/chat`.

## 3) Ngoài phạm vi (Out of Scope)
- Test sâu backend API nội bộ không liên quan trực tiếp giao diện ChatModule.
- Pen-test, load test, security hardening.
- So sánh pixel-perfect UI.

## 4) Thành phần kỹ thuật tester cần biết
- Cờ bật module:
  - `localStorage.chat_module_enable = "1"` để dùng ChatModule mới.
- Chế độ xử lý packet websocket:
  - `localStorage.chat_socket_packet_mode = "legacy-only" | "dual-run" | "v2-only"`.
- Route:
  - Màn hình chat chính: `/chat`.

## 5) Luồng nghiệp vụ chính
- Luồng A: Agent nhận chat mới -> chat vào `Processing` -> chọn chat -> xem detail -> gửi tin -> đánh dấu seen.
- Luồng B: Agent mở `History` -> lọc dữ liệu -> mở hội thoại cũ -> load thêm phiên cũ.
- Luồng C: Agent chọn `Segment` -> mở danh sách theo segment -> lọc -> mở detail.
- Luồng D: Agent tạo/sửa/xóa segment -> sidebar cập nhật đúng -> panel segment phản ánh đúng dữ liệu.
- Luồng E: Realtime đồng bộ khi có packet (message/state/typing/end/leave).

## 6) Tiêu chí pass/fail tổng thể
Pass khi thỏa đồng thời:
- Không có lỗi blocker/critical ở P0.
- Tất cả case P0 pass ở cả 3 mode packet (`legacy-only`, `dual-run`, `v2-only`).
- Case P1 không có lỗi làm sai dữ liệu store hoặc sai luồng nghiệp vụ.
- Không xuất hiện sai lệch selection state nghiêm trọng khi chuyển panel/channel/segment.

Fail khi có một trong các dấu hiệu:
- Mất dữ liệu hội thoại khi chuyển panel hoặc reload trang.
- Unread/seen hiển thị sai kéo dài sau realtime update.
- Segment CRUD thành công nhưng sidebar/panel không phản ánh đúng.
- Load thêm lịch sử bị trùng/mất message hoặc thứ tự sai.

## 7) Danh sách tài liệu liên quan
- `02_Environment_And_Data.md`
- `03_Test_Scenarios_P0_P1.md`
- `04_Test_Matrix.md`
- `05_Regression_Checklist.md`
- `06_Known_Issues_And_Debug_Guide.md`
