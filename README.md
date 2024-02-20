# RSUPPORT TEST 

## 개발환경
- Intellj IDEA (2023.3.2 Ultimate Edition)
- Spring Boot 3.2.2
- Java 21
- Gradle 8.5
- MySQL Community Edition (8.3.0) 


### 구현(운영) 환경에 대한 선정
Spring Boot 3.2.x 버전이 (23.11.23) 릴리즈 되었으며 24.11.23 까지 EOF (End Of Support) 되기 때문에 3.2.x 의 최근버전을 선택했습니다.
관련하여 Java Version 도 21 로 변경하며 사용하는 JDK (Java Development Kit) 의 제공벤더에 맞게 빌드 스크립트를 약간 수정합니다. 
사용하는 OpenJDK 는 [Eclipse Adotium](https://adoptium.net/temurin/releases/) 이며 brew cask 를 이용하여 설치했습니다. 

:heavy_exclamation_mark: 만약 설치된 벤더가 다르다면  `vendor = JvmVendorSpec.ADOPTIUM` 구분을 주석처리 해주세요.


> https://spring.io/projects/spring-boot#support  
> https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.system-requirements 