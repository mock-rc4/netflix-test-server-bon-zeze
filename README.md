# netflix-test-server-bon-zeze
+ 넷플릭스_b 서버 팀 프로젝트을 위한 저장소

-----


## 2022.03.19 개발 일지


### 제제 & 본(Bon)

<details>
<summary>1. 기능 API 목록 작성 </summary>
<div markdown="1">
  

기능 API 목록
- 계정(Account)
    - [ ] 생성
        - [x] 회원가입 `bon`
        - [ ] 이메일 인증 (*우선순위 구현 이후*)
        - [ ] 결제수단 등록 (*우선순위 구현 이후*)
    - [x] 변경
        - [x] 이메일 `zeze`
        - [x] 비밀번호 `zeze`
        - [x] 휴대폰번호 `zeze`
        - [x] 멤버쉽 `zeze`
    - [ ] 탈퇴 `bon`
    - [ ] 조회 `bon`
    - 로그인
        - [x] 일반 로그인 `zeze`
        - 소셜 로그인
            - [ ] 네이버 `bon`
            - [ ] 카카오톡 `zeze`
            - [ ] 구글 `bon`
            - [ ] 페이스북 `bon`
    - [x] 로그아웃 `zeze`
    - [ ] 비밀번호 찾기
        - [ ] 문자 보내기 `bon`
        - [ ] SMS 인증 `bon`


- 프로필(Profile)
    - [ ] 생성(1계정당 5개 가능) `zeze`
    - [ ] 변경
        - [ ] 전체 변경 (언어,이름,자동재생설정) `zeze`
        - [ ] 사진선택 `zeze`
    - [ ] 삭제 `bon`
    - [ ] 목록 조회 `bon`
    - [ ] 조회 `bon`

---

- 영화
    - 목록 조회
        - [ ] TOP 10 순위 영상들
        - [ ] 찜한 콘텐츠 보여주기
        - [ ] 인기 콘텐츠 보여주기
        - [ ] 최신 등록 콘텐츠
        - [ ] 시청중인 콘텐츠
        - [ ] 장르별 콘텐츠
    - [ ] 상세 정보 조회
    - [ ] 조회

- 시리즈
    - 목록 조회
        - [ ] TOP 10 순위 영상들
        - [ ] 찜한 콘텐츠 보여주기
        - [ ] 인기 콘텐츠 보여주기
        - [ ] 최신 등록 콘텐츠
        - [ ] 시청중인 콘텐츠
        - [ ] 장르별 콘텐츠
    - [ ] 상세 정보 조회
    - [ ] 조회


</div>
</details>

<details>
<summary>2. ERD 1차 초안 설계</summary>
<div markdown="1">

![vvzvzv](https://user-images.githubusercontent.com/34790699/159122534-d40937c3-096f-4635-a5ac-2782a6accb06.png)

</div>
</details>

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

###



### 제제(Zeze)

#### API 개발
- 프로필
  - 프로필 생성 API 구현
  - 프로필 전체 변경 API 구현
  - 프로필 사진 지정 API 구현
- 리팩토링
  - 회원 로그인 API Req 결과 리팩토링
  - 회원 변경 API 컬럼 리팩토링
  - 회원 로그아웃 API JWT 전체 구현 리팩토링

#### 개발&협업 회고록
  - 프론트분들과 협업하면 Req, Res 값이 변경됨에 따라 API가 많이 차이 날 수 있음을 깨달았다.
  - 
