package br.com.raphaelmaracaipe.portfolio.data

import br.com.raphaelmaracaipe.portfolio.data.flow.alertmessage.AlertMessageFlow
import br.com.raphaelmaracaipe.portfolio.data.flow.di.FlowModule
import javax.inject.Inject

class AlertMessageRepository @Inject constructor(
    @FlowModule.AlertMessage private val alertMessageFlow: AlertMessageFlow
) {
}