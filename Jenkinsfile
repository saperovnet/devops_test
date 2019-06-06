node {
    // Get Artifactory server instance, defined in the Artifactory Plugin administration page.
    def server = Artifactory.server "GP"
    // Create an Artifactory Maven instance.
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo

    stage('Clone sources') {
       // git url: 'https://github.com/saperovnet/devops_test.git'
       checkout scm 
   }

    stage('Artifactory configuration') {
        // Tool name from Jenkins configuration
        rtMaven.tool = "maven"
        // Set Artifactory repositories for dependencies resolution and artifacts deployment.
        rtMaven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
    }

    stage('Maven build') {
        buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install'
    }

    stage('Publish build info') {
        server.publishBuildInfo buildInfo
    }
    
    stage ('Deploy') {
        sh "ssh root@10.128.0.11 'systemctl stop gp-app'"
        sh "scp target/example-*-SNAPSHOT.jar root@10.128.0.11:/opt/gp-test/gp-app.jar"
        sh "ssh root@10.128.0.11 'systemctl start gp-app'"
        sh "ssh root@10.128.0.11 'systemctl status gp-app'"
        sh "ssh root@10.128.0.16 'systemctl stop gp-app'"
        sh "scp target/example-*-SNAPSHOT.jar root@10.128.0.16:/opt/gp-test/gp-app.jar"
        sh "ssh root@10.128.0.16 'systemctl start gp-app'"
        sh "ssh root@10.128.0.16 'systemctl status gp-app'"
    }
    stage ('Http test'){
        sleep 10
        def backend1_response = httpRequest 'http://35.193.171.181:8080/up'
        def backend2_response = httpRequest 'http://34.66.138.84:8080/up'
        println("Backend1 status: "+backend1_response.status)
        println("Backend2 status: "+backend2_response.status)
    }
}
