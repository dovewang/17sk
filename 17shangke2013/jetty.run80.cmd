cd/d  %~dp0
set  MAVEN_OPTS=-Xmx1024m -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=12345
mvn   -Dmaven.test.skip=true  jetty:run -Djetty.port=80