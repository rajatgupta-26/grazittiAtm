# Grazitti Atm
Atm machine test

### Prerequisites
* Maven - you can easily install it on MAC OS X by
```
$ brew install maven
```

### Running project 
* Clone and go to the project by running command
```
$ git clone git@github.com:Skhwan/atm-simulator.git
$ cd atm-simulator
```

* Then prepare project dependencies with
```
$ mvn clean install
```

* Run project using command
```
$ mvn spring-boot:run
```

The project should start on port 8010

### API structure
1. Default(Without Currency Specified)
```
http://localhost:8010/grazitti-atm/dispense?amount=2020
```
Response
```
{"responseCode":"0","responseDesc":"SUCCESS","responseStatus":"SUCCESS","currencyBreakdown":"2000 : 1,20 : 1,"}
```

2. With Currency Specified
```
http://localhost:8010/grazitti-atm/dispense?amount=2020&requiredNotes=100,20
```
Response
```
{"responseCode":"0","responseDesc":"SUCCESS","responseStatus":"SUCCESS","currencyBreakdown":"100 : 20,20 : 1,"}```
