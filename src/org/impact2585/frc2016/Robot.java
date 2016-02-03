package org.impact2585.frc2016;


import org.impact2585.lib2585.ExecuterBasedRobot;
import org.impact2585.frc2016.Environment;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends ExecuterBasedRobot {

	private static final long serialVersionUID = -6071317869900252678L;
	private Environment environ;

	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	environ = new Environment(this);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    /**
     * @returns the environment
     */
    public synchronized Environment getEnvironment() {
    	return this.environ;
    }
    
    /**Sets environment
     * @param environment consists of the robot's systems
     */
    public synchronized void setEnvironment(Environment environment) {
    	this.environ = environment;
    }
    
    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.RobotBase#free()
     */
    @Override
    public void free(){
    	super.free();
    	environ.destroy();
    }
}
    

