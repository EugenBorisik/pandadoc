# Pandadoc example

## Technology stack
Programming language: **Java**  
Build tool: **Maven**  
Browser automation: **Selenium WebDriver**  
Calls to REST api: **OkHttp3**  
Unit testing framework: **TestNG**  
Reporting: **Allure**  

## Before run
You need to set three parameters before test execution in _src/main/resources/application.properties_:
* user.id - username to login in pandadoc app
* user.password - password to login is pandadoc app
* api.key - API-KEY value from user settings to have possibility to execute calls to api

## How to run tests
To run all tests:  
`mvn clean test`  
To run some suite:  
`mvn clean test -DsuiteXmlFile=suites/ui.xml`  
To see report:  
`mvn allure:serve`