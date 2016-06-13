# mobitech_content_sdk_demo
Demo of Mobitech content SDK usage.
(Download apk here)
[![Download apk here](https://www.dropbox.com/s/fmki0a0dyweyvld/content_demo.apk?dl=1)]
To use Mobitech content SDK, follow these steps:

_ 1. Define dependency to Mobitech's content SDK in your app gradle.build file:
```sh
compile('io.mobitech:content_sdk:+@aar') {
        transitive = true
    }
```

_ 2. Initiate the SDK with a user ID (in Application class):
```sh
 ContentService.init(ContentDemoContext.this, userID);
```

_ 3. Use any of the classes in package io.mobitech.content.services to get data (see reference in this demo app)

[![See the demo video here](https://img.youtube.com/vi/ZqnXu4TB_Hc/0.jpg)](https://www.youtube.com/watch?v=ZqnXu4TB_Hc)
