# 🌿JOBCHO🌿
> 잔디를 벤치마킹하여 만든 팀 협업 플랫폼

**Jobcho**는 팀원 간의 실시간 소통과 프로젝트 관리를 지원하는 웹 기반 협업툴입니다.  
잔디(JANDI)의 기능을 참고하여 구현되었으며, 메시지 채팅, 공지 관리, 할 일, 파일 공유 등의 기능을 제공합니다.
### API 명세서 링크
> https://spotless-seeker-00f.notion.site/JOBCHO-API-22d13ddc688b80449ec1c9b64f0b609f?pvs=73

### Notion
> https://spotless-seeker-00f.notion.site/16513ddc688b806aacb5f59a6befa64e?p=22913ddc688b80a092aad24c232204da&pm=c

<br><br>

## 선정 이유
- Spring Boot를 학습하면서 실시간 채팅, 파일 업로드, 알림 등 다양한 기능을 직접 구현해보고 싶은 욕심이 생겼습니다.
- 기업 협업툴인 잔디(JANDI)를 벤치마킹하여 웹 협업 플랫폼을 만들자는 목표로 프로젝트를 기획했습니다.
- 단순한 채팅과 일정 관리 협업툴이 아닌 깃허브와 같은 파일 관리를 직접 설계하고 구현하고 싶었습니다.

<br><br>

## ⩤ 주요 기능
- 1:1 고객센터 ( 관리자 페이지 및 사용자 권한 구분 )
- 워크 스페이스 또는 채팅방 초대 
- 워크스페이스 내 다중 채팅
- 할 일 관리 기능
- 공지사항 등록 및 열람
- 워크스페이스 내 파일 관리

<br><br>

## 📠 기술 스택
- Frontend : HTML, CSS, JavaScript
- Template : Thymeleaf
- Backend : Spring Boot, Spring MVC, JPA
- Database : Oracle
- Build Tool : Gradle

<br><br>

## 🔧 기능 정리
### 🛠 백엔드
#### 🧑 사용자 관리
- 로그인 및 로그인 유지 기능
- 비로그인 시 로그인 페이지 외 접근 제한
- 비밀번호 찾기 기능 -> 이메일로 임시 비밀번호 전송 
- 사용자 계정 설정
- 사용자 활동 상태 확인
- 고객센터 1:1 실시간 채팅 -> WebSocket

#### 🏢 워크스페이스 및 채팅방
- 워크스페이스 생성 및 설정한 도메인으로 접근 가능
- 워크스페이스 생성 시 ‘나와의 채팅’ 자동 생성
- 워크스페이스 및 채팅방에 멤버 초대 -> 이메일 초대
- 채팅방 파일 등록
- 채팅방 메시지 및 답글 등록, 수정, 삭제
- 채팅방 멘션 -> ex) `@송효준`
- 공지사항 및 할 일 등록
- 즐겨찾기 등록 
- 알림 기능 -> 공지사항, 할 일, 멘션
- 채팅방 나가기 및 삭제

#### 📽️ 프로젝트 관리
- 브랜치 별 커밋 수 시각화
- 브랜치 별 커밋  
- 파일 및 폴더 업로드

<br><br>

## 👨🏻‍💻 역할
### 🙋🏻‍♂️ <a href="https://github.com/SongHyojun0228" target="_blank">송효준</a>
#### ➬ 프론트엔드
- 회원가입, 로그인, 비밀번호 찾기 페이지
- 계정 설정 페이지
- 팀생성, 홈화면 페이지
- 워크스페이스 및 채팅방 멤버 초대 페이지
- 1 : 1 고객센터 페이지
- 워크스페이스 및 채팅방 조직도 페이지
- 알람 센터 페이지
- 프로젝트 파일, 폴더 업로드 페이지

#### 🔙 백엔드 
- 회원가입, 로그인, 로그아웃
- 비밀번호 찾기 -> 이메일로 임시 비밀번호 전송
- 비로그인 시 로그인 페이지 외 접근 제한 및 로그인 유지
- 사용자 계정 설정
- 워크 스페이스 생성 시 사용자가 지정한 도메인 설정
- 사용자 활동 상태 확인
- 관리자와 고객센터 1:1 실시간 채팅 -> WebSocket
- 즐겨찾기 등록
- 채팅방 멘션 기능 -> ex) @송효준
- 워크스페이스 및 채팅방 멤버 식별
- 브랜치 생성 및 해당 브랜치로 커밋
- 폴더 구분 기반의 세분화된 파일 업로드 

#### ⚾ 데이터베이스
- 테이블 설계
- 무결성 제약 조건 설계

### 🙋🏻‍♂️ <a href="https://github.com/walwal123" target="_blank">유승원</a>
#### ➬ 프론트엔드
- 워크스페이스 페이지
- 즐겨찾기, 할 일 자세히 보기 페이지
- 프로젝트 차트 페이지
  
#### 🔙 백엔드
- 워크 스페이스 별 나와의 채팅방 
- 웹소켓 기반 다중 채팅방 구현
- 채팅방 수정, 삭제, 나가기 구현
- 채팅방 파일 업로드 구현
- 메시지 별 부가 기능 -> 수정, 삭제, 답글 등
- 공지사항, 할 일 등록

<br><br>

## 성장점
- 처음으로 스프링으로 만든 프로젝트였는데 실제 서비스처럼 동작하도록 만들다 보니 웹 애플리케이션의 구조와 흐름을 더 이해할 수 있는 계기가 됐습니다.
- Spring Security를 사용한 로그인 처리, 도메인 기반 접근 제어, WebSocket 실시간 채팅, 폴더 구조 기반 파일 업로드 등 여러 기능을 직접 구현하면서 많은 고민과 오류 등 큰 경험을 할 수 있었습니다.
- WebSocket을 활용한 1:1 고객센터와 다중 채팅방 기능을 구현하고, 멘션이나 할 일 등록 시 실시간 알림이 가도록 만들면서 실시간 이벤트 처리 흐름에 대해 익숙해졌습니다.
- DB 설계에서는 무결성, 참조 제약 조건 등을 신경 쓰며 데이터가 안정적으로 유지되도록 설계하는 방법을 배울 수 있었습니다.
  
<br><br>

## 📆 기간
- 2025년 04월 중반 ~ 2025년 06월 후반

<br><br>

## 📸 스크린샷
| 회원가입 |
|:-:|
| <img width="1758" alt="1  회원가입" src="https://github.com/user-attachments/assets/c7bce6d8-2d6d-4186-b9e4-84f4a98a90ed" /> |

---

| 로그인 | 비밀번호 찾기 |
|:-:|:-:|
| <img width="1758" alt="2  로그인" src="https://github.com/user-attachments/assets/366038a3-3496-4c97-87ad-c662f6009f09" /> | <img width="1758" alt="3  비밀번호찾기" src="https://github.com/user-attachments/assets/2d676aa3-ec8f-472a-9cdf-3cb0e6441d18" /> |

---

| 홈 화면 | 팀 생성 |
|:-:|:-:|
| <img width="1758" alt="4  홈화면" src="https://github.com/user-attachments/assets/61fd65fd-e6bd-4f4c-8557-f3f151e65213" /> | <img width="1758" alt="5  팀생성" src="https://github.com/user-attachments/assets/070618a5-223a-4bbd-bb8d-185e8de481c4" /> |

--- 

| 할 일 | 조직도 |
|:-:|:-:|
| <img width="1768" alt="6  할 일" src="https://github.com/user-attachments/assets/d43990d3-5dcb-4d14-8961-a7185cf82e48" /> | <img width="1768" alt="7  조직도" src="https://github.com/user-attachments/assets/1ba04850-7cb5-47c0-883b-10f2c834ef70" /> |

---

| 알림 | 채팅방 초대 |
|:-:|:-:|
| <img width="1768" alt="8  알람" src="https://github.com/user-attachments/assets/debccb29-0baa-4dee-a8ff-27bf8ad25428" /> | <img width="1758" alt="9  채팅방 초대" src="https://github.com/user-attachments/assets/a29cc3a2-ac7f-47c0-be18-b48296a98e35" /> |

---

| 프로젝트 차트 |
|:-:|
| <img width="1768" alt="10  프로젝트 매니저" src="https://github.com/user-attachments/assets/e1107dde-80f2-47eb-9395-9bb4af15fc9e" /> |

---

| 프로젝트 커밋 |
|:-:|
| <img width="1768" alt="11  프로젝트 커밋" src="https://github.com/user-attachments/assets/63a5315f-322a-4793-9388-2ce25d9fb41d" /> |

---

| 프로젝트 파일 시각화 |
|:-:|
| <img width="1768" alt="12  프로젝트 뷰" src="https://github.com/user-attachments/assets/c6151df6-0eea-4fc7-a766-8914f6054bc0" /> |
