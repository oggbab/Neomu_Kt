# Neomu
너&amp;무 APP 개인 프로젝트
_
특징:위치기반 sns
기술: android studio/java/photoshop/firebase/google API

https://play.google.com/store/apps/details?id=com.neomu.neomu

<div>
<img width="80%" src="https://user-images.githubusercontent.com/43426702/51152854-fbd71b80-18b0-11e9-9093-7ebf61a2c7ba.PNG">
</div>


Refactorying

[1차]
1. androidX refactoring
2. 공통 영역 상속 구조로 변경(인터페이스/BaseActitivy 등)
3. 리소스 최소 파일만 남기기
4. 하드코딩 제거 (string/dimen/color 등)
5. 코틀린
6. 라이브러리 추가
- ankle (intent/log등)
- retrofit2 / okhttp
7. 불필요 기능/클래 제거
8. 중복 코드 인터페이스로 분리

[2차]
1. 보안 강화
2. 모드 분리(디버그/릴리즈)
3. MVVM
4. RXJava
...