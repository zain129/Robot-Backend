# Robot Backend
This repository contains the backend implementation for the assignment provided by Idealo.

### API
This project consists of two POST API endpoint.

#### [POST] /execute-script
* Request Body <br>
  `CommandRequestDTO Object`
  * `{ "stringCommand": "FORWARD 3", "currentRowPosition": 1, "currentColPosition": 1, "facePosition": "DOWN" }`
* Response <br>
  `CommandResponseDTO object`
  * `{ "operationType": "FORWARD", "movingSteps": 3, "otherInfo": null, "newRowPosition": 1, "newColPosition": 4 }`

#### [POST] /execute-all-commands
* Request Body <br>
  `List of CommandRequestDTO Objects`
  * `[{ "stringCommand": "FORWARD 3", "currentRowPosition": 1, "currentColPosition": 1, "facePosition": "DOWN" }]`
* Response <br>
  `List of CommandResponseDTO Objects`
  * `[{ "operationType": "FORWARD", "movingSteps": 3, "otherInfo": null, "newRowPosition": 1, "newColPosition": 4 }]`

### Explanation
* The purpose of this project is to facilitate the core functionality of the robot application. <br>
* It features a POST method designed to receive and process execution scripts. <br>
* Upon successful validation of the execution commands, a list of command objects is returned. <br>
* In case of incorrect execution commands, an exception is thrown to handle the error condition. <br>


### Summary
#### What was expected?
* To create the backend of an application which will execute commands to move a robot in a grid of 5x5.
* To add test cases in order to ensure that the code written is working as expected.

#### What is done?
* Added a POST API, which takes the command and return back a response after processing it.
* In case of invalid command, a null response will be returned.
* Test cases are included to ensure proper working of the backend of the system.

#### What is next?
* Code review will be done.
* Review comments will be resolved (if any).
* A pull-request will be raised to merge code from develop branch into master branch.

<br/>

#### Credits
* https://stackoverflow.com
* https://docs.diffblue.com
