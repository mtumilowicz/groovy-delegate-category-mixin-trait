package team
/**
 * Created by mtumilowicz on 2018-11-18.
 */
class TeamLeader {
    
    @Delegate Worker worker = new Nobody()
    
    void toSenior() {
        worker = new SeniorDeveloper()
    }
    
    void toJunior() {
        worker = new JuniorDeveloper()
    }
}
