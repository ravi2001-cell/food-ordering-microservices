# Food Ordering Microservices Platform

A production-style food ordering microservices application built with Java 21, Spring Boot 3.x, Maven, and Docker. Designed for AWS DevOps/DevSecOps hands-on learning and practice.

## Project Overview

This project is a complete demonstration of modern microservices architecture with a focus on DevOps and DevSecOps practices. The application is intentionally kept simple to allow focus on infrastructure, deployment pipelines, and operational aspects rather than complex business logic.

The platform supports building expertise in:
- **AWS**: VPC, RDS, ECR, EKS, ALB, CloudWatch, IAM, Security Groups
- **Infrastructure as Code**: Terraform
- **CI/CD**: Jenkins
- **Build Tools**: Maven
- **Code Quality**: SonarQube
- **Security Scanning**: OWASP Dependency Check, Trivy
- **Containerization**: Docker with multi-stage builds
- **Container Orchestration**: Kubernetes, EKS
- **Configuration Management**: Helm
- **GitOps**: Argo CD
- **Monitoring**: Prometheus, Grafana, CloudWatch
- **Logging**: ELK/EFK centralized logging
- **DevSecOps**: Security hardening, secret management, network policies
- **Troubleshooting**: Application and infrastructure debugging

## Technology Stack

| Component | Version/Details |
|-----------|-----------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.3.1 |
| **Build Tool** | Maven 3.9.4+ |
| **Database** | MySQL 8.0+ (AWS RDS in production) |
| **Container Runtime** | Docker with multi-stage builds |
| **Container Registry** | AWS ECR (future) |
| **Orchestration** | Kubernetes/EKS (future) |
| **Configuration Mgmt** | Helm (future) |
| **CI/CD** | Jenkins (future) |
| **Monitoring** | Prometheus, Grafana, CloudWatch (future) |
| **Logging** | ELK/EFK (future) |
| **GitOps** | Argo CD (future) |

## Microservices Architecture

### 1. User Service (Port 8081)
- **Purpose**: Manages user profiles and registration
- **Database**: `usersdb`
- **Endpoints**:
  - `POST /users` - Create new user
  - `GET /users` - List all users
  - `GET /users/{id}` - Get user by ID
- **Health Check**: `GET /actuator/health`

### 2. Restaurant Service (Port 8082)
- **Purpose**: Manages restaurant listings and information
- **Database**: `restaurantsdb`
- **Endpoints**:
  - `POST /restaurants` - Create new restaurant
  - `GET /restaurants` - List all restaurants
  - `GET /restaurants/{id}` - Get restaurant by ID
- **Health Check**: `GET /actuator/health`

### 3. Order Service (Port 8083)
- **Purpose**: Manages food orders
- **Database**: `ordersdb`
- **Endpoints**:
  - `POST /orders` - Create new order
  - `GET /orders` - List all orders
  - `GET /orders/{id}` - Get order by ID
  - `GET /orders/user/{userId}` - Get orders by user
  - `PUT /orders/{id}/status` - Update order status
- **Health Check**: `GET /actuator/health`

### 4. Payment Service (Port 8084)
- **Purpose**: Processes payments (simulated)
- **Database**: `paymentsdb`
- **Features**: Simulated payment processing with 80% success rate
- **Endpoints**:
  - `POST /payments` - Process payment
  - `GET /payments/{id}` - Get payment by ID
  - `GET /payments/order/{orderId}` - Get payment by order
- **Health Check**: `GET /actuator/health`

### 5. Notification Service (Port 8085)
- **Purpose**: Sends notifications (stateless)
- **Database**: None (stateless service)
- **Endpoints**:
  - `POST /notifications` - Send notification
- **Health Check**: `GET /actuator/health`

## Service Ports

| Service | Port |
|---------|------|
| User Service | 8081 |
| Restaurant Service | 8082 |
| Order Service | 8083 |
| Payment Service | 8084 |
| Notification Service | 8085 |

## API Endpoints Examples

### User Service
```bash
# Create User
POST http://localhost:8081/users
Content-Type: application/json

{
  "name": "Ravi",
  "email": "ravi@example.com"
}

# Get All Users
GET http://localhost:8081/users

# Get User by ID
GET http://localhost:8081/users/1
```

### Restaurant Service
```bash
# Create Restaurant
POST http://localhost:8082/restaurants
Content-Type: application/json

{
  "name": "Paradise Biryani",
  "location": "Hyderabad"
}

# Get All Restaurants
GET http://localhost:8082/restaurants

# Get Restaurant by ID
GET http://localhost:8082/restaurants/1
```

### Order Service
```bash
# Create Order
POST http://localhost:8083/orders
Content-Type: application/json

{
  "userId": 1,
  "restaurantId": 1,
  "totalPrice": 499.00
}

# Get All Orders
GET http://localhost:8083/orders

# Get Order by ID
GET http://localhost:8083/orders/1

# Get Orders by User
GET http://localhost:8083/orders/user/1

# Update Order Status
PUT http://localhost:8083/orders/1/status
Content-Type: application/json

{
  "status": "CONFIRMED"
}
```

### Payment Service
```bash
# Process Payment
POST http://localhost:8084/payments
Content-Type: application/json

{
  "orderId": 1,
  "amount": 499.00
}

# Get Payment by ID
GET http://localhost:8084/payments/1

# Get Payment by Order
GET http://localhost:8084/payments/order/1
```

### Notification Service
```bash
# Send Notification
POST http://localhost:8085/notifications
Content-Type: application/json

{
  "userId": 1,
  "message": "Your order has been confirmed"
}
```

## Database Configuration

### Environment Variables

All database-backed services use these environment variables:

```bash
DB_HOST=localhost          # Database host (default: localhost)
DB_PORT=3306              # Database port (default: 3306)
DB_NAME=<service_db>      # Database name (specific to each service)
DB_USERNAME=root          # Database username (default: root)
DB_PASSWORD=password      # Database password (default: password)
```

### Service-Specific Databases

| Service | Database Name |
|---------|---------------|
| User Service | usersdb |
| Restaurant Service | restaurantsdb |
| Order Service | ordersdb |
| Payment Service | paymentsdb |
| Notification Service | None (stateless) |

### Connection String Example
```
jdbc:mysql://localhost:3306/usersdb?useSSL=false&serverTimezone=UTC
```

### Production Configuration

For production deployments using AWS:
- Database host and credentials will be supplied via **AWS Secrets Manager** or **Kubernetes Secrets**
- All environment variables will be injected through:
  - **AWS ECS Task Definitions**
  - **Kubernetes ConfigMaps and Secrets**
  - **Helm values files**

## Build & Run Locally

### Prerequisites
- Java 21+
- Maven 3.9.4+
- MySQL 8.0+ (running locally or remotely)
- Docker (optional, for containerization)

### Build Individual Service

```bash
# Navigate to service directory
cd user-service

# Run tests
mvn clean test

# Package the application
mvn clean package

# Run the JAR
java -jar target/user-service-*.jar

# Or with environment variables
DB_HOST=localhost DB_PORT=3306 DB_NAME=usersdb \
DB_USERNAME=root DB_PASSWORD=password \
java -jar target/user-service-*.jar
```

### Build All Services

```bash
# From root directory
for service in user-service restaurant-service order-service payment-service notification-service; do
  cd $service
  mvn clean package
  cd ..
done
```

## Docker

### Build Docker Image

```bash
# Navigate to service directory
cd user-service

# Build Docker image
docker build -t user-service:1.0.0 .

# List built images
docker images | grep user-service
```

### Run Docker Container

```bash
# Run with default environment variables
docker run -p 8081:8081 user-service:1.0.0

# Run with custom environment variables
docker run -p 8081:8081 \
  -e DB_HOST=mysql.example.com \
  -e DB_PORT=3306 \
  -e DB_NAME=usersdb \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=securepassword \
  user-service:1.0.0

# Run in background
docker run -d -p 8081:8081 \
  --name user-service \
  user-service:1.0.0

# View logs
docker logs user-service

# Stop container
docker stop user-service
```

### Build All Docker Images

```bash
# From root directory
for service in user-service restaurant-service order-service payment-service notification-service; do
  cd $service
  docker build -t $service:1.0.0 .
  cd ..
done
```

## Health Checks

### Liveness Probe (Is the service alive?)
```bash
curl http://localhost:8081/actuator/health/liveness
```

### Readiness Probe (Is the service ready to accept traffic?)
```bash
curl http://localhost:8081/actuator/health/readiness
```

### Full Health Status
```bash
curl http://localhost:8081/actuator/health
```

### Application Info
```bash
curl http://localhost:8081/actuator/info
```

### Metrics
```bash
curl http://localhost:8081/actuator/metrics
```

### Specific Metrics
```bash
curl http://localhost:8081/actuator/metrics/jvm.memory.used
curl http://localhost:8081/actuator/metrics/http.server.requests
```

**Note**: These health endpoints are crucial for:
- AWS ALB health checks
- Kubernetes liveness and readiness probes
- Application troubleshooting
- Monitoring and alerting

## Current Project Status

### ✅ Completed
- [x] Java 21 compatible codebase
- [x] Spring Boot 3.3.1 application framework
- [x] Maven builds with proper configuration
- [x] Docker multi-stage builds for all services
- [x] Spring Boot Actuator enabled on all services
- [x] Global exception handling
- [x] Structured logging with SLF4J
- [x] Unit and integration tests
- [x] Environment-driven configuration
- [x] MySQL database integration (where applicable)
- [x] RESTful API endpoints
- [x] Input validation with Jakarta Bean Validation
- [x] Five independent microservices

### ⏳ Next Phase (Infrastructure & DevOps)
- [ ] AWS Terraform Infrastructure (separate repository)
- [ ] Jenkins CI/CD Pipeline
- [ ] SonarQube code quality analysis
- [ ] OWASP Dependency Check
- [ ] Trivy container scanning
- [ ] Kubernetes manifest files
- [ ] Helm charts
- [ ] Argo CD GitOps configuration
- [ ] Prometheus monitoring
- [ ] Grafana dashboards
- [ ] CloudWatch integration
- [ ] ELK/EFK centralized logging
- [ ] Security hardening (RBAC, Network Policies, Secrets Management)

## Architecture Diagram

### Current Application Architecture
```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT APPLICATIONS                      │
└────────────────────────────────────────────────────────────────┬┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
            ┌───────▼─────────┐     ┌──────▼────────┐
            │  REST Clients   │     │   API Tools   │
            │  (Postman, etc) │     │  (curl, wget) │
            └───────┬─────────┘     └──────┬────────┘
                    │                       │
                    └───────────┬───────────┘
                                │
                ┌───────────────▼───────────────┐
                │   API Gateway / Load Balancer │
                │  (Future: AWS ALB)            │
                └───────────────┬───────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
   ┌────▼─────┐         ┌──────▼──────┐         ┌─────▼────┐
   │   User   │         │ Restaurant  │         │  Order   │
   │ Service  │         │  Service    │         │ Service  │
   │(8081)    │         │  (8082)     │         │ (8083)   │
   └────┬─────┘         └──────┬──────┘         └─────┬────┘
        │                      │                     │
        │                      │                     │
   ┌────▼─────┐         ┌──────▼──────┐         ┌─────▼────┐
   │ usersdb  │         │restaurantsdb│         │ ordersdb │
   │(MySQL)   │         │  (MySQL)    │         │ (MySQL)  │
   └──────────┘         └─────────────┘         └──────────┘
        
        ┌──────────────────────┐
        │ Payment Service      │
        │     (8084)           │
        └──────────┬───────────┘
                   │
        ┌──────────▼───────────┐
        │  paymentsdb (MySQL)  │
        └──────────────────────┘

        ┌──────────────────────────┐
        │ Notification Service     │
        │     (8085)               │
        │   (Stateless - No DB)    │
        └──────────────────────────┘
```

### Future AWS Infrastructure
```
┌──────────────────────────────────────────────────────────────┐
│                    AWS CLOUD INFRASTRUCTURE                  │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │                    AWS VPC                             │ │
│  │                                                        │ │
│  │  ┌──────────────┐  ┌──────────────┐                   │ │
│  │  │ Public Subnet│  │ Private SN   │                   │ │
│  │  │ (ALB)        │  │ (Microservs) │                   │ │
│  │  └──────┬───────┘  └──────┬───────┘                   │ │
│  │         │                 │                            │ │
│  │  ┌──────▼─────────────────▼──────┐                    │ │
│  │  │ Application Load Balancer     │                    │ │
│  │  │ (Health Checks, Traffic Dist) │                    │ │
│  │  └──────────────┬─────────────────┘                   │ │
│  │                 │                                      │ │
│  │         ┌───────▼────────┐                            │ │
│  │         │  Target Groups │                            │ │
│  │         └───────┬────────┘                            │ │
│  │                 │                                      │ │
│  │         ┌───────▼────────────────────┐               │ │
│  │         │  AWS EKS (Kubernetes)      │               │ │
│  │         │  - Deployments             │               │ │
│  │         │  - Services                │               │ │
│  │         │  - Ingress                 │               │ │
│  │         │  - ConfigMaps/Secrets      │               │ │
│  │         │  - RBAC                    │               │ │
│  │         │  - Network Policies        │               │ │
│  │         └──────────────────────────┘               │ │
│  │                 │                                      │ │
│  │         ┌───────▼────────────────┐                   │ │
│  │         │  AWS RDS (MySQL)       │                   │ │
│  │         │  - Multi-AZ            │                   │ │
│  │         │  - Automated Backups   │                   │ │
│  │         └────────────────────────┘                   │ │
│  │                                                        │ │
│  │         ┌────────────────────────┐                    │ │
│  │         │  AWS ECR (Container)   │                    │ │
│  │         │  Registry              │                    │ │
│  │         └────────────────────────┘                    │ │
│  │                                                        │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  AWS Monitoring & Logging                              │ │
│  │  - CloudWatch (Logs, Metrics, Alarms)                  │ │
│  │  - ELK Stack (Elasticsearch, Logstash, Kibana)         │ │
│  │  - Prometheus (Metrics Collection)                     │ │
│  │  - Grafana (Visualization)                             │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

## CI/CD Pipeline Architecture (Future)

```
Developer
   │
   ├─── git push ──┐
   │               │
   │          ┌────▼──────────┐
   │          │  GitHub Repo  │
   │          │ food-ordering │
   │          └────┬──────────┘
   │               │
   │          ┌────▼──────────────────┐
   │          │  Jenkins (CI/CD)       │
   │          └────┬──────────────────┘
   │               │
   │          ┌────▼─────────────────────────┐
   │          │  Trigger Pipeline             │
   │          └────┬────────────────────────┘
   │               │
   │          ┌────▼──────────────┐
   │          │  Checkout Code    │
   │          └────┬──────────────┘
   │               │
   │          ┌────▼──────────────┐
   │          │  Maven Build      │
   │          │  - mvn clean test │
   │          │  - mvn package    │
   │          └────┬──────────────┘
   │               │
   │          ┌────▼──────────────────┐
   │          │  SonarQube Analysis   │
   │          │  - Code Quality       │
   │          │  - Test Coverage      │
   │          └────┬──────────────────┘
   │               │
   │          ┌────▼──────────────────────┐
   │          │  OWASP Dependency Check   │
   │          │  - Vulnerability Scan     │
   │          └────┬──────────────────────┘
   │               │
   │          ┌────▼──────────────┐
   │          │  Docker Build     │
   │          │  - Multi-stage    │
   │          │  - Image creation │
   │          └────┬──────────────┘
   │               │
   │          ┌────▼──────────────────┐
   │          │  Trivy Scan           │
   │          │  - Image Vulnerability│
   │          └────┬──────────────────┘
   │               │
   │          ┌────▼─────────────────┐
   │          │  Push to AWS ECR      │
   │          │  - Docker Registry    │
   │          └────┬─────────────────┘
   │               │
   │          ┌────▼──────────────────┐
   │          │  Update GitOps Repo   │
   │          │  - Update image tag   │
   │          └────┬──────────────────┘
   │               │
   └───────────────┤
                   │
            ┌──────▼──────────────┐
            │  GitOps Repo        │
            │ (Helm + ArgoCD)     │
            └──────┬──────────────┘
                   │
            ┌──────▼──────────────┐
            │  Argo CD            │
            │  - Continuous Deploy│
            └──────┬──────────────┘
                   │
            ┌──────▼──────────────┐
            │  AWS EKS            │
            │  - Deployment       │
            │  - Service Update   │
            └─────────────────────┘
```

## GitOps Workflow (Future with Argo CD)

```
Developer
   │
   ├─── Commits code to main branch
   │
   ├─── Jenkins CI Pipeline:
   │    ├─── Build
   │    ├─── Test
   │    ├─── Scan (SonarQube, OWASP, Trivy)
   │    └─── Push Docker image to ECR
   │
   └─── Jenkins updates GitOps repository
        └─── Updates Helm values with new image tag
             │
             └─── Argo CD detects change
                  │
                  └─── Automatically deploys to EKS
                       │
                       └─── Desired state = Actual state
```

## Future Kubernetes Components

When Kubernetes manifests are created, they will include:

- **Deployments**: For managing microservice pods
- **Services**: For internal and external service discovery
  - ClusterIP (internal communication)
  - NodePort (external access)
  - LoadBalancer (cloud load balancing)
- **Ingress**: For HTTP/HTTPS routing and SSL termination
- **ConfigMaps**: For non-sensitive configuration data
- **Secrets**: For sensitive data (database credentials, API keys)
- **Liveness Probes**: Restart unhealthy pods
- **Readiness Probes**: Route traffic only to ready pods
- **Resource Requests & Limits**: CPU and memory constraints
- **RBAC (Role-Based Access Control)**: Fine-grained permissions
- **Network Policies**: Restrict inter-pod communication
- **Persistent Volumes**: For stateful data
- **StatefulSets**: For stateful applications (if needed)
- **DaemonSets**: For node-level services (monitoring agents)
- **Horizontal Pod Autoscaling**: Auto-scale based on metrics

## Future Helm Charts

Kubernetes manifests will be packaged as Helm charts with:

- **Chart.yaml**: Chart metadata
- **values.yaml**: Configurable values
- **templates/**: Kubernetes resource templates
- **charts/**: Dependencies
- **Environment-specific values**: Dev, Staging, Production

Helm benefits:
- Template reusability across microservices
- Environment-specific deployments
- Easy version management
- One-command deployment: `helm install my-release ./food-ordering`

## Future Monitoring Stack

### Prometheus
- Scrapes metrics from `/actuator/metrics` endpoints
- Time-series database for metrics storage
- Query language (PromQL) for alerting and visualization

### Grafana
- Dashboards for visualizing Prometheus metrics
- Pre-built dashboards for:
  - JVM metrics (memory, GC, threads)
  - HTTP request metrics
  - Database connection pools
  - Custom application metrics

### CloudWatch
- AWS native monitoring service
- Integrated with EKS for pod/node metrics
- Log aggregation from all containers
- Custom metrics and alarms

### Alertmanager
- Alert routing and grouping
- Notification to Slack, PagerDuty, email, etc.
- Alert silencing and inhibition rules

Example metrics available:
```
- jvm.memory.used
- jvm.memory.max
- jvm.gc.memory.allocated
- http.server.requests (count, duration, exceptions)
- logback.events (total, errors, warnings)
- process.uptime
- system.load.average
```

## Future Logging Architecture

### ELK Stack (Elasticsearch, Logstash, Kibana)
- All container logs forwarded to centralized logging
- Elasticsearch indexes logs for fast searching
- Kibana provides dashboards and visualization

### EFK Stack (Elasticsearch, Fluent Bit/Fluentd, Kibana)
- Lighter weight alternative to ELK
- Fluent Bit for log forwarding
- Better suited for Kubernetes environments

Log sources:
- Application logs (stdout/stderr)
- Container runtime logs
- Kubernetes event logs
- AWS CloudWatch logs

Example log queries:
```
- All ERROR level messages in order-service
- All failed payment transactions
- All 5xx HTTP responses
- User registration attempts by timestamp
```

## Future DevSecOps Stages

### 1. Development Phase
- OWASP dependency check in CI/CD
- SonarQube for code vulnerabilities
- Local secret scanning (before commit)

### 2. Build Phase
- Trivy container image scanning
- Signed images (Docker Content Trust)
- SBOM (Software Bill of Materials)

### 3. Deployment Phase
- **RBAC**: Restrict pod permissions
- **Network Policies**: East-west traffic control
- **Pod Security Policies / Pod Security Standards**: Runtime security
- **Secrets Management**:
  - AWS Secrets Manager
  - HashiCorp Vault
  - Kubernetes Secrets (encrypted at rest)
- **Image Pull Secrets**: Authenticate with private registries

### 4. Runtime Phase
- **Falco**: Runtime security monitoring
- **Kyverno / OPA Gatekeeper**: Policy enforcement
- **Container scanning**: Detect vulnerable images at runtime
- **RBAC enforcement**: Audit pod access

### 5. Monitoring & Audit
- **CloudTrail**: Audit all AWS API calls
- **VPC Flow Logs**: Network traffic monitoring
- **Container runtime logs**: Docker/containerd logs
- **Kubernetes audit logs**: All API server requests

## Troubleshooting Guide

### Application Won't Start

**Symptoms**: Container exits immediately or service unreachable

**Diagnostics**:
```bash
# Check logs
docker logs user-service

# Check container status
docker ps -a

# If Kubernetes:
kubectl logs pod/user-service-xxx -n default

# Check health endpoint
curl http://localhost:8081/actuator/health
```

**Common Causes**:
1. **Wrong database credentials**
   - Verify `DB_HOST`, `DB_USERNAME`, `DB_PASSWORD`
   - Test MySQL connection: `mysql -h localhost -u root -p`

2. **Port already in use**
   - Check: `netstat -tuln | grep 8081`
   - Kill process: `lsof -i :8081` and `kill -9 <PID>`

3. **Database not running**
   - Start MySQL: `docker run -d -p 3306:3306 mysql:8.0`
   - Verify: `mysql -h localhost -u root -p -e "SHOW DATABASES;"`

4. **Java version mismatch**
   - Verify: `java -version` (must be Java 21)

### Database Connection Errors

**Error**: `Can't connect to MySQL server`

**Solutions**:
```bash
# Verify MySQL is running
ps aux | grep mysql

# Test connection
mysql -h localhost -u root -p

# Check MySQL logs
docker logs mysql-container

# Verify database exists
mysql -u root -p -e "SHOW DATABASES;"

# Create database if missing
mysql -u root -p -e "CREATE DATABASE usersdb;"
```

### Health Check Failures

**Error**: `GET /actuator/health returns 503`

**Causes**:
1. Database connectivity issue
2. Service startup incomplete
3. Resource exhaustion (memory/CPU)

**Debug**:
```bash
# Get detailed health status
curl http://localhost:8081/actuator/health

# Check specific health components
curl http://localhost:8081/actuator/health/db
curl http://localhost:8081/actuator/health/liveness
curl http://localhost:8081/actuator/health/readiness

# Monitor metrics
curl http://localhost:8081/actuator/metrics/jvm.memory.used
```

### Kubernetes Pod Crashes (CrashLoopBackOff)

**Debug**:
```bash
# Check pod status
kubectl get pods -n default

# View logs
kubectl logs pod/user-service-xxx -n default --previous

# Describe pod for events
kubectl describe pod user-service-xxx -n default

# Check resource usage
kubectl top pods -n default
```

**Common Causes**:
- Wrong environment variables
- Image not found in registry
- InsufficientMemory / OutOfCpu
- Liveness probe failing

### High Memory Usage

**Monitor**:
```bash
# Check JVM memory
curl http://localhost:8081/actuator/metrics/jvm.memory.used
curl http://localhost:8081/actuator/metrics/jvm.memory.max

# Check heap size
curl http://localhost:8081/actuator/metrics/jvm.memory.usage

# Full Prometheus-style metrics
curl http://localhost:8081/actuator/prometheus
```

**Solutions**:
- Increase heap size: `java -Xmx512m -jar app.jar`
- Optimize application code
- Implement caching
- Use resource limits in Kubernetes

### Slow API Responses

**Monitor**:
```bash
# Check HTTP request metrics
curl http://localhost:8081/actuator/metrics/http.server.requests

# Get request distribution
curl http://localhost:8081/actuator/metrics/http.server.requests?tag=uri:/orders

# Check database performance
curl http://localhost:8081/actuator/metrics | grep db
```

**Optimization**:
- Add database indexes
- Implement caching (Redis, memcached)
- Profile with Java Flight Recorder
- Check slow query logs in MySQL

## Installation & Setup

### Local Development Setup

```bash
# 1. Clone repository
git clone https://github.com/ravi2001-cell/food-ordering-microservices.git
cd food-ordering-microservices

# 2. Install Java 21
# macOS with Homebrew
brew install openjdk@21

# 3. Install Maven
brew install maven

# 4. Start MySQL
docker run -d \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_DATABASE=usersdb \
  --name mysql \
  mysql:8.0

# 5. Wait for MySQL to be ready
sleep 10

# 6. Build all services
for service in user-service restaurant-service order-service payment-service notification-service; do
  cd $service
  mvn clean package
  cd ..
done

# 7. Run services (in separate terminals)
cd user-service && java -jar target/user-service-*.jar &
cd restaurant-service && java -jar target/restaurant-service-*.jar &
cd order-service && java -jar target/order-service-*.jar &
cd payment-service && java -jar target/payment-service-*.jar &
cd notification-service && java -jar target/notification-service-*.jar &

# 8. Test services
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
curl http://localhost:8084/actuator/health
curl http://localhost:8085/actuator/health
```

## Best Practices Implemented

✅ **Code Quality**
- Clean architecture (Controller → Service → Repository)
- Separation of concerns
- Exception handling and validation
- Structured logging

✅ **Security**
- No hardcoded secrets
- Environment variable configuration
- Input validation (Jakarta Bean Validation)
- Secure HTTP status codes

✅ **Operational Excellence**
- Spring Boot Actuator for observability
- Health checks (liveness, readiness)
- Metrics collection
- Centralized exception handling

✅ **Docker Best Practices**
- Multi-stage builds (smaller images)
- Non-root user execution
- Proper .dockerignore
- Health check instructions

✅ **Database Design**
- Separate databases per service (database-per-service pattern)
- Proper entity relationships
- Timestamps (createdAt, updatedAt)
- Appropriate data types (BigDecimal for money)

✅ **Testing**
- Unit tests for services
- Integration tests for controllers
- Test database configuration
- Validation tests

## Future Improvements

### Application Level
- Inter-service communication (REST, gRPC)
- Distributed tracing (Jaeger, Zipkin)
- Circuit breaker pattern (Hystrix, Resilience4j)
- API rate limiting
- Caching strategy (Redis, Memcached)

### DevOps Level
- Automated infrastructure provisioning (Terraform)
- Full CI/CD pipeline (Jenkins)
- GitOps workflow (Argo CD)
- Multi-environment deployments
- Blue-green and canary deployments

### Security Level
- mTLS between services
- OAuth2/JWT authentication
- OWASP Top 10 compliance
- Vulnerability scanning and patching
- Secrets rotation policies

## License

Educational purposes - AWS DevOps/DevSecOps hands-on learning

## Author

Ravi (ravi2001-cell)

## Links

- **Repository**: https://github.com/ravi2001-cell/food-ordering-microservices
- **Issues & Discussions**: https://github.com/ravi2001-cell/food-ordering-microservices/issues

---

**Last Updated**: September 2, 2026
**Status**: Actively maintained for learning purposes
