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
        sh "scp target/example-0.0.1-SNAPSHOT.jar root@10.128.0.11:/opt/gp-test/gp-app.jar"
        sh "ssh root@10.128.0.11 'systemctl start gp-app'"
        sh "ssh root@10.128.0.11 'systemctl status gp-app'"
    }
    
}
