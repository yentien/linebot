# 使用 OpenJDK 21 映像作為基礎映像
FROM openjdk:21-jdk

# 設定工作目錄
WORKDIR /app

# 複製所有文件到工作目錄
COPY build/libs/*.jar /app/app.jar

# 定義容器啟動時執行的命令
CMD ["java", "-jar", "app.jar"]
