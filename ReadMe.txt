This is a springboot application.
Java Version - 1.8
Spring Boot Version - 2.3.3.RELEASE

APIs -

Download - Change the source path property in application.properties file to a valid file on you local, then hit the API from chrome or any browser of your choice. 
The file you passed will get downloaded to your downloads folder.

Delete - Pass the source file path in the POST request as given in swagger documentation and leave target file path as null.
The file at the given ath will get deleted.

Copy - Pass the source file path and target file path in the POST request as given in swagger documentation.
The file will get copied to target path.