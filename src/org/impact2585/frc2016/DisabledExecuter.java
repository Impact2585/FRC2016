package org.impact2585.frc2016;

import org.impact2585.lib2585.Executer;

/**
 * executer for when the robot is disabled
 */
public class DisabledExecuter implements Executer{
	private Environment environ;

	/**Constructer that takes in an environment parameter
	 * @param environment is the environment that the robot is running with
	 */
	public DisabledExecuter(Environment environment) {
		environ = environment;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		environ.getWheelSystem().drive(0, 0);
	}

}
