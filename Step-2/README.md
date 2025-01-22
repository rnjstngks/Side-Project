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

## 2. 개발 완료 후 빌드를 하기 위한 사전 작업 (WSL 환경에서 작업)

* 2-1. JDK 17 설치
```sh
sudo apt install -y openjdk-17
```

* 2-2. JAVA_HOME 환경 변수 설정

사용자의 .bashrc 파일에 아래의 내용을 추가해줍니다.
```sh
vi ~/.bashrc
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
```

그리고 적용
```sh
source ~/.bashrc
```

* 2-3. WSL 환경에서 빌드하기 위해 개발하면서 gradle build 했던 내용 정리

```sh
./gradlew --stop
./gradlew clean
./gradlew build
```