FROM ibm-semeru-runtimes:open-17.0.8.1_1-jdk-focal

RUN apt -y update
RUN apt -y install git

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
