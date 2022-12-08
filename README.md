# BoardAPI 명세서

https://documenter.getpostman.com/view/24658431/2s8YzRxhMR

# ERD 모델링
https://www.erdcloud.com/d/THKhsq4nuiEPT3wfk
<img width="1033" alt="image" src="https://user-images.githubusercontent.com/105099062/206415103-45d45782-e05b-448b-ac6f-b21b3b23cbd3.png">

## spring 2주차
1. 처음 설계한 API 명세서의 변경사항점
  * JWT 인증 하기 위해 조금 변경되었던 것 같고, param은 변하지 않았다.
2. ERD를 먼저 설계한 후, Entity를 개발했을 때 어떤 도움?
  * ERD를 처음 만들어봐서 만들기만 해놓고 잘 보지는 않았다..
3. JWT를 사용하여 인증/인가 구현 시, 장점?
  * URL param, header로 사용할 수 있다.
  * request 생성만 한다면, 누구나 서버로 요청할 수 있다. (stateless)
  * 저장소에 따로 저장하지 않기 때문에 세션 방식보다 상대적으로 서버에 부담이 덜함.
4. JWT의 한계점
  * 세션 방식은 악의적으로 사용될 경우, 해당 세션을 지우면 되지만, JWT는 만료 기간이 정해져있다.
  * Payload의 정보가 제한적이다 (중요한 정보를 넣을 수 없음.)
5. 댓글이 달려 있는 게시글을 삭제 시, 무슨 문제 발생? DB 관점에서 해결 방법
  * 🙄 cascade로 FK 값을 다 제거해준다..? (찾아봐야겠다)
6. 5 와 같은 문제 발생 시, JPA 어떻게 해결?
  * 연관관계 매핑 시, Cascade, Orphan 옵션을 사용하도록 한다!
7. Ioc, DI 에 대해 간략히 설명.
  * 사용자가 객체를 생성하는 것이 아닌, 스프링 IoC 컨테이너로부터 객체를 주입(생성자를 통해) 받는 것을 말한다.

### 해결하지 못한 것들.
* 게시글 당 댓글 목록을 불러오는 기능을 구현하지 못 함..
* 예외 처리가 엉성하게 되어 있음..
