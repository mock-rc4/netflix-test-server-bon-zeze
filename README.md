## 💻 넷플릭스 팀 프로젝트
### 📝 Introduction
+ 프론트엔드와 백엔드가 함께한 넷플릭스 팀 프로젝트입니다.
+ 참여 인원 : 서버 2명 / 프론트엔드 1명


### 🛠 Structure

<details>
	<summary><b>⚙️ Architecture</b></summary>
<div markdown="1">

![image](https://user-images.githubusercontent.com/65826145/178272183-a2a2a9ee-3c22-4bd2-8c98-80724bd2c435.png)

</div>
</details>

<details>
	<summary><b>⚙️ 디렉토리 맵</b></summary>
<div markdown="1">
	
```
netflix-test-server-bon-zeze
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─example
    │  │          └─demo
    │  │              │  DemoApplication.java
    │  │              │
    │  │              ├─config
    │  │              │  │  BaseException.java
    │  │              │  │  BaseResponse.java
    │  │              │  │  BaseResponseStatus.java
    │  │              │  │  Constant.java
    │  │              │  │
    │  │              │  └─secret
    │  │              │          Secret.java
    │  │              │
    │  │              ├─src
    │  │              │  │  WebSecurityConfig.java
    │  │              │  │
    │  │              │  ├─account
    │  │              │  │  │  AccountController.java
    │  │              │  │  │  AccountDao.java
    │  │              │  │  │  AccountProvider.java
    │  │              │  │  │  AccountService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Account.java
    │  │              │  │          PatchAccountReq.java
    │  │              │  │          PatchPasswordReq.java
    │  │              │  │          PostAccountRes.java
    │  │              │  │          PostLoginReq.java
    │  │              │  │
    │  │              │  ├─alarm
    │  │              │  │  │  AlarmController.java
    │  │              │  │  │  AlarmDao.java
    │  │              │  │  │  AlarmProvider.java
    │  │              │  │  │  AlarmService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Alarm.java
    │  │              │  │          GetAlarmRes.java
    │  │              │  │          SetAlarmReq.java
    │  │              │  │
    │  │              │  ├─assessment
    │  │              │  │  │  AssessmentController.java
    │  │              │  │  │  AssessmentDao.java
    │  │              │  │  │  AssessmentProvider.java
    │  │              │  │  │  AssessmentService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Assessment.java
    │  │              │  │
    │  │              │  ├─bookmark
    │  │              │  │  │  BookmarkController.java
    │  │              │  │  │  BookmarkDao.java
    │  │              │  │  │  BookmarkProvider.java
    │  │              │  │  │  BookmarkService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Bookmark.java
    │  │              │  │          BookmarkReq.java
    │  │              │  │          GetBookmarkRes.java
    │  │              │  │          PatchBookmarkReq.java
    │  │              │  │
    │  │              │  ├─category
    │  │              │  │  │  CategoryController.java
    │  │              │  │  │  CategoryService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          MainCategory.java
    │  │              │  │
    │  │              │  ├─character
    │  │              │  │  │  CharacterDao.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Character.java
    │  │              │  │
    │  │              │  ├─email
    │  │              │  │  │  EmailController.java
    │  │              │  │  │  EmailService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          EmailDto.java
    │  │              │  │          EmailNotificationReqDto.java
    │  │              │  │          VerificationReqDto.java
    │  │              │  │
    │  │              │  ├─genre
    │  │              │  │  │  GenreController.java
    │  │              │  │  │  GenreDao.java
    │  │              │  │  │  GenreProvider.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Genre.java
    │  │              │  │
    │  │              │  ├─googleAccount
    │  │              │  │  │  ConfigUtils.java
    │  │              │  │  │  GoogleAccountController.java
    │  │              │  │  │  GoogleAccountDao.java
    │  │              │  │  │  GoogleAccountProvider.java
    │  │              │  │  │  GoogleAccountService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          GoogleAccount.java
    │  │              │  │
    │  │              │  ├─kakaoAccount
    │  │              │  │  │  KakaoAccountController.java
    │  │              │  │  │  KakaoAccountDao.java
    │  │              │  │  │  KakaoAccountService.java
    │  │              │  │  │  KakaoOAuth.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          KakaoAccount.java
    │  │              │  │          PostKakaoAccount.java
    │  │              │  │
    │  │              │  ├─lineAccount
    │  │              │  │  │  LineAccountController.java
    │  │              │  │  │  LineAccountDao.java
    │  │              │  │  │  LineAccountProvider.java
    │  │              │  │  │  LineAccountService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          LineAccount.java
    │  │              │  │
    │  │              │  ├─naverAccount
    │  │              │  │  │  NaverAccountController.java
    │  │              │  │  │  NaverAccountDao.java
    │  │              │  │  │  NaverAccountProvider.java
    │  │              │  │  │  NaverAccountService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          NaverAccount.java
    │  │              │  │
    │  │              │  ├─profile
    │  │              │  │  │  ProfileController.java
    │  │              │  │  │  ProfileDao.java
    │  │              │  │  │  ProfileProvider.java
    │  │              │  │  │  ProfileService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          PatchProfileReq.java
    │  │              │  │          PostProfileReq.java
    │  │              │  │          PostProfileRes.java
    │  │              │  │          Profile.java
    │  │              │  │
    │  │              │  ├─profilePhoto
    │  │              │  │  │  ProfilePhotoController.java
    │  │              │  │  │  ProfilePhotoDao.java
    │  │              │  │  │  ProfilePhotoProvider.java
    │  │              │  │  │  ProfilePhotoService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          GetProfilePhotoRes.java
    │  │              │  │          PatchProfilePhotoReq.java
    │  │              │  │          ProfilePhoto.java
    │  │              │  │
    │  │              │  ├─search
    │  │              │  │  │  SearchDao.java
    │  │              │  │  │  SearchProvider.java
    │  │              │  │  │  SearchService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          Search.java
    │  │              │  │
    │  │              │  ├─sms
    │  │              │  │  │  SmsController.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          MessagesReqDto.java
    │  │              │  │          SendSmsResDto.java
    │  │              │  │          SendVerificationNumberResDto.java
    │  │              │  │          SmsRequest.java
    │  │              │  │          SmsRequestDto.java
    │  │              │  │          VerificationReqDto.java
    │  │              │  │
    │  │              │  ├─test
    │  │              │  │      TestController.java
    │  │              │  │
    │  │              │  ├─video
    │  │              │  │  │  VideoController.java
    │  │              │  │  │  VideoDao.java
    │  │              │  │  │  VideoProvider.java
    │  │              │  │  │  VideoService.java
    │  │              │  │  │
    │  │              │  │  └─domain
    │  │              │  │          GetVideoRes.java
    │  │              │  │          Video.java
    │  │              │  │          VideoContent.java
    │  │              │  │          VideoDetail.java
    │  │              │  │
    │  │              │  └─videoPlay
    │  │              │      │  VideoPlayController.java
    │  │              │      │  VideoPlayDao.java
    │  │              │      │  VideoPlayProvider.java
    │  │              │      │  VideoPlayService.java
    │  │              │      │
    │  │              │      └─domain
    │  │              │              VideoPlay.java
    │  │              │
    │  │              └─utils
    │  │                      AES128.java
    │  │                      JwtService.java
    │  │                      ValidationRegex.java
    │  │
    │  └─resources
    │          application.yml
    │          logback-spring.xml
    │
    └─test
        └─java
            └─com
                └─example
                    └─demo
                            DemoApplicationTests.java
.gitignore
build.gradle
gradlew
gradlew.bat
README.md
settings.gradle
```
	
</div>
</details>

<details>
	<summary><b>⚙️ API 명세서</b></summary>
<div markdown="1">
	
➡️ https://docs.google.com/spreadsheets/d/1XnL2T2ZSV2B-bibM3HePSYKhbeYDM_Ar/edit#gid=990061567
	
</div>
</details>

<details>
	<summary><b>⚙️  기능 목록</b></summary>
<div markdown="1">

- **계정(Account)**
    - [x] 생성
        - [x] 회원 가입 단계 조회 `bon`
		- [x] 회원 등록 `bon`
        - [x] 이메일 인증 (*우선순위 구현 이후*)
        - [ ] 결제수단 등록 (*우선순위 구현 이후*)
    - [x] 변경
        - [x] 이메일 `zeze`
        - [x] 비밀번호 `zeze`
        - [x] 휴대폰번호 `zeze`
        - [x] 멤버쉽 `zeze`
    - [x] 탈퇴 `bon`
    - [x] 전체 회원 조회 또는 membership으로 검색 조회 `bon`
    - [x] 식별자로 회원 조회 `bon`

    - 로그인
        - [x] 일반 로그인 `zeze`
        - 소셜 로그인
            - [x] 네이버 `bon`
            - [ ] 카카오톡 `zeze`
            - [x] 구글 `bon`
            - [x] 라인 `bon`
  
    - [ ] 로그아웃 `zeze`
    - [x] 비밀번호 찾기
        - [x] 문자 보내기 `bon`
        - [x] SMS 인증 `bon`

	
- **프로필(Profile)**
    - [x] 생성(1계정당 5개 가능) `zeze`
    - [x] 변경
        - [x] 전체 변경 (언어,이름,자동재생설정) `zeze`
        - [x] 사진 변경 `zeze`
    - [x] 삭제 `bon`
    - [x] 목록 조회 `bon`
    - [x] 조회 `bon`
    - [x] 닉네임 조회 `zeze`

- **비디오(Video)**
    - [x] 목록 조회 (1개의 API) `제제`
        - [x]  TOP 10 콘텐츠 목록 조회 `제제`
        - [x]  내가 찜한 콘텐츠 목록 조회 `제제`
        - [x]  인기 콘텐츠 목록 조회 `제제`
        - [x]  신규 콘텐츠 목록 조회 `제제`
        - [x]  시청중인 콘텐츠 목록 조회 `제제`
        - [x]  장르별 콘텐츠 목록 조회 `제제` `본`
    - [x]  장르 대분류 목록 조회 (영화, 시리즈 내부 기능) `제제`
    - [x]  하나의 영화 또는 시리즈의 방영분 (회차)목록 조회 `본`
    - [x]  상세정보 조회(작품정보, 해당 작품에 속한 모든 출연자, 장르, 특징, 총망라 조회)`본`
    - [x] 작품의 출연자 조회 (출연자 이름 클릭) `본`
    - [x] 출연자가 참여한 작품 목록 조회 (출연자 이름 클릭) `본`
    - [x] 작품의 특징 조회 (영화/시리즈 특징 클릭) `본`
    - [x] 특징별 작품 목록 조회 (영화/시리즈 특징 클릭) `본`
    - [x] 장르별 수상작 컨텐츠 조회 (영화/시리즈 모두) `본`
    - [x] 최다 검색 컨텐츠 조회 (영화/시리즈 모두) `본`
    - [x] 상세페이지 첫화면 동영상 재생시 정보 조회 `본`
	
- **북마크(Bookmark)**
    - [x]  북마크 누르기 `제제`
    - [x]  북마크 변경  `제제`
    - [x]  북마크 조회 `제제`
- **좋아요(Assessment)**
    - [x]  좋아요 누르기 `본`
    - [x]  좋아요 변경  `본`
    - [x]  좋아요 조회 `본`
- **영상 시청을 위한 정보(Play)**
    - [x]  생성  `본`
    - [x]  변경  `본`
    - [x]  조회  `본` 
- **알림(Alarm)**
    - [x]  생성  `제제`
    - [x]  변경  `제제`
    - [x]  조회  `제제`
- **검색(Search)**
    - [x] 검색(제목/사람/장르) 콘텐츠 목록 조회 `제제`
- **감독(Director)**
    - [x] 감독이 제작한 작품 목록 조회 `본`
    - [x] 작품 제작에 참여한 감독 목록 조회 `본`
- **기타(ETC)**
    - [x] 성인인증 필요 여부 검증 조회 `본`
</div>
</details>

<details>
	<summary><b>⚙️  ERD 구성</b></summary>
<div markdown="1">

![final_Netflix](https://user-images.githubusercontent.com/65826145/161184838-f26ca921-06b0-40b2-acd2-78026eb8562c.png)

	
</div>
</details>






<br>








### 📝 개발일지

<details>
	<summary><b>개발 일지 (22.03.19 ~ 22.03.31)</b></summary>
<div markdown="1">

<br>
	
<details>
	<summary><b>2022.03.19 개발 일지</b></summary>
<div markdown="1">
	
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
	
</div>
</details>

<details>
	<summary><b>2022.03.20 개발 일지</b></summary>
<div markdown="1">
	
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


</div>
</details>

<details>
	<summary><b>2022.03.21 개발 일지</b></summary>
<div markdown="1">

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

</div>
</details>

<details>
	<summary><b>2022.03.22 개발 일지</b></summary>
<div markdown="1">

## 2022.03.22 개발 일지

### 본(Bon)

#### API 개발
- 소셜 로그인 서비스 WORKFLOW https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/70#issue-1189058146
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

</div>
</details>

<details>
	<summary><b>2022.03.23 개발 일지 ➕피드백1</b></summary>
<div markdown="1">	

## 2022.03.23 개발 일지
	
### 1차 피드백
- 모든 테이블에는 createdAt, updatedAt, status 컬럼이 들어가는 것이 좋음
- 관리자 API보다는 사용자API에 집중하여 구현하는 것이 좋음
- ERD 테이블에 관하여 재설계 필요
	- 영화,시리즈 대신 비디오로 통일
	- 영화에 인물을 넣는 것이 아닌 배우 테이블을 따로 생성
	
	
### 1차 위클리 스크럼 회의록
- 각 팀원들 전원이 현재까지 진행 상황에 대한 보고
- 실제 서비스에 가까운 형태로 어떻게 구현할 수 있을지에 대한 토론
- 프론트 팀원들의 개발 진행속도가 느려서(회원가입 단계 진행중인 상태) 이후에 요청사항이 있을 때 디스코드를 통해 소통하기로 의견을 모으고 마무리.

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

</div>
</details>

<details>
	<summary><b>2022.03.24 개발 일지</b></summary>
<div markdown="1">

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

</div>
</details>

<details>
	<summary><b>2022.03.25 개발 일지</b></summary>
<div markdown="1">

## 2022.03.25 개발 일지

### 본(Bon)

#### API 개발
- 시리즈의 시즌과 회차 갯수 목록 조회
- 구글 소셜 로그인 서비스 
	- 파라메터를 조합하여 구글 로그인 URL을 불러오는 API 
	- 소셜 로그인 WORKFLOW https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/70#issue-1189058146
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
- `개발`
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

</div>
</details>

<details>
	<summary><b>2022.03.26 개발 일지</b></summary>
<div markdown="1">

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
    - 소셜 로그인 WORKFLOW https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/70#issue-1189058146	
    - 파라메터를 조합하여 Google 로그인 창 URL을 반환
    - Google ID의 액세스 토큰 반환
    - Google Account 조회
    - Google Account로 회원가입
    - Google Account로 로그인

4. Line REST LOGIN API
    - 소셜 로그인 WORKFLOW https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/70#issue-1189058146	
    - 파라메터를 조합하여 Line 로그인 창 URL을 반환
    - Line ID의 액세스 토큰 반환
    - Line Account 조회
    - Line Account로 회원가입
    - Line Account로 로그인
5. 네이버 소셜 로그인 패스워드 암호화, 소셜로그인 유형 검사(DAO) 적용, 줄간격 리팩토링 등


### 제제(Zeze)
#### API & Todo
- `개발`
	- 메인 페이지의 카테고리(Top10,인기,장르 등) uri 목록 조회 API 구현
	- 검색 조회 API 구현 `진행중`

#### ISSUES
- Git 충돌 발생 해결 -> 
  https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/48#issue-1182603888


</div>
</details>

<details>
	<summary><b>2022.03.27 개발 일지</b></summary>
<div markdown="1">

## 2022.03.27 개발 일지
### 본(Bon)
#### API 개발

- 배우(Actor) 출연작품 목록 조회
- 특징(Character) 작품 목록 조회
- 작품에 출연하는 배우 목록을 조회 (videoIdx 사용)
- 작품에 속한 장르 종류 목록을 조회 (videoIdx 사용)
- 작품이 가지는 특징 목록을 조회 (videoIdx 사용)
- 작품의 기타 상세 정보를 조회 (videoIdx 사용)
- 성인인증 필요여부 검증 (true or false)


### 제제(Zeze)
#### API & Todo
- `개발`
	- 알람,검색 코드 골격 구현
	- 프로필 알림 조회 API 구현
	- 검색(제목/사람/장르) 조회 API 구현
	- 검색 기록 저장 로직 구현
- `ERD`
	- ERD 3차 설계 잘못된 부분 수정&보완
- `DOCS`
	- Issue 정리

#### ISSUE
JdbcTemplate - SQL Injection 위험 -> https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/47#issue-1182603514

</div>
</details>

<details>
	<summary><b>2022.03.28 개발 일지</b></summary>
<div markdown="1">
	
## 2022.03.28 개발 일지
### 제제 & 본(Bon)

- 구현했던 API를 넷플릭스 실제 서비스에 맞춰서 어떻게 수정, 보완할 수 있을지 협의함
- ERD
	- Account : membershipStartDate 컬럼 추가 -> 정기 결제일을 표시하고, 멤버쉽에 따른 시청 권한을 가지도록 해야하므로.


### 본(Bon)
#### API 개발

- 최다 검색 컨텐츠 조회

   - 가장 빈번히 검색된 키워드를 통해 작품 목록을 조회
- 수상작 장르별 컨텐츠 조회

   - 장르별 수상 이력이 있는 작품 목록을 조회
- 상세페이지 첫화면 동영상 재생시 정보 불러오기조회

   - 첫화면 재생시 이전 기록으로부터 현재 재생시간을 불러오고, 에피소드 식별자를 반환


#### 지난 Pull Request Comment를 따라 리팩토링
#44 (comment)


### 제제(Zeze)
#### API & Todo
- `개발`
	- 알람 설정 API 구현
	- 비디오 알람 조회 API 구현
	- 이번주 공개 컨텐츠 목록 조회 API 구현
	- 다음주 공개 컨텐츠 목록 조회 API 구현

</div>
</details>

<details>
	<summary><b>2022.03.29 개발 일지</b></summary>
<div markdown="1">
	
## 2022.03.29 개발 일지

### 본(Bon)
#### API 개발
- 감독 제작 작품 목록 조회
- 작품제작에 참여한 감독 목록을 조회

#### Refactoring
- 불필요한 파라메터 제거

#### ERD
- Director, DirectorParticipate 테이블 추가

#### DB
- 시리즈 작품 정보 데이터 추가
	- 관련 테이블 필요한 모든 테이터 추가(장르, 특징, 출연진, 감독 데이터와 이를 있는 관계 테이블의 데이터)

#### ISSUE
![image](https://user-images.githubusercontent.com/34790699/160873000-fa97411b-abfe-41df-a580-90734b43c556.png)  
  
→해당 ISSUE 조회하기 (https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/65#issue-1186629313)

### 제제(Zeze)
#### API & Todo
- `개발`
	- 찜하기 조회 API 구현
- `리팩토링`
	- 기타 리팩토링 진행 `계속 진행 예정`
- `DB`
	- 영화 작품 정보 데이터 및 관련 테이블 데이터 추가
- `DOCS`
	- API 명세서 최신화 반영 

</div>
</details>

<details>
	<summary><b>2022.03.30 개발 일지 ➕피드백2</b></summary>
<div markdown="1">
	
## 2022.03.30 개발 일지

### 2차 피드백
- Git에 관하여 Issue는 브랜치 전략을 세워서 정리했을때 더 좋음
- 모든 메서드에 try, catch문을 쓰기보단 예외 처리문을 따로 구현하는 방법 구상
- API 에 관해 한번에 보내는 연습을 해보는 것도 좋음
	
	
### 2차 위클리 스크럼 회의록
- 각 팀원들 전원이 현재까지 진행 상황에 대한 보고
- 프론트 팀원께서 각종 조회 API에서 필요한 공통의 데이터를 더 추가하기를 희망한다는 의견
- 프론트 팀원들의 개발 단계가 작품 목록을 조회하는 수준에 이르러서 조회하는 방법, 보여지는 형태 등에 대해서 논의
- 마감까지 남은 시간동안 가능한 범위에서의 리팩토링을 계속할 수 있도록 필요할 때마다 디스코드로 연락을 주고받기로 하고 마무리.


#### 2주차 위클리 스크럼 후 Todo
- 각종 조회 API에서 필요한 공통된 데이터를 조금 더 추가할 것
- 내일 저녁 이전 오후까지 수정, 보완에 대한 희망사항을 프론트 팀원들로부터 공유 받을 예정


### 본(Bon)
#### API
- 리팩토링
	- 외부 API 사용을 위한 baseURL 변경
	- Validation & 에러코드 추가
	- 외부 API 사용을 위한 baseURL 변경, 파라메터 추가, Validation & 에러코드 추가 등
	- 프로필 삭제시 jwt를 header에서 요구하지 않음
	- profile 목록과 profile 조회시 profilePhotoIdx -> profilePhotoUrl으로 리턴하도록 쿼리 구조 변경

#### DOCS
- 담당하고 있는 API 기능 명세 전체 작성 완료
	- 48건의 API 시트 검수
	- 1건 삭제 (사유 : 구현하고 보니 관리자를 위한 API이므로 삭제)
	- 47건의 API 시트에 스크린 샷 첨부 후 양식과 줄간격 통일

#### Todo tomorrow
- email 인증 api 알아보고 가능하다면 적용시키기




### 제제(Zeze)
#### API & Todo
- `리팩토링`
	- 로그인시 멤버쉽 반영 리팩토링
	- 회원 핸드폰번호 변경 리팩토링
	- 프로필 생성시 jwt 미적용 리팩토링
	- 로그아웃 리팩토링 `진행중`
	
</div>
</details>

<details>
	<summary><b>2022.03.31 개발 일지</b></summary>
<div markdown="1">

## 2022.03.31 개발 일지
### 본(Bon)
#### API 개발
- 이메일로 메세지 보내기
- 이메일로 6자리 인증번호 보내기

#### 리팩토링
- 쿼리문을 잘못 작성하여 일부 API가 오작동 하는것을 발견하여 수정한 후 서버 반영

#### DB
- 프론트 팀원의 요청을 해결 : 충분한 양의 데이터가 필요
	- 약 1천건의 데이터를 mysql procedure를 사용하여 일련의 규칙을 따라 추가 
		- 비디오 방영분 정보 데이터
		-  비디오와 장르간 관계 정보 데이터
	
#### documentations
####  그동안 공부하며 모으고 기록해왔던 내용을 정리하여 issue에 업로드함.
- Validation을 위해 작성하였던 정규식 뜯어보기 https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/72#issue-1189099715
- Spring 공부 정리 https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/71#issue-1189099646	
- (네이버, 구글, 라인)소셜 로그인 서비스 WORK FLOW https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/70#issue-1189058146
- 자바로 하는 HTTP 요청 사용과 스프링 컨트롤러 어노테이션 정리 https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/69#issue-1189053121
- Mysql Procedure 공부와 실제 데이터 연산에 적용하기 https://github.com/mock-rc4/netflix-test-server-bon-zeze/issues/65#issue-1186629313
	
	
### 제제(Zeze)
#### API & Todo
- `개발`
	- 카카오 소셜 로그인 API  -> `진행중`
- `리팩토링`
	- Top10 조회시에는 세로 사진 반환
- `DB`
	- 추가 데이터 생성
- `DOCS`
	- API 명세서 최신화 완료

	
</div>
</details>

</div>
</details>
