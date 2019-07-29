FROM openjdk:8-jdk
MAINTAINER <leandroaraujo_sp@hotmail.com>

#
# EXPOSE 80

# VOLUME /data

COPY build/libs/*.jar /app/service.jar
COPY config.yml /app

# Por enquanto, copia o config na geração da imagem
CMD ["java", "-jar", "/app/service.jar", "server", "app/config.yml"]