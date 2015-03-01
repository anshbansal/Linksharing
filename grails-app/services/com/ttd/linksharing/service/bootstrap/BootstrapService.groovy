package com.ttd.linksharing.service.bootstrap

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

import static com.ttd.linksharing.util.TestUtil.createDocumentResource
import static com.ttd.linksharing.util.TestUtil.createLinkResource
import static com.ttd.linksharing.util.TestUtil.createTopic
import static com.ttd.linksharing.util.TestUtil.createUser


@Transactional
class BootstrapService {

    def userService
    def topicService
    def resourceService
    def readingItemService

    def init() {
        2.times {
            User user = createUser("aseem${it}@aseem.com")
            user.admin = true
            userService.save(user)

            5.times {
                Topic topic = createTopic(user, it)
                topicService.save(topic)

                5.times {
                    resourceService.save(createLinkResource(topic, it))
                    resourceService.save(createDocumentResource(topic, it + 5))
                }
            }

            readingItemService.findForUser(user).each { ReadingItem item ->
                if (item.id % 3 == 0) {
                    item.isRead = true
                }
                readingItemService.save(item)
            }
        }
    }
}
