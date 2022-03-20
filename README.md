# netflix-test-server-bon-zeze
+ 넷플릭스_b 서버 팀 프로젝트을 위한 저장소




# 2022.03.19 개발 일지


## 제제 & 본(Bon)

<details>
<summary>기능 API 목록 작성 </summary>
<div markdown="1">
  

기능 API 목록
- [ ] 계정(Account)
    - 생성 (1)
        - [x] 회원가입
        - [ ] 이메일 인증
        - [ ] 결제수단 등록
    - 변경 (4)
        - [ ] 이메일
        - [ ] 비밀번호
        - [ ] 휴대폰번호
        - [ ] 멤버쉽 (결제API구현 이후 생각)
    - [ ] 탈퇴
    - [ ] 조회
    - 로그인
        - [ ] 일반 로그인
        - 소셜 로그인
            - [ ] 네이버
            - [ ] 카카오톡
            - [ ] 구글
            - [ ] 페이스북
    - [ ] 로그아웃
    - 비밀번호 찾기 (2)
        - [ ] 문자 보내기
        - [ ] SMS 인증


- 프로필(Profile)
    - [ ] 생성(1계정당 5개 가능)
    - 변경
        - [ ] 언어
        - [ ] 이름
        - [ ] 자동재생설정
        - [ ] 사진선택
    - [ ] 삭제
    - [ ] 목록 조회
    - [ ] 조회 

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

## 본(Bon)


- 기획서 제출 후, 제제와 함께 ERD 설계와 기능 API 목록 정리를 위한 논의를 진행
(2:00 pm ~ 10:30 pm  / 8 hours 30 minutes )

- EC2, RDS 서버 구축
- 서브 도메인(dev, prod) 적용
- prod 폴더에 스프링 템플릿 적용
- 서브도메인(dev, prod)에 각각 SSL 적용(By CertBot)

### 느낀점 및 협업 내용

- 서버 개발 2인 모두가 납득을 해야 ERD 설계와 API 기능 정리가 가능했다.
- 그래서 시간이 제법 딜레이 되었으나, 둘 모두 협업이 처음이었기에 오히려 유의미했다.
- 아직 의견이 하나가 되지 못하는 사안에 대해서는, 차후에 다시 이야기를 나누어 가보기로 결정했다.
    
    
    - Q : API 기능 중, 넷플릭스 영화/시리즈 좋아요 및 싫어요에 대한 API를 구현에 대한 의견
        - 본 : 좋아요 또는 싫어요 버튼을 누를때마다 해당 데이터를 서버측 DB에 저장 API(POST),
               특정 영화시리즈나 특정 프로필에 대한 좋아요 또는 싫어요 데이터를 얻어오는  API(GET)가 필요하다고 생각
        - 제제 : 클라이언트에서 그냥 클릭만 하고 이번 서비스에서 데이터 분석등을 위한 DB 자료 저장은 불필요하다고 생각 → 사용자에게 필요하지 않은 API 기능이라고 생각
        
    - Q : createdAt, updatedAt, status를 모든 테이블 컬럼에 넣어야 하는지에 대한 의견
        - 본 : 모두 넣는게 좋을 것 같다고 생각
        - 제제 : 사용자가 이용하기에 필요한 테이블에만 넣는게 좋을 것 같다고 생각
    - Q : 사용자가 이용하는 서비스 범위 밖의 테이블 내 레코드를 생성하는 API에 대한 의견 (예 : 영화나 시리즈의 레코드 생성)
        - 본 : 관리자를 위한 API도 필요하므로 필요한 API라고 생각
        - 제제 : 사용자가 사용하는 서비스 기준으로 필요하지 않은 API라고 생각
- 현재로 구현 가능한 API 기능과 테이블을 정리하고, 다음 피드백까지 서로 분담하여 진행하기로 했다. (영역 : Account와 Profile 전반)
- 이후 구현 가능한 API, 다만 아직은 우선순위상 나중인 것들에 대해 이야기 해 보았다.
    - 출연 배우에 대한 API 
      - 특정 배우가 출연한 모든 영화/시리즈 작품을 조회
      - 특정 배우가 출연한 모든 영화/시리즈 작품을 검색

# 2022.03.20 개발 일지

## 본(Bon)
+ 계정(Account)서비스를 위한 골격 구현
+ 넷플릭스 계정 회원가입 API 구현
