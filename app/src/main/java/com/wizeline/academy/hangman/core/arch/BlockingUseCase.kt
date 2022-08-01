package com.soyvictorherrera.bdates.core.arch

abstract class BlockingUseCase<Params, ReturnValue> {
    abstract operator fun invoke(params: Params): ReturnValue
}
