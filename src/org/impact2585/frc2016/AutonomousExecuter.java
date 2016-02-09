package org.impact2585.frc2016;

import org.impact2585.frc2016.systems.WheelSystem;
import org.impact2585.lib2585.Executer;

/**
 * Executer for autonomous mode
 */
public class AutonomousExecuter implements Executer{
	private Environment env;
	private WheelSystem drivetrain;
	private long initialTime;
	/**Constructor for auton 
	 * @param environ the environment of the robot
	 */
	public AutonomousExecuter(Environment environ) {
		env = environ;
		drivetrain = env.getWheelSystem();
		initialTime = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		if(System.currentTimeMillis() <= initialTime + 5000)
			drivetrain.drive(1.0, 0);
	}


}
