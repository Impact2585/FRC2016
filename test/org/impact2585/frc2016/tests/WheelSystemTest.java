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
	private double currentRampForward;
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
		currentRampForward = 0;
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
		Assert.assertTrue(currentRampForward == 0 && rotate == 0);
		
		//tests deadzone
		driveForward = 0.14;
		rotate = 0.14;
		drivetrain.run();
		Assert.assertTrue(currentRampForward == 0 && rotate == 0);
		
		//tests forward driving
		driveForward = -0.5;
		drivetrain.run();
		Assert.assertTrue(-0.25 == currentRampForward && rotate == 0);
		
		//tests turning and if currentRampForward immediately goes to 0 if the input is 0
		rotate = 1;
		driveForward = 0;
		drivetrain.run();
		Assert.assertTrue(currentRampForward == 0 && rotate == 1);
		
		//tests turning and driving simultaneously
		rotate = 1;
		driveForward = 0.5;
		drivetrain.setCurrentRampForward(0);
		drivetrain.run();
		Assert.assertTrue(currentRampForward == 0.25 && rotate == 1);
		
		//tests invert
		invert = true;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(currentRampForward == -0.125 && rotate == -0.5);
		
		
		//tests if it does not invert if the button is still pressed
		invert = true;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(currentRampForward == -0.3125 && rotate == -0.5);
		
		//tests if drivetrain continues to be inverted
		invert = false;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(currentRampForward ==  -0.40625 && rotate == -0.5);
		
		//tests if it inverts to the original position
		invert = true;
		rotate = 0.7;
		driveForward = 0.5;
		drivetrain.run();
		Assert.assertTrue(currentRampForward == 0.046875 && rotate == 0.7);
		
		// see if movement and rotation go back to 0 again
		rotate = driveForward = 0;
		drivetrain.run();
		Assert.assertTrue(currentRampForward == 0 && rotate == 0);
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
		public void drive(double movement, double currentRotate) {
			currentRampForward = movement;
			rotate = currentRotate;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.WheelSystem#setCurrentRampForward(double)
		 */
		@Override
		public void setCurrentRampForward(double rampForward) {
			super.setCurrentRampForward(rampForward);
			currentRampForward = rampForward;
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

