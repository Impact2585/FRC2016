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
	private boolean invert;
	
	
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
		invert = false;
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
		drivetrain.run();
		Assert.assertTrue(driveForward == 0.7 && rotate == 1);
		
		//tests invert
		invert = true;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(driveForward == -0.5 && rotate == 0.5);
		
		//tests if drivetrain continues to be inverted
		invert = false;
		rotate = -0.6;
		driveForward = 0.6;
		drivetrain.run();
		Assert.assertTrue(driveForward == -0.6 && rotate == 0.6);
		
		//tests if it inverts to the original position
		invert = true;
		rotate = 0.7;
		driveForward = 0.7;
		drivetrain.run();
		Assert.assertTrue(driveForward == 0.7 && rotate == 0.7);
		
		//tests if invert continues again
		invert = false;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(driveForward == 0.5 && rotate == -0.5);
		
		//tests if it inverts again
		invert = true;
		rotate = -0.8;
		driveForward = 0.8;
		drivetrain.run();
		Assert.assertTrue(driveForward == -0.8 && rotate == 0.8);
		
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
		 * @see org.impact2585.frc2016.input.InputMethod#getInvert()
		 */
		@Override
		public boolean invert() {
			return invert;
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

