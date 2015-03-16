package com.ttd.linksharing.util

import com.ttd.linksharing.enums.Visibility

import static org.hibernate.criterion.Restrictions.ilike

class NamedQueries {

    static Closure nameLike() {
        return { String term -> ilike 'name', '%' + term + '%' }
    }

    static Closure publicScope() {
        return { eq 'scope', Visibility.PUBLIC }
    }

}
