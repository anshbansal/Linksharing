import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

import static com.ttd.linksharing.util.TestUtil.*

class BootStrap {

    def init = { servletContext ->
        2.times {
            User user = createUser("aseem${it}@aseem.com")

            5.times {
                Topic topic = createTopic(user, it)
                user.addToTopics(topic)

                5.times {
                    topic.addToResources(createLinkResource(topic))
                    topic.addToResources(createDocumentResource(topic))
                }
            }

            user.readingsItems.each { ReadingItem item ->
                if (item.id % 3 == 0) {
                    item.isRead = true
                }
            }
            user.save(failOnError: true, flush: true)
        }
    }

    def destroy = {
    }
}