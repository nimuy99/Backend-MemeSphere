# Build stage
FROM bellsoft/liberica-openjdk-alpine:17 AS builder

WORKDIR /app

# Gradle 관련 파일 복사 및 빌드
COPY gradlew build.gradle settings.gradle /app/
COPY gradle /app/gradle
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 프로젝트 전체 복사 및 빌드 실행
COPY . .
RUN ./gradlew clean build -x test --no-daemon

# Run stage
FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

# JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# GitHub Actions에서 생성한 `application.yml`을 컨테이너 내부로 복사
COPY src/main/resources/application.yml /app/config/application.yml

EXPOSE 8080

# Spring Boot가 설정 파일을 읽을 수 있도록 `SPRING_CONFIG_LOCATION` 지정
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=/app/config/application.yml"]
