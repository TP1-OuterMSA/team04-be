# Stage 1: Build
FROM amazoncorretto:17 AS builder

WORKDIR /app

# gradlew 실행 권한 부여
COPY gradlew .
RUN chmod +x gradlew

# Gradle 및 소스 복사
COPY . .

# 빌드 수행 (테스트 제외)
RUN ./gradlew clean build -x test

# JAR 파일 확인용
RUN ls -la /app/build/libs/


# Stage 2: Run
FROM amazoncorretto:17

WORKDIR /app

# 빌드 결과 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# 포트 설정
EXPOSE 8080

# 타임존 설정
ENV TZ=Asia/Seoul

# 애플리케이션 실행 (Vault 외부 설정 파일 사용)
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.location=file:/vault/secrets/application.yml"]
