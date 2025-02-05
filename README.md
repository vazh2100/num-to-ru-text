### Installation

---
Add to `settings.gradle.kts`:
```
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add to `build.gradle.kts`:
```
dependencies {
    ...
    implementation("com.github.vazh2100:num-to-ru-text:v1.0.0")
}
```

### Usage

```kotlin
fun main() {
    val numToRu = NumToRu() // for cardinal numbers  // for ordinal numbers use NumToRu(ordinal = true) instead
    for (num in 0..999_999_999) {
        println(numToRu.convert(num))
        Thread.sleep(300)
    }
}
```
