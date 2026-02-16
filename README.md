# EnjoyClaude

<br/><br/><br/>



## 목차

---

* [CLAUDE.md](#CLAUDE.md)
* [프로젝트 파일 tree 구조](#프로젝트-파일-tree-구조)

<br/><br/><br/>



## CLAUDE.md

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



## 프로젝트 파일 tree 구조

---

```text
<project>/.claude/                   # 프로젝트 특정 설정
├── CLAUDE.md                        # 프로젝트 메모리 파일
├── settings.json                    # 팀과 공유되는 설정
├── settings.local.json              # 개인 설정 (git 무시)
└── commands/                        # 프로젝트 커스텀 명령어
```