pipeline {
    environment {
        DOCKER_HOST = 'tcp://192.168.1.11:2375'
    }
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build | API') {
            steps {
                dir("Kwetter API") {
                    sh 'mvn -B -DskipTests clean package'
                    archiveArtifacts artifacts: 'target/', fingerprint: true
                }
            }
        }
        stage('Build image | API') {
            steps {
                dir("Kwetter API") {
                    sh 'mvn clean package docker:build -DskipTests'
                }
            }
        }
        stage('Build image | Angular') {
            steps {
                dir("Kwetter-Angular") {
                    sh 'docker build -t ma.ade/KwetterAngular:latest -t ma.ade/KwetterAngular:1.0 -f Dockerfile'
                }
            }
        }
        stage('Unittests & Sonarqube | API') {
            steps {
                dir("Kwetter API") {
                    configFileProvider([configFile(fileId: '568fd3ab-9b40-4b96-803c-9bd2bf3ce12b', variable: 'SonarSettings')]) {
                        sh 'mvn -s $SonarSettings clean package sonar:sonar -B'
                    }
                }
            }
        }
        stage('Integration tests | API') {
            steps {
                dir("Kwetter API") {
                    sh 'mvn clean verify'
                }
            }
        }
        stage('Artifactory | API') {
            steps {
                dir("Kwetter API") {
                     configFileProvider([configFile(fileId: 'b7d2e7fe-2005-44f6-bde2-77587a78c6a2', variable: 'ArtifactorySettings')]) {
                         sh 'mvn -s $ArtifactorySettings clean package deploy -DskipTests -B'
                     }
                }
            }
        }
        stage('Deploy | API | Dev') {
            when {
                branch 'dev'
            }
            steps {
                dir("Kwetter API") {
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/dev.kwetter/stop'
                    sh 'curl -v -X DELETE http://192.168.1.11:2375/containers/dev.kwetter'
                    sh 'curl -v -X POST -H "Content-Type: application/json" -d \'{"Image": "ma.ade/kwetter2:latest","ExposedPorts": {"8080/tcp": { "HostPort": "59388" }}}\' http://192.168.1.11:2375/containers/create?name=dev.kwetter'
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/dev.kwetter/start'
                }
            }
        }
        stage('Deploy | API | Master') {
            when {
                branch 'master'
            }
            steps {
                dir("Kwetter API") {
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/kwetter/stop'
                    sh 'curl -v -X DELETE http://192.168.1.11:2375/containers/kwetter'
                    sh 'curl -v -X POST -H "Content-Type: application/json" -d \'{"Image": "ma.ade/kwetter2:latest","ExposedPorts": {"8080/tcp": { "HostPort": "5938" }}}\' http://192.168.1.11:2375/containers/create?name=kwetter'
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/kwetter/start'
                }
            }
        }
        stage('Deploy | Angular | Dev') {
            when {
                branch 'dev'
            }
            steps {
                dir("Kwetter-Angular") {
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/dev.kwetter.angular/stop'
                    sh 'curl -v -X DELETE http://192.168.1.11:2375/containers/dev.kwetter.angular'
                    sh 'curl -v -X POST -H "Content-Type: application/json" -d \'{"Image": "ma.ade/KwetterAngular:latest","ExposedPorts": {"4200/tcp": { "HostPort": "4201" }}}\' http://192.168.1.11:2375/containers/create?name=dev.kwetter.angular'
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/dev.kwetter.angular/start'
                }
            }
        }
        stage('Deploy | Angular | Master') {
            when {
                branch 'master'
            }
            steps {
                dir("Kwetter-Angular") {
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/kwetter.angular/stop'
                    sh 'curl -v -X DELETE http://192.168.1.11:2375/containers/kwetter.angular'
                    sh 'curl -v -X POST -H "Content-Type: application/json" -d \'{"Image": "ma.ade/KwetterAngular:latest","ExposedPorts": {"4200/tcp": { "HostPort": "4201" }}}\' http://192.168.1.11:2375/containers/create?name=kwetter.angular'
                    sh 'curl -v -X POST http://192.168.1.11:2375/containers/kwetter.angular/start'
                }
            }
        }
        stage('Success') {
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                }
            }
        }
    }
    post {
        changed {
            script {
                if (currentBuild.result == 'FAILURE') {
                    step([$class           : 'Mailer',
                          recipients       : emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                                 [$class: 'RequesterRecipientProvider']]),
                          sendToIndividuals: true])
                }
                if (currentBuild.result == 'SUCCESS') {
                    step([$class           : 'Mailer',
                          recipients       : emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                                 [$class: 'DevelopersRecipientProvider']]),
                          sendToIndividuals: true])
                }
            }
        }
    }
}
