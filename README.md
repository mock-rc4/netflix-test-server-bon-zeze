# netflix-test-server-bon-zeze
💻 넷플릭스_b 서버 팀 프로젝트을 위한 저장소

<details>
	<summary><b>기능 API 목록</b></summary>
<div markdown="1">
  

기능 API 목록
- 계정(Account)
    - [x] 생성
        - [x] 회원 가입 단계 조회 `bon`
		- [x] 회원 등록 `bon`
        - [ ] 이메일 인증 (*우선순위 구현 이후*)
        - [ ] 결제수단 등록 (*우선순위 구현 이후*)
    - [x] 변경
        - [x] 이메일 `zeze`
        - [x] 비밀번호 `zeze`
        - [x] 휴대폰번호 `zeze`
        - [x] 멤버쉽 (*결제API 구현 이후*)
    - [x] 탈퇴 `bon`
    - [x] 전체 회원 조회 또는 membership으로 검색 조회 `bon`
    - [x] 식별자로 회원 조회 `bon`

    - 로그인
        - [x] 일반 로그인 `zeze`
        - 소셜 로그인
            - [x] 네이버 `bon`
            - [ ] 카카오톡 `zeze`
            - [ ] 구글 `bon`
            - [ ] 페이스북 `bon`
  
    - [ ] 로그아웃 `zeze`
    - [x] 비밀번호 찾기
        - [x] 문자 보내기 `bon`
        - [x] SMS 인증 `bon`



- 프로필(Profile)
    - [x] 생성(1계정당 5개 가능) `zeze`
    - [x] 변경
        - [x] 전체 변경 (언어,이름,자동재생설정) `zeze`
        - [x] 사진선택 `zeze`
    - [x] 삭제 `bon`
    - [x] 목록 조회 `bon`
    - [x] 조회 `bon`
    - [x] 닉네임 조회 `zeze`

---

- 비디오
    - [ ] 목록 조회 (1개의 API) `제제`
        - [x]  TOP 10 순위 영상들 (서비스) + API (알림) `제제`
        - [x]  찜한 콘텐츠 보여주기 (서비스) + API (+ 내가 찜한 콘텐츠) `제제`
        - [x]  인기 콘텐츠 보여주기 (서비스) `제제`
        - [x]  신규 콘텐츠 (서비스) + API (알림) `제제`
        - [x]  시청중인 콘텐츠 (서비스) `제제`
        - [x]  장르별 콘텐츠 (서비스) `제제` `본`
    - [x]  하나의 영화 또는 시리즈의 방영분 (회차)목록 조회 (서비스) `본`
    - [x]  좋아요 조회 (서비스) `o` `본`
    - [ ]  상세정보 조회(작품정보, 해당 작품에 속한 모든 출연자, 장르, 특징, 총망라 조회)`본`
    - [x]  장르 대분류 목록 조회 (영화, 시리즈 내부 기능) `제제`
    - [ ] 출연자 조회 (출연자 이름 클릭) `본`
    - [ ] 특징 조회 (영화/시리즈 특징 클릭) `본`
- 북마크
    - [x]  북마크 누르기 `제제`
    - [x]  북마크 변경  `제제`
    - [x]  좋아요 누르기 `본`
- 좋아요	
    - [x]  좋아요 누르기 `본`
    - [x]  좋아요 변경  `본`
- 영상 시청을 위한 정보
    - [x]  생성  `본`
    - [x]  변경  `본`
    - [x]  조회  `본`
    - 동영상 스트리밍?    
- 알림 기능 API `제제`
- 검색
    - 조회 (제목/사람/장르) `제제`
- [기타] 영화 크롤링 서버 저장 외부 API `본`


</div>
</details>

<details>
	<summary><b>ERD 구성</b></summary>
<div markdown="1">

![vvzvzv](https://user-images.githubusercontent.com/34790699/159122534-d40937c3-096f-4635-a5ac-2782a6accb06.png)

</div>
</details>

-----


## 2022.03.19 개발 일지
### 제제 & 본(Bon)
1. 개발 명세서 작성
2. ERD 1차 초안 
<details>
<summary>3. 협업 회의 내용</summary>
<div markdown="1">

**회의 회고록**
- 현재로 구현 가능한 API 기능과 테이블을 정리하고, 다음 피드백까지 서로 분담하여 진행하기로 했다. (영역 : Account와 Profile 전반)
- 아직 의견이 하나가 되지 못하는 사안에 대해서는, 차후에 다시 이야기를 나누어 가보기로 결정했다.
- 이후 구현 가능한 API, 다만 아직은 우선순위상 나중인 것들에 대해 이야기 해 보았다.
    - 출연 배우에 대한 API 
      - 특정 배우가 출연한 모든 영화/시리즈 작품을 조회
      - 특정 배우가 출연한 모든 영화/시리즈 작품을 검색
  
**협의하던 부분**  
- Q : API 기능 중, 넷플릭스 영화/시리즈 좋아요 및 싫어요에 대한 API를 구현에 대한 의견
   - 본 : 좋아요 또는 싫어요 버튼을 누를때마다 해당 데이터를 서버측 DB에 저장 API(POST),<br>
          특정 영화시리즈나 특정 프로필에 대한 좋아요 또는 싫어요 데이터를 얻어오는  API(GET)가 필요하다고 생각
   - 제제 : 좋아요 또는 싫어요 버튼은 넷플릭스의 딥러닝(사용자 관심 분석)을 위한 시스템이라고 생각<br>
           북마크 기능이 따로 존재하여, 좋아요를 한다고해도 사용자에게 현재 좋아요를 눌렀다는 기능 외에는 실제 서비스로 반영 될 수 있는 부분이 없다고 생각<br>
           필요 여부에 관하여 생각해보야 할 점이라고 생각

- Q : createdAt, updatedAt, status를 모든 테이블 컬럼에 넣어야 하는지에 대한 의견
  - 본 : 모두 넣는게 좋을 것 같다고 생각
  - 제제 : 사용자가 이용하기에 필요한 테이블에만 넣는게 좋을 것 같다고 생각

- Q : 사용자가 이용하는 서비스 범위 밖의 테이블 내 레코드를 생성하는 API에 대한 의견 (예 : 영화나 시리즈의 레코드 생성)
  - 본 : 관리자를 위한 API도 필요하므로 필요한 API라고 생각
  - 제제 : 사용자가 사용하는 서비스 기준으로 필요하지 않은 API라고 생각
    
- Q : 넷플릭스 각 영화의 인물, 카데고리등을 따로 테이블로 빼야하는가에 대한 의견
  - 본 : 영화 테이블에 인물 최대 20명까지(예시) 컬럼 수를 늘려서 영화에서 한번에 다루어야 한다고 생각<br>
        join 은 무겁기 때문에 비정규화가 더 적합하다고 생각
  - 제제 : 한 테이블에 인물20명,카테고리5개,기타...등등의 컬럼을 모두 추가하면 테이블이 너무 무겁다고 생각<br>
          정규화를 지켜서 테이블로 생성하는게 좋다고 생각
  - 협의 : 우선은 TEXT 로 인물을 string 값으로 하나의 컬럼에 적어서 개발해 본 후 추후에 재협의
  
  
</div>
</details>

### 본(Bon)

- 기획서 제출 후, 제제와 함께 ERD 설계와 기능 API 목록 정리를 위한 논의를 진행
(2:00 pm ~ 10:30 pm  / 8 hours 30 minutes )

- EC2, RDS 서버 구축
- 서브 도메인(dev, prod) 적용
- prod 폴더에 스프링 템플릿 적용
- 서브도메인(dev, prod)에 각각 SSL 적용(By CertBot)

#### 느낀점 및 협업 내용
- 서버 개발 2인 모두가 납득을 해야 ERD 설계와 API 기능 정리가 가능했다.
- 그래서 시간이 제법 딜레이 되었으나, 둘 모두 협업이 처음이었기에 오히려 유의미했다.
- 아직 의견이 하나가 되지 못하는 사안에 대해서는, 차후에 다시 이야기를 나누어 가보기로 결정했다.

### 제제(Zeze)

- 본과 함께 API 기능 정리 및 ERD 설계

#### 회의 회고록
  - ERD 를 설계하며 서로 다른 스타일을 맞춰나가며 협업하여 ERD를 설계하는 경험을 쌓을 수 있었다.

<br>

## 2022.03.20 개발 일지

### 본(Bon)


#### API

+ 계정(Account)
  + API 서비스를 위한 코드 골격 구현
  + 회원 가입 API 구현
  + 회원 목록 조회 API 구현(전체 목록 조회, queryString으로 membership 조건 조회)
  + 회원 조회 API 구현(accountIdx로 검색)
  + 회원 탈퇴 API 구현
  

+ SMS 문자 발송 서비스
  + SMS 메시지 전송 API 구현
  + SMS 인증번호 전송 API 구현 (6자리 난수 생성 후 client에게 전송, client는 response와 휴대폰번호로 발송된 번호가 일치하는지 대조)

#### What I did today
+ AWS 서버에 탄력적 IP를 사용하도록 변경
+ 서브 도메인(dev, prod) 외 별도의 대표 도메인(teamflix.shop) 을 서버에 적용 
+ 9000번 포트에서 작동중인 Spring boot 서버를 prod 도메인과 대표 도메인이 가리키도록 세팅
#### ISSUE
+ **AWS 서버 내부 에러 발생**
  
  + 아래의 error log와 이미지 참조
  
  ```
      -- Unit nginx.service has begun starting up.
    Mar 20 08:46:49 ip-172-31-41-81 nginx[23645]: nginx: [emerg] open() "/etc/nginx/sites-enabled/dir" failed
    Mar 20 08:46:49 ip-172-31-41-81 nginx[23645]: nginx: configuration file /etc/nginx/nginx.conf test failed
    Mar 20 08:46:49 ip-172-31-41-81 systemd[1]: nginx.service: Control process exited, code=exited status=1
    Mar 20 08:46:49 ip-172-31-41-81 sudo[23642]: pam_unix(sudo:session): session closed for user root
    Mar 20 08:46:49 ip-172-31-41-81 systemd[1]: nginx.service: Failed with result 'exit-code'.
    Mar 20 08:46:49 ip-172-31-41-81 systemd[1]: Failed to start A high performance web server and a reverse pr
    -- Subject: Unit nginx.service has failed
    -- Defined-By: systemd
    -- Support: http://www.ubuntu.com/support
    --
    -- Unit nginx.service has failed.
    --
    -- The result is RESULT.
  ```
  
  
  
  
  ![ㅋㅍㅋㅍ](https://user-images.githubusercontent.com/34790699/159164528-8736ca08-8430-478a-a797-7da45acaa3ee.png)

  + **발생 배경**
    + dev, prod 이외 별도의 서버 대표 도메인 적용을 위해 server 블록을 만들어 nginx 세팅 도중 위와 같은 에러가 발생했다.
    + 해결을 위해 시도해 본 방법은 다음과 같다.
      
      + certbot 인증 수단 전체 제거 
      + certbot 삭제 후 재설치 & 재적용
      + default 파일 삭제후 재구성
      + 서브도메인(dev, prod)의 server 블록을 삭제
      + Apache2의 실행 중단
      + Apache2의 80번 포트 kill
      + Apache2 완전히 삭제
      + proxy pass에 영향이 있을 수 있는 파일을 탐색(nginx.conf 등)해 보았으나 특별한 이상을 발견하지 못했다.
   + **해결한 방법**
    1. nginx를 완전히 삭제하고 다시 설치했다.
    2. 서브도메인 적용을 위해 서버 블록을 나누고 SSL 적용을 위한 certificaton 모듈을 실행& 적용하였다.
    3. 완전한 삭제 후 재설치, 처음부터 모든 일련의 과정을 적용하고 나니 문제 없이 작동하는 것이 확인되었다.


### 제제(Zeze)

#### API 개발
- 도메인 연결
- 계정(Account)
  - 회원 로그인 API 구현
  - 회원 로그아웃 API 구현
  - 회원 이메일 변경 API 구현
  - 회원 비밀번호 변경 API 구현
  - 회원 휴대폰번호 변경 API 구현
  - 회원 멤버쉽 변경 API 구현 (결제 API랑 무관)

#### 개발&협업 회고록
  - Git 에서 협업하며 개발하는 경험을 쌓을 수 있었다.
  - 서로 코드 스타일, 규약등을 맞춰야 할 필요성을 느낄 수 있었다.
  - 데이터 값을 넣을 때 서로 공유하지 않으면 혼동이 올 수 있음을 느꼈다.

#### 개발 이슈
  - `java.lang.NumberFormatException: For input string: "login"`
    - string 을 int 형으로 변환 할 수 없다고 하여서 코드에서 오류를 찾느라 당황하였다.
    - 원인/이유) POST Mapping 인데 PostMan 에서 Get 으로 호출하였더니 발생한 오류



<br>

## 2022.03.21 개발 일지

### 본(Bon)


#### API

+ 계정(Account)
  + 기존의 회원 가입 API 제거 
  + 프론트엔드 팀원과 협의 후 해당 조건에 맞는 신규 API 2종 추가 -> **넷플릭스의 회원가입 flow를 그대로 따름**


    1. 넷플릭스 회원가입 단계 조회 API 구현 



      + 이메일을 form 으로 제출하면 세가지 유형 상태가 발생 (미가입 계정 or 이메일&비밀번호만 등록된 계정 or 결제수단까지 모두 등록된 계정)
      + 이메일을 서버에 넘기면 아래의 세 가지 경우중 하나를 리턴
        ```
        1. 처음보는 이메일
        2. 이메일과 비번은 설정(가입)이 되어있는데 결제가 안되어있는 경우
        3. 가입과 결제가 다 되어있는경우 
        ```
    2. 회원 가입 API 구현 (email, password로 회원 등록)

#### What I did today


+ API 명세서의 최신화
+ 프론트 엔드 팀원의 요청에 맞게 API를 고안하여 아래와 같이 초안을 작성 후 공유 및 논의

   + 회원가입
      1. GET : 회원가입 단계 조회
        ```
         - request item : 이메일
         - response item: 0/1/2 (case code)
        ```
      2. POST : 처음보는 이메일일 때 계정 생성
        ```
         - request item : 이메일, 비밀번호
         - response item : 계정 식별자, 토큰
        ```
      3. PATCH :  결제 수단 등록
        ```
         - request item : 토큰, 계정 식별자, 멤버쉽 유형
         - response item : 계정 식별자, 토큰
        ```
+ ERD 
   + 회원가입시 email과 password만 있으면 회원 등록이 이루어지므로 나머지 컬럼을 필수 필드가 아닌 Nullable한 값으로 변경
	+ -> id 비밀번호로 계정을 등록하고, 이외 정보의 입력 과정은 차후 회원가입 단계별 진행을 통해 별도로 처리되기 때문.
	
	![ㅜㅜㅜ](https://user-images.githubusercontent.com/34790699/159532524-32a5d955-d2f5-4540-bf00-75c06f01ca0d.png)

	
#### 회고

+ 처음으로 프론트엔드 팀원과 API 형태에 대해 소통해 보았다.
+ 요구사항을 100% 반영할 수 있도록 API를 설계후 구현해 보는 과정을 가졌다.
+ 긴밀한 협의를 통해 앞으로도 이와 같이 반영해 갈 수 있으면 좋겠다고 생각한다.



### 제제(Zeze)

#### API 개발
- 프로필
  - 프로필 코드 골격 구현
  - 프로필 생성 API 구현
  - ~프로필 전체 변경 API 구현~
  - ~프로필 사진 지정 API 구현~
- 리팩토링
  - 회원 변경 API 컬럼 리팩토링
  - ~회원 로그아웃 API JWT 전체 구현 리팩토링~ → `진행중`

#### ERD Update
- Profile 테이블 변경

#### 개발&협업 회고록
  - 프론트분들과 협업하면 Req, Res 값이 변경됨에 따라 API가 많이 차이 날 수 있음을 깨달았다.
  - 협업하다보니 git에 대한 혼동이 생겨서 계속해서 공부해야 할 것 같다.

#### 개발 이슈
- Git 충돌 발생 해결 -> 
  https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/48#issue-1182603888

<br>

## 2022.03.22 개발 일지

### 본(Bon)

#### API 개발
- 네이버 소셜 로그인 서비스 API
- 파라메터를 조합하여 네이버 로그인 URL을 불러오는 API 구현
- 네이버 로그인창에서 아이디와 비밀번호 입력시 네이버 엑세스 토큰 반환 API 구현
- 네이버 계정에 대한 정보 조회 기능 API 구현
- 네이버 계정으로 회원가입 기능 API 구현
- 네이버 계정으로 로그인 기능 API 구현
- 네이버 계정만으로 로그아웃(연결끊기) 기능 API 구현
- 프로필 삭제 API 구현
- 계정 식별자를 통해 프로필 목록을 조회하는 API 구현
- 프로필을 조회하는 API 구현

#### What I did today
- nohup 명령어를 사용한 백그라운드 실행으로 Spring Boot Server를 정상적으로 운영할 수 없었던 이슈를 해결.
	- 하단 ISSUE란 참조.
- NETFLIX 사이트 실 서비스를 기반으로 API를 모델링 하기 위해 사이트 내부 기능 사용 시도 (소셜 로그인, 로그인 등)
	![bxzvb](https://user-images.githubusercontent.com/34790699/159534184-18b26235-2e90-41b2-a6a3-e665c09de309.png)
	+ 페이스북 계정 정보로 Netflix의 계정을 찾을 수 없는 경우 다음과 같은 문구를 반환
		```
		사용 중이신 페이스북 계정이 시스템 기록에 있는 계정과 일치하지 않습니다.
		Netflix 이메일과 비밀번호를 사용하여 로그인해 주세요.
		```
		-> **참고하여 네이버 소셜 로그인 서비스도 같은 Workflow를 따르도록 설계했다.**
		


- 네이버 소셜 로그인에 대한 workflow를 완전히 이해하고 습득하여 추후 팀원들에게 전달할 내용을 정리
	+ 자세한 건 https://developers.naver.com/docs/login/api/api.md 네이버 REST 로그인 API 명세를 참고하여 구현했다.
	+ 이전에 구현해본 카카오 REST API 로그인과 마찬가지로 Documents를 참고해 구현해 볼 수 있었으며,
	+ 단지 금번 진행하는 팀프로젝트를 위한 최적의 로직 설계에 대해 고민하는 시간을 가졌다. -> 어떻게 하면 가장 컴팩트하고 심플하게 필요한 값을 전달 할 수 있는가?
		
	+ 네이버 소셜로그인을 모든 네이버 계정에게 허용하려면 애플리케이션 **검수 심사**를 받아야 한다.
		+ naver developers 네이버 로그인 사전 검수 가이드 문서 (https://developers.naver.com/docs/login/verify/verify.md)
		+ 아래의 조항을 보자.
		![image](https://user-images.githubusercontent.com/34790699/159537562-4b06833d-ce93-4627-b13c-94f7bc284ac0.png)
		+ 우리 팀은 넷플릭스와 동일한 흐름의 서비스 개발을 지향하고 있다.
		+ 넷플릭스 회원등록은 이메일과 비밀번호 설정을 통해 이루어진다.
		+ 따라서 네이버 애플리케이션 사전 검수시 준수사항, 위 조항에 위배된다.
		
		+ 검수 심사를 받지 않은 경우, 검수 요건에 부합되지 않는 경우
		+ 네이버 로그인 API는 회원가입 및 로그인의 모든 서비스를 허용된 네이버 ID에게만 가능하게 한다.
		+ 달리 말하면 Application 등록자가 허용할 대상으로 처리한 네이버 계정만이 해당 기능을 이용할 수 있다.
		+ 팀원들의 네이버 계정 ID를 요청해서, 네이버 소셜 로그인 서비스를 이용해 볼 수 있도록 설계한다.
	
	
+ ERD
	+ 소셜 로그인 idx를 너무 작은 용량으로 세팅해놔서 VARCHAR(30)- > VARCHAR(50)으로 변경
		![image](https://user-images.githubusercontent.com/34790699/159535326-894448e2-858e-401f-9ccf-0dfc690a03be.png)

	+ Current Status on 2022.03.23
	
	
		![ㅁㅁㅁ](https://user-images.githubusercontent.com/34790699/159532725-0053e5dc-840f-47f6-ac54-bf2b569faec6.png)
	
#### ISSUES

1. AWS 서버내 nohub 명령으로 백그라운드 동작시 최신 빌드가 반영 되지 않음
	+ 해결 방법
		1. aws 인스턴스를 중지후 시작 -> 정상적으로 반영됨
		2. PID KILL으로 해결
	- 실행을 하고나면, 명령어를 실행한 경로에 nohup.out이라는 파일이 생기며, program 이라는 프로세스가 뿜어내는 로그들을 찍게 되는데, 이후에 해당 프로세스를 kill하거나 재실행 하기 위해서는, 프로세스를 일일히 pid를 알아내서 kill 필요


2. 프론트 & 백 간에 CORS(Cross Origin Resource Sharing) 에러 발생
3. Pull Request의 Complex Conflicts
	![image](https://user-images.githubusercontent.com/34790699/159536691-1337819b-a58b-44f8-8cb1-c3a0922691d9.png)
			
			
	+ 무거운 SPring Boot의 빌드시 나타나는 과부하 이슈때문에 로컬 빌드파일을 올린 것이 문제의 원인.
	+ 레포지토리에 Build를 올렸을 때, build 폴더와 .build 폴더 내부의 바이너리 파일 등이 origin/dev -> origin/main으로 merge 시도시 complexive conflicts일으킴
	+ 깃헙의 Pull Request에서 complexive conflicts라서 자동 머징이 안되고, 바이너리 파일들, 로그 파일들이 `====>> HEAD` 와같은 형태로 마킹됨
	+ `.gitignore` 안에다가 gradle 관련 것들을 추가 후, 성공적으로 병합 처리를 마무리


	
### 제제(Zeze)

#### API 개발
- 프로필
  - 프로필 전체 변경 API 구현
  - 프로필 사진 지정 API 구현
- 프로필사진
  - 프로필사진 코드 골격 구현 
  - 프로필사진 개별 조회 API 구현
  - 프로필사진 전체 조회 API 구현
- 리팩토링
  - 회원 로그인 API 리팩토링
  - ~회원 로그아웃 API JWT 전체 구현 리팩토링~ → `진행중`

#### 개발&협업 회고록
- git 충돌로 개발보다 많은 시간을 보냈다. git을 기본적으로 공부할 필요를 느꼈다

#### ISSUE
- Git 충돌 발생 해결 -> 
  https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/48#issue-1182603888


## 2022.03.23 개발 일지

### 제제 & 본(Bon)
- 기능 API 목록 재정리
- ERD 2차 설계
	- 영화&시리즈를 Video 로 통합
- Github Repository의 Projects, Issues 기능을 사용하기로 결정

### 본(Bon)
- 비디오, 영화 또는 시리즈 평가 서비스 코드 골격 구현
- 영화 또는 시리즈 평가 추가 API 구현
- 영화 또는 시리즈 평가 조회 API 구현


### 제제(Zeze)
- API uri 리팩토링 (피드백 반영)

<br>

## 2022.03.24 개발 일지

### 본(Bon)

#### API 개발
- 영화 또는 시리즈 평가 변경
- 영화 또는 시리즈 장르별 목록 조회 
- 한 영화 또는 시리즈의 방영분 목록 전체 조회
- 재생 기록 추가 
- 재생 기록 조회
- 재생 기록 변경 
- 하나의 영화 또는 시리즈의 방영분 목록을 시즌 번호로 조회

#### ISSUES
- Spring Boot BUild Issue - aws 서버상에서 build가 불가능한 문제 해결을 위한 시도
  1. IntelliJ remote development 연동은 build를 하는 기능이 없음
  2. SSL을 사용한 Jetbrain Gateway 연결을 시도했으나 timeout expired 와 함께 접속이 실패
  3. 똑같은 시도를 여러번 해봤지만 접속 실패 후 AWS 서버가 다운되는 현상이 계속해서 발생, AWS 인스턴스 중지후 시작으로 복구
- **WinSCP를 통해 로컬 빌드 demo-0.0.1-SNAPSHOT.jar 파일을 AWS 서버에 전송후 구동하여 문제를 해결!!**

### 제제(Zeze)

#### API 개발
- 찜하기 추가 API 구현
- 찜하기 변경 API 구현

#### ISSUES
- BUILD ERROR : `java.lang.NoClassDeFoundError` 해결 -> https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/50#issue-1182828859

<br>

## 2022.03.25 개발 일지

### 본(Bon)

#### API 개발
- 시리즈의 시즌과 회차 갯수 목록 조회
- 구글 소셜 로그인 서비스 
	- 파라메터를 조합하여 구글 로그인 URL을 불러오는 API 

#### What I did today
- 각 테이블마다 종속적인 튜플들을 어떻게 추가하면 좋을지에 대한 고민과 논의
	- DB에 하나의 작품에 대한 정보를 추가하기 위한 과정이 빈번한 수작업이 요구된다.
- 편의성을 위한 관리자용 API를 고려하였으나 사용자를 위한 API 개발이 우선이므로 좋은 대안이 아님을 멘토님께 질의 후 답변을 통해 판단
	- 사용자 입장에서 먼저 구현해보도록 하자.
- 역할을 분담하여 자신이 맡았던 메인 기능 API를 대부분 구현하였으므로 외부 API 탐색
	- 멤버십 설정시 결제 기능을 사용하기 위해 카카오 페이 간편결제를 구현하고자 했으나 사업자 등록 & 가맹점 번호가 필요하므로 취소
	- 구글 소셜 로그인인 공부 (진행중)
### 제제(Zeze)
#### API & Todo
- 장르, 특징 코드 골격 구현
- 장르 대분류 조회 API 구현
- Top10 컨텐츠 조회 API 구현
- 인기 컨텐츠 조회 API 구현
- 시청중인 컨텐츠 조회 API 구현
- 장르별 컨텐츠 조회 API 구현
- 신규 컨텐츠 조회 API 구현
- 찜하기 컨텐츠 조회 API 구현
- 프로필 닉네임 조회 API 

#### ISSUES
- Git 충돌 발생 해결 -> 
  https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/48#issue-1182603888

<br>

## 2022.03.26 개발 일지
### 제제 & 본(Bon)
- 회의를 통한 API 기능 명세서 재정리
	- 알람 기능 추가
	- 검색 기능 추가
	- 기타 조회 추가
- ERD 3차 설계
	- 알림, 검색 테이블 추가
	- 비디오 테이블에서 컬럼 추가
### 본(Bon)

#### API 개발
1. 소셜로그인 골격을 변경하는 Commit
     - Facebook은 Social Login이 Javascript Documents 이므로 이외의 Social Login 서비스를 제공하는 Google, Line의 REST Login API를 사용하여 로그인 관련 서비스를 구현하였다.


2. 이전 Pull Request에서의 Review를 반영한 수정내역 Commit

3. Google REST LOGIN API
    - 파라메터를 조합하여 Google 로그인 창 URL을 반환
    - Google ID의 액세스 토큰 반환
    - Google Account 조회
    - Google Account로 회원가입
    - Google Account로 로그인

4. Line REST LOGIN API
    - 파라메터를 조합하여 Line 로그인 창 URL을 반환
    - Line ID의 액세스 토큰 반환
    - Line Account 조회
    - Line Account로 회원가입
    - Line Account로 로그인
5. 네이버 소셜 로그인 패스워드 암호화, 소셜로그인 유형 검사(DAO) 적용, 줄간격 리팩토링 등


### 제제(Zeze)
#### API & Todo
- 메인 페이지의 카테고리(Top10,인기,장르 등) uri 목록 조회 API 구현
- 검색 조회 API 구현 `진행중`

#### ISSUES
- Git 충돌 발생 해결 -> 
  https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/48#issue-1182603888


<br>

## 2022.03.27 개발 일지
### 본(Bon)
### 제제(Zeze)
#### API & Todo
- 알람,검색 코드 골격 구현
- 프로필 알림 조회 API 구현
- 검색(제목/사람/장르) 조회 API 구현
- 검색 기록 저장 로직 구현
- ERD 3차 설계 잘못된 부분 수정&보완
- Issue 정리
