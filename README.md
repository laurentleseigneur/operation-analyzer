# Bonita audit tool

## Purpose

This utility tools aims to detect some design patterns in processes that use BDM. 
It should be use the identify some patterns that may not work properly on latest Bonita BPM after migration of the platform.
 
## Checks 
 
The tools doses a lookup on all process definition, and point out (as warning) following patterns:
 
 
### Data definition
 
* a single process variable type is one of the BDM managed objects
* a single activity variable type is one of the BDM managed objects
* a multiple process variable uses a BDM query as default value
* a multiple activity variable uses a BDM query as default value

### Operations  
  
* a process data variable is assigned with a Business Object
* a activity data variable is assigned with a Business Object
* a process data variable is assigned with a BDM query
* a activity data variable is assigned with a BDM query
 
## Output
 
a report is generated, with two options : console output and/or a report file. The report is a json format.
  
example: 
  
```json
{
  "processReportWarnings" : [{
    "processName" : "myProcessName",
    "processVersion" : "1.0",
    "message" : "process variable 'vPerson' is defined with type 'com.company.model.Person' which is managed by BDM",
    "processDisplayName" : "my process display name"
  }]
}
```
## Usage

* unzip distribution
* copy bonita-client-sp-[YOUR_VERSION]jar and bonita-common-sp-[YOUR_VERSION]jar to the /lib directory. 
The jar are available on [https://customer.bonitasoft.com/download/products](Bonitasoft customer portal), depends of your version  
* configure a bonita-home to connect to your Bonita BPM platform. see [http://documentation.bonitasoft.com/search/site/bonita%20home](Bonitasoft documentation about bonita home)
* run with a json report file:

```shell
java -jar bonita-operation-analyzer-[VERSION].jar -bonitaHome [PATH_TO_BONITA_HOME] -user [TENANT_ADMIN_LOGIN] -password [TENANT_ADMIN_PASSWORD] -outputFile [PATH_TO_REPORT_FILE] 
```

* run with a console output:

```shell
java -jar bonita-operation-analyzer-[VERSION].jar -bonitaHome [PATH_TO_BONITA_HOME] -user [TENANT_ADMIN_LOGIN] -password [TENANT_ADMIN_PASSWORD] -outputConsole
```

* run with a console output and a json report file:

```shell
java -jar bonita-operation-analyzer-[VERSION].jar -bonitaHome [PATH_TO_BONITA_HOME] -user [TENANT_ADMIN_LOGIN] -password [TENANT_ADMIN_PASSWORD] -outputConsole -outputFile [PATH_TO_REPORT_FILE]
```

### Supported version





   
   
 
  
