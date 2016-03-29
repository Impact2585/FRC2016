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
	private boolean toggleArmSpeed;
	private boolean isLeftLimitSwitchClosed;
	private boolean isRightLimitSwitchClosed;
	private boolean isShootingSwitchClosed;
	private boolean ignoreIntakeLimitSwitch;
	private boolean shoot;
	private boolean turnLeverReverse;
	private double leverSpeed;
	
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
		toggleArmSpeed = false;
		isLeftLimitSwitchClosed = false;
		isRightLimitSwitchClosed = false;
		ignoreIntakeLimitSwitch = false;
		turnLeverReverse = false;
		shoot = false;
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
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == IntakeSystem.ARM_SPEED);
		
		//tests if the arms move backwards
		digitalMoveArmAwayFromBot = false;
		digitalMoveArmTowardsBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//tests if the arms don't move if the users pushes both buttons
		digitalMoveArmAwayFromBot = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the arms and the wheels can both move simultaneously
		digitalMoveArmTowardsBot = false;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == IntakeSystem.ARM_SPEED);
		
		//turn off the digital input
		moveWheelsForward = false;
		digitalMoveArmAwayFromBot = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if arms move away with the analog input
		analogMoveArmAwayFromBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == IntakeSystem.ARM_SPEED);
		
		//tests if the arms move towards the bot with analog input
		analogMoveArmTowardsBot = 1;
		analogMoveArmAwayFromBot = 0;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//tests if the arms don't move at all if Harris pushes both triggers
		analogMoveArmAwayFromBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the input arms can move while the input runs
		analogMoveArmAwayFromBot = 0;
		moveWheelsForward = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//tests if the arm speed multiplier can be toggled off
		toggleArmSpeed = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == -1);
		
		//tests if the arm speed multiplier doesn't toggle if Harris keeps pushing the button
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == -1);
		
		//tests if the arm speed multiplier toggles on
		moveWheelsForward = false;
		toggleArmSpeed = false;
		ioshooter.run();
		toggleArmSpeed = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -IntakeSystem.ARM_SPEED);
		
		//tests if the arm speed multiplier toggles off for digital input
		toggleArmSpeed = false;
		ioshooter.run();
		analogMoveArmTowardsBot = 0;
		digitalMoveArmTowardsBot = true;
		toggleArmSpeed = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -1);
		
		//tests if the intake arms do not move forward if both limit switches are pressed
		isLeftLimitSwitchClosed = true;
		isRightLimitSwitchClosed = true;
		digitalMoveArmTowardsBot = false;
		analogMoveArmTowardsBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 0);
		
		//tests if the intake arms do not move forward if only one limit switch is pressed and if the intake still works
		moveWheelsForward = true;
		isLeftLimitSwitchClosed = false;
		analogMoveArmAwayFromBot = 0;
		analogMoveArmTowardsBot = 1;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 1 && armSpeed == 0);
		
		//tests if the arms can move back if the limit switch is pressed
		analogMoveArmAwayFromBot = 1;
		analogMoveArmTowardsBot = 0;
		moveWheelsForward = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == 1);
		
		//tests if the arms can move forward if the limit switch is no longer pressed
		analogMoveArmTowardsBot = 0.7;
		analogMoveArmAwayFromBot = 0;
		isRightLimitSwitchClosed = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -analogMoveArmTowardsBot);
		
		//tests if the arms can move forward if both limit switches and x button are pressed
		isLeftLimitSwitchClosed = true;
		isRightLimitSwitchClosed = true;
		ignoreIntakeLimitSwitch = true;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -analogMoveArmTowardsBot);
		
		//tests if the arms can move forward if one limit switch and the x button are pressed
		isLeftLimitSwitchClosed = false;
		ioshooter.run();
		Assert.assertTrue(wheelSpeed == 0 && armSpeed == -analogMoveArmTowardsBot);
		
		//tests if the lever can automatically turn forward to shoot then reverse back after B button is pressed
		shoot = true;
		ioshooter.run();
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 1.0);
		ioshooter.setStartTime(System.currentTimeMillis()-750);
		ioshooter.run();
		Assert.assertTrue(leverSpeed == -1.0);
		isShootingSwitchClosed = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0);
		
		//tests if lever will turn in reverse if Y button is pressed
		shoot = false;
		turnLeverReverse = true;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == -0.5 );
		
		//tests if lever will not run if neither B nor Y buttons are pressed
		turnLeverReverse = false;
		ioshooter.run();
		Assert.assertTrue(leverSpeed == 0);
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
		 * @see org.impact2585.frc2016.systems.IntakeSystem#isLeftSwitchClosed()
		 */
		@Override
		public boolean isLeftSwitchClosed() {
			return isLeftLimitSwitchClosed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#isRightSwitchClosed()
		 */
		@Override
		public boolean isRightSwitchClosed() {
			return isRightLimitSwitchClosed;
		}
		
		

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#isShootingSwitchClosed()
		 */
		@Override
		public boolean isShootingSwitchClosed() {
			return isShootingSwitchClosed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setInput(org.impact2585.frc2016.input.InputMethod)
		 */
		@Override
		protected void setInput(InputMethod newInput) {
			super.setInput(newInput);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#spinLever(double)
		 */
		@Override
		public void spinLever(double speed) {
			leverSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.systems.IntakeSystem#setStartTime(long)
		 */
		@Override
		protected void setStartTime(long startTime) {
			super.setStartTime(startTime);
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

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#toggleSpeed()
		 */
		@Override
		public boolean toggleSpeed() {
			return toggleArmSpeed;
		}
		
		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#ignoreIntakeLimitSwitch()
		 */
		@Override
		public boolean ignoreIntakeLimitSwitch(){
			return ignoreIntakeLimitSwitch;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#turnLever()
		 */
		@Override
		public boolean shoot() {
			return shoot;
		}

		/* (non-Javadoc)
		 * @see org.impact2585.frc2016.input.InputMethod#turnLeverReverse()
		 */
		@Override
		public boolean turnLeverReverse(){
			return turnLeverReverse;
		}
		
		
	}
	
}
