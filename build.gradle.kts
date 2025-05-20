plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.jobfinder"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("mysql:mysql-connector-java:8.0.33")  // Kết nối với MySQL
	implementation ("org.mapstruct:mapstruct:1.5.2.Final") // Chọn phiên bản mới nhất
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	compileOnly("org.projectlombok:lombok:1.18.30")
	annotationProcessor ("org.projectlombok:lombok:1.18.30")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation ("javax.xml.bind:jaxb-api:2.3.1")
	implementation ("org.glassfish.jaxb:jaxb-runtime:2.3.1")
	// https://mvnrepository.com/artifact/org.modelmapper/modelmapper
	implementation("org.modelmapper:modelmapper:3.1.1")
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
}

tasks.withType<Test> {
	useJUnitPlatform()  // Đảm bảo sử dụng JUnit Platform cho việc kiểm thử
}
