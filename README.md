## Overview
------------

interpret-me is an interactive notebook server that let you execute code on the server side and get the result via rest web service. currently it support only python

## Background
-----------

Interactive notebooks are experiencing a rise in popularity. Notebooks offer an environment for Data scientists to comfortably share research, collaborate with others and explore and visualize data. The data usually comes from executable code that can be written in the client (e.g. Python, SQL) and is sent to the server for execution. Popular notebook technologies which this approach are [Apache Zeppelin](https://zeppelin.apache.org/) and [Jupyter Notebooks](http://jupyter.org/).



## Technical overview about python Interpreter
- Current python interpreter implementation spawns new system python process through ProcessBuilder and re-directs it's stdin\strout

- When interpreter is starting it launches a python process inside a Java ProcessBuilder. Python is started with -i (interactive mode) and -u (unbuffered stdin, stdout and stderr) options. Thus the interpreter has a "sleeping" python process.

- Interpreter sends command to python with a Java outputStreamWiter and read from an InputStreamReader. To know when stop reading stdout, interpreter sends print "**MARKER**"after each command and reads stdout until he receives back the **MARKER**.

## Features
----------

### Implemented Features
 execute python

- Request Body :
```javascript
{
	"code": "%python print 1+1"
}
```
- Response :
```javascript
{
    "result": "2",
    "sessionId": "1807293929"
}
```
preserve the state and variables from previous requests via the sessionId parameter


### Future Features
- support more interpreter like php, groovy, ... (you can simply support an inetrpreter by extending the abstract class Interpreter located in me.interpretme.entity package and implement execute method without forgoting to add @InterpreterType annotation)
- handle exception related to the interpreter
- create a web client to consume the execute endpoint


## Getting Started
-------------
- clone this repository
- cd the project directory and run
```
mvn clean spring-boot:run
```
hit the url localhost:8060/interpreter/execute and send a Post Request with a body like this

```javascript
{
	"code": "%python print 1+1"
}
```
if the port 8060 is already used you can change it in the application.properties to another available one

to execute integration tests run

```
mvn clean test
```
### Prerequisites

Python 2 or 3 installed

## DONE and not DONE
### Features
- [x] init spring boot 1.5.x project
- [x] build /execute endpoint that accept json and return json
- [x] add support to variable and state
- [x] add support to sessions via sessionId parameter
- [x] write and perform unit and integration tests
### Exceptions handling
- [x] if the piece of code cannot be parsed
- [x] if the type of the interpreter is not known
- [ ] if the interpreter takes long time to finish [not implemented yet]
- [ ] if the user code have side effect [not implemented yet]
- [ ] if the interpreter processs encounter some kind of error [not implemented yet]