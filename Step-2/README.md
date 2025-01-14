# Step-2

## 목표 
* 로그인, CRUD를 기능을 넣은 게시판 구성

## 1. 개발 환경 구성

* 1-1. JDK 설치
    * 저는 Java 17로 설치하였습니다. (https://www.oracle.com/java/technologies/downloads/)

* 1-2. STS 설치
    * https://spring.io/tools

* 1-3. STS Content Assist 기능 설정
    * STS IDE 실행 -> Window -> Preferences
    * Java -> editor -> content Assist
    * Auto Activation 박스 안에 있는 Auto activation triggers for java 입력란에, 아래의 문자열 코드 복사하여 붙여넣기
        * .qwertyuioplkjhgfdsazxcvbnm_QWERTYUIOPLKJHGFDSAZXCVBNM

## 2. gradle.build, application.properties 설명

* 2-1. inventory.ini 에 그룹 별 호스트 정의
    * package 그룹 => kubespray를 사용하기 전 필요한 패키지 및 작업 진행 하기 위해 모든 호스트를 넣어줍니다.
    * master 그룹 => kubespray 실행은 master1 서버에서만 진행하기 때문에, master1 호스트만 넣어줍니다.

* 2-2. setup.yml 작성
    * 호스트 그룹 마다 Playbook이 실행 될 역할(role)과 태그 정의

* 2-3. 역할(role) 별 태스크 구성
    * package 태스크:
        * kubespray 설치 작업을 하기 전 SSH 접속을 Password로 하기 위해 sshpass 패키지를 설치 해줍니다.
        * 또한, 패키지 설치 시에 dpkg 잠금을 해결 하기 위해 unattended-upgrades 서비스와 apt-daily.timer를 종료 시켜줍니다.

    * master 태스크:
        * kubespray 실행하기 위한 사전 패키지 설치
        * kubespray 용 inventory.ini 설정
        * Container Runtime 설정
        * kubespray 실행