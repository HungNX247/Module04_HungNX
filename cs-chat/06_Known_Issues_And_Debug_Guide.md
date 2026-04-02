# 06 - Known Issues và Debug Guide
Cập nhật: 2026-04-02

## 1) Mục tiêu
Tài liệu này giúp tester:
- Biết trước các điểm rủi ro/known issues đã ghi nhận.
- Thu thập đúng thông tin khi báo lỗi cho dev.
- Rút ngắn thời gian reproduce và khoanh vùng root cause.

## 2) Known issues hiện tại (cần theo dõi khi test)
### KI-001 - Transfer request handler context có dấu hiệu không đồng nhất
- Khu vực: `src/websocket/chat/router/chatPacketRouter.js`
- Mô tả ngắn: gọi helper queue với context shape không đồng nhất (`dispatch` object vs function).
- Rủi ro: packet transfer request có thể cập nhật queue không đúng trong một số flow.

### KI-002 - Response transfer result check nhầm trường dữ liệu
- Khu vực: `src/websocket/chat/router/chatPacketRouter.js`
- Mô tả ngắn: handler có dấu hiệu check `packetType.res` thay vì `packetData.res`, và tham chiếu biến chưa rõ nguồn.
- Rủi ro: branch accept/fail transfer có thể đi sai.

### KI-003 - Close action ChatModule còn luồng legacy
- Khu vực: `src/views/features/App/ChatModule/components/ChatModuleCloseChatAction.js`
- Mô tả ngắn: import action mới nhưng flow runtime vẫn có đường dẫn legacy.
- Rủi ro: kết quả đóng chat không đồng nhất giữa mode packet.

### KI-004 - Client service end conversation có dấu hiệu unresolved identifier
- Khu vực: `src/views/features/App/ChatModule/clientService/client.service.js`
- Mô tả ngắn: có reference tên hàm/biến cần đối chiếu lại.
- Rủi ro: một nhánh kết thúc hội thoại có thể lỗi trong điều kiện đặc thù.

## 3) Các state path cần chụp khi bug
Nếu gặp lỗi, ưu tiên capture state tại các path sau trong Redux DevTools:
- `chatModule.ui`
- `chatModule.indexes.processingList`
- `chatModule.indexes.historySections`
- `chatModule.indexes.segmentSections`
- `chatModule.indexes.threads`
- `chatModule.entities.conversations.entities`
- `chatModule.entities.messages.entities`
- `chatModule.entities.chatRequests.entities`
- `chatModule.entities.groupChatRequests.entities`

## 4) Các action/event cần note khi reproduce
Ghi lại action sequence (nếu có) quanh thời điểm lỗi:
- `chatModule/setActivePanel`
- `chatModule/setVisibleHistorySectionKey`
- `chatModule/setVisibleSegmentSectionKey`
- `chatModule/receiveConversationList`
- `chatModule/receiveConversationDetail`
- `chatModule/syncProcessingSnapshot`
- `chatModule/receiveSocketMessage`
- `chatModule/clearConversationRuntimeData`
- Action prefix `chatSocket/*` liên quan packet

## 5) Mẫu bug report chuẩn
Dùng mẫu này khi tạo bug ticket:
- Tiêu đề:
  - `[ChatModule][panel/mode] Mô tả ngắn lỗi`
- Môi trường:
  - Build:
  - Packet mode:
  - Account:
  - Browser:
- Bước tái hiện:
  1.
  2.
  3.
- Kết quả thực tế:
- Kết quả mong đợi:
- Tần suất:
  - 100% / ngẫu nhiên / 1 lần
- Attach:
  - Video
  - Screenshot
  - Redux state snapshot
  - Network/socket log (nếu có)

## 6) Cách khoanh vùng nhanh theo triệu chứng
### Triệu chứng A - Danh sách đúng nhưng detail sai
- Kiểm tra:
  - `selectedConversationByPanel`
  - `visibleHistorySectionKey` / `visibleSegmentSectionKey`
  - `indexes.threads[conversationKey]`
- Nghi ngờ:
  - sai restore selection theo panel/section
  - stale runtime data chưa được clear

### Triệu chứng B - Unread không đúng sau realtime
- Kiểm tra:
  - `entities.conversations[conversationKey].unreadCount`
  - action seen/unseen có được dispatch
  - packet mode đang chạy
- Nghi ngờ:
  - packet bridge middleware và v2 handler lệch branch

### Triệu chứng C - Load more bị trùng/mất message
- Kiểm tra:
  - `thread.messageIds`
  - `thread.nextCursor`, `thread.hasMoreOlder`
  - mode query (`initial`/`more`)
- Nghi ngờ:
  - prepend merge logic
  - cursor stale

### Triệu chứng D - Segment CRUD xong nhưng UI không đổi
- Kiểm tra:
  - request/response endpoint segment
  - cache invalidation tag `ChatSegment`
  - `visibleSegmentSectionKey` và URL `segment_id`
- Nghi ngờ:
  - invalidation không kick fetch
  - đang ở segment vừa xóa mà chưa fallback

## 7) Rule handoff bug cho dev
- Nếu blocker/critical: ping ngay trong ngày, kèm video + state snapshot.
- Nếu major/minor: gom theo nhóm feature (Processing/History/Segment/Realtime).
- Mỗi bug phải có packet mode để dev có thể đối chiếu nhanh.
