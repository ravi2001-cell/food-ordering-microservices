pipeline {
    agent any
    tools {
        maven 'mymaven'
        nodejs 'mynode'
      //  sonarScanner 'Myscanner'
    }
    stages {
        stage('code checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ravi2001-cell/food-ordering-microservices.git'
            }
        }
        stage('build') {
            parallel {
                stage ('user-service') {
                    steps {
                        dir('user-service') {
                            sh 'mvn clean package'
                        }
                    }
                }
                stage ('Restaurant-service') {
                    steps {
                        dir('restaurant-service') {
                            sh 'mvn clean package'
                        }
                    }
                }
                stage ('Order-service') {
                    steps {
                        dir('order-service') {
                            sh 'mvn clean package'
                        }
                    }
                }
                stage ('Payment-service') {
                    steps {
                        dir('payment-service') {
                            sh 'mvn clean package'
                        }
                    }
                }
                stage ('Notification-service') {
                    steps {
                        dir('notification-service') {
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Frontend') {
                 steps {
                   dir('frontend') {
                       sh '''
                            node --version
                            npm --version
                            npm install
                            npm run build
                         '''
                          }
                     }
                }
            }
        }
       stage('CQA') {
    steps {
        withSonarQubeEnv('Mysonar') {

            dir('user-service') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=food-user'
            }

            dir('restaurant-service') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=food-restaurant'
            }

            dir('order-service') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=food-order'
            }

            dir('payment-service') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=food-payment'
            }

            dir('notification-service') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=food-notification'
            }
            dir('frontend') {
                 script {
                    def scannerHome = tool 'Myscanner'

                       sh """
                          ${scannerHome}/bin/sonar-scanner \
                          -Dsonar.projectKey=food-frontend \
                           -Dsonar.projectName=food-frontend \
                           -Dsonar.sources=src \
                              -Dsonar.exclusions=node_modules/**,dist/**
                           """
                 }
            }
        }
    }
}
        stage ('docker buid') {
            steps {
            sh 'docker build -t rkdocker1800/fms:user-v1 ./user-service'
            sh 'docker build -t rkdocker1800/fms:order-v1 ./order-service'
            sh 'docker build -t rkdocker1800/fms:restaurant-v1 ./restaurant-service'
            sh 'docker build -t rkdocker1800/fms:payment-v1 ./payment-service'
            sh 'docker build -t rkdocker1800/fms:notification-v1 ./notification-service'
            sh 'docker build -t rkdocker1800/fms:frontend-v1 ./frontend'
            }
        }
        stage ('trivy') {
            steps {
                    sh 'trivy image rkdocker1800/fms:user-v1'
                    sh 'trivy image rkdocker1800/fms:restaurant-v1'
                    sh 'trivy image rkdocker1800/fms:order-v1'
                    sh 'trivy image rkdocker1800/fms:payment-v1'
                    sh 'trivy image rkdocker1800/fms:notification-v1'
                    sh 'trivy image rkdocker1800/fms:frontend-v1'
            }
        }
        stage ('push') {
            steps {
                script {
                     withDockerRegistry(credentialsId: 'docker') {
                        sh 'docker push rkdocker1800/fms:user-v1'
                        sh 'docker push rkdocker1800/fms:restaurant-v1'
                        sh 'docker push rkdocker1800/fms:order-v1'
                        sh 'docker push rkdocker1800/fms:payment-v1'
                        sh 'docker push rkdocker1800/fms:notification-v1'
                        sh 'docker push rkdocker1800/fms:frontend-v1'
                    }
                }
            }
        }
       stage('Deploy') {
    steps {
        sh 'docker stack deploy -c docker-stack.yaml foodapp'
        sh 'docker stack services foodapp'
            }
        }
    }
}
