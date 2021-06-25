# Instructions of Installation

## Considerations

* We strongly recommend you the use of IntellIJ or Eclipse IDE.

## Get Spring 2.4.5v

- Follow the instructions in the link below 
  - https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html
  
## Get Lombok

- Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
    - https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html
    
    Or you can add the dependency to your pom.xml file

## Add MariaDB dependency to your project
    
- Add the dependency to your pom.xml file

    `<groupId>org.mariadb.jdbc</groupId>
     <artifactId>mariadb-java-client</artifactId>
	 <scope>runtime</scope> 
     <version>2.7.3</version>`
    