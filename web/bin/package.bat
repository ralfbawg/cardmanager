
echo.
echo [信息] 打包Web工程，生成war/jar包文件。
echo.

%~d0
cd %~dp0

cd ..
call mvn clean package spring-boot:repackage -Dmaven.test.skip=true -U

pause