package ru.egordubina.foodapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadPromoUseCase @Inject constructor() {
    operator fun invoke(): Flow<String> {
        return flow {
            emit("String 3")
        }
    }
}