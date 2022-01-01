@ECHO OFF
call mvn clean
call mvn test -P suite1
call mvn test -P suite2
call mvn test -P suite3
pause