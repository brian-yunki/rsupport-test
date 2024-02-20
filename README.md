# RSUPPORT TEST 

## :gear: 개발환경
- Intellj IDEA (2023.3.2 Ultimate Edition)
- Spring Boot 3.2.2
- Java 21
- Gradle 8.5
- MySQL Community Edition (8.3.0) 
- Etc 
  - AqueryTool (free erd) 


### :thinking: 구현(운영) 환경에 대한 선정
Spring Boot 3.2.x 버전이 (23.11.23) 릴리즈 되었으며 24.11.23 까지 EOF (End Of Support) 되기 때문에 3.2.x 의 최근버전을 선택했습니다.
관련하여 Java Version 도 21 로 변경하며 사용하는 JDK (Java Development Kit) 의 제공벤더에 맞게 빌드 스크립트를 약간 수정합니다. 
사용하는 OpenJDK 는 [Eclipse Adotium](https://adoptium.net/temurin/releases/) 이며 brew cask 를 이용하여 설치했습니다. 

:link: https://spring.io/projects/spring-boot#support  
:link: https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.system-requirements  

:heavy_exclamation_mark: 만약 설치된 벤더가 다르다면  `vendor = JvmVendorSpec.ADOPTIUM` 구분을 주석처리 해주세요.

### :thinking: 프레임워크 (Persistance & Heavy Traffic)
Hibernate & JPA (Java Persistence API) 는 동기(blocking) ORM 프레임워크라 고민을 많이 했습니다. 
JD (Job Description) 와 과제 요구사항 모두 Hibernate 사용 요구사항이 있었기 때문에 R2DBC 와 Hibernate Reactive 를 모두 고민했습니다.

- Spring Webflux + R2DBC (MySQL)
  - Hibernate 요구사항 (제약)
  - 연관관계 (제약)
  - 페이지네이션 (제약)
  - 기존 프로젝트 마이그레이션 어려움 (굳이 변경하려면 Hibernate Reactive 를 사용하라고 함)
- Hibernate Reactive + Vert.x 
  - Spring Boot 미지원 (제약)
  - Vert.x 사용 (제약)

위 두가지 사용에 대해서 고려를 했으나 모두 요구사항에 부합되지 않는 것으로 판단했습니다. 
Spring Webflux + R2DBC 의 경우 [MySQL](https://spring.io/projects/spring-data-r2dbc) 까지 지원하기 때문에 편리하나 여러 제약사항으로 제외하였으며 Hibernate Reactive + Vert.x 는 Spring 미지원과 Vert.x 사용등 러닝커브가 작용함. 
Hibernate Reactive + Vert.x 의 경우 LINE의 개발자가 Kotiln으로 진행한 내용이 있었으며 Stack overflow 등에 자료를 참고하여 구현해 본 결과 Hibernate 의 psersistance 구성이 xml로 구성되고 Entity 및 Repository의 Component Scan 등을 지원하기에는 구성과 설정에 시간이 소요될 것으로 예상되어 제외하였습니다.

### :grin: 선택한 구성 
이번 과제에서는 Spring Boot 와 JPA (Hibernate)의 일반적인 구성으로 진행하는 것이 좋을 것으로 판단하고 진행했습니다. 
  - spring-boot-starter-data-jpa
  - spring-boot-starter-web


--- 
## :coffee: 구현과정 

### 도메인설계 및 테이블 설계 
대략적인 도메인설계 후 데이터베이스 표준화치침에서 요구하는 단어사전과 용어사전을 만들고 용어 사전을 기반으로 테이블 필드를 구성했습니다. 
![img.png](doc/img-01.png)

