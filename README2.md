### Preconditions
* java 11+
* gradle 6+

### Run the tests
```shell
$ ./gradlew clean test -i
```

### Run the application
```shell
$ ./gradlew clean bootRun
```
The application will start running on port 8080 (http) with context path ''

Note: if you want to run it on different port and/or with different context path, please modify the file `src/main/resources/application.yml` accordingly.

### Run the e2e test
Once the application is running (see the step above), you can run the following command in another console
```shell
$ ./run-test_darwin_amd64
```
You'll see something similar to 
```text
PASS

== PLEASE INCLUDE THIS WITH YOUR SUBMISSION. ==
Well done! Your code is: hacker-c2masqma0brp9okjem2g-man
== PLEASE INCLUDE THIS WITH YOUR SUBMISSION. ==
```

### Extra for experts
1. Consider that the state returned may be maliciously manipulated by the client. How can we prevent this?
   
_Solution 1_: we can store the pair <`gameId`: `hash(state)`> in a DB every time we generate a `new` (fresh) state.
Then every time we get a `validation` request, we can check for the existence by `gameId` and integrity by `hash(state)`.

_Solution 2_: we can introduce a new field in the state, a type of message authentication code (MAC):
* when a new game is generated, the server returns its state with a new field hashcode, which is the HMAC of the state: HMAC_SHA256("secret_key", state)
* when a new validation request comes from a client, the server calculates the hash of the state, and compare it with the field hashcode that is inside the request.
This way the server can simultaneously verify both the data integrity and the authenticity of a message. If the validation pass, the server calculate the hash of the new state, and put it in the field hashcode of the new state.