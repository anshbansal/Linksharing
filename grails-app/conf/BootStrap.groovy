import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

import static com.ttd.linksharing.util.TestUtil.*

class BootStrap {

    def init = { servletContext ->
        2.times {
            User user = createUser("aseem${it}@aseem.com")
            user.save()

            5.times {
                Topic topic = createTopic(user, it)
                topic.save()

                5.times {
                    createLinkResource(topic).save()
                    createDocumentResource(topic).save()
                }
            }

            ReadingItem.findAllWhere(user: user).each { ReadingItem item ->
                if (item.id % 3 == 0) {
                    item.isRead = true
                    item.save()
                }
            }
        }
    }

    def destroy = {
    }
}