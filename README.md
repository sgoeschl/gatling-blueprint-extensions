# Introduction

This project contains some utility methods I found useful for Gatling projects

* Pretty-Printing Boon JSON objects
* Hierarchical Directory Search
* Simulation coordinates


# Pretty-Printing Boon JSON Objects

Since I'm using Gatling also for functional testing some JSON handling would be extremly helpful

* Pretty-print the JSON to have a meaningful diff
* Remove some parts of the JSON objects on the fly, e.g. UUID or timestamps

Usage is straight-forward

```
// Pretty-print JSON
FilteringJsonPrettyPrinter.print(departmentJson);

// Pretty-print and remove "id" element
FilteringJsonPrettyPrinter.print(departmentJson, "id");

// Pretty-print and remove "employees" tree
FilteringJsonPrettyPrinter.print(departmentJson, "employees");
```

Please note that removing of JSON elements do not use JSON path expression - that would be awesone but difficult to implement.


# Hierarchical Directory Search

A common pattern to configure test scenarios is to pick up configuration files along a directory tree - you start at a leaf directory and walk up until you hit the configured root directory

* Locate the first matching CSV file containing user credentials
* Locate a list of property files to merge them in the correct order

This is implemented by [HierarchicalFileLocator](https://github.com/sgoeschl/gatling-blueprint-extensions/blob/master/src/main/java/org/github/sgoeschl/gatling/blueprint/extensions/file/HierarchicalFileLocator.java) and [PropertiesResolver](https://github.com/sgoeschl/gatling-blueprint-extensions/blob/master/src/main/java/org/github/sgoeschl/gatling/blueprint/extensions/file/HierarchicalFileLocator.java).


# Simulation Coordinates

This is a rather abstract concept to identify a test scenario being executed by

* tenant
* application
* site
* scope

The [SimulationCoordinates](https://github.com/sgoeschl/gatling-blueprint-extensions/blob/master/src/main/java/org/github/sgoeschl/gatling/blueprint/extensions/SimulationCoordinates.java) are used to implement managable multi-tenant performance tests

In short the `SimulationCoordinates` define a hierarchical directory tree which used to pick up various configuration files - tweaking the configuration of a complex test setup turns to providing and/or editing a configuration file.



