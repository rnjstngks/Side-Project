# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너로 복사
COPY sbb-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# JAR 파일 실행
CMD ["java", "-jar", "app.jar"]