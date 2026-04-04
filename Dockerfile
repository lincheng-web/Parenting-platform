FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY parenting-core/pom.xml ./parenting-core/
COPY parenting-web/pom.xml ./parenting-web/
COPY parenting-boot/pom.xml ./parenting-boot/

COPY parenting-core/src ./parenting-core/src
COPY parenting-web/src ./parenting-web/src
COPY parenting-boot/src ./parenting-boot/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/parenting-boot/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]