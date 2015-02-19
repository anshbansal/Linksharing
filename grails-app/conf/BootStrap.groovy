import com.ttd.linksharing.domain.DocumentResource
import com.ttd.linksharing.domain.LinkResource
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.ResourceRating
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.enums.Visibility

class BootStrap {

    def init = { servletContext ->
        createUsers()
        createTopics()
        createResources()
    }

    def destroy = {
    }

    def createUsers = {
        new User(
                username: "Aseem",
                email: "aseem@aseem.com",
                firstName: "Aseem",
                password: "Aseem"
        ).save()

        new User(
                username: "Aseem2",
                email: "aseem2@aseem.com",
                firstName: "Aseem",
                password: "Aseem"
        ).save()
    }

    def createTopics = {
        User.list().each { User usr ->
            5.times {
                new Topic(name: "topic$it", createdBy: usr, scope: Visibility.PUBLIC).save()
            }
        }
    }

    def createResources = {
        Topic.list().each { Topic topic ->
            5.times {
                new LinkResource(
                        description: "Dummy LinkResouce-$it for topic ${topic.name}",
                        createdBy: topic.createdBy,
                        topic: topic,
                        url: "http://www.google.com"
                ).save()

                new DocumentResource(
                        description: "Dummy DocumentResource-$it for topic ${topic.name}",
                        createdBy: topic.createdBy,
                        topic: topic,
                        filePath: "~/abc.txt"
                ).save()
            }
        }
    }

    def createReadingItems = {
        User.list().each { User usr ->
            List<Resource> resources = Resource.findWhere(createdBy: usr)

        }
    }
}