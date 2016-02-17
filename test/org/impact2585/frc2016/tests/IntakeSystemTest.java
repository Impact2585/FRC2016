package org.impact2585.frc2016.tests;



import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.systems.IntakeSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * the test for the intake system
 */
public class IntakeSystemTest {
	private InputMethod input;
	private boolean moveArmAwayFromBot;
	private boolean moveArmTowardsBot;
	private boolean moveWheelsForward;
	private boolean moveWheelsBackwards;
	private TestIntakeSystem ioshooter;
	private double wheelSpeed;
	private double armSpeed;
	
	/**
	 * sets up the intake system test
	 */
	@Before
	public void setUp() {
		ioshooter = new TestIntakeSystem();
		input = new InputTest();
		ioshooter.setInput(input);
		moveArmAwayFromBot = false;
		moveArmTowardsBot = false;
		moveWheelsBackwards = false;
		moveWheelsForward = false;
		
	}

	/**
	 * the tests to run for the intake system
	 */
	@Test
	public void test() {
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the shooter shoots
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == 0);
		
		//tests if the intake intakes
		moveWheelsBackwards = true;
		moveWheelsForward = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == -1 && armSpeed == 0);
		
		//tests if the wheels don't move if the person pushes both buttons
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the arms move forward
		moveWheelsForward = false;
		moveWheelsBackwards = false;
		moveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -0.3);
		
		//tests if the arms move backwards
		moveArmAwayFromBot = false;
		moveArmTowardsBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0.3);
		
		//tests if the arms don't move if the users pushes both buttons
		moveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the arms and the wheels can both move simultaneously
		moveArmTowardsBot = false;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == -0.3);
		
		
	}
	
	/**
	 * A testable intake system
	 */
	private class TestIntakeSystem extends IntakeSystem{

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#spinWheels(double)
		 */
		@Override
		public void spinWheels(double speed) {
			wheelSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#moveArms(double)
		 */
		@Override
		public void moveArms(double speed) {
			armSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setInput(org.impact2585.frc2016.input.InputMethod)
		 */
		@Override
		protected void setInput(InputMethod newInput) {
			super.setInput(newInput);
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
			return 0;
			
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#getInvert()
		 */
		@Override
		public boolean invert() {
			return false;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#rotationValue()
		 */
		@Override
		public double rotationValue() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#topArmForward()
		 */
		@Override
		public boolean topArmForward() {
			return false;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#topArmBackward()
		 */
		@Override
		public boolean topArmBackward() {
			return false;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#backArmForwardValue()
		 */
		@Override
		public double backArmForwardValue() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#backArmBackwardValue()
		 */
		@Override
		public double backArmBackwardValue() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#outtake()
		 */
		@Override
		public boolean outtake() {
			return moveWheelsBackwards;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#intake()
		 */
		@Override
		public boolean intake() {
			return moveWheelsForward;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#moveTowardsBot()
		 */
		@Override
		public boolean moveTowardsBot() {
			return moveArmTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#moveAwayFromBot()
		 */
		@Override
		public boolean moveAwayFromBot() {
			return moveArmAwayFromBot;
		}
		
	}
	
}
