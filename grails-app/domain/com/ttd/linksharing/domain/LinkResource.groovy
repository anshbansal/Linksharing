package com.ttd.linksharing.domain

class LinkResource extends Resource{

    String url

    static constraints = {
        url url: true
    }
}
