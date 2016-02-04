package org.impact2585.frc2016.tests;


import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.systems.WheelSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * test for the wheel system
 */
public class WheelSystemTest {
	private TestWheelSystem drivetrain;
	private InputTest input;
	private double driveForward;
	private double rotate;
	
	
	/**
	 * Initializes the test wheel system and input
	 */
	@Before
	public void setUp() {
		drivetrain = new TestWheelSystem();
		input = new InputTest();
		drivetrain.setInput(input);
		driveForward = 0;
		rotate = 0;
	}

	/**
	 * Tests initial state, deadzone, rotating, and driving forward
	 */
	@Test
	public void test() {
				
		//tests if the robot isn't moving at the start
		drivetrain.run();
		Assert.assertTrue(driveForward == 0 && rotate == 0);
		
		//tests deadzone
		driveForward = 0.14;
		rotate = 0.14;
		drivetrain.run();
		Assert.assertTrue(driveForward == 0 && rotate == 0);
		
		//tests forward driving
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(0.5 == driveForward && rotate == 0);
		
		//tests turning
		rotate = 1;
		driveForward = 0;
		drivetrain.run();
		Assert.assertTrue(driveForward == 0 && rotate == 1);
		
		//tests turning and driving simultaneously
		rotate = 1;
		driveForward = 0.7;
		Assert.assertTrue(driveForward == 10 && rotate == 1);
	}
	
	/**
	 * testable version of wheelsystem
	 */
	private class TestWheelSystem extends WheelSystem {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#setInput(org.impact2585.frc2016.input.InputMethod)
		 */
		@Override
		protected synchronized void setInput(InputMethod controller) {
			super.setInput(controller);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#getInput()
		 */
		@Override
		public InputMethod getInput() {
			return super.getInput();
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#drive(double, double)
		 */
		@Override
		public void drive(double driveStraight, double currentRotate) {
			driveForward = driveStraight;
			rotate = currentRotate;
		}
		
	}
	
	/**
	 * input version for testing
	 */
	private class InputTest implements InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#forwardMovement()
		 */
		@Override
		public double forwardMovement() {
			return driveForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
		 */
		@Override
		public double rotationValue() {
			return rotate;
		}
		
	}
}

