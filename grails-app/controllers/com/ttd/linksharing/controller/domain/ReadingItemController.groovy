package com.ttd.linksharing.controller.domain

class ReadingItemController {

    def markAsRead() {
        if (params.readUnread == 'markUnread') {
            render "markedUnread"
        } else {
            render "markedRead"
        }
    }

}
