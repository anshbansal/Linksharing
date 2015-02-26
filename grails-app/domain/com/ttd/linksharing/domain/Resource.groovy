package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

abstract class Resource {

    String description
    Date dateCreated
    Date lastUpdated

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [readingItem: ReadingItem, ratings: ResourceRating]

    static constraints = {
        description type: 'text'
        id composite: ['description', 'topic']
    }

    static mapping = {
        tablePerHierarchy false
    }

    static namedQueries = {
        recentPublicResources {
            'topic' {
                eq('scope', Visibility.PUBLIC)
            }
            order("dateCreated", "desc")
        }
    }
}
