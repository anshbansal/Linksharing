package com.ttd.linksharing.job

import com.ttd.linksharing.domain.Invitation

class PurgeInvitationJob {
    static triggers = {
        cron name: 'EveryDayAt12AM', startDelay: 10000, cronExpression: "0 0 0 1/1 * ? *"
    }

    def execute() {
        Invitation.where {
            lastUpdated < (new Date() - 7)
        }.deleteAll()
    }
}
