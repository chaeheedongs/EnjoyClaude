# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

EnjoyClaude는 Spring Boot 2.7.5 기반의 WAR 패키징 웹 애플리케이션입니다.
- Java 17 사용
- Gradle 빌드 시스템
- Lombok을 통한 보일러플레이트 코드 감소
- Spring Boot DevTools로 개발 편의성 제공

## 빌드 및 실행 명령어

### 기본 빌드 및 실행
```bash
# 프로젝트 빌드
./gradlew build

# WAR 파일 빌드 (패키징)
./gradlew war

# 애플리케이션 실행 (Spring Boot)
./gradlew bootRun

# 빌드 산출물 정리
./gradlew clean
```

### 테스트
```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "com.enjoy.EnjoyClaude.EnjoyClaudeApplicationTests"

# 특정 테스트 메서드 실행
./gradlew test --tests "클래스명.메서드명"
```

### 개발 모드
```bash
# DevTools가 포함되어 있어 코드 변경 시 자동 재시작됩니다
./gradlew bootRun
```

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/enjoy/EnjoyClaude/
│   │   └── EnjoyClaudeApplication.java    # Spring Boot 메인 애플리케이션
│   └── resources/
│       └── application.yaml               # 애플리케이션 설정
└── test/
    └── java/com/enjoy/EnjoyClaude/
        └── EnjoyClaudeApplicationTests.java
```

## 아키텍처 특징

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
