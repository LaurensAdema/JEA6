pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
            reuseNode true
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                archiveArtifacts artifacts: 'target/', fingerprint: true
            }
        }
        stage('Build image') {
            steps {
                sh 'mvn clean package docker:build -DskipTests'
            }
        }
        stage('Test & Sonarqube') {
            steps {
                configFileProvider([configFile(fileId: 'f713633d-bca9-449b-98f3-2b2fff5297c5', variable: 'sonar-settings')]) {
                    sh 'mvn -s $sonar-settings clean package sonar:sonar -B'
                }
            }
        }
        stage('Artifactory') {
            steps {
                configFileProvider([configFile(fileId: 'artifactory-settings', variable: 'artifactory-settings')]) {
                    sh 'mvn -s $artifactory-settings clean package deploy -DskipTests -B'
                }
            }
        }
//        stage('Deploy stack test') {
//            agent {
//                docker {
//                    image 'docker:17.12-dind'
//                    args '-v /var/run/docker.sock:/var/run/docker.sock'
//                    reuseNode true
//                }
//            }
//            when {
//                branch 'sop'
//            }
//            steps {
//                sh 'docker stack deploy -c config/test-stack.yml kwetter-test'
//            }
//        }
//            steps {
//                sh 'mvn clean verify'
//            }
//        }
        stage('Integration tests') {
            steps {
                sh 'mvn clean verify'
            }
        }
//        stage('Deploy stack master') {
//            agent {
//                docker {
//                    image 'docker:17.12-dind'
//                    args '-v /var/run/docker.sock:/var/run/docker.sock'
//                    reuseNode true
//                }
//            }
//            when {
//                branch 'dev'
//            }
//            steps {
//                sh 'docker stack deploy -c config/stack.yml kwetter'
//            }
//        }
        stage('success') {
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
