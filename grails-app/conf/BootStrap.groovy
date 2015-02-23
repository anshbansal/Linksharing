import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

import static com.ttd.linksharing.util.TestUtil.*

class BootStrap {

    def userService
    def topicService
    def resourceService
    def readingItemService

    def init = { servletContext ->
        2.times {
            User user = createUser("aseem${it}@aseem.com")
            userService.save(user)

            5.times {
                Topic topic = createTopic(user, it)
                topicService.save(topic)

                5.times {
                    resourceService.save(createLinkResource(topic, it))
                    resourceService.save(createDocumentResource(topic, it + 5))
                }
            }

            ReadingItem.findAllWhere(user: user).each { ReadingItem item ->
                if (item.id % 3 == 0) {
                    item.isRead = true
                }
                readingItemService.save(item)
            }
        }
    }

    def destroy = {
    }
}