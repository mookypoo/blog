<Directory Structure> 
- domain 
    - user



<Test>
Controller Layers 
    - Mostly unit tests for controller level validation (eg. using @Valid)
Service Integration DB Layers 
    - When logic deals with db constraints, triggers etc
    - extend "DBIntegrationTestHelper" to use MariaDB TestContainer setup 


<Database>
Foreign Key 
- FK_[child_table]_[parent_table]
- eg. FK_health_entry_pet, FK_activity_health_entry
