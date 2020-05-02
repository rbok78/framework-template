# Framework Template

![](https://github.com/rbok78/framework-template/workflows/Build/badge.svg)

Template for my automation projects

## Getting Started

These instructions will get you a copy of the project up and running on your local Linux machine for development and testing purposes.

### Prerequisites

OpenJDK version 11.0.7

Maven version 3.6.1

Chrome version 81

Git version 2.20.1

### Installing

Clone this project using `git clone git@github.com:rbok78/framework-template.git`

Download ChromeDriver from [here](https://chromedriver.storage.googleapis.com/81.0.4044.69/chromedriver_linux64.zip) and unpack into /usr/local/bin directory

Download Allure from [here](https://repo1.maven.org/maven2/io/qameta/allure/allure-commandline/2.13.3/allure-commandline-2.13.3.zip) and unpack into /opt/allure-2.13.3 directory

Create symlink using `sudo ln -s /opt/allure-2.13.3/bin/allure /usr/local/bin/`

## Running Tests

Run tests sequentially

    mvn clean test

Run tests in parallel

    mvn clean test -P parallel-execution
    
Run only tests with a specific tag e.g. smoke tests

    mvn clean test -P smoke-tests

Run tests on selenium grid

    mvn clean test -DgridUrl=http://localhost:4444/wd/hub

## Viewing Results

Create Allure report and open it in the browser

    allure serve target/allure-results/