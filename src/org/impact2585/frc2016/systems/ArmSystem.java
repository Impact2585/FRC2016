package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * The system for the robot's arm
 */
public class ArmSystem implements RobotSystem, Runnable{
	
	private Environment env;
	private InputMethod input;
	private SpeedController topArm;
	private SpeedController bottomArm;
	public static final double SPEED_MULTIPLIER = 0.7;
	
	

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;
		input = env.getInput();
		topArm = new Talon(RobotMap.TOP_ARM);
		bottomArm = new Talon(RobotMap.BOTTOM_ARM);
		
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
		double backArmForwardValue = input.backArmForwardValue();
		double backArmBackwardValue = input.backArmBackwardValue();
		
		if (input.topArmForward() && !input.topArmBackward()) {
			setTopArmSpeed(0.5);
		} else if(input.topArmBackward() && !input.topArmForward()) {
			setTopArmSpeed(-0.5);
		} else {
			setTopArmSpeed(0);
		}
		
		if(backArmForwardValue > 0 && backArmBackwardValue == 0) {
			setBottomArmSpeed(backArmForwardValue * SPEED_MULTIPLIER);
		} else if (backArmBackwardValue > 0 && backArmForwardValue == 0) {
			setBottomArmSpeed(-backArmBackwardValue * SPEED_MULTIPLIER);
		} else {
			setBottomArmSpeed(0);
		}
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
