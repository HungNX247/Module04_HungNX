# 02 - Môi Trường Và Dữ Liệu Test
Cập nhật: 2026-04-02

## 1) Mục tiêu
Tài liệu này mô tả cách chuẩn bị môi trường và dữ liệu để tester có thể chạy test ChatModule ổn định, lặp lại được.

## 2) Điều kiện tiên quyết
- Đã đăng nhập bằng account agent hợp lệ.
- Đã có quyền xem/manipulate chat theo role cần test.
- Hệ thống websocket đang hoạt động bình thường.
- Trình duyệt đã bật Redux DevTools (khuyến nghị).

## 3) Cấu hình bật/tắt ChatModule
Chạy trong browser console trước khi vào `/chat`:

```js
localStorage.setItem("chat_module_enable", "1");
```

Nếu cần quay về giao diện cũ:

```js
localStorage.setItem("chat_module_enable", "0");
```

Sau khi đổi giá trị, reload trang.

## 4) Cấu hình packet mode cho websocket router
Đặt mode trong browser console:

```js
localStorage.setItem("chat_socket_packet_mode", "legacy-only"); // hoặc dual-run, v2-only
```

Giá trị hợp lệ:
- `legacy-only`
- `dual-run`
- `v2-only`

Khuyến nghị test:
- Vòng 1 (smoke): `dual-run`.
- Vòng 2 (bắt buộc): chạy lại P0 trên `legacy-only` và `v2-only`.

## 5) Danh sách tài khoản test để xác nhận
Cần có tối thiểu 4 loại account:
- Agent thường có quyền chat.
- Agent không có quyền tiếp nhận chat (để test nhận request/chuyển chat).
- QA/Lead (nếu cần flow liên quan permission).
- Creator segment và non-creator segment (để test action sửa/xóa segment).

## 6) Bộ dữ liệu tối thiểu cần có sẵn
Để chạy đầy đủ P0/P1, cần chuẩn bị:
- Ít nhất 1 hội thoại đang ở `Processing`.
- Ít nhất 3 hội thoại ở `History` cho mỗi kênh chính:
  - MESSENGER
  - INSTAGRAM
  - ZALO
  - ZALO_PA
  - TIKTOK
  - LIVECHAT
- Ít nhất 2 segment:
  - Segment A có dữ liệu hội thoại.
  - Segment B rỗng (để test empty state).
- Ít nhất 1 hội thoại có:
  - unread > 0
  - tags
  - ticket info
  - nhiều session (để test load thêm lịch sử)

## 7) Kịch bản tạo dữ liệu nhanh (gợi ý)
- Dùng 2 tài khoản (Agent A, Agent B) + 1 khách hàng test.
- Thực hiện:
  - Gửi/nhận tin nhắn để tạo unread và last message updates.
  - Chuyển chat giữa agents để tạo packet transfer.
  - Đóng chat / rời group chat để tạo packet end/leave.
  - Tạo segment với điều kiện đơn giản, đảm bảo có ít nhất 1 kết quả match.

## 8) Cách reset trạng thái để retest
Trước mỗi vòng regression:
- Xóa key localStorage liên quan:
  - `chat_socket_packet_mode` (nếu muốn về mặc định).
  - các key custom config nếu cần.
- Reload trang.
- Chọn lại mode packet cần test.
- Đảm bảo đã về route `/chat`.

## 9) Dấu hiệu môi trường không sẵn sàng
Tạm dừng test và thông báo dev nếu gặp:
- `/chat` không mở được hoặc chuyển qua giao diện cũ dù đã bật flag.
- Socket disconnect liên tục, không nhận packet.
- API list/detail/segment trả lời lỗi 5xx hoặc timeout liên tục.
- Dữ liệu mới tạo không xuất hiện ở cả giao diện cũ và mới.
