# Robot Backend
This repository contains the backend implementation for the assignment provided by Idealo.

### API
This project consists of a single API endpoint.

#### [POST] /execute-script/
* Request Body <br>
  `List of String commands: e.g. { "FORWARD 3", "WAIT" }`
* Response <br>
  `List of CommandRspDTO object`

### Explaination
* The purpose of this project is to facilitate the core functionality of the robot application. <br>
* It features a POST method designed to receive and process execution scripts. <br>
* Upon successful validation of the execution commands, a list of command objects is returned. <br>
* In case of incorrect execution commands, an exception is thrown to handle the error condition. <br>


