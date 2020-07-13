# Best Buy

Android app to compare unit price.

## ToDo

- Emphasize best one

## Development

### Requirements

- Windows 10
- Adopt OpenJDK 11
  - Set environment variable "JAVA_HOME" to the OpenJDK
- Android Studio 4 with plugins:
  - [CheckStyle-IDEA](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/)
  - [Kotlin](https://plugins.jetbrains.com/plugin/6954-kotlin/)
  - [Save Actions](https://plugins.jetbrains.com/plugin/7642-save-actions/)
  - [SpotBugs](https://plugins.jetbrains.com/plugin/14014-spotbugs/)

### Settings for SpotBugs

Setting to use SpotBugs on Andriod Studio.

1. Select "File" > "Settings" and open Settings dialog
2. Select "Tools" > "SpotBugs" on sidebar
3. Uncheck "Compile affected files before analyze"

### Settings for unit test

Setting to use Adopt OpenJDK 11 by unit test on Andriod Studio.

1. Select "Run" > "Edit Configurations..." and open Run/Debug Configurations dialog
2. Select "Templates" > "Android JUnit" on sidebar
3. Set JRE: to JAVA_HOME of Adopt OpenJDK 11
