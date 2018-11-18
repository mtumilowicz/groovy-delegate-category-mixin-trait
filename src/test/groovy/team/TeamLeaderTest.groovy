package team

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-18.
 */
class TeamLeaderTest extends Specification {
    
    def "delegate work to senior"() {
        given:
        def teamLeader = new TeamLeader()
        
        when:
        teamLeader.toSenior()
        
        then:
        teamLeader.doTask() == "senior developer is working on the task"
    }

    def "delegate work to junior"() {
        given:
        def teamLeader = new TeamLeader()

        when:
        teamLeader.toJunior()

        then:
        teamLeader.doTask() == "junior developer is working on the task"
    }

    def "there is nobody that can perform the task"() {
        given:
        def teamLeader = new TeamLeader()

        when:
        teamLeader.doTask()
        
        then:
        thrown(IllegalStateException)
    }
    
    def "team leader implements Worker"() {
        expect:
        new TeamLeader() instanceof Worker
    }
}
