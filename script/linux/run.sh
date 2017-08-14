#!/bin/bash

echo "Blocked when adb device connected..."
adb wait-for-devices;

echo "Begin to run the tests"
adb shell am instrument -w -r --user 0 -e debug false -e class com.pyy.uitest.Sign com.pyy.uitest.test/android.support.test.runner.AndroidJUnitRunner > result.log

echo "Done!Please check the result.log."


