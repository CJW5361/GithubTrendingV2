![image](https://github.com/user-attachments/assets/5865eb4f-9f96-43dd-aeba-8caa7a07f670)


```markdown
# GitHub Trending with AI Analysis

GitHub 트렌딩 저장소들을 확인하고 AI를 통해 저장소를 분석할 수 있는 웹 애플리케이션입니다.

## 주요 기능

- GitHub 트렌딩 저장소 실시간 조회
- AI를 활용한 저장소 분석
  - README 자동 요약
  - 주요 기능 및 특징 분석
  - 기술 스택 분석
- 대화형 AI 챗봇
  - 저장소에 대한 질의응답
  - 상세 정보 문의

## 기술 스택

### Frontend
- Vue 3 (Composition API)
- TypeScript
- Vite
- CSS Variables for theming

### Backend
- Spring Boot
- Java
- OpenAI API

## 설치 및 실행

### 사전 요구사항
- Java 17+
- OpenAI API 키

### 백엔드 설정
```bash
cd backend
# application.properties 파일에 API 키 설정
# OPENAI_API_KEY=your_api_key
# GITHUB_TOKEN=your_github_token
./gradlew bootRun
```

### 프론트엔드 설정
```bash
cd frontend
npm install
npm run dev
```

## 주요 기능 설명

### 다크/라이트 모드
- 사용자 선호도에 따른 테마 변경
- 로컬 스토리지를 통한 테마 설정 유지

### 저장소 분석
- README 내용 기반 자동 분석
- 구조화된 정보 제공
  - 프로젝트 목적
  - 주요 기능
  - 기술 스택
  - 특징

### AI 챗봇
- 저장소 관련 질의응답
- 실시간 대화형 인터페이스
- 상세 정보 조회 가능




## 프로젝트 구조

```
project/
├── frontend/
│   ├── src/
│   │   ├── assets/
│   │   │   └── main.css
│   │   ├── components/
│   │   │   └── ChatBot.vue
│   │   ├── services/
│   │   │   └── api.ts
│   │   ├── stores/
│   │   │   └── github.ts
│   │   ├── views/
│   │   │   ├── HomeView.vue
│   │   │   └── AboutView.vue
│   │   └── App.vue
│   ├── index.html
│   └── package.json
├── backend/
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── example/
│                       └── github_trending_backend/
│                           ├── controller/
│                           │   └── ChatController.java
│                           └── service/
│                               └── ChatService.java
└── crawler/
    └── github_crawler.py
```

주요 구성요소:
1. **frontend**: Vue 3 + TypeScript 기반의 프론트엔드
   - components: 재사용 가능한 Vue 컴포넌트
   - services: API 통신 관련 로직
   - stores: Pinia 상태 관리
   - views: 페이지 컴포넌트

2. **backend**: Spring Boot 기반의 백엔드
   - controller: API 엔드포인트 정의
   - service: 비즈니스 로직 처리

3. **crawler**: Python 기반의 GitHub 트렌딩 크롤러
   - 주기적으로 GitHub 트렌딩 페이지 크롤링
   - 수집된 데이터를 백엔드 API로 전송



## 작성자

- [CHOI JIUK]

## 감사의 글

- OpenAI - GPT API 제공
- GitHub - 데이터 제공
```
