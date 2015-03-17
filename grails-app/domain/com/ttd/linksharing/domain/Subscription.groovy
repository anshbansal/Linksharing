package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness
import com.ttd.linksharing.enums.Visibility

class Subscription {
    Seriousness seriousness = Seriousness.SERIOUS
    Date dateCreated

    static belongsTo = [user: User, topic: Topic]

    static constraints = {
        seriousness nullable: true
        topic unique: 'user'
    }

    static namedQueries = {

        subscriptionsOfUser { User user ->
            eq 'user', user
        }

        subscriptionsOfTopic { Topic topic ->
            eq 'topic', topic
        }

        subscriptionsOfPublicTopics {
            'topic' {
                publicTopics()
            }
        }
        subscriptionsOfPublicTopicsOrHavingTopicIds { List<Long> topicIds ->
            createAlias('topic', 't')
            or {
                eq 't.scope', Visibility.PUBLIC

                if (topicIds.size()) {
                    'in' 't.id', topicIds
                }
            }
        }

        subscriptionsHavingTopicNameIlike { String term ->
            'topic' {
                topicsHavingNameIlike(term)
            }
        }
    }
}
