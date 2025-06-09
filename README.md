<Directory Structure> 
- domain 
    - user



<Test>
Controller Layers 
    - Mostly unit tests for controller level validation (eg. using @Valid)
Service Integration DB Layers 
    - When logic deals with db constraints, triggers etc
    - extend "DBIntegrationTestHelper" to use MariaDB TestContainer setup 


