# EnjoyClaude

## 스택
Java 17, SpringBoot 2.7.5

## 기본 빌드 및 실행
```bash
./gradlew build   # 프로젝트 빌드
./gradlew war     # WAR 파일 빌드 (패키징)
./gradlew bootRun # 애플리케이션 실행 (Spring Boot)
./gradlew clean   # 빌드 산출물 정리
```

## 테스트
```bash
./gradlew test # 전체 테스트 실행
./gradlew test --tests "com.enjoy.EnjoyClaude.EnjoyClaudeApplicationTests" # 특정 테스트 클래스 실행
./gradlew test --tests "클래스명.메서드명" # 특정 테스트 메서드 실행
```

## 개발 모드
```bash
./gradlew bootRun # DevTools가 포함되어 있어 코드 변경 시 자동 재시작됩니다
```

## 프로젝트 구조

```text
src/
├── main/
│   ├── java/com/enjoy/EnjoyClaude/
│   │   ├── config                         # Spring 설정
│   │   ├── domains                        # 도메인 레이어
│   │   ├── infrastructures                # 인프라스트럭처 레이어
│   │   ├── interfaces                     # 인터페이스 레이어
│   │   ├── services                       # 서비스 레이어
│   │   └── EnjoyClaudeApplication.java    # Spring Boot 메인 애플리케이션
│   └── resources/
│       └── application.yaml               # 애플리케이션 설정
└── test/
    └── java/com/enjoy/EnjoyClaude/
        └── EnjoyClaudeApplicationTests.java
```

## 아키텍처 특징
Layered Architecture

### 패키징 방식
- **WAR 패키징**: `war` 플러그인 사용하여 외부 Tomcat 서버에 배포 가능
- `providedRuntime`으로 Tomcat 의존성 관리 (외부 컨테이너 사용 시 충돌 방지)

### 주요 의존성
- **spring-boot-starter-web**: RESTful 웹 서비스 개발
- **spring-boot-devtools**: 개발 중 자동 재시작 및 LiveReload 지원
- **Lombok**: 모든 스코프(main, test)에서 사용 가능하도록 설정됨
- **spring-boot-configuration-processor**: `application.yaml` 설정 메타데이터 생성

### 설정 관리
- `application.yaml`을 통한 애플리케이션 설정
- Spring Boot Configuration Processor가 자동완성 및 검증 지원
