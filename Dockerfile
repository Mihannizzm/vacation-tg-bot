# Используем OpenJDK в качестве базового образа
FROM openjdk:17-jdk-slim

# Создаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY vacation-tg-bot.jar app.jar

# Указываем команду для запуска бота
CMD ["java", "-jar", "app.jar"]