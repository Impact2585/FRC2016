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
	private boolean digitalMoveArmAwayFromBot;
	private boolean digitalMoveArmTowardsBot;
	private double analogMoveArmAwayFromBot;
	private double analogMoveArmTowardsBot;
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
		digitalMoveArmAwayFromBot = false;
		digitalMoveArmTowardsBot = false;
		moveWheelsBackwards = false;
		moveWheelsForward = false;
		analogMoveArmAwayFromBot = 0;
		analogMoveArmTowardsBot = 0;
		
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
		digitalMoveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//tests if the arms move backwards
		digitalMoveArmAwayFromBot = false;
		digitalMoveArmTowardsBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == IntakeSystem.ARM_SPEED);
		
		//tests if the arms don't move if the users pushes both buttons
		digitalMoveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the arms and the wheels can both move simultaneously
		digitalMoveArmTowardsBot = false;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//turn off the digital input
		moveWheelsForward = false;
		digitalMoveArmAwayFromBot = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if arms move away with the analog input
		analogMoveArmAwayFromBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//tests if the arms move towards the bot with analog input
		analogMoveArmTowardsBot = 1;
		analogMoveArmAwayFromBot = 0;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == IntakeSystem.ARM_SPEED);
		
		//tests if the arms don't move at all if Harris pushes both triggers
		analogMoveArmAwayFromBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the input arms can move while the input runs
		analogMoveArmAwayFromBot = 0;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == IntakeSystem.ARM_SPEED);
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
	private class InputTest extends InputMethod {

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#outake()
		 */
		@Override
		public boolean outake() {
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
		 * @see org.impact2585.frc2016.input.InputMethod#digitalMoveIntakeTowardsBot()
		 */
		@Override
		public boolean digitalMoveIntakeTowardsBot() {
			return digitalMoveArmTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#digitalMoveIntakeAwayFromBot()
		 */
		@Override
		public boolean digitalMoveIntakeAwayFromBot() {
			return digitalMoveArmAwayFromBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeTowardsBot()
		 */
		@Override
		public double analogMoveIntakeTowardsBot() {
			return analogMoveArmTowardsBot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#analogMoveIntakeAwayFromBot()
		 */
		@Override
		public double analogMoveIntakeAwayFromBot() {
			return analogMoveArmAwayFromBot;
		}
				
	}
	
}
