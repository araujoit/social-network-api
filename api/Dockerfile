FROM openjdk:8-jdk
MAINTAINER <leandroaraujo_sp@hotmail.com>

# Copia o jar para dentro do container
COPY build/libs/*.jar /app/service.jar
COPY config.yml /opt/config.yml

# Por enquanto, copia o config na geração da imagem
CMD ["java", "-jar", "/app/service.jar", "server", "opt/config.yml"]
