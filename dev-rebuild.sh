#!/bin/bash

SERVICE=${1:-consumer}
CLEAN_BUILD=${2:-false}

echo "🔨 Building $SERVICE with Maven..."

# Build the Maven project first
if [ "$SERVICE" = "consumer" ]; then
    cd consumer && mvn clean package -DskipTests && cd ..
elif [ "$SERVICE" = "producer" ]; then
    cd producer && mvn clean package -DskipTests && cd ..
else
    echo "❌ Unknown service: $SERVICE"
    exit 1
fi

echo "🐳 Rebuilding Docker container for $SERVICE..."

# Stop and remove container
docker-compose stop $SERVICE
docker-compose rm -f $SERVICE

# Remove image if clean build requested
if [ "$CLEAN_BUILD" = "true" ]; then
    echo "🧹 Cleaning Docker image..."
    docker rmi $(docker-compose config --services | grep $SERVICE | xargs -I {} echo "$(basename $(pwd))_{}" | tr '[:upper:]' '[:lower:]') 2>/dev/null || true
fi

# Rebuild and start
docker-compose build --no-cache $SERVICE
docker-compose up -d $SERVICE

# Show logs
docker-compose logs -f $SERVICE
