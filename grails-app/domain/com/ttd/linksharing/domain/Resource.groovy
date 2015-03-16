package com.ttd.linksharing.domain

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

            fetchMode('createdBy', FetchMode.JOIN)
        }

        publicResources {
            'topic' {
                publicTopics()
            }
            fetchMode('topic', FetchMode.JOIN)
        }

        showResourceToUser { User user ->
            or {
                'user' {
                    isAdmin()
                }
                'topic' {
                    showTopicToUser()
                }
            }
            fetchMode('topic', FetchMode.JOIN)
            fetchMode('user', FetchMode.JOIN)
        }

        forUserId { Long userId ->
             'createdBy' {
                 eq 'id', userId
             }
            fetchMode('createdBy', FetchMode.JOIN)
        }

        forTopic { Topic topic ->
            eq 'topic', topic
        }

        descriptionLike { String term ->
            ilike 'description', '%' + term + '%'
        }
    }
}
