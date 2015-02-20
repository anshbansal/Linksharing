import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

import static com.ttd.linksharing.util.TestUtil.*

class BootStrap {

    def init = { servletContext ->
        createUsers()
        createTopics()
        createResources()
    }

    def destroy = {
    }

    def createUsers = {
        2.times {
            createUser("aseem${it}@aseem.com").save(flush: true)
        }
    }

    def createTopics = {
        User.list().each { User user ->
            5.times {
                createTopic(user).save(flush: true)
            }
        }
    }

    def createResources = {
        Topic.list().each { Topic topic ->
            5.times {
                createLinkResource(topic).save(flush: true)
                createDocumentResource(topic).save(flush: true)
            }
        }
    }

    def createReadingItems = {
        User.list().each { User usr ->
            List<Resource> resources = Resource.findWhere(createdBy: usr)

        }
    }
}