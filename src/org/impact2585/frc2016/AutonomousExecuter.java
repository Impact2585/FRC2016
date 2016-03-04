package org.impact2585.frc2016;

import org.impact2585.frc2016.systems.WheelSystem;
import org.impact2585.lib2585.Executer;

/**
 * Executer for autonomous mode
 */
public enum AutonomousExecuter implements Executer, Initializable{
	
	/**
	 *This version of auton does nothing
	 */
	NONE, 
	
	/**
	 * This version of auton drives forward for 1.5 seconds
	 */
	BASIC, 
	
	/**
	 * This version of auton waits for 10 seconds, and then drives forward for 1.5 seconds
	 */
	DELAYED;
	
	private Environment env;
	private WheelSystem drivetrain;
	private long initialTime;
	public static final int BASIC_DURATION = 1500;
	public static final int DELAYED_DURATION = 1500;
	public static final int DELAYED_WAIT = 10000;

	
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
		case NONE:
			drivetrain.drive(0, 0);
			break;
		case BASIC:
			if(System.currentTimeMillis() <= initialTime + BASIC_DURATION)
				drivetrain.drive(1.0, 0);
			else
				drivetrain.drive(0, 0);
			break;
		case DELAYED:
			if(System.currentTimeMillis() >= initialTime + DELAYED_WAIT && System.currentTimeMillis() <= initialTime + DELAYED_DURATION + DELAYED_WAIT)
				drivetrain.drive(1.0, 0);
			else
				drivetrain.drive(0, 0);
			break;
		default:
			drivetrain.drive(0, 0);
		}
	}
}
