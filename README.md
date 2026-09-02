# Food Ordering Microservices Platform

A production-style food ordering microservices application built with Java 21, Spring Boot 3.x, Maven, and Docker. Designed for AWS DevOps/DevSecOps hands-on learning and practice.

## Project Overview

This project is a complete demonstration of modern microservices architecture with a focus on DevOps and DevSecOps practices. The application is intentionally kept simple to allow focus on infrastructure, deployment, monitoring, logging, and security aspects rather than application complexity.

The platform supports building expertise in:
- AWS (VPC, RDS, ECR, EKS, ALB, CloudWatch)
- Terraform
- Jenkins CI/CD
- Maven
- SonarQube
- OWASP Dependency Check
- Docker & Multi-stage builds
- Trivy security scanning
- Kubernetes & EKS
- Helm
- Argo CD GitOps
- Prometheus & Grafana monitoring
- CloudWatch
- ELK/EFK centralized logging
- GitOps workflows
- Security scanning and hardening
- Application and infrastructure troubleshooting

## Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Build Tool**: Maven 3.x
- **Database**: MySQL 8.0+ (AWS RDS in production)
- **Container**: Docker with multi-stage builds
- **Orchestration**: Kubernetes/EKS (future)
- **CI/CD**: Jenkins (future)
- **Monitoring**: Prometheus, Grafana, CloudWatch (future)
- **Logging**: ELK/EFK (future)

## Microservices

### 1. User Service (Port 8081)
- Manages user profiles and registration
- **Endpoints**: POST /users, GET /users, GET /users/{id}
- **Database**: `usersdb`

### 2. Restaurant Service (Port 8082)
- Manages restaurant listings
- **Endpoints**: POST /restaurants, GET /restaurants, GET /restaurants/{id}
- **Database**: `restaurantsdb`

### 3. Order Service (Port 8083)
- Manages food orders
- **Endpoints**: POST /orders, GET /orders, GET /orders/{id}
- **Database**: `ordersdb`

### 4. Payment Service (Port 8084)
- Processes payments (simulated)
- **Endpoints**: POST /payments, GET /payments/{id}
- **Database**: `paymentsdb`

### 5. Notification Service (Port 8085)
- Sends notifications
- **Endpoints**: POST /notifications
- **Database**: None (stateless)

## Service Ports

| Service | Port |
|---------|------|
| User Service | 8081 |
| Restaurant Service | 8082 |
| Order Service | 8083 |
| Payment Service | 8084 |
| Notification Service | 8085 |

## Environment Variables

All database services support:
```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=<service_db>
DB_USERNAME=root
DB_PASSWORD=password
```

## Build & Run

```bash
cd user-service
mvn clean test
mvn clean package
java -jar target/user-service-*.jar
```

## Docker

```bash
cd user-service
docker build -t user-service:1.0.0 .
docker run -p 8081:8081 user-service:1.0.0
```

## Health Checks

```bash
curl http://localhost:8081/actuator/health
curl http://localhost:8081/actuator/info
curl http://localhost:8081/actuator/metrics
```

## Project Status

✅ Java 21 compatible
✅ Spring Boot 3.x
✅ Maven builds
✅ Docker ready
✅ Spring Boot Actuator
✅ Exception handling
✅ Logging
✅ Tests
✅ Environment-driven configuration

## Future Infrastructure

- AWS Terraform (separate repo)
- Jenkins CI/CD (separate config)
- Kubernetes manifests (separate repo)
- Helm charts (separate repo)
- Argo CD GitOps (separate repo)
- Prometheus/Grafana (separate config)
- ELK/EFK logging (separate setup)

## License

Educational purposes - AWS DevOps/DevSecOps hands-on learning
