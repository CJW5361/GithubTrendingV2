

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
│   │   ├── components/
│   │   ├── views/
│   │   └── App.vue
│   └── package.json
└── backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   └── resources/
    │   └── test/
    └── build.gradle
```


## 작성자

- [CHOI JIUK]

## 감사의 글

- OpenAI - GPT API 제공
- GitHub - 데이터 제공
```
