# mobitech_content_sdk_demo
Demo of Mobitech content SDK usgage.

To use Mobitech content SDK, follow these steps:

1. Define dependency to Mobitech's content SDK in your app gradle.build file:
```sh
compile('io.mobitech:content_sdk:+@aar') {
        transitive = true
    }
```

2. Initiate the SDK with a user ID (in Application class):
```sh
 ContentService.init(ContentDemoContext.this, userID);
```

3. Use any of the classes in package io.mobitech.content.services to get data (see reference in this demo app)
    

