DCSA OVS (Parent)
================================================

Code standard
-------------------------------------
We use [Google Java Style](https://google.github.io/styleguide/javaguide.html).
When using IntelliJ it is recommended to download and activate the
[google-java-format plugin](https://github.com/google/google-java-format).

Clone and running using docker-compose
-----------------------------------------

**[RECOMMENDED]**
Setup a Github Personal Access Token as mentioned [here](https://github.com/dcsaorg/DCSA-Core/blob/master/README.md#how-to-use-dcsa-core-packages).

If you would like to build required DCSA packages individually, begin with step 1.

1) Clone **DCSA-OVS** (with ``--recurse-submodules`` option.)


2) Build and run with
```
mvn -U clean package
docker-compose up -d -V --build
```
(the database is automatically included when running with docker)

Building and running manually/locally
-------------------------------------

Initialize your local postgresql database as described in DCSA-Information-Model/datamodel/README.md,
then build and run with
```
mvn -U clean package
mvn -pl ovs-service -am spring-boot:run
```


Running from inside an IDE
-----------------------------------------
Initialize your local postgresql database as described in datamodel/README.md, make sure you
modify the run environment for the ```Application``` class so it includes the following environment
variable
```
SPRING_PROFILES_ACTIVE=dev
```
Then just start the ```Application``` class.


Branching and versioning
------------------------

The branching and devopment model is described
[here](https://dcsa.atlassian.net/wiki/spaces/DDT/pages/71204878/Development+flow+and+CI).
