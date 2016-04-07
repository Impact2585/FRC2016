package org.impact2585.frc2016;

import org.impact2585.frc2016.systems.IntakeSystem;
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
	DELAYED,
	
	/**
	 * This version of auton drives forward for 5 meters then shoots
	 */
	SHOOT;
	
	private Environment env;
	private WheelSystem drivetrain;
	private long initialTime;
	private IntakeSystem ioshooter;
	private boolean startingShoot;
	private double distanceDrivenForward;
	private long stallingStartTime;
	private boolean startStall;
	public static final int BASIC_DURATION = 1500;
	public static final int DELAYED_DURATION = 1500;
	public static final int DELAYED_WAIT = 10000;
	public static final int ANGLE = 500;

	
	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;
		drivetrain = env.getWheelSystem();
		initialTime = System.currentTimeMillis();
		ioshooter = env.getIntakeSystem();
		startingShoot = true;
		startStall = false;
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
		case SHOOT:
			if(drivetrain.driveDistanceForward(5)) {
				if(startingShoot) {
					ioshooter.setShoot(true);
					startingShoot = false;
				}
				ioshooter.spinWheels(127);
				ioshooter.shoot();
			}
			
			if(handsUpDontShoot()) {
				ioshooter.rotateAngle(ANGLE, true);;
			}
			break;
		default:
			drivetrain.drive(0, 0);
		}
	}
	
	/**
	 * @return true if the drivetrain can't move for more than half a second, false if otherwise
	 */
	public boolean handsUpDontShoot() {
		if(isStalling()) {
			if(!startStall) {
				stallingStartTime = System.currentTimeMillis();
				startStall = true;
			}
			if(System.currentTimeMillis() - stallingStartTime >= 500) {
				return true;
			}
			return false;
		} else {
			startStall = false;
			return false;
		}
	}
	
	/**
	 * @return true if the drivetrain is not moving false if otherwise
	 */
	public boolean isStalling() {
		return distanceDrivenForward == drivetrain.getDistanceDrivenForward();
	}
}
