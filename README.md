# MatchEasy
개발자 스터디 모집 프로젝트



## 프로젝트 개요

* 개발 스터디 모집하는 사이트를 찾다가 보면 모집을 원하는 사람이 게시글을 올려놓고 해당 게시글에 카카오톡 오픈채팅 링크를 첨부하여 대화를 하고, 댓글을 이용할 때에도 요청자에게 오픈채팅방 링크를 보내주어 대화를 유도하는 경우가 대부분이었습니다. 

* 모집사이트 자체에서 채팅기능을 이용하여 서로 대화가 가능하고 요청을 통해 팀 구성을 하면 팀 프로젝트의 일정까지 관리해주는 사이트가 있으면 좋겠다는 생각에 ‘MatchEasy’라는 프로젝트를 기획하게 되었습니다.


    
## 주요 기능과 로직

1. 로그인 : JWT을 이용한 AccessToken, RefreshToken 발급

2. 게시글 상세 : JPA관계 매핑으로 여러 테이블의 데이터 Json으로 반환

3. 게시글 채팅 : STOMP프로토콜을 활용한 실시간 채팅기능

4. 나의 지원 관리 : 나의 요청목록 리스트 및 수락, 거절 상태 확인 

5.지원자 관리 : 나의 게시글에 지원한 지원자 리스트 및 수락, 거절 기능

6.나의 팀 관리 : 생성된 팀의 일정을 FullCalendar 라이브러리를 이용하여 등록, 삭제



## ERD
![image](https://user-images.githubusercontent.com/64828230/161278051-4dc18e71-3b9c-4284-a628-12dc9be9af8e.png)



## 사용방법

### 1. 로그인, 회원가입, 회원정보 수정  

* 회원가입 시 본인의 선호 포지션을 입력할 수 있고, 사용가능한 기술 스택을 여러 개 추가할 수 있습니다.
* 기술 스택은 추후 팀 모집 시 팀장이  보고 지원자를 판단하는 용도로 사용될 수 있습니다.
![image](https://user-images.githubusercontent.com/64828230/161278618-8971c0e0-dd59-4e96-94ec-9ac0592f4176.png)
![image](https://user-images.githubusercontent.com/64828230/161278631-afda5e99-4047-493d-a1ff-d8b71999db1f.png)

* 로그인 후, 메인 페이지에서 사이드바 메뉴를 클릭하면 회원정보 수정 페이지로 진입할 수 있습니다.
* 회원정보 수정을 완료하기 전에 비밀번호 확인을 필수로 해야 합니다. 
![image](https://user-images.githubusercontent.com/64828230/161278827-ae0be017-0466-4adf-b0f7-384c85491433.png)

* 비밀번호 확인을 완료하면 새로운 비밀번호를 등록하는 버튼이 생깁니다.
* 수정을 원한다면 새로운 비밀번호 등록 버튼을 눌러 새로운 비밀번호를 입력하고, 원하지 않는다면 완료버튼을 눌러서 수정을 완료합니다
![image](https://user-images.githubusercontent.com/64828230/161278892-4bd4c4e7-4d4d-4272-8d13-78858999057c.png)


### 2. 홈 화면 (게시글 등록 및 관리)

* 메인 화면에서는 게시글의 리스트를 볼 수 있고, 게시글을 등록할 수 있습니다.
![image](https://user-images.githubusercontent.com/64828230/161278969-8dd963ca-0326-47d4-930c-43574ae1429f.png)
![image](https://user-images.githubusercontent.com/64828230/161278979-698b05ad-45d8-4d97-89a7-76e098781e52.png)


### 3. 게시글 세부화면 
* 모집 포지션을 볼 수 있고, 원하는 포지션에 지원할 수 있습니다.
* 궁금한 사항에 대해서는 실시간 채팅을 이용하여 게시글 등록자와 채팅을 할 수 있습니다.
![image](https://user-images.githubusercontent.com/64828230/161279065-1e8d6e49-bec2-47a3-8b4f-221df9e04bab.png)


### 4. 나의 지원 관리
* 내가 모집 공고에 지원한 리스트들을 볼 수 있습니다.
![image](https://user-images.githubusercontent.com/64828230/161279106-0b01c2b2-cc01-427d-b94b-dcf082b5b760.png)




### 5. 지원자 관리
* 내가 등록한 게시글에 지원한 지원자 목록을 확인할 수 있습니다
* 수락, 거절을 할 수 있고 수락이 완료된 인원들로 구성된 팀을 생성할 수 있습니다.
![image](https://user-images.githubusercontent.com/64828230/161279185-93f8b262-fb6c-4921-8ed9-ac3dc720c430.png)




### 6. 나의 팀 관리 
* 달력의 일자를 클릭하거나 일정을 등록할 수 있고, 등록된 일정을 클릭하여 삭제할 수 있습니다.
* 일정 완료설정에서 완료된 일정을 완료처리 할 수 있습니다.
![image](https://user-images.githubusercontent.com/64828230/161279270-afb24a75-67dd-4893-a014-c09dec1d2105.png)




# 4. 이텔릭체(기울어진 글씨) 작성
*텍스트*

# 5. 굵은 글씨 작성
**텍스트**

# 6. 인용
> 인용1

> 인용2
>> 인용안의 인용

# 7. 수평선 넣기

---
  
# 8. 링크 달기
(1) 인라인 링크  

[블로그 주소](https://lsh424.tistory.com/)

(2) 참조 링크  

[블로그 주소][blog]

[blog]: https://lsh424.tistory.com/

# 9. 이미지 추가하기
![이탈리아 포지타노](https://user-images.githubusercontent.com/31477658/85016059-f962aa80-b1a3-11ea-8c91-dacba2666b78.jpeg)

### 이미지 사이즈 조절
<img src="https://user-images.githubusercontent.com/31477658/85016059-f962aa80-b1a3-11ea-8c91-dacba2666b78.jpeg"  width="700" height="370">

### 이미지 파일로 추가하기
<img src="Capri_Island.jpeg" width="700">

# 10. 코드블럭 추가하기

```swift
public struct CGSize {
  public var width: CGFloat
  public var heigth: CGFloat
  ...
}
```

# etc

**텍스트 굵게**  
~~텍스트 취소선~~

### [개행]  

스페이스바를 통한 문장개행  
스페이스바를 통한 문장개행  

br태그를 사용한 문장개행
<br>
<br>
br태그를 사용한 문장개행


### [체크박스]

다음과 같이 체크박스를 표현 할 수 있습니다. 
* [x] 체크박스
* [ ] 빈 체크박스
* [ ] 빈 체크박스

### [이모지 넣기]
❤️💜💙🤍

### [표 넣기]
|왼쪽 정렬|가운데 정렬|오른쪽 정렬| 
|:---|:---:|---:| 
|내용1|내용2|내용3| 
|내용1|내용2|내용3| 

<br>

### 정리내용
[정리 내용 보기](https://lsh424.tistory.com/37)
