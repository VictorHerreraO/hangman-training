package com.wizeline.academy.hangman.core.arch

abstract class UseCase<Params, ReturnValue> {
    abstract suspend operator fun invoke(params: Params): ReturnValue
}
