
echo.
echo [��Ϣ] ���Web���̣�����war/jar���ļ���
echo.

%~d0
cd %~dp0

cd ..
call mvn clean package spring-boot:repackage -Dmaven.test.skip=true -U

pause