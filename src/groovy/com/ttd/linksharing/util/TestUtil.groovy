package com.ttd.linksharing.util

import com.ttd.linksharing.domain.DocumentResource
import com.ttd.linksharing.domain.LinkResource
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

class TestUtil {

    static Map getResourceMap(Topic topic, int it) {
        [description: "Dummy LinkResouce for topic ${topic.name} - ${it}",
         createdBy: topic.createdBy,
         topic: topic]
    }

    static User createUser(String emailAddress) {
        new User(email: emailAddress, username: emailAddress, password: 'igdefault', firstName: emailAddress)
    }

    static Topic createTopic(User user, int it) {
        new Topic(name: "topic${it}", createdBy: user)
    }

    static LinkResource createLinkResource(Topic topic, int it) {
        Map args = getResourceMap(topic, it) + [url: "http://www.google.com"]
        new LinkResource(args)
    }

    static DocumentResource createDocumentResource(Topic topic, it) {
        Map args = getResourceMap(topic, it) + [filePath: "~/abc.txt"]
        new DocumentResource(args)
    }

    static ReadingItem createReadingItem(User user, Resource resource) {
        new ReadingItem(user:  user, resource: resource)
    }
}
