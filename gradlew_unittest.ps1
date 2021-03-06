# $env:JAVA_HOME = "C:\Program Files\AdoptOpenJDK\jdk-11.0.7.10-hotspot"
$env:ANDROID_SDK_ROOT = "$env:USERPROFILE\AppData\Local\Android\Sdk"

# Jacoco
.\gradlew.bat test jacocoTestReport
if ($LastExitCode -ne 0) {
    Write-Output "Jacoco was failed by error code: $LastExitCode" 
    Write-Host -NoNewLine "Press any key to exit..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}
Start-Process "app\build\reports\jacoco\jacocoTestReport\html\index.html"

exit 0
