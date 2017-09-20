# ProgAssignment2Team6

## Team 6: Programming Assignment 2
Team members: Kaixin Zhang

## Required Tools:
1. Java Version: 1.8.0
2. Maven Version: 3.5.0
3. Git

## Download:
1. Maven: https://maven.apache.org/download.cgi
2. Git Download: https://git-scm.com/downloads
3. Eclipse Download: http://www.eclipse.org/downloads/packages/release/Mars/2  (Windows, MacOX and Linux)

## Installation Guide:
1. Maven: https://maven.apache.org/install.html
		For windows: https://www.mkyong.com/maven/how-to-install-maven-in-windows/
2. Git: https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
3. Eclipse: https://developer.atlassian.com/docs/getting-started/set-up-the-atlassian-plugin-sdk-and-build-a-project/set-up-the-eclipse-ide-for-linux

## Some useful tips for git:
Add existing project: 
https://help.github.com/articles/adding-an-existing-project-to-github-using-the-command-line/

Basic Git commands:
https://confluence.atlassian.com/bitbucketserver/basic-git-commands-776639767.html



Project Github Link:
https://github.com/zkx0804/ProgAssignment1Team6

## Add trec-car tool dependency to project
Please download the trec-car tool to your local disk, use terminal direct to ..\trec-car-tools\java1.7, and use command "mvn clean install" to install the jar to the local .m2 file. Then the project should be able to run locally.



## Run project using Maven in terminal
1. Make sure the treccar-tool-1.4.jar is in .m2 file. File should be .m2\repository\edu\unh\cs\treccar\treccar-tools\1.4
2. Clone the ProgAssignment2Team6 repo, then cd into the directory
3. Run maven command:    
    - mvn exec:java -Dexec.mainClass="main.GetRankingResult" -Dexec.args="true"
    - mvn exec:java -Dexec.mainClass="main.PercisionAtR" -Dexec.args="true"
4. Args:
    - true: Use lucene default scoring function
    - false: Use custom scoring function.


