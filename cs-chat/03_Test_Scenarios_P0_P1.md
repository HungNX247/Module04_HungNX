# 03 - Test Scenarios (P0 / P1)
Cập nhật: 2026-04-02

## 1) Quy ước ưu tiên
- P0: Bắt buộc pass trước release.
- P1: Quan trọng, cần pass để đảm bảo ổn định nghiệp vụ.

## 2) Scenarios P0
### CHM-P0-001 - Mở ChatModule thành công
- Given: `chat_module_enable=1`.
- When: vào route `/chat`.
- Then:
  - Hiện giao diện ChatModule.
  - Có sidebar categories (`Processing`, `History`, `Segment`).

### CHM-P0-002 - Processing auto select item đầu
- Given: panel `Processing` có >= 1 hội thoại.
- When: mở panel `Processing` khi chưa có item được chọn.
- Then:
  - Hội thoại đầu danh sách được select.
  - Detail pane hiện đúng thông tin hội thoại được chọn.

### CHM-P0-003 - Realtime nhận tin nhắn mới
- Given: đang mở `/chat`, websocket connected.
- When: có packet message tới hội thoại đang tồn tại.
- Then:
  - Preview/last message cập nhật.
  - Danh sách `Processing` reorder đúng logic.
  - Unread/seen cập nhật đúng theo ngữ cảnh đang mở hay không mở detail.

### CHM-P0-004 - Gửi tin nhắn từ detail
- Given: đang mở detail 1 hội thoại ở `Processing`.
- When: gửi text message.
- Then:
  - Message optimistic hiện ngay.
  - Khi ACK về, state message cập nhật đúng (không nhân đôi message).
  - Last message và timestamp cập nhật đúng.

### CHM-P0-005 - Seen/unseen đồng bộ
- Given: hội thoại có unread > 0.
- When: mark seen hoặc unseen qua action/menu.
- Then:
  - Unread count cập nhật đúng trên list item.
  - Detail và list không bị lệch trạng thái.

### CHM-P0-006 - Đóng hội thoại (end conversation)
- Given: hội thoại đang nằm trong `Processing`.
- When: thực hiện action đóng hội thoại.
- Then:
  - Hội thoại bị remove khỏi `Processing`.
  - Hệ thống select hội thoại tiếp theo đúng quy tắc.
  - Runtime data cũ không bị leak sang hội thoại khác.

### CHM-P0-007 - History list fetch + open detail
- Given: panel `History`.
- When: chọn 1 channel và click 1 hội thoại.
- Then:
  - Danh sách load dữ liệu thành công.
  - Detail load session/message thành công.
  - Thứ tự message không bị đảo lộn.

### CHM-P0-008 - Detail load thêm lịch sử (cursor)
- Given: detail có `hasMoreOlder=true`.
- When: bấm load thêm.
- Then:
  - Session/message cũ được prepend đúng thứ tự.
  - Không trùng message/session.
  - Scroll restore giữ vị trí hợp lý.

### CHM-P0-009 - Segment list fetch + open detail
- Given: có segment có dữ liệu.
- When: chọn segment trong sidebar.
- Then:
  - URL có `panel=segment&segment_id=...`.
  - List segment load đúng.
  - Mở detail thành công như history flow.

### CHM-P0-010 - Tạo segment mới
- Given: mở drawer tạo segment.
- When: nhập tên + điều kiện hợp lệ và submit.
- Then:
  - API update segment thành công.
  - Segment mới hiện ở sidebar.
  - Chọn vào segment mới có thể load list.

### CHM-P0-011 - Sửa segment
- Given: user là creator của segment.
- When: sửa tên/icon/điều kiện và submit.
- Then:
  - Segment cập nhật thành công.
  - Sidebar và panel phản ánh dữ liệu mới.

### CHM-P0-012 - Xóa segment đang active
- Given: đang mở panel segment của Segment X.
- When: xóa Segment X thành công.
- Then:
  - Hệ thống fallback về panel `History`.
  - URL bỏ `segment_id`.
  - Không bị crash/treo detail pane.

## 3) Scenarios P1
### CHM-P1-001 - Filter quick/advanced ở History
- Given: panel `History`.
- When: áp dụng quick filter + advanced filter.
- Then:
  - Danh sách thay đổi đúng điều kiện.
  - Counter filter hoạt động đúng.

### CHM-P1-002 - Search + Sort ở History
- Given: panel `History`.
- When: nhập keyword và đổi sort (`newest`/`oldest`).
- Then:
  - Query mới được gửi.
  - Danh sách và paging reset đúng.

### CHM-P1-003 - Hard refresh History
- Given: đang ở panel `History`, có data cũ.
- When: bấm refresh.
- Then:
  - Section reset về page 1.
  - Cursor reset đúng.
  - Runtime data stale được clear theo batch.

### CHM-P1-004 - Filter/search/sort ở Segment
- Given: panel `Segment`.
- When: thao tác filter/search/sort.
- Then:
  - Hành vi tương đương History.
  - Cursor/page reset đúng.

### CHM-P1-005 - Chuyển panel giữ memory selection
- Given: đã chọn hội thoại riêng cho `Processing`, `History`, `Segment`.
- When: chuyển qua lại các panel.
- Then:
  - Mỗi panel nhớ đúng hội thoại đã chọn trước đó.

### CHM-P1-006 - Chuyển channel History giữ memory theo section
- Given: đã chọn hội thoại A ở channel 1, hội thoại B ở channel 2.
- When: đổi qua lại channel 1/2.
- Then:
  - Mỗi channel restore đúng selected conversation riêng.

### CHM-P1-007 - URL restore state sau reload
- Given: URL đang là segment/history mode có params đầy đủ.
- When: reload trang.
- Then:
  - Panel + channel/segment được restore đúng.
  - Không về sai panel mặc định.

### CHM-P1-008 - Typing indicator
- Given: đang mở hội thoại.
- When: nhận packet typing và stop typing.
- Then:
  - Badge/indicator typing hiện ẩn đúng timeout.
  - Không bị stuck typing vô hạn.

### CHM-P1-009 - Transfer chat packets
- Given: có flow chuyển chat giữa agents.
- When: receive packet transfer request/result.
- Then:
  - Queue request và processing list cập nhật đúng.
  - Không tạo duplicate conversation key.

### CHM-P1-010 - Test 3 packet modes
- Given: cùng bộ case P0.
- When: chạy lần lượt ở `legacy-only`, `dual-run`, `v2-only`.
- Then:
  - Không lỗi nghiệp vụ quan trọng.
  - Kết quả không lệch nghiêm trọng giữa các mode.

## 4) Mẫu kết quả testcase
Mỗi testcase nên lưu:
- Build version.
- Packet mode.
- Account đang test.
- Screenshot/recording nếu fail.
- Redux action/state mốc thời điểm lỗi (nếu có).
