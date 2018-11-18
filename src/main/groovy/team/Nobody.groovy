package team

/**
 * Created by mtumilowicz on 2018-11-18.
 */
class Nobody implements Worker {
    @Override
    def doTask() {
        throw new IllegalStateException("there is nobody to perform the task")
    }
}
