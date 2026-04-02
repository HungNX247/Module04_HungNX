# 05 - Regression Checklist ChatModule
Cập nhật: 2026-04-02

## 1) Thông tin run
- Build version:
- Packet mode:
- Tester:
- Ngày test:

## 2) Smoke checklist (bắt buộc)
- [ ] Bật `chat_module_enable=1`, vào `/chat` lên đúng giao diện ChatModule.
- [ ] Processing list hiện dữ liệu và auto select item đầu nếu chưa có selected.
- [ ] Gửi text message thành công, không duplicate message.
- [ ] Realtime packet message cập nhật preview + timestamp đúng.
- [ ] Seen/unseen cập nhật đúng unread.
- [ ] End conversation remove khỏi Processing và select next đúng.
- [ ] History list load dữ liệu thành công ở kênh ALL.
- [ ] Mở detail từ History thành công.
- [ ] Detail load more prepend đúng thứ tự, không trùng message.
- [ ] Segment list load dữ liệu thành công.
- [ ] Tạo segment thành công.
- [ ] Xóa segment đang active fallback về History đúng.

## 3) Filter/Paging checklist
- [ ] History search reset page/cursor đúng.
- [ ] History sort reset page/cursor đúng.
- [ ] History quick filter hoạt động đúng.
- [ ] History advanced filter hoạt động đúng.
- [ ] Segment search/sort/filter có hành vi tương đương History.
- [ ] Hard refresh History clear stale runtime data đúng.
- [ ] Hard refresh Segment clear stale runtime data đúng.

## 4) Selection/Navigation checklist
- [ ] Chuyển panel qua lại giữ đúng selected theo panel.
- [ ] Chuyển channel History giữ selected riêng theo section.
- [ ] Chuyển segment X/Y giữ selected riêng theo segment section.
- [ ] Reload URL với `panel=history&channel=...` restore đúng.
- [ ] Reload URL với `panel=segment&segment_id=...` restore đúng.

## 5) Realtime/WebSocket checklist
- [ ] Typing on/off hiện đúng, timeout đúng.
- [ ] Transfer packet không làm crash list/detail.
- [ ] Group invite/request queue cập nhật đúng.
- [ ] Sau reconnect/resume, processing snapshot đồng bộ đúng.

## 6) 3-mode checklist
- [ ] P0 pass ở `legacy-only`.
- [ ] P0 pass ở `dual-run`.
- [ ] P0 pass ở `v2-only`.

## 7) Final gate
- [ ] Không còn bug blocker.
- [ ] Không còn bug critical.
- [ ] Đã cập nhật bug list + bằng chứng (video/screenshot/log).
- [ ] Đã thông báo kết quả regression cho dev lead.
