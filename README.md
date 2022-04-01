# MatchEasy
MatchEasy는 '개발자 스터디 모집사이트'입니다.        🖥[사이트바로가기](http://www.matcheasy.site)

<br>

## 프로젝트 개요

* 개발 스터디 모집하는 사이트를 찾다가 보면 모집을 원하는 사람이 게시글을 올려놓고 해당 게시글에 카카오톡 오픈채팅 링크를 첨부하여 대화를 하고, 댓글을 이용할 때에도 요청자에게 오픈채팅방 링크를 보내주어 대화를 유도하는 경우가 대부분이었습니다. 

* 모집사이트 자체에서 채팅기능을 이용하여 서로 대화가 가능하고 요청을 통해 팀 구성을 하면 팀 프로젝트의 일정까지 관리해주는 사이트가 있으면 좋겠다는 생각에 ‘MatchEasy’라는 프로젝트를 기획하게 되었습니다.

<br>
    
## 주요 기능과 로직

1. 로그인 : JWT을 이용한 AccessToken, RefreshToken 발급

2. 게시글 상세 : JPA관계 매핑으로 여러 테이블의 데이터 Json으로 반환

3. 게시글 채팅 : STOMP프로토콜을 활용한 실시간 채팅기능

4. 나의 지원 관리 : 나의 요청목록 리스트 및 수락, 거절 상태 확인 

5. 지원자 관리 : 나의 게시글에 지원한 지원자 리스트 및 수락, 거절 기능

6. 나의 팀 관리 : 생성된 팀의 일정을 FullCalendar 라이브러리를 이용하여 등록, 삭제

<br>

## ERD
![image](https://user-images.githubusercontent.com/64828230/161278051-4dc18e71-3b9c-4284-a628-12dc9be9af8e.png)

<br>

## 사용방법

<br>

### 1. 로그인, 회원가입, 회원정보 수정  

<br>

* 회원가입 시 본인의 선호 포지션을 입력할 수 있고, 사용가능한 기술 스택을 여러 개 추가할 수 있습니다.
* 기술 스택은 추후 팀 모집 시 팀장이  보고 지원자를 판단하는 용도로 사용될 수 있습니다.

<br>

![image](https://user-images.githubusercontent.com/64828230/161278618-8971c0e0-dd59-4e96-94ec-9ac0592f4176.png)
![image](https://user-images.githubusercontent.com/64828230/161278631-afda5e99-4047-493d-a1ff-d8b71999db1f.png)

<br>
<br>


* 로그인 후, 메인 페이지에서 사이드바 메뉴를 클릭하면 회원정보 수정 페이지로 진입할 수 있습니다.
* 회원정보 수정을 완료하기 전에 비밀번호 확인을 필수로 해야 합니다. 

<br>

![image](https://user-images.githubusercontent.com/64828230/161278827-ae0be017-0466-4adf-b0f7-384c85491433.png)

<br>
<br>



* 비밀번호 확인을 완료하면 새로운 비밀번호를 등록하는 버튼이 생깁니다.
* 수정을 원한다면 새로운 비밀번호 등록 버튼을 눌러 새로운 비밀번호를 입력하고, 원하지 않는다면 완료버튼을 눌러서 수정을 완료합니다

<br>

![image](https://user-images.githubusercontent.com/64828230/161278892-4bd4c4e7-4d4d-4272-8d13-78858999057c.png)

<br>
<br>

---

### 2. 홈 화면 (게시글 등록 및 관리)

<br>

* 메인 화면에서는 게시글의 리스트를 볼 수 있고, 게시글을 등록할 수 있습니다.

<br>

![image](https://user-images.githubusercontent.com/64828230/161278969-8dd963ca-0326-47d4-930c-43574ae1429f.png)
![image](https://user-images.githubusercontent.com/64828230/161278979-698b05ad-45d8-4d97-89a7-76e098781e52.png)

<br>
<br>

---

### 3. 게시글 세부화면 

<br>

* 모집 포지션을 볼 수 있고, 원하는 포지션에 지원할 수 있습니다.
* 궁금한 사항에 대해서는 실시간 채팅을 이용하여 게시글 등록자와 채팅을 할 수 있습니다.

<br>

![image](https://user-images.githubusercontent.com/64828230/161279065-1e8d6e49-bec2-47a3-8b4f-221df9e04bab.png)

<br>
<br>

---

### 4. 나의 지원 관리

<br>

* 내가 모집 공고에 지원한 리스트들을 볼 수 있습니다.

<br>

![image](https://user-images.githubusercontent.com/64828230/161279106-0b01c2b2-cc01-427d-b94b-dcf082b5b760.png)

<br>
<br>

---

### 5. 지원자 관리
<br>

* 내가 등록한 게시글에 지원한 지원자 목록을 확인할 수 있습니다
* 수락, 거절을 할 수 있고 수락이 완료된 인원들로 구성된 팀을 생성할 수 있습니다.

<br>

![image](https://user-images.githubusercontent.com/64828230/161279185-93f8b262-fb6c-4921-8ed9-ac3dc720c430.png)

<br>
<br>

---

### 6. 나의 팀 관리

<br>

* 달력의 일자를 클릭하거나 일정을 등록할 수 있고, 등록된 일정을 클릭하여 삭제할 수 있습니다.
* 일정 완료설정에서 완료된 일정을 완료처리 할 수 있습니다.

<br>

![image](https://user-images.githubusercontent.com/64828230/161279270-afb24a75-67dd-4893-a014-c09dec1d2105.png)

<br>
<br>




## 주요기술

<br>




### 1. JWT Refresh Token을 이용한 인증과정

<br>

* AccessToken만을 이용하여 인증체크를 할 경우, 탈취되면 회원정보의 중요한 정보가 노출될 수 있고 한번 탈취된 토큰을 추후에도 사용하여 악용할 수 있다는 치명적인 단점이 존재합니다. 저는 프로젝트를 제작하면서 이러한 단점을 개선해보고자 RefreshToken을 도입하였습니다. RefreshToken은 AccessToken을 재발급 해주는 용도로 사용이 되며 AccessToken의 유효기간은 30분으로 짧게, RefreshToken의 유효기간은 2주로 길게 설정해주었습니다. RefreshToken을 사용하므로써 AccessToken을 탈취당하여도 30분이 지나면 이미 사용할 수 없는 상태가 되기 때문에 안전하였고 혹시라도 RefreshToken을 탈취당하여도 단순 AccessToken의 재발급 용도로만 사용되기 때문에 큰 이슈가 없다는 장점을 얻게 되었습니다.


![image](https://user-images.githubusercontent.com/64828230/161280074-7c7ce564-3287-4326-b247-7e2b91f5bd02.png)

<br>


### 2. JPA

<br>

* Java ORM 표준인 JPA를 도입하여 객체 중심의 Domain설계를 하였고, 비즈니스 로직을 만드는 데에 초점을 두었습니다. 
 예전에 만들었던 프로젝트에서는 SQL Mapper인 Mybatis를 사용하였습니다. Mybatis를 사용하면서 느낀점은 데이터를 생성, 수정, 조회, 삭제할 때 Mapper에 써야하는 코드가 계속 중복되었고, DB에 너무 종속적인 것 같았습니다. 그래서 조금 더 객체지향적인 코드를 위해서 JPA를 도입하게 되었습니다.
 그 결과 개발하면서 ERD를 보는 의존도가 현저히 낮아졌고, 코드의 재활용성이 증가하여 반복적인 코드의 사용이 줄어 들었으며 fetch Join을 통해서 원하는 테이블의 데이터만을 Join하여 SQL의 성능도 높였습니다. 
 JPA를 사용하면서 얻는 장점이 많았지만 복잡한 조회 쿼리를 사용할 때에는 JPQL역시 복잡해질 수 있다는 것을 느꼈고, 원치 않는 쿼리가 많이 나오기 때문에 성능을 최적화하는 것이 가장 중요할 것 같다고 생각했습니다.

<br>

### 3. MapStruct

<br>

* MapStruct는 객체 간의 매핑에 대한 코드를 자동으로 생성해주는 라이브러리 입니다.
 저는 이번 프로젝트를 만들면서 효율적인 계층 간의 데이터 이동에 대해서 고민했습니다. 소규모 프로젝트라는 이유로 Controller에서 DTO를 사용하지 않고 View로부터 넘어온 요청을 해석하여 Service단에서 Entity를 직접 업데이트하거나 Entity로부터 데이터를 View로 다시 전달하는 식의 로직을 구성했었습니다. 그러나 이는 노출 되어서는 안되는 Entity(Model)의 데이터의 속성이 외부에 노출되어 보안문제가 생기거나, Entity의 상태를 변경시켜 데이터에 혼란을 줄 수 있다는 위험이 존재한다는 것을 알게 되었습니다.
 Controller(Presentation Layer)와 Service(Business Layer), Repository(Data Access Layer)로 구성된 계층간의 데이터 이동에는 DTO를 사용하였습니다. 비즈니스 로직이 모두 담겨있는 Service단에서 MapStruct를 이용하여 Repository계층으로 이용할 때에는 DTO를 Entity로, 데이터를 다시 Controller계층으로 반환할 때에는 Entity를 DTO로 변환해주는 코드를 작성했습니다. 



<br>

### 4. STOMP

<br>

* 프로젝트를 제작하면서 채팅기능을 넣어 사용자 간의 실시간 통신을 할 수 있도록 하였습니다. WebSocket 위에서 동작하는 문자 기반 메세징 프로토콜인 STOMP를 도입하였습니다. STOMP는 pub/sub 구조로 되어 있어서 메시지를 전송, 수신이 명확하다는 장점이 있습니다. 메시지 브로커를 만들어서 채팅방에 입장하면 채팅방의 Id로 구분하여 publisher(송신자)가 채팅방을 바라볼 수 있도록 하고 메시지를 보내면 subscriber(수신자)에게 전달해 줄 수 있도록 만들었습니다.
 WebSocket을 처음 프로젝트에 도입해보아서 원래 Spring에서 제공하는 WebSocket API를 사용하려고 계획했으나, 각 커넥션마다 WebSocketHandler를 구현해야 했고 메시지 형식을 커스터마이징 할 필요없는 STOMP를 채택하였습니다. 이는 이번 프로젝트에서 WebSocket에 대해서 공부를 하게 된 계기가 되었습니다.
 
 
 
<br>

 ### 5. Custom Exception
 
<br>

* Java에서 제공하는 표준 예외인 ‘IllegalArgumentException’을 메시지와 예외사항에 맞게 사용한다면 원하는 예외처리를 할 수 있을 것입니다. 그러나 이는 HTTP Status에 맞는 정형화된 메시지만 출력할 뿐 제가 원하는 Status에 message를 전달하기는 어려웠습니다. 저는 이번 프로젝트를 제작하면서 상황에 맞는 예외처리와 메시지를 전달하기 위하여 사용자 정의 예외를 사용해 보았습니다. 사용자 정의 예외를 사용하면 클래스의 이름만으로 어떤 상황의 예외인지 유추가 가능합니다. 이는 추후 리팩토링이나 유지보수에 매우 효과적일 것이라는 생각이 들었습니다. 또 한 예외에 필요한 메시지, 전달할 정보의 데이터, 데이터 가공 메소드들을 한 곳에서 관리할 수 있기 때문에 책임이 분리된 객체 지향적인 코드가 될 수 있습니다.



<br>

 ### 6. RESTful API
 
<br>

* 이번 프로젝트는 RESTful API로 구성하였습니다. 규모가 작은 프로젝트이기 때문에 Spring Template Engine인 Thymeleaf를 사용하는 것으로 처음 설계를 하였습니다. 전반적인 HTTP의 흐름을 공부하면서 서버와 클라이언트의 역할을 명확하게 분리시키고 싶었고 추후 프로젝트의 확장성을 고려하여 SSR이 아닌 BackEnd와 FrontEnd가 구분된 RESTful API 방식의 프로젝트를 제작하게 되었습니다.






