package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.kauailabs.navx.frc.AHRS;
import com.team1389.y2016.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurntableControl {
	boolean zeroing;
	CANTalon turn;
	AHRS imu;
	AnalogGyro gyro;
	double reconciled;
	double setpoint;
	boolean setting;
	double maxSpeed = .5;

	public TurntableControl(CANTalon turn, AHRS imu, AnalogGyro gyro) {
		this.turn = turn;
		this.imu = imu;
		this.gyro = gyro;
	}

	public Command teleopTurntable(InputDevice joy) {
		return new Command() {
			@Override
			public boolean execute() {
				reconciled = gyro.getAngle() + imu.getAngle();
				if (reconciled < -180)
					reconciled += 360;
				if (reconciled > 180)
					reconciled -= 360;
				SmartDashboard.putBoolean("clear", isClear());
				SmartDashboard.putNumber("gyro", -gyro.getAngle());
				SmartDashboard.putNumber("reconciled", reconciled);
				SmartDashboard.putNumber("imu", imu.getAngle());
				if (joy.getButton(10).isTriggered() || joy.getButton(1).isTriggered()
						|| joy.getButton(3).isTriggered()) {
					zeroing = true;
					setting=false;
				}
				if (joy.getButton(8).isTriggered()) {
					setting=false;
					gyro.calibrate();
					gyro.reset();
					imu.reset();
					zeroing = false;
				}
				if (Math.abs(joy.getAxis(4).read()) > .1) {
					zeroing = false;
					setting=false;
				}
				if (zeroing&&!setting) {
					if (Math.abs(reconciled) < 1) {
						zeroing = false;
					} else {
						double setSpeed = Math.abs(reconciled) / 30;
						if (setSpeed > maxSpeed)
							setSpeed = maxSpeed;
						setSpeed *= Math.signum(reconciled);
						turn.set(setSpeed);
					}
				} else if(!setting){
					turn.changeControlMode(TalonControlMode.PercentVbus);
					if (Math.abs(reconciled) >= 90) {
						if (Math.signum(joy.getAxis(4).read()) != -Math.signum(reconciled)) {
							turn.set(joy.getAxis(4).read() / 4);
						} else {
							turn.set(0);
						}
					} else {
						turn.set(joy.getAxis(4).read() / 4);
					}
				}
				return false;
			}
		};
	}

	public Command moveAngle(double angle) {
		return new Command() {
			double target = reconciled + angle;

			@Override
			public boolean execute() {
				setting = true;
				while (setting&&Math.abs(reconciled-target) > 3) {
					reconciled = gyro.getAngle() + imu.getAngle();
					if (reconciled < -180)
						reconciled += 360;
					if (reconciled > 180)
						reconciled -= 360;
					double setSpeed = Math.abs(reconciled-target) / 70;
					if (setSpeed > maxSpeed)
						setSpeed = maxSpeed;
					setSpeed *=Math.signum(reconciled-target);
					turn.set(setSpeed);
				}
				setting=false;
				return true;
			}

		};
	}

	public boolean isClear() {
		return Math.abs(reconciled) < 5;
	}

}