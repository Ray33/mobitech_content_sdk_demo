# Mobitech content SDK demo
Demo of Mobitech content SDK usage.

// TODO: update link
[(Download apk here)](https://www.dropbox.com/s/fmki0a0dyweyvld/content_demo.apk?dl=1)


To use Mobitech content SDK, follow these steps:

1. Define dependency to Mobitech's content SDK in your app gradle.build file:
```sh
compile('io.mobitech.content:content_api_sdk:+@aar') {
        transitive = true
    }
```

2. Initiate the RecommendationService from the SDK (in a place where you want to use it):
Example of simplest form of the initialization:
```java
RecommendationService recommendationService = RecommendationService.build(context, publisherKey, userId);
```
There are also another, more detailed options of initialization:
```java
RecommendationService.build(context, publisherKey, userId, userAgent, country);
RecommendationService.build(context, publisherKey, userId, userAgent, country, userIp);
RecommendationService.build(context, publisherKey, userId, userAgent, country, userIp, locale)
```
3. Use methods from the RecommendationService to get the data. You can use next methods:
 - getOrganicContent
 - getPromotedContent
 - getVideoContent
 - getMixedContent
 - getMixedVideoContent


// TODO: update video
[![See the demo video here](https://img.youtube.com/vi/ZqnXu4TB_Hc/0.jpg)](https://www.youtube.com/watch?v=ZqnXu4TB_Hc)
