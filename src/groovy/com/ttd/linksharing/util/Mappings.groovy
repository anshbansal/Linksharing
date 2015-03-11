package com.ttd.linksharing.util

import com.ttd.linksharing.enums.Visibility

class Mappings {

    static List getScopes(Boolean includePrivates) {
        includePrivates ? Visibility.values() : [Visibility.PUBLIC]
    }

    static Long parseStringOrLong(def attr, Long defaultValue = 0) {
        if (! attr) {
            return defaultValue
        }

        if (attr.class == String.class) {
            return Long.parseLong(attr)
        } else {
            return attr
        }
    }
}
