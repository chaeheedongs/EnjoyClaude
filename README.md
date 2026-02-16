# EnjoyClaude

<br/><br/><br/>



### 목차

---

* [CLAUDE.md](#CLAUDE.md)
* [프로젝트 파일 tree 구조](#프로젝트-파일-tree-구조)
* [SuperClaude](#SuperClaude)

<br/><br/><br/>



### CLAUDE.md

---

```text
$ tree ./.claude/

./.claude/
|-- CLAUDE.md
#  ...
```

- 클로드 최상위 컨텍스트 설정 파일
- 명령 실행 전 항상 참고하는 파일
- 모든 명령어에서 실행되기 때문에 공통적인 설정만 해야 한다

<br/><br/><br/>



### 프로젝트 파일 tree 구조

---

```text
<project>/.claude/                   # 프로젝트 특정 설정
├── CLAUDE.md                        # 프로젝트 메모리 파일
├── settings.json                    # 팀과 공유되는 설정
├── settings.local.json              # 개인 설정 (git 무시)
└── commands/                        # 프로젝트 커스텀 명령어
```

<br/><br/><br/>



### SuperClaude

---

- SuperClaude 명령어 도움말
  - python 설치해야 한다.

<br/><br/>

📋 사용 가능한 명령어 목록

| 명령어 | 설명 |
|--------|------|
| `/sc:analyze` | 코드 품질, 보안, 성능, 아키텍처 전반에 걸친 종합 분석 |
| `/sc:brainstorm` | 소크라틱 대화를 통한 대화형 요구사항 탐색 |
| `/sc:build` | 지능형 오류 처리 및 최적화를 통한 프로젝트 빌드, 컴파일, 패키징 |
| `/sc:business-panel` | 적응형 상호작용 모드를 갖춘 다중 전문가 비즈니스 분석 |
| `/sc:cleanup` | 코드 정리, 데드 코드 제거, 프로젝트 구조 최적화 |
| `/sc:design` | 시스템 아키텍처, API, 컴포넌트 인터페이스 설계 |
| `/sc:document` | 컴포넌트, 함수, API, 기능에 대한 집중된 문서 생성 |
| `/sc:estimate` | 작업, 기능, 프로젝트에 대한 개발 추정치 제공 |
| `/sc:explain` | 코드, 개념, 시스템 동작에 대한 명확한 설명 제공 |
| `/sc:git` | 지능형 커밋 메시지와 워크플로우 최적화를 통한 Git 작업 |
| `/sc:implement` | 지능형 페르소나 활성화와 MCP 통합을 통한 기능 및 코드 구현 |
| `/sc:improve` | 코드 품질, 성능, 유지보수성에 대한 체계적 개선 적용 |
| `/sc:index` | 지능형 조직화를 통한 종합 프로젝트 문서 및 지식 베이스 생성 |
| `/sc:load` | Serena MCP 통합을 통한 프로젝트 컨텍스트 로딩 |
| `/sc:reflect` | Serena MCP 분석 기능을 사용한 작업 반영 및 검증 |
| `/sc:save` | 세션 컨텍스트 지속성을 위한 Serena MCP 통합 |
| `/sc:select-tool` | 복잡도 점수 및 작업 분석 기반 지능형 MCP 도구 선택 |
| `/sc:spawn` | 지능형 분해 및 위임을 통한 메타 시스템 작업 오케스트레이션 |
| `/sc:spec-panel` | 저명한 사양 및 소프트웨어 엔지니어링 전문가를 활용한 다중 전문가 사양 검토 |
| `/sc:task` | 지능형 워크플로우 관리 및 위임을 통한 복잡한 작업 실행 |
| `/sc:test` | 커버리지 분석 및 자동화된 품질 리포팅을 통한 테스트 실행 |
| `/sc:troubleshoot` | 코드, 빌드, 배포, 시스템 동작의 문제 진단 및 해결 |
| `/sc:workflow` | PRD 및 기능 요구사항으로부터 구조화된 구현 워크플로우 생성 |

<br/><br/>

🚩 주요 플래그 카테고리

> 모드 활성화 플래그
- --brainstorm: 협업적 탐색 마인드셋 활성화
- --introspect: 사고 과정 노출 (자체 분석)
- --task-manage: 다단계 작업 오케스트레이션 (3단계 이상)
- --orchestrate: 다중 도구 병렬 실행 최적화
- --token-efficient: 토큰 사용량 30-50% 감소

<br/>

> 분석 깊이 플래그
- --think: 표준 구조화 분석 (~4K 토큰)
- --think-hard: 심층 분석 (~10K 토큰)
- --ultrathink: 최대 깊이 분석 (~32K 토큰)

<br/>

> 실행 제어 플래그
- --delegate [auto|files|folders]: 서브 에이전트 병렬 처리
- --concurrency [n]: 최대 동시 작업 제어 (1-15)
- --loop: 반복적 개선 사이클 활성화
- --validate: 실행 전 리스크 평가
- --safe-mode: 최대 검증, 보수적 실행

<br/>

> 출력 최적화 플래그
- --uc / --ultracompressed: 심볼 기반 압축 통신
- --scope [file|module|project|system]: 작업 범위 정의
- --focus [performance|security|quality|architecture|accessibility|testing]: 특정 도메인 집중

<br/><br/>

💡 사용 예시

> Context7을 사용한 심층 분석
- /sc:analyze --think-hard --context7 src/

<br/>

> 검증을 포함한 UI 개발
- /sc:implement --magic --validate "사용자 대시보드 추가"

<br/>

> 토큰 효율적인 작업 관리
- /sc:task --token-efficient --delegate auto "인증 시스템 리팩토링"

<br/>

> 안전한 프로덕션 배포
- /sc:build --safe-mode --validate --focus security

<br/><br/>

📌 플래그 우선순위 규칙

1. 안전 우선: --safe-mode > --validate > 최적화 플래그
2. 명시적 오버라이드: 사용자 플래그 > 자동 감지
3. 깊이 계층: --ultrathink > --think-hard > --think
4. 범위 우선순위: system > project > module > file

<br/><br/><br/>