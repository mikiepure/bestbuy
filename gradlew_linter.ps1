$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jre"
$env:ANDROID_SDK_ROOT = "$env:USERPROFILE\AppData\Local\Android\Sdk"

# CheckStyle
.\gradlew.bat checkstyle
if ($LastExitCode -ne 0) {
    Write-Error "CheckStyle was failed by error code: $LastExitCode"
    Write-Host -NoNewLine "Press any key to exit..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}
Start-Process "build\reports\checkstyle\checkstyle.html"

# SpotBugs
.\gradlew.bat spotbugsMain
if ($LastExitCode -ne 0) {
    Write-Error "SpotBugs was failed by error code: $LastExitCode"
    Write-Host -NoNewLine "Press any key to exit..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}
Start-Process "app\build\reports\spotbugs\main.html"

exit 0
