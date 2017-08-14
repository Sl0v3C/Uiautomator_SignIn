@echo off

adb wait-for-devices
set "err=%errorlevel%"
if "%err%"=="0" (
	echo "Begin to run the tests"
	adb shell am instrument -w -r --user 0 -e debug false -e class com.pyy.uitest.Sign com.pyy.uitest.test/android.support.test.runner.AndroidJUnitRunner > result.log 

	echo "Done!Please check the result.log."
) else (
	echo "Please put this script into the folder with adb.exe or add adb.exe path to PATH"
)
pause


