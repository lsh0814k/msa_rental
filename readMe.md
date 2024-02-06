#도서 대여 서비스
## 대여 MS
    - 회원 MS : https://github.com/lsh0814k/msa_member
    - 도서 MS : https://github.com/lsh0814k/msa_book
    - 베스트셀러 MS : https://github.com/lsh0814k/msa_bestBook
### 프로젝트 구성
    - Java 17
    - Spring boot 3.2.2
    - Spring Data JPA
    - H2
    - kafka
### 아키텍쳐
    - 핵사고날 아키텍쳐
### 기능
    도서를 대여하고 반납하는 기능을 담당
        1. 도서 대여
            1-1 kafka producer를 이용하여 메세지 전송
                1-1-a 포인트 적립
                1-1-b 베스트 셀러 등록 및 대여 count 증가
                1-1-c 도서 상태를 대여 불가능 상태로 변경
        2. 도서 반납
            2-1 kafka producer를 이용하여 메세지 전송
                2-1-a 포인트 적립
                2-1-b 도서 상태를 대여 가능 상태로 변경
        3. 연체 해제
            3-1 대여 상태를 대여 가능으로 변경
            3-2 kafka producer를 이용하여 메세지 전송
                3-2-a 포인트 차감
### 참조
    MSA 실습
    인프런 한정헌님 강의를 통해 작성된 코드 입니다.
    https://www.inflearn.com/course/eda%EA%B8%B0%EB%B0%98-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%EA%B5%AC%ED%98%84/dashboard
