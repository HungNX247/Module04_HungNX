# 04 - Test Matrix ChatModule
Cập nhật: 2026-04-02

## 1) Mục tiêu
Matrix này dùng để đảm bảo tester không bỏ sót tổ hợp quan trọng của ChatModule.

## 2) Trục matrix cần phủ
- Trục A: Panel
  - `Processing`
  - `History`
  - `Segment`
- Trục B: Kênh
  - `ALL`
  - `MESSENGER`
  - `INSTAGRAM`
  - `ZALO`
  - `ZALO_PA`
  - `TIKTOK`
  - `LIVECHAT`
- Trục C: Packet mode
  - `legacy-only`
  - `dual-run`
  - `v2-only`
- Trục D: Loại hội thoại
  - 1-1 chat
  - group chat/invite
  - incoming request

## 3) Mức độ test theo vòng
### Vòng Smoke (30-60 phút)
- Packet mode: `dual-run`.
- Chạy case:
  - P0-001 -> P0-009
  - P0-012
- Kênh bắt buộc:
  - `ALL`
  - `LIVECHAT`
  - 1 kênh Meta (`MESSENGER` hoặc `INSTAGRAM`)

### Vòng Full Regression
- Packet mode: chạy đầy đủ 3 mode.
- Chạy toàn bộ P0 + P1.
- Kênh bắt buộc:
  - Tất cả kênh có quyền trên tài khoản.

## 4) Bảng matrix tối thiểu cần tick
| ID | Panel | Kênh/Segment | Packet mode | Loại hội thoại | Bắt buộc |
|---|---|---|---|---|---|
| MX-001 | Processing | LIVECHAT | dual-run | 1-1 | Yes |
| MX-002 | Processing | MESSENGER/INSTAGRAM | dual-run | 1-1 | Yes |
| MX-003 | Processing | LIVECHAT | v2-only | 1-1 | Yes |
| MX-004 | Processing | LIVECHAT | legacy-only | 1-1 | Yes |
| MX-005 | History | ALL | dual-run | 1-1 | Yes |
| MX-006 | History | Từng kênh có quyền | dual-run | 1-1 | Yes |
| MX-007 | History | LIVECHAT | v2-only | 1-1 | Yes |
| MX-008 | Segment | Segment có data | dual-run | 1-1 | Yes |
| MX-009 | Segment | Segment rỗng | dual-run | - | Yes |
| MX-010 | Segment | Segment có data | v2-only | 1-1 | Yes |
| MX-011 | Processing | LIVECHAT | dual-run | incoming request | Yes |
| MX-012 | Processing | LIVECHAT | dual-run | group invite | Yes |
| MX-013 | Processing -> History | ALL | dual-run | end conversation | Yes |
| MX-014 | Processing -> Segment | Segment active | dual-run | selection memory | Yes |

## 5) Matrix cho Segment CRUD
| ID | Role | Action | Kỳ vọng |
|---|---|---|---|
| SG-001 | Creator | Tạo segment | Tạo thành công, hiện sidebar |
| SG-002 | Creator | Sửa segment | Dữ liệu cập nhật đúng |
| SG-003 | Creator | Xóa segment | Xóa thành công, fallback khi đang active |
| SG-004 | Non-creator | Hover row action | Không hiện action sửa/xóa |

## 6) Matrix cho Detail Paging
| ID | Panel nguồn | hasMoreOlder | Action | Kỳ vọng |
|---|---|---|---|---|
| DT-001 | Processing | true | Load more | Prepend đúng thứ tự |
| DT-002 | History | true | Load more | Prepend đúng thứ tự |
| DT-003 | Segment | true | Load more | Prepend đúng thứ tự |
| DT-004 | History | false | Load more | Không hiện/không trigger sai |

## 7) Matrix cho Selection Memory
| ID | Tình huống | Kỳ vọng |
|---|---|---|
| SM-001 | Chuyển Processing -> History -> Processing | Nhớ selected của Processing |
| SM-002 | Chuyển History channel A -> B -> A | Nhớ selected riêng theo section |
| SM-003 | Chuyển Segment X -> Y -> X | Nhớ selected riêng theo segment section |
| SM-004 | Reload với URL panel=segment&segment_id | Restore đúng panel/segment |

## 8) Rule đánh dấu completion
- 1 dòng matrix được xem là done khi:
  - Đã chạy testcase liên quan.
  - Đã ghi kết quả pass/fail.
  - Nếu fail đã có bug ticket link.
