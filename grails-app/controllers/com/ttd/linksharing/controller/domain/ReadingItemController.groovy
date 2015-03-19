package com.ttd.linksharing.controller.domain

class ReadingItemController {

    def readingItemService

    def markAsRead() {
        Boolean isRead = params.readUnread == 'markUnread'
        Long resourceId = params.int('resourceId')

        Boolean status = readingItemService.toggleResourceReadStatus(session?.loggedUser, resourceId, isRead)

        if (status) {
            if (isRead) {
                render "markedUnread"
            } else {
                render "markedRead"
            }
        } else {
            render "Error"
        }

    }
}
