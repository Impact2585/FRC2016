package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * The system for the robot's arm
 */
public class ArmSystem implements RobotSystem, Runnable{
	
	private Environment env;
	private InputMethod input;
	private SpeedController topArm;
	private SpeedController bottomArm;
	public static final double BOTTOM_ARM_SPEED = 0.5;
	public static final double TOP_ARM_SPEED = 0.7;
	
	

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;
		input = env.getInput();
		topArm = new Spark(RobotMap.TOP_ARM);
		bottomArm = new Spark(RobotMap.BOTTOM_ARM);
		
	}
	
	
	/**sets the top arm's motor to speed
	 * @param speed the speed to set the motor to
	 */
	public void setTopArmSpeed (double speed) {
		topArm.set(speed);
	}
	
	/**sets the bottom arm's motor to speed
	 * @param speed the speed to set the motor to
	 */
	public void setBottomArmSpeed(double speed) {
		bottomArm.set(speed);
	}
	
	/**sets the input method of the arm system
	 * @param newInput the input to set the system to
	 */
	protected void setInput(InputMethod newInput) {
		input = newInput;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		setTopArmSpeed(input.moveTopArm() * TOP_ARM_SPEED);
		setBottomArmSpeed(input.moveBottomArm() *BOTTOM_ARM_SPEED);

	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if(bottomArm instanceof SensorBase) {
			SensorBase motor = (SensorBase) bottomArm;
			motor.free();
		}
		
		if(topArm instanceof SensorBase) {
			SensorBase motor = (SensorBase) topArm;
			motor.free();
		}
	}


}
