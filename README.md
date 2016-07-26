# garden plotter


#### By _**Paul Hess**_

## Description

A visual planning tool for designing garden spaces.

## Setup/Installation Requirements

* _Clone this repository_
* _Install the [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Java SRE](http://www.java.com/en/)._
* _[Install gradle](http://codetutr.com/2013/03/23/how-to-install-gradle/)_
* _Open a terminal and run Postgres_
```
$ postgres
```
* _Open a new tab in terminal and create the `three_d_garden` database:_
```
$ psql
$ CREATE DATABASE three_d_garden;
$ psql three_d_garden < three_d_garden.sql
```
* _Navigate back to the directory where this repository has been cloned and run gradle:_
```
$ gradle run
```
* _Open localhost:4567 in a browser._

## Known Bugs



## Support and contact details

_To contact, leave a comment on Github._

## Technologies Used

* _Java_
* _JUnit_
* _FluentLenium_
* _Gradle_
* _Spark_
* _SQL_
* _JBCrypt _from mindrot.org_

### License

*MIT License*

Copyright (c) 2016 **_Paul Hess_**
