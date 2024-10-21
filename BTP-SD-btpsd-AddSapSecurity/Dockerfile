FROM eclipse-temurin

# Install system dependencies
RUN apt-get update && apt-get install -y \
    git \
    curl \
    libpng-dev \
    libonig-dev \
    libxml2-dev \
    zip \
    unzip
    
# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/BTP-SD-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "BTP-SD-0.0.1-SNAPSHOT.jar"]
