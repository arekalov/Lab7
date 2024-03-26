plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.arekalov"

repositories {
    mavenCentral()
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml") // Укажите необходимые модули JavaFX
}

dependencies {
    implementation("org.openjfx:javafx-controls:17") // Зависимость на JavaFX Controls
    implementation("org.openjfx:javafx-fxml:17") // Зависимость на JavaFX FXML
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}