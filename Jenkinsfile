node {
    // Get Artifactory server instance, defined in the Artifactory Plugin administration page.
    def server = Artifactory.server "GP"
    // Create an Artifactory Maven instance.
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo
    echo env.BRANCH_NAME

    stage('Clone sources') {
       // git url: 'https://github.com/saperovnet/devops_test.git'
       checkout scm 
   }

    stage('Artifactory configuration') {
        // Tool name from Jenkins configuration
        rtMaven.tool = "maven"
        // Set Artifactory repositories for dependencies resolution and artifacts deployment.
        rtMaven.deployer.addProperty("branch", env.BRANCH_NAME)
        rtMaven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server 
        rtMaven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
    }

    stage('Maven build') {
        buildInfo = rtMaven.run pom: 'pom.xml', goals: "clean install -Djar.finalName=example-0.0.1-${env.BRANCH_NAME}-SNAPSHOT"
    }

    stage('Publish build info') {
        buildInfo.name = env.BRANCH_NAME
        server.publishBuildInfo buildInfo
    }
   
}
