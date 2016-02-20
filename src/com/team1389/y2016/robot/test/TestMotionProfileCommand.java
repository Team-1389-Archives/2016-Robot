package com.team1389.y2016.robot.test;

import org.strongback.command.Command;

import com.team1389.base.util.control.ConstantAccellerationMotionProfile;
import com.team1389.base.util.control.MotionProfile;
import com.team1389.base.webserver.chart.Chart;
import com.team1389.base.webserver.chart.DataPoint;
import com.team1389.base.webserver.chart.WebChartManager;

public class TestMotionProfileCommand extends Command{
	
	MotionProfile profile;
	Chart position;
	Chart velocity;
	Chart acceleration;
	
	public TestMotionProfileCommand() {
		position = new Chart(1, 1, "position", "time");
		velocity = new Chart(1, 1, "velocity", "time");
		acceleration = new Chart(1, 1, "acceleration", "time");
		
		WebChartManager.getInstance().addChart("position", position);
		WebChartManager.getInstance().addChart("velocity", velocity);
		WebChartManager.getInstance().addChart("acceleration", acceleration);
		
	}
	
	@Override
	public void initialize() {
		MotionProfile profile = new ConstantAccellerationMotionProfile(15, 3, 1.2);
		double time = profile.getDuration();
		for (int i = 0; i < 100; i += 1){
			double t = time / 100 * i;
			position.addPoint(new DataPoint(t,profile.getPosition(t)));
			velocity.addPoint(new DataPoint(t,profile.getVelocity(t)));
			acceleration.addPoint(new DataPoint(t,profile.getAcceleration(t)));
		}
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

}
