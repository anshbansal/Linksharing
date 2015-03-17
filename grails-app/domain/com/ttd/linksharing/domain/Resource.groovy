package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility
import org.hibernate.FetchMode

abstract class Resource {

    String description
    Date dateCreated
    Date lastUpdated

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [readingItems: ReadingItem, ratings: ResourceRating]

    static constraints = {
        description type: 'text'
        id composite: ['description', 'topic']
    }

    static mapping = {
        tablePerHierarchy false
    }

    static namedQueries = {
        recentResources {
            order("dateCreated", "desc")
        }

        resourcesHavingDescriptionIlike { String term ->
            ilike 'description', '%' + term + '%'
        }
        resourcesOfTopic { Topic topic ->
            eq 'topic', topic
        }

        resourcesOfPublicTopics {
            'topic' {
                publicTopics()
            }
        }

        resourcesOfPublicTopicsOrHavingTopicIds { List<Long> topicIds ->
            createAlias('topic', 't')
            or {
                eq 't.scope', Visibility.PUBLIC

                if (topicIds.size()) {
                    'in' 't.id', topicIds
                }
            }
        }

        resourcesForCreatorId { Long creatorId ->
            'createdBy' {
                eq 'id', creatorId
            }
        }
    }
}
