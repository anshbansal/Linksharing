package com.ttd.linksharing.util

class ServiceUtil {

    static Boolean validateAndSave(def domainObject, Map args) {
        domainObject.validate() && domainObject.save(args)
    }

}
