# EMPLOYEE - SERVICE #

## Startimi i aplikacionit: ##
 * ```docker-compose up```
* ```mvn spring-boot:run```

## Consumer ##
Konsumeri degjon per mesazhe nga tre topic te cilat dergohen nga nje kafka producer. Dergimi i te dhenave tek produceri behet ndermjet nje api. Struktura e konsumerit eshte e perbere nga nje @KafkaListener i cili degjon mesazhe nga tre topic: employee-general-information-topic, employee-qualification-topic employee-project-topic. Per secilen topic eshte i konfiguruar nje @KafkaHandler ku ne baze te tipit te eventit qe vjen tek listeneri ekzekutohet handler-i perkates.

## Exceptions ##
Trajtimi i exceptions te qenderzuar eshte implementuar me ane te nje klase ``EmployeeControllerAdvice.class`` e cila trashegon ``ResponseEntityExceptionHandler.class`` nga ku dy exception jan bere @Override nga klasa ResponseEntityExceptionHandler dhe tre tjere custom:

* ``HttpMediaTypeNotSupportedException.class`` ne rastin kur media type eshte e ndryshme nga application/json

* ``UnsupportedEventTypeException.class`` ne cdo request kerkohet nje header i quajtur X-EVENT-TYPE (INSERT,DELETE OSE UPDATE) ne rastin kur ky header mungon ose nuk eshte nga tipet e suportuara behet throw ky exception.

* ``MethodArgumentNotValidException.class`` ne rast kur fushat e kerkuara tek modelet nuk plotsohen ose nuk plotsojne kriteret e kerkuara

* ``EmployeeNotFoundException.class`` ne rastin kur punonjesi nuk ekziston

* ``ProjectNotFoundException.class`` ne rastin kur projekti nuk ekziston

## Logs ##
Aplikacioni ruan logs ne dy file system.logs dhe employee-service.logs, i pari ka level-in warn dhe ruan loget e root ndersa i dyti ka levelin info dhe ruan loget e shtuar tek konsumeri , produceri dhe exception. Per te shkruar loget ne file eshte perdorur logback.
## Api ##

Ne rastin kur shtohet nje informacion gjeneral krijohet nje punonjes i ri ne mongodb, me pas per te shtuar ose fshire kualifikimet ose projektet e punonjesit kerkohet si path variabel ID e punonjesit. Secili nga endpoint-et pervec ``/api/emloyee `` kerkon nje header  X-EVENT-TYPE (INSERT,UPDATE ose DELETE).

#### Punonjesit ####
* GET: /api/emloyee ``curl http://localhost:8080/api/employee``
#### Informacionet Gjenerale ####
* POST: /api/employee/general-information
 ``curl -H "Content-Type: application/json" -H "X-EVENT-TYPE: INSERT" -d '{"passportID":"passID","name":"name","surname":"surname","email":"email@yahoo.com","age":26}' -X POST http://localhost:8080/api/employee/general-information``
* PUT: /api/employee/{id}/general-information
 ``curl -H "Content-Type: application/json" -H "X-EVENT-TYPE: UPDATE" -d '{"passportID":"passID","name":"name","surname":"surname","email":"updatedEmail@yahoo.com","age":26}' -X POST http://localhost:8080/api/employee/general-information``
* DELETE: /api/employee/{id}/general-information
``curl -H "X-EVENT-TYPE: DELETE"  -X DELETE http://localhost:8080/api/employee/5f9032c99eb72e267e2d535e/general-information``
#### Kualifikimet ####
* POST: /api/employee/{id}/qualification
* insert:``curl -H "Content-Type: application/json" -H "X-EVENT-TYPE: INSERT" -d '["java","spring","mongodb","kafka"]' -X POST http://localhost:8080/api/employee/5f9032c99eb72e267e2d535e/qualification``
* delete:``curl -H "Content-Type: application/json" -H "X-EVENT-TYPE: DELETE" -d '["java","spring","mongodb","kafka"]' -X POST http://localhost:8080/api/employee/5f9032c99eb72e267e2d535e/qualification`` 
#### Projektet ####
* POST: /api/employee/{id}/project
 ``curl -H "Content-Type: application/json" -H "X-EVENT-TYPE: INSERT" -d '{"projectID":"projectID123","name":"project name","teamleader":"teamleader","frontDevelopers":["dev1","dev2"],"endDevelopers":["dev3","dev4"],"description":"project desc","startDate":"2020-10-12"}' -X POST http://localhost:8080/api/employee/5f9032c99eb72e267e2d535e/project`` 

* DELETE: /api/employee/{id}/project/{projectID}
``curl  -H "X-EVENT-TYPE: DELETE" -X DELETE http://localhost:8080/api/employee/5f9032c99eb72e267e2d535e/project/projectID123``


