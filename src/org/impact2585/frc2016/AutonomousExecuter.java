package org.impact2585.frc2016;

import org.impact2585.frc2016.systems.WheelSystem;
import org.impact2585.lib2585.Executer;

/**
 * Executer for autonomous mode
 */
public enum AutonomousExecuter implements Executer, Initializable{
	
	/**
	 *This version of auton drives forward
	 */
	BASIC;
	
	private Environment env;
	private WheelSystem drivetrain;
	private long initialTime;

	
	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;
		drivetrain = env.getWheelSystem();
		initialTime = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		switch(this) {
		case BASIC:
			if(System.currentTimeMillis() <= initialTime + 1000)
				drivetrain.drive(1.0, 0);
			else
				drivetrain.drive(0, 0);
			break;
		default:
			drivetrain.drive(0, 0);
		}
	}
}
