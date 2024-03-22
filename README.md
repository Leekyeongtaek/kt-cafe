# 프로젝트 주제
투썸 플레이스에서 키오스크를 사용해보고 프로젝트로 만들어보면 좋을 것 같다고 생각되어 카페 주문을 주제로 선정하게 됐습니다.<br>
주문 도메인의 주문 등록, 유효성 검사 부분은 조용호 님의 우아한 객체지향 유튜브를 참고하여 만들었습니다.<br>
[우아한 객체지향 링크](https://www.youtube.com/watch?v=dJ5C4qRqAgA&ab_channel=%EC%9A%B0%EC%95%84%ED%95%9C%ED%85%8C%ED%81%AC)

# 프로젝트 상세
Jdk : 1.7 <br>
SpringBoot : 3.1.5 <br>
MySql : 8.0.32 <br>
QueryDsl : 5.0.0 <br>

# 도메인 모델
[도메인 다이어그램](https://github.com/Leekyeongtaek/kt-cafe/issues/1#issue-2054730088)

# 데이터베이스 ERD 링크
[ERD 클라우드](https://www.erdcloud.com/u/lkt900520@gmail.com)

# 기능 목록
|상품|주문/결제|
|------|---|
|등록|등록(할인 정책)|
|수정|결제|
|상세조회|취소|
|목록 검색|목록 검색|
|옵션 그룹 삭제|상세조회|
|옵션 삭제|리뷰 등록|
||리뷰 수정|
||리뷰 목록 조회|

# 요구사항
1. 할인 정책은 주문 등록시 조건에 따라 자동 적용되며 오전할인, 요일별 상품타입 할인이 존재하고 오전 할인이 우선 적용된다.(할인된 금액은 payment 테이블의 discountAmount 필드에 기록)
3. 오전 할인은 주문 시간이 AM 08:00 ~ 10:00 주문에 한하여 옵션을 제외한 주문상품 총금액의 20%를 할인해준다.
4. 요일별 할인은 주문 상품 목록에서 해당되는 상품 타입이 있다면 옵션 가격을 제외한 (상품 금액 * 개수)만큼 할인해준다. 월,금(커피:10%),화(케이크:5%),수(음료:5%),목(빵:7%)
5. 상품명은 중복될 수 없다.
6. 상품별 상품 옵션 그룹명은 중복될 수 없다.
7. 상품 옵션은 최초 등록 후에는 1개 이하로 삭제할 수 없다. 상품 옵션을 전체삭제 하려면 상품 옵션 그룹을 삭제해야 한다.
8. 주문의 상태가 주문완료인 경우만 결제요청이 가능하다.
9. 주문의 상태가 주문완료인 경우만 결제취소 요청이 가능하다.
11. 리뷰 등록은 주문의 상태가 결제완료인 경우만 가능하다.
12. 리뷰는 주문당 한 개만 등록이 가능하다.
13. 주문 목록의 검색시 주문한 상품명을 통한 검색이 가능하다.
