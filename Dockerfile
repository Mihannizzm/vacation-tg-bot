# Используем OpenJDK в качестве базового образа
FROM openjdk:17-jdk-slim

# 2. Устанавливаем рабочую директорию
WORKDIR /app

# 3. Копируем Gradle-файлы и код
COPY . .

# 4. Собираем jar
RUN ./gradlew build --no-daemon

# 5. Запускаем
CMD ["java", "-jar", "build/libs/vacation-tg-bot.jar"]