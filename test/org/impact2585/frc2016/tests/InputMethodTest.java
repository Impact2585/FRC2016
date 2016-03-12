package org.impact2585.frc2016.tests;

import org.impact2585.frc2016.input.InputMethod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests InputMethod
 */
public class InputMethodTest {
	private TestInput input;
	
	private double forwardMovement;
	private double rotationValue;
	private boolean invert;
	
	private double analogTopArm;
	private boolean digitalTopArmForward;
	private boolean digitalTopArmBackward;
	
	private double bottomArmForward;
	private double bottomArmBackwards;
	private double reversibleBottomArmValue;
	
	private boolean intake;
	private boolean outake;
	
	private double analogMoveIntakeTowardsBot;
	private double analogMoveIntakeAwayFromBot;
	private boolean digitalMoveIntakeTowardsBot;
	private boolean digitalMoveIntakeAwayFromBot;
	
	/**
	 * sets up the test
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		invert = false;
		digitalTopArmForward = false;
		digitalTopArmBackward = false;
		outake = false;
		intake = false;
		digitalMoveIntakeAwayFromBot = false;
		digitalMoveIntakeAwayFromBot = false;
	}

	/**
	 * test cases for the TestInput
	 */
	@Test
	public void test() {
		//tests the drivetrain
		forwardMovement = 1;
		Assert.assertTrue(input.forwardMovement() == 1);
		
		//tests moving the top arm with analog input
		analogTopArm = 1;
		Assert.assertTrue(input.moveTopArm() == 1);
		
		//tests moving the top arm with digital input
		digitalTopArmForward = true;
		analogTopArm = 0;
		Assert.assertTrue(input.moveTopArm() == 1);
		
		//tests if the top arm doesn't move if Harris presses both digital input buttons
		digitalTopArmBackward = true;
		Assert.assertTrue(input.moveTopArm() == 0);
		
		//tests if the top arm can move backwards with digital input
		digitalTopArmForward = false;
		Assert.assertTrue(input.moveTopArm() == -1);
		
		//tests that the bottom arm moves with two analog buttons
		bottomArmForward = 1;
		Assert.assertTrue(input.moveBottomArm() == 1);
		
		//tests if the bottom arm doesn't move if Harris presses both triggers
		bottomArmBackwards = 1;
		Assert.assertTrue(input.moveBottomArm() == 0);
		
		//tests if the bottom arm moves backwards
		bottomArmForward = 0;
		Assert.assertTrue(input.moveBottomArm() == -1);
		
		//tests if the bottom arm moves with a reversible analog input
		bottomArmBackwards = 0;
		reversibleBottomArmValue = -1;
		Assert.assertTrue(input.moveBottomArm() == -1);
		
		//tests if the input arms move away with analog input
		analogMoveIntakeAwayFromBot = 1;
		Assert.assertTrue(input.moveIntake() == 1);
		
		//tests if the input arms move towards the bot with analog input
		analogMoveIntakeAwayFromBot = 0;
		analogMoveIntakeTowardsBot = 1;
		Assert.assertTrue(input.moveIntake() == -1);
		
		//tests if the input arms don't move if Harris presses both triggers
		analogMoveIntakeAwayFromBot = 1;
		Assert.assertTrue(input.moveIntake() == 0);
		
		//tests if the input arms don't move if one of the analog inputs are negative
		analogMoveIntakeAwayFromBot = -1;
		analogMoveIntakeTowardsBot = 0;
		Assert.assertTrue(input.moveIntake() == 0);
		
		//tests if the input arms move towards the bot with digital input
		analogMoveIntakeAwayFromBot = 0;
		digitalMoveIntakeTowardsBot = true;
		Assert.assertTrue(input.moveIntake() == -1);
		
		//tests if the input arms move away from the bot with digital input
		digitalMoveIntakeTowardsBot = false;
		digitalMoveIntakeAwayFromBot = true;
		Assert.assertTrue(input.moveIntake() == 1);
		
		//tests if the input arms don't move if Harris presses both buttons
		digitalMoveIntakeTowardsBot = true;
		analogMoveIntakeAwayFromBot = 0;
		Assert.assertTrue(input.moveIntake() == 0);
	}
	
	/**
	 * testable version of TestInput
	 */
	private class TestInput extends InputMethod{

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#forwardMovement()
		 */
		@Override
		public double forwardMovement() {
			return forwardMovement;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
		 */
		@Override
		public double rotationValue() {
			return rotationValue;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#invert()
		 */
		@Override
		public boolean invert() {
			return invert;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogTopArm()
		 */
		@Override
		public double analogTopArm() {
			return analogTopArm;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalTopArmForward()
		 */
		@Override
		public boolean digitalTopArmForward() {
			return digitalTopArmForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalTopArmBackward()
		 */
		@Override
		public boolean digitalTopArmBackward() {
			return digitalTopArmBackward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#bottomArmForwardValue()
		 */
		@Override
		public double bottomArmForwardValue() {
			return bottomArmForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#bottomArmBackwardValue()
		 */
		@Override
		public double bottomArmBackwardValue() {
			return bottomArmBackwards;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#reversibleBottomArmValue()
		 */
		@Override
		public double reversibleBottomArmValue() {
			return reversibleBottomArmValue;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#outake()
		 */
		@Override
		public boolean outake() {
			return outake;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#intake()
		 */
		@Override
		public boolean intake() {
			return intake;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeTowardsBot()
		 */
		@Override
		public double analogMoveIntakeTowardsBot() {
			return analogMoveIntakeTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeAwayFromBot()
		 */
		@Override
		public double analogMoveIntakeAwayFromBot() {
			return analogMoveIntakeAwayFromBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalMoveIntakeTowardsBot()
		 */
		@Override
		public boolean digitalMoveIntakeTowardsBot() {
			return digitalMoveIntakeTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalMoveIntakeAwayFromBot()
		 */
		@Override
		public boolean digitalMoveIntakeAwayFromBot() {
			return digitalMoveIntakeAwayFromBot;
		}
			
	}

}
