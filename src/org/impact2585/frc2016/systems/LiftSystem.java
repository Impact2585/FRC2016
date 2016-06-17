package org.impact2585.frc2016.systems;


import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.lib2585.BooleanInputProcessor;

import edu.wpi.first.wpilibj.Spark;

/**
 * System for the lift
 */
public class LiftSystem implements RobotSystem, Runnable{

	private InputMethod input;
	private Spark winch;
	private Spark lift;
	private BooleanInputProcessor processor;
	public static final double SPEED_MULTIPLIER = 0.3;
	
	public LiftSystem() {
		processor = new BooleanInputProcessor() {
			
			/* (non-Javadoc)
			 * @see org.impact2585.lib2585.BooleanInputProcessor#process(java.lang.Boolean[])
			 */
			@Override
			public double process(Boolean... input) {
				if(input[0] && !input[1]) {
					return 1;
				} else if(input[1] && !input[0]) {
					return -1;
				} else {
					return 0;
				}
			}
		};
	}
	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		input = environ.getInput();
		winch = new Spark(RobotMap.WINCH);
		lift = new Spark(RobotMap.LIFT);
	}

	/**
	 * @return the input
	 */
	public InputMethod getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(InputMethod input) {
		this.input = input;
	}

	/**
	 * @param speed the speed of the winch motor
	 */
	public void setWinchMotorSpeed(double speed) {
		winch.set(speed);
	}
	
	/**
	 * @param speed the speed to set to the lift motor
	 */
	public void setLiftMotorSpeed(double speed) {
		lift.set(speed);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		setLiftMotorSpeed(SPEED_MULTIPLIER * processor.process(input.liftUp(), input.liftDown()));
		setWinchMotorSpeed(SPEED_MULTIPLIER * processor.process(input.windWinch(), input.unwindWinch()));
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		lift.free();
		winch.free();
	}

}
