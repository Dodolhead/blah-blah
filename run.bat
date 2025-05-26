@echo off
echo [1] Deleting old class files...
del /S /Q out\*.class

echo [2] Compiling Java files...
javac -d out ^
src\endgame\*.java ^
src\gui\*.java ^
src\actions\*.java ^
src\entities\*.java ^
src\items\*.java ^
src\map\*.java ^
src\tsw\*.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b %errorlevel%
)

echo [3] Copying resources...
xcopy /E /I /Y res out\res

echo [4] Running program...
java -cp out src/gui.Main
