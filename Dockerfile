# 第一階段：使用 Maven 與 Java 21 進行編譯與打包
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x Lumos/mvnw
RUN cd Lumos && ./mvnw clean package -DskipTests

# 第二階段：使用輕量化 Java 21 JRE 執行檔
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/Lumos/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]